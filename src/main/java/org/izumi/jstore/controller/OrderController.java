package org.izumi.jstore.controller;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import io.jmix.core.DataManager;
import io.jmix.core.EntitySerialization;
import io.jmix.core.FetchPlan;
import io.jmix.core.FetchPlans;
import io.jmix.core.SaveContext;
import lombok.RequiredArgsConstructor;
import org.izumi.jstore.dto.BucketDto;
import org.izumi.jstore.dto.CreateOrderDto;
import org.izumi.jstore.dto.ItemDto;
import org.izumi.jstore.entity.Order;
import org.izumi.jstore.entity.OrderItem;
import org.izumi.jstore.entity.Product;
import org.izumi.jstore.entity.Status;
import org.izumi.jstore.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Aiden Izumi (aka Flamesson).
 */
@RequiredArgsConstructor
@RequestMapping("/orders")
@RestController
public class OrderController extends AbstractController {
    private final DataManager dataManager;
    private final EntitySerialization entitySerialization;
    private final FetchPlans fetchPlans;

    @GetMapping
    public ResponseEntity<String> getAll() {
        final FetchPlan fetchPlan = fetchPlan();
        return ofWithoutHeaders(entitySerialization.toJson(
                dataManager.load(Order.class).all().fetchPlan(fetchPlan).list(),
                fetchPlan)
        );
    }

    @GetMapping(params = {"username"})
    public ResponseEntity<String> getAll(@RequestParam String username) {
        final FetchPlan fetchPlan = fetchPlan();
        return ofWithoutHeaders(entitySerialization.toJson(loadAll(username, fetchPlan), fetchPlan));
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody CreateOrderDto order) {
        final User user = userByUsername(order.getUser().getUsername());
        return ofWithoutHeaders(entitySerialization.toJson(toOrder(order.getBucket(), user)));
    }

    @GetMapping(value = "/cancel", params = {"id"})
    public ResponseEntity<String> cancel(@RequestParam String id) {
        cancelOrder(id);
        return ofWithoutHeaders("Ok");
    }

    private FetchPlan fetchPlan() {
        return fetchPlans.builder(Order.class).addFetchPlan(FetchPlan.BASE)
                .add("items", b -> b.addFetchPlan(FetchPlan.BASE)
                        .add("product", b1 -> b1.addFetchPlan(FetchPlan.BASE)
                                .add("images", FetchPlan.BASE)))
                .add("user", FetchPlan.BASE)
                .build();
    }

    private void cancelOrder(String id) {
        final Order order = dataManager.load(Order.class)
                .query("SELECT o FROM Order_ o WHERE o.id = :id")
                .parameter("id", UUID.fromString(id))
                .fetchPlan(fetchPlan())
                .one();
        restoreProductsRemainders(order);

        final SaveContext context = new SaveContext()
                .removing(order)
                .saving(products(order));
        dataManager.save(context);
    }

    private List<Order> loadAll(String username, FetchPlan fetchPlan) {
        return dataManager.load(Order.class)
                .query("SELECT o FROM Order_ o WHERE o.user.username = :username")
                .parameter("username", username)
                .fetchPlan(fetchPlan)
                .list();
    }

    private Order toOrder(BucketDto bucket, User user) {
        final Order order = dataManager.create(Order.class);
        order.setUser(user);
        order.setStatus(Status.FOR_DELIVERY);
        order.setItems(toItems(bucket.getItems(), order));
        order.setPrice(compose(order));
        reduceProductsRemainders(order.getItems());

        final LocalDate now = LocalDate.now();
        order.setDateOfIssue(now);
        order.setDeliveryDate(now.plusDays(5));
        order.setDeliveryAddress("Mock");

        final SaveContext context = new SaveContext()
                .saving(order)
                .saving(order.getItems())
                .saving(products(order));
        return dataManager.save(context).get(order);
    }

    private double compose(Order order) {
        final List<OrderItem> items = order.getItems();
        double sum = 0.0;
        for (OrderItem item : items) {
            sum += (item.getPrice() * item.getAmount());
        }

        return sum;
    }

    private List<OrderItem> toItems(Collection<ItemDto> dtos, Order order) {
        return dtos.stream()
                .map(dto -> {
                    final OrderItem item = dataManager.create(OrderItem.class);
                    final Product product = productById(dto.getProductId());
                    item.setProduct(product);
                    item.setPrice(product.getPrice());
                    item.setAmount(dto.getAmount());
                    item.setOrder(order);

                    return item;
                })
                .toList();
    }

    private void reduceProductsRemainders(Collection<OrderItem> items) {
        for (OrderItem item : items) {
            final Product product = item.getProduct();
            product.setRemainder(product.getRemainder() - item.getAmount());
        }
    }

    private void restoreProductsRemainders(Order order) {
        for (OrderItem item : order.getItems()) {
            final Product product = item.getProduct();
            product.setRemainder(product.getRemainder() + item.getAmount());
        }
    }

    private Collection<Product> products(Order order) {
        return order.getItems().stream().map(OrderItem::getProduct).collect(Collectors.toSet());
    }

    private User userByUsername(String username) {
        return dataManager.load(User.class)
                .query("SELECT u FROM User u WHERE u.username = :username")
                .parameter("username", username)
                .one();
    }

    private Product productById(String id) {
        return dataManager.load(Product.class)
                .query("SELECT p FROM Product p WHERE p.id = :id")
                .parameter("id", UUID.fromString(id))
                .one();
    }
}

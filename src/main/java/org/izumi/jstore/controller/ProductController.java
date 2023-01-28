package org.izumi.jstore.controller;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jmix.core.DataManager;
import io.jmix.core.EntitySerialization;
import lombok.RequiredArgsConstructor;
import org.izumi.jstore.entity.Category;
import org.izumi.jstore.entity.Product;
import org.izumi.jstore.repository.ProductRepository;
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
@RequestMapping("/products")
@RestController
public class ProductController extends AbstractController {
    private static final String ALL_KEYWORD = "все";
    private static final String FETCH_PLAN_NAME = "product-with-details";
    private final ProductRepository productRepository;
    private final EntitySerialization entitySerialization;
    private final DataManager dataManager;
    private final ObjectMapper mapper;

    @GetMapping(value = "/tea", params = {"offset", "limit"})
    public ResponseEntity<String> getTea(@RequestParam int offset, @RequestParam int limit) {
        return of(entitySerialization.toJson(
                productRepository.findAllByCategoryName(FETCH_PLAN_NAME, Category.TEA_NAME, offset / limit, limit)
        ));
    }

    @GetMapping(value = "/tea", params = {"offset", "limit", "collection"})
    public ResponseEntity<String> getTea(@RequestParam int offset,
                                         @RequestParam int limit,
                                         @RequestParam String collection) {
        if (ALL_KEYWORD.equalsIgnoreCase(collection)) {
            return getTea(offset, limit);
        }

        return of(entitySerialization.toJson(productRepository.findAllByCategoryNameAndCollectionName(
                FETCH_PLAN_NAME, Category.TEA_NAME, collection, offset / limit, limit
        )));
    }

    @GetMapping(value = "/tea", params = {"offset", "limit", "minPrice", "maxPrice"})
    public ResponseEntity<String> getTea(@RequestParam int offset,
                                         @RequestParam int limit,
                                         @RequestParam double minPrice,
                                         @RequestParam double maxPrice) {
        return of(entitySerialization.toJson(productRepository.findAll(
                FETCH_PLAN_NAME, Category.TEA_NAME, minPrice, maxPrice, offset / limit, limit
        )));
    }

    @GetMapping(value = "/tea", params = {"offset", "limit", "collection", "minPrice", "maxPrice"})
    public ResponseEntity<String> getTea(@RequestParam int offset,
                                         @RequestParam int limit,
                                         @RequestParam String collection,
                                         @RequestParam double minPrice,
                                         @RequestParam double maxPrice) {
        if (ALL_KEYWORD.equalsIgnoreCase(collection)) {
            return getTea(offset, limit, minPrice, maxPrice);
        }

        return of(entitySerialization.toJson(productRepository.findAll(
                FETCH_PLAN_NAME, Category.TEA_NAME, collection, minPrice, maxPrice, offset / limit, limit
        )));
    }

    @GetMapping(value = "/stuff", params = {"offset", "limit"})
    public ResponseEntity<String> getStuff(@RequestParam int offset, @RequestParam int limit) {
        return of(entitySerialization.toJson(
                productRepository.findAllByCategoryName(FETCH_PLAN_NAME, "Утварь", offset / limit, limit)
        ));
    }

    @GetMapping(value = "/stuff", params = {"offset", "limit", "collection"})
    public ResponseEntity<String> getStuff(@RequestParam int offset,
                                           @RequestParam int limit,
                                           @RequestParam String collection) {
        if (ALL_KEYWORD.equalsIgnoreCase(collection)) {
            return getStuff(offset, limit);
        }

        return of(entitySerialization.toJson(productRepository.findAllByCategoryNameAndCollectionName(
                FETCH_PLAN_NAME, Category.EXTRAS_NAME, collection, offset / limit, limit
        )));
    }

    @PostMapping("/ids")
    public ResponseEntity<String> getAllByIds(@RequestBody String ids) throws JsonProcessingException {
        ids = ids.replaceAll("\"ids\":", "").replaceAll("\\{", "");
        return ofWithoutHeaders(entitySerialization.toJson(dataManager.load(Product.class)
                .query("SELECT p FROM Product p WHERE p.id IN :ids")
                .parameter("ids", Arrays.asList(mapper.readValue(ids, String[].class)))
                .fetchPlan(FETCH_PLAN_NAME)
                .list())
        );
    }

    @GetMapping(value = "/stuff", params = {"offset", "limit", "minPrice", "maxPrice"})
    public ResponseEntity<String> getStuff(@RequestParam int offset,
                                           @RequestParam int limit,
                                           @RequestParam double minPrice,
                                           @RequestParam double maxPrice) {
        return of(entitySerialization.toJson(productRepository.findAll(
                FETCH_PLAN_NAME, Category.EXTRAS_NAME, minPrice, maxPrice, offset / limit, limit
        )));
    }

    @GetMapping(value = "/stuff", params = {"offset", "limit", "collection", "minPrice", "maxPrice"})
    public ResponseEntity<String> getStuff(@RequestParam int offset,
                                           @RequestParam int limit,
                                           @RequestParam String collection,
                                           @RequestParam double minPrice,
                                           @RequestParam double maxPrice) {
        if (ALL_KEYWORD.equalsIgnoreCase(collection)) {
            return getStuff(offset, limit, minPrice, maxPrice);
        }

        return of(entitySerialization.toJson(productRepository.findAll(
                FETCH_PLAN_NAME, Category.EXTRAS_NAME, collection, minPrice, maxPrice, offset / limit, limit
        )));
    }

    @GetMapping(value = "/tea/minPrice")
    public ResponseEntity<Double> getMinTeaPrice() {
        return of(dataManager.load(Product.class)
                .query("SELECT p FROM Product p " +
                        "INNER JOIN ProductInCategory pic ON pic MEMBER OF p.categories " +
                        "INNER JOIN Category c ON pic.category = c " +
                        "WHERE c.name = :categoryName " +
                        "ORDER BY p.price ASC")
                .parameter("categoryName", Category.TEA_NAME)
                .maxResults(1)
                .one()
                .getPrice());
    }

    @GetMapping(value = "/tea/maxPrice")
    public ResponseEntity<Double> getMaxTeaPrice() {
        return of(dataManager.load(Product.class)
                .query("SELECT p FROM Product p " +
                        "INNER JOIN ProductInCategory pic ON pic MEMBER OF p.categories " +
                        "INNER JOIN Category c ON pic.category = c " +
                        "WHERE c.name = :categoryName " +
                        "ORDER BY p.price DESC")
                .parameter("categoryName", Category.TEA_NAME)
                .maxResults(1)
                .one()
                .getPrice());
    }

    @GetMapping(value = "/stuff/minPrice")
    public ResponseEntity<Double> getMinStuffPrice() {
        return of(dataManager.load(Product.class)
                .query("SELECT p FROM Product p " +
                        "INNER JOIN ProductInCategory pic ON pic MEMBER OF p.categories " +
                        "INNER JOIN Category c ON pic.category = c " +
                        "WHERE c.name = :categoryName " +
                        "ORDER BY p.price ASC")
                .parameter("categoryName", Category.EXTRAS_NAME)
                .maxResults(1)
                .one()
                .getPrice());
    }

    @GetMapping(value = "/stuff/maxPrice")
    public ResponseEntity<Double> getMaxStuffPrice() {
        return of(dataManager.load(Product.class)
                .query("SELECT p FROM Product p " +
                        "INNER JOIN ProductInCategory pic ON pic MEMBER OF p.categories " +
                        "INNER JOIN Category c ON pic.category = c " +
                        "WHERE c.name = :categoryName " +
                        "ORDER BY p.price DESC")
                .parameter("categoryName", Category.EXTRAS_NAME)
                .maxResults(1)
                .one()
                .getPrice());
    }
}

package org.izumi.jstore.repository.impl;

import java.util.List;

import io.jmix.core.DataManager;
import lombok.RequiredArgsConstructor;
import org.izumi.jstore.components.CustomDataManager;
import org.izumi.jstore.entity.Product;
import org.izumi.jstore.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

/**
 * @author Aiden Izumi (aka Flamesson).
 */
@RequiredArgsConstructor
@Repository
public class ProductRepositoryImpl implements ProductRepository {
    private final DataManager dataManager;
    private final CustomDataManager customDataManager;

    @Override
    public Page<Product> findAllByCategoryName(String fetchPlanName, String categoryName, int page, int size) {
        final List<Product> content = dataManager.load(Product.class)
                .query("SELECT p FROM Product p " +
                        "INNER JOIN ProductInCategory pic ON pic MEMBER OF p.categories " +
                        "INNER JOIN Category c ON pic.category = c " +
                        "WHERE c.name = :categoryName")
                .fetchPlan(fetchPlanName)
                .parameter("categoryName", categoryName)
                .firstResult(page * size)
                .maxResults(size)
                .list();
        final long total = customDataManager.getCount(Product.class)
                .query("SELECT p FROM Product p " +
                        "INNER JOIN ProductInCategory pic ON pic MEMBER OF p.categories " +
                        "INNER JOIN Category c ON pic.category = c " +
                        "WHERE c.name = :categoryName")
                .parameter("categoryName", categoryName)
                .count();

        return new PageImpl<>(content, PageRequest.of(page, size), total);
    }

    @Override
    public Page<Product> findAllByCategoryNameAndCollectionName(String fetchPlanName,
                                                                String categoryName,
                                                                String collectionName,
                                                                int page,
                                                                int size) {
        final List<Product> content = dataManager.load(Product.class)
                .query("SELECT p FROM Product p " +
                        "INNER JOIN ProductInCategory pic ON pic.product = p " +
                        "INNER JOIN Category c ON pic.category = c " +
                        "INNER JOIN ProductInCollection pico ON pico.product = p " +
                        "INNER JOIN Collection co ON pico.collection = co " +
                        "WHERE c.name = :categoryName AND co.name = :collectionName")
                .fetchPlan(fetchPlanName)
                .parameter("categoryName", categoryName)
                .parameter("collectionName", collectionName)
                .firstResult(page * size)
                .maxResults(size)
                .list();
        final long total = customDataManager.getCount(Product.class)
                .query("SELECT p FROM Product p " +
                        "INNER JOIN ProductInCategory pic ON pic.product = p " +
                        "INNER JOIN Category c ON pic.category = c " +
                        "INNER JOIN ProductInCollection pico ON pico.product = p " +
                        "INNER JOIN Collection co ON pico.collection = co " +
                        "WHERE c.name = :categoryName AND co.name = :collectionName")
                .parameter("categoryName", categoryName)
                .parameter("collectionName", collectionName)
                .count();

        return new PageImpl<>(content, PageRequest.of(page, size), total);
    }

    @Override
    public Page<Product> findAll(String fetchPlanName, String categoryName,
                                 double minPrice, double maxPrice,
                                 int page, int size) {
        final List<Product> content = dataManager.load(Product.class)
                .query("SELECT p FROM Product p " +
                        "INNER JOIN ProductInCategory pic ON pic MEMBER OF p.categories " +
                        "INNER JOIN Category c ON pic.category = c " +
                        "WHERE c.name = :categoryName " +
                        "AND p.price >= :minPrice " +
                        "AND p.price <= :maxPrice")
                .fetchPlan(fetchPlanName)
                .parameter("categoryName", categoryName)
                .parameter("minPrice", minPrice)
                .parameter("maxPrice", maxPrice)
                .firstResult(page * size)
                .maxResults(size)
                .list();
        final long total = customDataManager.getCount(Product.class)
                .query("SELECT p FROM Product p " +
                        "INNER JOIN ProductInCategory pic ON pic MEMBER OF p.categories " +
                        "INNER JOIN Category c ON pic.category = c " +
                        "WHERE c.name = :categoryName " +
                        "AND p.price >= :minPrice " +
                        "AND p.price <= :maxPrice")
                .parameter("categoryName", categoryName)
                .parameter("minPrice", minPrice)
                .parameter("maxPrice", maxPrice)
                .count();

        return new PageImpl<>(content, PageRequest.of(page, size), total);
    }

    @Override
    public Page<Product> findAll(String fetchPlanName, String categoryName, String collectionName,
                                 double minPrice, double maxPrice,
                                 int page, int size) {
        final List<Product> content = dataManager.load(Product.class)
                .query("SELECT p FROM Product p " +
                        "INNER JOIN ProductInCategory pic ON pic.product = p " +
                        "INNER JOIN Category c ON pic.category = c " +
                        "INNER JOIN ProductInCollection pico ON pico.product = p " +
                        "INNER JOIN Collection co ON pico.collection = co " +
                        "WHERE c.name = :categoryName AND co.name = :collectionName " +
                        "AND p.price >= :minPrice " +
                        "AND p.price <= :maxPrice")
                .fetchPlan(fetchPlanName)
                .parameter("categoryName", categoryName)
                .parameter("collectionName", collectionName)
                .parameter("minPrice", minPrice)
                .parameter("maxPrice", maxPrice)
                .firstResult(page * size)
                .maxResults(size)
                .list();
        final long total = customDataManager.getCount(Product.class)
                .query("SELECT p FROM Product p " +
                        "INNER JOIN ProductInCategory pic ON pic.product = p " +
                        "INNER JOIN Category c ON pic.category = c " +
                        "INNER JOIN ProductInCollection pico ON pico.product = p " +
                        "INNER JOIN Collection co ON pico.collection = co " +
                        "WHERE c.name = :categoryName AND co.name = :collectionName " +
                        "AND p.price >= :minPrice " +
                        "AND p.price <= :maxPrice")
                .parameter("categoryName", categoryName)
                .parameter("collectionName", collectionName)
                .parameter("minPrice", minPrice)
                .parameter("maxPrice", maxPrice)
                .count();

        return new PageImpl<>(content, PageRequest.of(page, size), total);
    }
}

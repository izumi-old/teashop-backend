package org.izumi.jstore.repository;

import org.izumi.jstore.entity.Product;
import org.springframework.data.domain.Page;

/**
 * @author Aiden Izumi (aka Flamesson).
 */
public interface ProductRepository {
    Page<Product> findAllByCategoryName(String fetchPlanName, String categoryName, int page, int size);

    Page<Product> findAllByCategoryNameAndCollectionName(String fetchPlanName,
                                                         String categoryName,
                                                         String collectionName,
                                                         int page,
                                                         int size);

    Page<Product> findAll(String fetchPlanName,
                          String categoryName,
                          double minPrice,
                          double maxPrice,
                          int page,
                          int size);

    Page<Product> findAll(String fetchPlanName,
                          String categoryName,
                          String collectionName,
                          double minPrice,
                          double maxPrice,
                          int page,
                          int size);
}

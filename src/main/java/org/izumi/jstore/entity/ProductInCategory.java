package org.izumi.jstore.entity;

import io.jmix.core.metamodel.annotation.JmixEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Setter
@Getter
@JmixEntity
@Table(name = "PRODUCT_IN_CATEGORY", indexes = {
        @Index(name = "IDX_PRODUCTINCATEGORY_PRODUCT", columnList = "PRODUCT_ID"),
        @Index(name = "IDX_PRODUCTINCATEGORY_CATEGORY", columnList = "CATEGORY_ID")
})
@Entity
public class ProductInCategory extends StandardEntity {

    @JoinColumn(name = "PRODUCT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @JoinColumn(name = "CATEGORY_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;
}
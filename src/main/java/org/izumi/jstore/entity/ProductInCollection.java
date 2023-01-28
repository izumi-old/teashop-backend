package org.izumi.jstore.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.jmix.core.metamodel.annotation.JmixEntity;

@JmixEntity
@Table(name = "PRODUCT_IN_COLLECTION", indexes = {
        @Index(name = "IDX_PRODUCTINCOLLECTIO_PRODUCT", columnList = "PRODUCT_ID"),
        @Index(name = "IDX_PRODUCTINCOLLEC_COLLECTION", columnList = "COLLECTION_ID")
})
@Entity
public class ProductInCollection extends StandardEntity {

    @JoinColumn(name = "PRODUCT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @JoinColumn(name = "COLLECTION_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Collection collection;

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
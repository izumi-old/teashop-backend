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
@Table(name = "PRODUCT_IN_BUCKET", indexes = {
        @Index(name = "IDX_PRODUCT_IN_BUCKET_PRODUCT", columnList = "PRODUCT_ID"),
        @Index(name = "IDX_PRODUCT_IN_BUCKET_BUCKET", columnList = "BUCKET_ID")
})
@Entity
public class ProductInBucket extends StandardEntity {

    @JoinColumn(name = "PRODUCT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @JoinColumn(name = "BUCKET_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Bucket bucket;
}
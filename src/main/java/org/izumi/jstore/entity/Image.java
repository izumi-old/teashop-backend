package org.izumi.jstore.entity;

import io.jmix.core.metamodel.annotation.JmixEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Setter
@Getter
@JmixEntity
@Table(name = "IMAGE", indexes = {
        @Index(name = "IDX_IMAGE_PRODUCT", columnList = "PRODUCT_ID")
})
@Entity
public class Image extends StandardEntity {

    @JoinColumn(name = "PRODUCT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @Column(name = "URL", length = 511)
    private String url;
}
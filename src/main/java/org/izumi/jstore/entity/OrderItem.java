package org.izumi.jstore.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.jmix.core.metamodel.annotation.JmixEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "ORDER_ITEM", indexes = {
        @Index(name = "IDX_ORDERITEM_ORDER_ID", columnList = "ORDER_ID"),
        @Index(name = "IDX_ORDERITEM_PRODUCT_ID", columnList = "PRODUCT_ID")
})
@JmixEntity
@Entity
public class OrderItem extends StandardEntity {

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @JoinColumn(name = "ORDER_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Order order;

    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private Product product;

    @Positive
    @Column(name = "AMOUNT", nullable = false)
    private Integer amount;

    @Positive
    @Column(name = "PRICE", nullable = false)
    private Double price;
}
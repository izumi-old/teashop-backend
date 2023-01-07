package org.izumi.jstore.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Positive;

import io.jmix.core.metamodel.annotation.JmixEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "ORDER_" +
        "", indexes = {
        @Index(name = "IDX_ORDER_USER_ID", columnList = "USER_ID")
})
@JmixEntity
@Entity(name = "Order_")
public class Order extends StandardEntity {

    @JoinColumn(name = "USER_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    @Column(name = "DATE_OF_ISSUE", nullable = false)
    private LocalDate dateOfIssue;

    @Column(name = "DELIVERY_DATE", nullable = false)
    private LocalDate deliveryDate;

    @Column(name = "DELIVERY_ADDRESS", nullable = false)
    private String deliveryAddress;

    @Column(name = "STATUS")
    private Integer status;

    @Positive
    @Column(name = "PRICE", nullable = false)
    private Double price;

    public Status getStatus() {
        return status == null ? null : Status.fromId(status);
    }

    public void setStatus(Status status) {
        this.status = status.getId();
    }
}
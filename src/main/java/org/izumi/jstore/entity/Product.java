package org.izumi.jstore.entity;

import io.jmix.core.metamodel.annotation.JmixEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Positive;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JmixEntity
@Table(name = "PRODUCT")
@Entity
public class Product extends StandardEntity {

    @OneToMany(mappedBy = "product")
    private List<ProductInCategory> categories;

    @OneToMany(mappedBy = "product")
    private List<Image> images;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Positive
    @Column(name = "PRICE", nullable = false)
    private Double price;

    @Column(name = "REMAINDER", nullable = false)
    private Integer remainder;
}
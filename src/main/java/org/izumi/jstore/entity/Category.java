package org.izumi.jstore.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import io.jmix.core.metamodel.annotation.JmixEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "CATEGORY")
@JmixEntity
@Entity
public class Category extends StandardEntity {

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;
}
package org.izumi.jstore.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

@JmixEntity
@Table(name = "COLLECTION")
@Entity
public class Collection extends StandardEntity {
    public static final String TEA_TYPE = "tea";
    public static final String EXTRA_TYPE = "extra";

    @InstanceName
    @Column(name = "NAME")
    private String name;

    @Column(name = "TYPE")
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
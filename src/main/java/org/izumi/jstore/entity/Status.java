package org.izumi.jstore.entity;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public enum Status implements EnumClass<Integer> {
    GOING(10), FOR_DELIVERY(20), FOR_RECEIPT(30), RECEIVED(40);;

    private final Integer id;

    Status(Integer id) {
        this.id = id;
    }

    @Nonnull
    @Override
    public Integer getId() {
        return id;
    }

    @Nullable
    public static Status fromId(Integer id) {
        for (Status at : Status.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}
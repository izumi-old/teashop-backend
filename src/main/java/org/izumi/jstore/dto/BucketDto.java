package org.izumi.jstore.dto;

import java.util.Collection;

import lombok.Data;

/**
 * @author Aiden Izumi (aka Flamesson).
 */
@Data
public class BucketDto {
    private Collection<ItemDto> items;
}

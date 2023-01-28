package org.izumi.jstore.dto;

import lombok.Data;

/**
 * @author Aiden Izumi (aka Flamesson).
 */
@Data
public class ItemDto {
    private String productId;
    private String productName;
    private Integer amount;
}

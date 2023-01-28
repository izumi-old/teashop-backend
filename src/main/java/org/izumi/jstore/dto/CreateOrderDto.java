package org.izumi.jstore.dto;

import lombok.Data;
import org.izumi.jstore.entity.User;

/**
 * @author Aiden Izumi (aka Flamesson).
 */
@Data
public class CreateOrderDto {
    private User user;
    private BucketDto bucket;
}

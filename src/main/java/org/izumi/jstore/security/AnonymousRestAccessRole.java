package org.izumi.jstore.security;

import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import org.izumi.jstore.entity.Category;
import org.izumi.jstore.entity.Image;
import org.izumi.jstore.entity.Product;
import org.izumi.jstore.entity.ProductInCategory;

@ResourceRole(name = "AnonymousRestRole", code = AnonymousRestAccessRole.CODE, scope = "API")
public interface AnonymousRestAccessRole {
    String CODE = "anonymous-rest-access";

    @EntityAttributePolicy(entityClass = Product.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Product.class, actions = EntityPolicyAction.READ)
    void product();

    @EntityAttributePolicy(entityClass = Image.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Image.class, actions = EntityPolicyAction.READ)
    void image();

    @EntityAttributePolicy(entityClass = ProductInCategory.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = ProductInCategory.class, actions = EntityPolicyAction.READ)
    void productInCategory();

    @EntityAttributePolicy(entityClass = Category.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Category.class, actions = EntityPolicyAction.READ)
    void category();
}

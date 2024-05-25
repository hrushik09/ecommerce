package io.hrushik09.ecommerce.inventory.domain.inventoryitems.validation;

import io.hrushik09.ecommerce.inventory.domain.inventoryitems.model.CreateInventoryItemRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidQuantityConstraintValidator implements ConstraintValidator<ValidQuantityConstraint, CreateInventoryItemRequest> {
    @Override
    public boolean isValid(CreateInventoryItemRequest request, ConstraintValidatorContext context) {
        return request.minimumStockLevel() < request.reorderPoint()
                && request.reorderPoint() < request.quantityAvailable()
                && request.quantityAvailable() < request.maximumStockLevel();
    }
}

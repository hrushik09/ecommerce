package io.hrushik09.ecommerce.auth.domain.users.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.List;

public class ListContainsUniqueStringsConstraintValidator implements ConstraintValidator<ListContainsUniqueStringsConstraint, List<String>> {
    @Override
    public boolean isValid(List<String> list, ConstraintValidatorContext context) {
        return list != null && list.size() == new HashSet<>(list).size();
    }
}

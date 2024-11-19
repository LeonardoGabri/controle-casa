package org.acme.infra.validacao;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ReferenciaCobrancaValidator implements ConstraintValidator<ReferenciaCobranca, String> {
    private static final String REGEX = "^(0[1-9]|1[0-2])/\\d{4}$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        return value.matches(REGEX);
    }
}

package org.acme.infra.validacao;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ReferenciaCobrancaValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ReferenciaCobranca {
    String message() default "O campo referenciaCobranca deve estar no formato MM/AAAA.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

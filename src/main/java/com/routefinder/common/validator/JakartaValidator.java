package com.routefinder.common.validator;

import com.routefinder.common.exception.InvalidParameterException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class JakartaValidator {

    private static final String JAKARTA_VALIDATION_ERROR_MESSAGE =
            "Exception happened during the request processing. Violations: %s";

    public static <OBJECT> void validateWithJakartaValidator(@Valid OBJECT object) {

        Set<ConstraintViolation<OBJECT>> constraintViolations;

        try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
            jakarta.validation.Validator validator = validatorFactory.getValidator();
            constraintViolations = validator.validate(object);
        }
        if (!CollectionUtils.isEmpty(constraintViolations)) {
            StringBuilder error = new StringBuilder();
            for (ConstraintViolation<OBJECT> violation : constraintViolations) {
                error
                        .append(violation.getRootBeanClass())
                        .append(" classField -> ")
                        .append(violation.getPropertyPath().toString())
                        .append(": ")
                        .append(violation.getMessage())
                        .append(";");
            }
            throw new InvalidParameterException(String.format(JAKARTA_VALIDATION_ERROR_MESSAGE, error));
        }
    }
}

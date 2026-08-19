package com.shipping.demo.common.validator;


import java.lang.reflect.Field;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.shipping.demo.common.exception.MandatoryFieldIsEmptyException;

@Component
public class MandatoryFieldValidator extends FieldValidator {

    public static final String CANNOT_BE_EMPTY = " cannot be empty!";
    @Override
    protected boolean isFieldValidationNeeded(Field field) {
        return field.isAnnotationPresent(MandatoryField.class);
    }

    @Override
    protected void validateFieldValue(Field field, Object fieldValue) {
        if (isEmpty(fieldValue)) {
            throw new MandatoryFieldIsEmptyException(field.getName() + CANNOT_BE_EMPTY);
        }
    }

    private boolean isEmpty(Object value) {
        return value == null || isEmptyString(value) || isEmptyList(value);
    }

    private boolean isEmptyString(Object value) {
        return value instanceof String && !StringUtils.hasText((String) value);
    }

    private boolean isEmptyList(Object value) {
        return value instanceof List<?> && CollectionUtils.isEmpty((List<?>) value);
    }
}

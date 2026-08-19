package com.shipping.demo.common.validator;

import java.lang.reflect.Field;

public interface Validator {
    <OBJECT> void validate(OBJECT object, Class<?> objectClass, Field[] objectFields);
}

package com.shipping.demo.common.validator;

import com.shipping.demo.common.exception.TechnicalException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

public abstract class FieldValidator implements Validator {

    private static final String CUSTOM_PACKAGE_PREFIX = "com.shipping.demo";
    public static final String COULD_NOT_FIND_FIELD_VALUE = "Couldn't find field value during field check for: ";

    public <OBJECT> void validate(OBJECT object, Class<?> objectClass, Field[] objectFields) {
        validateFields(object, objectFields);
        validateSuperFields(object, objectClass);
    }

    private <OBJECT> void validateFields(OBJECT object, Field[] objectFields) {
        Arrays.stream(objectFields)
                .filter(field -> isNeedValidateField(field))
                .forEach(field -> {
                    try {
                        Object value = getObjectFieldValueByName(object, field.getName());
                        if (isFieldValidationNeeded(field)) {
                            validateFieldValue(field, value);
                        }
                        if (isCollection(field)) {
                            validateCollectionElements(value);
                        }
                        if (isMap(field)) {
                            validateMapElements(value);
                        }
                        if (isCustomClass(field)) {
                            validate(value, value.getClass(), value.getClass().getDeclaredFields());
                        }
                    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                        throw new TechnicalException(COULD_NOT_FIND_FIELD_VALUE + field.getName());
                    }
                });
    }

    private boolean isNeedValidateField(Field field) {
        return isFieldValidationNeeded(field) || isCollection(field) || isMap(field) || isCustomClass(field);
    }

    protected abstract boolean isFieldValidationNeeded(Field field);

    private boolean isCollection(Field field) {
        return field.getType().equals(Collection.class);
    }

    private boolean isMap(Field field) {
        return field.getType().equals(Map.class);
    }

    private boolean isCustomClass(Field field) {
        if (field == null || field.getType().isEnum()) {
            return false;
        }
        return field.getType().getPackageName().contains(CUSTOM_PACKAGE_PREFIX);
    }

    protected abstract void validateFieldValue(Field field, Object fieldValue);

    private void validateCollectionElements(Object object) {
        if (object != null && object instanceof Collection<?> collection) {
            collection.forEach(item -> validate(item, item.getClass(), item.getClass().getDeclaredFields()));
        }
    }

    private void validateMapElements(Object object) {
        if (object != null && object instanceof Map<?, ?> map) {
            map.forEach((key, value) -> {
                Object keyObject = key;
                validate(key, keyObject.getClass(), keyObject.getClass().getDeclaredFields());
                Object valueObject = value;
                validate(value, valueObject.getClass(), valueObject.getClass().getDeclaredFields());
            });
        }
    }

    private <OBJECT> Object getObjectFieldValueByName(OBJECT object, String fieldName)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        Method getterMethod = object.getClass().getMethod("get" + fieldName);
        return getterMethod.invoke(object);
    }

    private <OBJECT> void validateSuperFields(OBJECT object, Class<?> objectClass) {
        Class<?> superClass = objectClass.getSuperclass();
        while (superClass != null) {
            validateFields(object, superClass.getDeclaredFields());
            superClass = superClass.getSuperclass();
        }
    }
}

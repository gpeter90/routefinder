package com.routefinder.common.domain;

import com.routefinder.common.exception.DomainValidationException;
import com.routefinder.common.exception.InvalidParameterException;
import com.routefinder.common.validator.JakartaValidator;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class DomainService<DATA_TO_PERSIST> {

    public static final String DOMAIN_ERROR_MESSAGE =
            "Exception happened during execute domain action with %s \n Cause: %s";

    @Transactional(rollbackOn = Exception.class)
    public DATA_TO_PERSIST save(DATA_TO_PERSIST dataToPersist) {
        if (dataToPersist != null) {
            log.debug("{}", dataToPersist);
            validate(dataToPersist);
            try {
                return executeDomainAction(dataToPersist);
            } catch (Exception exception) {
                log.error(String.format(DOMAIN_ERROR_MESSAGE, dataToPersist, exception.getMessage()));
                throw exception;
            }
        }
        return null;
    }

    private void validate(@Valid DATA_TO_PERSIST dataToPersist) {

        try {
            JakartaValidator.validateWithJakartaValidator(dataToPersist);
        } catch (InvalidParameterException invalidParameterException) {
            throw new DomainValidationException(invalidParameterException.getMessage());
        }
    }

    protected DATA_TO_PERSIST executeDomainAction(DATA_TO_PERSIST dataToPersist) {

        if (isReturnSavedObject()) {
            return updateWithReturn(dataToPersist);
        } else {
            update(dataToPersist);
        }
        return null;
    }

    private boolean isReturnSavedObject() {
        return getClass().isAnnotationPresent(PersistedEntityProvider.class);
    }

    protected DATA_TO_PERSIST updateWithReturn(DATA_TO_PERSIST dataToPersist) {
        return null;
    }

    protected void update(DATA_TO_PERSIST dataToPersist) {
    }
}

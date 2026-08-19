package com.shipping.demo.common.usecase;

import com.shipping.demo.common.exception.BusinessLogicException;
import com.shipping.demo.common.exception.ConnectionException;
import com.shipping.demo.common.exception.InvalidParameterException;
import com.shipping.demo.common.exception.MandatoryFieldIsEmptyException;
import com.shipping.demo.common.exception.NotFoundException;
import com.shipping.demo.common.exception.TechnicalException;
import com.shipping.demo.common.validator.JakartaValidator;
import com.shipping.demo.common.validator.MandatoryFieldValidator;
import com.shipping.demo.common.validator.Validator;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Slf4j
public abstract class UseCase<
        REQUEST,
        RESPONSE
        > {

    public RESPONSE execute(REQUEST request) {
        try {
            if (isRequestLoggingEnabled()) {
                logInfo(getClass().getSimpleName() + "'s request: " + request);
            }
            if (Objects.nonNull(request)) {
                validate(request);
            }
            RESPONSE response = executeBusinessLogic(request);
            if (isRequestLoggingEnabled()) {
                logInfo(getClass().getSimpleName() + "'s request has been successfully processed!");
            }
            if (isResponseLoggingEnabled() && Objects.nonNull(response)) {
                logInfo(String.format("%s's response: %s", getClass().getSimpleName(), response));
            }
            return response;
        } catch (MandatoryFieldIsEmptyException | InvalidParameterException exception) {
            logWarn(exception);
            throw exception;
        } catch (BusinessLogicException businessLogicException) {
            logWarn(businessLogicException);
            throw businessLogicException;
        } catch (ConnectionException connectionException) {
            logWarn(connectionException);
            throw connectionException;
        } catch (NotFoundException notFoundException) {
            logWarn(notFoundException);
            throw notFoundException;
        } catch (Exception exception) {
            logError(exception);
            throw new TechnicalException(exception.getMessage());
        }
    }

    private boolean isRequestLoggingEnabled() {
        return !getClass().isAnnotationPresent(SkipUseCaseRequestLogging.class);
    }

    protected void logInfo(String message) {
        log.info(message);
    }

    protected void logInfo(String message, Throwable throwable) {
        log.info(message, throwable);
    }

    private void validate(REQUEST request) {
        validateWithJakartaValidator(request);
        validateWithCustomValidators(request);
    }

    public void validateWithJakartaValidator(@Valid REQUEST request) {
        JakartaValidator.validateWithJakartaValidator(request);
    }

    public void validateWithCustomValidators(REQUEST request) {
        Class<REQUEST> requestClass = getRuntimeRequestClass();

        List<Validator> validatorList = List.of(getValidators());


        Stream<Validator> onlyMandatoryFieldValidatorStream =
                validatorList.stream().filter(validator -> validator instanceof MandatoryFieldValidator);

        Stream<Validator> validatorsExceptMandatoryFieldValidatorStream =
                validatorList.stream().filter(validator -> !(validator instanceof MandatoryFieldValidator));

        Stream.concat(onlyMandatoryFieldValidatorStream, validatorsExceptMandatoryFieldValidatorStream)
                .forEach(validator ->
                        validator.validate(
                                request,
                                requestClass,
                                requestClass.getDeclaredFields()
                        )
                );
        if (isExtraValidationNeeded()) {
            runExtraValidation(request);
        }
    }

    @SuppressWarnings("unchecked")
    private Class<REQUEST> getRuntimeRequestClass() {
        return (Class<REQUEST>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    protected Validator[] getValidators() {
        return new Validator[]{};
    }

    private boolean isExtraValidationNeeded() {
        return this instanceof UseCaseWithExtraValidation;
    }

    private void runExtraValidation(REQUEST request) {
        ((UseCaseWithExtraValidation<REQUEST>) this).validate(request);
    }

    protected abstract RESPONSE executeBusinessLogic(REQUEST request);

    private boolean isResponseLoggingEnabled() {
        return !getClass().isAnnotationPresent(SkipUseCaseResponseLogging.class);
    }

    private void logWarn(Exception exception) {
        if (
                exception instanceof BusinessLogicException businessLogicException
                        && !CollectionUtils.isEmpty(businessLogicException.getMessageVariableList())
        ) {
            log.warn(businessLogicException.getMessage() + "\n" + businessLogicException.getMessageVariableList());
        } else {
            log.warn(exception.getMessage());
        }
    }

    private void logError(Exception exception) {
        log.error(exception.getMessage(), exception);
    }
}


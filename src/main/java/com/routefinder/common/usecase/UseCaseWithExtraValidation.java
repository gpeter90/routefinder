package com.routefinder.common.usecase;

public interface UseCaseWithExtraValidation<REQUEST> {
    void validate(REQUEST request);
}

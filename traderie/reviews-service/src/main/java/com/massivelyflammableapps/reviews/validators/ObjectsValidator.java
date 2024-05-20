package com.massivelyflammableapps.reviews.validators;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ObjectsValidator<T> {

    private final ValidatorFactory factory= Validation.buildDefaultValidatorFactory();

    private final Validator validator = factory.getValidator();

    public Set<String> validate(T objectToValidate){
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(objectToValidate);
        if(!constraintViolations.isEmpty()){
            return constraintViolations
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toSet());
        }
        return Collections.emptySet();
    }
}

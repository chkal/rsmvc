package de.chkal.rsmvc.core.validation;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@ApplicationScoped
public class FormValidator {

    private ValidatorFactory validatorFactory;

    protected FormValidator() {
        // CDI eyes only
    }

    @Inject
    public FormValidator(ValidatorFactory validatorFactory) {
        this.validatorFactory = validatorFactory;
    }

    public ValidationResult validate(Object form) {
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<Object>> violations = validator.validate(form);
        return new ValidationResult(violations);
    }

}

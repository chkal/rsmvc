package de.chkal.rsmvc.core.validation;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class ValidationResult {

    private Set<ConstraintViolation<Object>> violations;

    public ValidationResult(Set<ConstraintViolation<Object>> violations) {
        this.violations = violations;
    }

    public boolean hasErrors() {
        return !violations.isEmpty();
    }

    public ValidationAction onError() {
        if (hasErrors()) {
            return new FailedValidationAction(this);
        } else {
            return new PassedValidationAction();
        }
    }

    public Set<ConstraintViolation<Object>> getViolations() {
        return violations;
    }
}

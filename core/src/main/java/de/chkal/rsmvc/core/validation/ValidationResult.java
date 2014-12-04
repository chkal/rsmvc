package de.chkal.rsmvc.core.validation;

import de.chkal.rsmvc.core.AbstractModel;
import de.chkal.rsmvc.core.InstantRenderingException;
import de.chkal.rsmvc.core.Model;
import de.chkal.rsmvc.core.ModelAndView;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class ValidationResult {

    private Set<ConstraintViolation<Object>> violations;

    public ValidationResult(Set<ConstraintViolation<Object>> violations) {
        this.violations = violations;
    }

    public void onErrorRender() {
        if (hasErrors()) {
            Model model = new Model();
            storeInModel(model);
            throw new InstantRenderingException(model);
        }
    }

    public void onErrorRender(String view) {
        if (hasErrors()) {
            ModelAndView modelAndView = new ModelAndView(view);
            storeInModel(modelAndView);
            throw new InstantRenderingException(modelAndView);
        }
    }

    public boolean hasErrors() {
        return !violations.isEmpty();
    }

    public void storeInModel(AbstractModel<?> model) {
        storeInModel(model, "errors");
    }

    public void storeInModel(AbstractModel<?> model, String key) {
        model.with(key, violations);
    }

}

package de.chkal.rsmvc.core.validation;

import de.chkal.rsmvc.core.AbstractModel;
import de.chkal.rsmvc.core.InstantRenderingException;
import de.chkal.rsmvc.core.Model;
import de.chkal.rsmvc.core.ModelAndView;

public class FailedValidationAction implements ValidationAction {

    private final AbstractModel model = new Model();
    private final ValidationResult result;

    protected FailedValidationAction(ValidationResult result) {
        this.result = result;
        this.model.with("errors", result.getViolations());
    }

    @Override
    public ValidationAction with(AbstractModel m) {
        model.with(m);
        return this;
    }

    @Override
    public ValidationAction with(String key, Object value) {
        model.with(key, value);
        return this;
    }

    @Override
    public void render() {
        throw new InstantRenderingException(model);
    }

    @Override
    public void render(String view) {
        throw new InstantRenderingException(new ModelAndView(view).with(model));
    }

}

package de.chkal.rsmvc.core.validation;

import de.chkal.rsmvc.core.*;

public class FailedValidationAction implements ValidationAction {

    private final Model model = new Model();

    protected FailedValidationAction(ValidationResult result) {
        this.model.with("errors", result.getViolations());
    }

    @Override
    public ValidationAction with(ReadableModel m) {
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

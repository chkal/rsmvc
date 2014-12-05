package de.chkal.rsmvc.core.validation;

import de.chkal.rsmvc.core.ReadableModel;

public class PassedValidationAction implements ValidationAction {

    @Override
    public void render() {
        // nothing to do
    }

    @Override
    public void render(String view) {
        // nothing to do
    }

    @Override
    public ValidationAction with(ReadableModel model) {
        return this;
    }

    @Override
    public ValidationAction with(String key, Object value) {
        return this;
    }
}

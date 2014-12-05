package de.chkal.rsmvc.core.validation;

import de.chkal.rsmvc.core.WritableModel;

public interface ValidationAction extends WritableModel<ValidationAction> {

    void render();

    void render(String view);

}

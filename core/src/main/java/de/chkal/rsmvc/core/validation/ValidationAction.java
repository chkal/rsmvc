package de.chkal.rsmvc.core.validation;

import de.chkal.rsmvc.core.AbstractModel;

public interface ValidationAction {

    void render();

    void render(String view);

    ValidationAction with(AbstractModel model);

    ValidationAction with(String key, Object value);

}

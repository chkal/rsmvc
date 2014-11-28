package de.chkal.rsmvc.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractModel<T extends AbstractModel<T>> implements Viewable {

    protected final Map<String, Object> model = new HashMap<>();

    public AbstractModel() {
    }

    public AbstractModel(Map<String, Object> model) {
        this.model.putAll(model);
    }

    public T with(String key, Object value) {
        model.put(key, value);
        return (T) this;
    }

    @Override
    public String getViewName() {
        return null;
    }

    @Override
    public Map<String, Object> getModel() {
        return Collections.unmodifiableMap(model);
    }
}

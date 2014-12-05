package de.chkal.rsmvc.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class BasicModel<T extends BasicModel<T>>
        implements WritableModel<T>, ReadableModel, Viewable {

    protected final Map<String, Object> model = new HashMap<>();

    public BasicModel() {
    }

    public BasicModel(Map<String, Object> model) {
        this.model.putAll(model);
    }

    @Override
    public T with(String key, Object value) {
        model.put(key, value);
        return (T) this;
    }

    @Override
    public T with(ReadableModel m) {
        model.putAll(m.getModel());
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

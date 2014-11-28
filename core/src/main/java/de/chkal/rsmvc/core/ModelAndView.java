package de.chkal.rsmvc.core;

import java.util.Map;

public class ModelAndView extends AbstractModel<ModelAndView> {

    private final String viewName;

    public ModelAndView(String viewName) {
        this.viewName = viewName;
    }

    public ModelAndView(String viewName, Map<String, Object> model) {
        super(model);
        this.viewName = viewName;
    }

    @Override
    public String getViewName() {
        return viewName;
    }

}

package de.chkal.rsmvc.core;

import java.util.Map;

public interface Viewable {

    String getViewName();

    Map<String, Object> getModel();

}

package de.chkal.rsmvc.core;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ModelAndView implements Viewable {

    private final String viewName;

    private final Map<String, Object> model = new HashMap<>();

    public ModelAndView(String viewName) {
        this(viewName, null);
    }

    public ModelAndView(String viewName, Map<String, Object> model) {
        this.viewName = viewName;
        if (model != null) {
            this.model.putAll(model);
        }
    }

    public ModelAndView with(String key, String value) {
        model.put(key, value);
        return this;
    }

    @Override
    public void render(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {

            for (Map.Entry<String, Object> entry : model.entrySet()) {
                request.setAttribute(entry.getKey(), entry.getValue());
            }

            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(viewName);
            dispatcher.forward(request, response);

        } catch (ServletException e) {
            throw new IOException(e);
        }
    }

}

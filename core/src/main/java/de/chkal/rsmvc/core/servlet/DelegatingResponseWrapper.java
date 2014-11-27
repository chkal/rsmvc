package de.chkal.rsmvc.core.servlet;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class DelegatingResponseWrapper extends HttpServletResponseWrapper {

    public DelegatingResponseWrapper(HttpServletResponse response) {
        super(response);
    }

}

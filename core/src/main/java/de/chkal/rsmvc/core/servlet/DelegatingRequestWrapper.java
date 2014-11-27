package de.chkal.rsmvc.core.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class DelegatingRequestWrapper extends HttpServletRequestWrapper {

    public DelegatingRequestWrapper(HttpServletRequest request) {
        super(request);
    }

}

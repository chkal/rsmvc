package de.chkal.rsmvc.core.provider;

import de.chkal.rsmvc.core.annotation.View;
import de.chkal.rsmvc.core.Viewable;
import de.chkal.rsmvc.core.servlet.DelegatingRequestWrapper;
import de.chkal.rsmvc.core.servlet.DelegatingResponseWrapper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Map;

@Provider
public class ViewableMessageBodyWriter implements MessageBodyWriter<Viewable> {

    @Context
    private HttpServletRequest request;

    @Context
    private HttpServletResponse response;

    @Context
    private ResourceInfo resourceInfo;

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return Viewable.class.isAssignableFrom(type);
    }

    @Override
    public long getSize(Viewable viewable, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    @Override
    public void writeTo(Viewable viewable, Class<?> type, Type genericType, Annotation[] annotations,
                        MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
            throws IOException, WebApplicationException {

        // Undertow verifies that request/response are wrappers
        DelegatingRequestWrapper requestWrapper = new DelegatingRequestWrapper(request);
        DelegatingResponseWrapper responseWrapper = new DelegatingResponseWrapper(response);

        String effectiveViewName = getEffectiveViewName(viewable);
        render(requestWrapper, responseWrapper, effectiveViewName, viewable.getModel());

    }

    private String getEffectiveViewName(Viewable viewable) {

        // prefer the view specified in the Viewable itself
        if (viewable.getViewName() != null && !viewable.getViewName().trim().isEmpty()) {
            return viewable.getViewName().trim();
        }

        // fallback to the method
        for (Annotation a : resourceInfo.getResourceMethod().getAnnotations()) {
            if (a instanceof View) {
                return ((View) a).value().trim();
            }
        }

        // fallback to the class
        for (Annotation a : resourceInfo.getResourceClass().getAnnotations()) {
            if (a instanceof View) {
                return ((View) a).value().trim();
            }
        }

        throw new IllegalStateException("Cannot determine view name!");

    }

    private void render(HttpServletRequest request, HttpServletResponse response, String viewName,
                        Map<String, Object> model) throws IOException {

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

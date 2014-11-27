package de.chkal.rsmvc.core.provider;

import de.chkal.rsmvc.core.servlet.DelegatingRequestWrapper;
import de.chkal.rsmvc.core.Viewable;
import de.chkal.rsmvc.core.servlet.DelegatingResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@Provider
public class ViewableMessageBodyWriter implements MessageBodyWriter<Viewable> {

    @Context
    private HttpServletRequest request;

    @Context
    private HttpServletResponse response;

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

        viewable.render(requestWrapper, responseWrapper);

    }

}

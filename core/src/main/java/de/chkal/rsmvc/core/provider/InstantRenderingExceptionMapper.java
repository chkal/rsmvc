package de.chkal.rsmvc.core.provider;

import de.chkal.rsmvc.core.InstantRenderingException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InstantRenderingExceptionMapper implements ExceptionMapper<InstantRenderingException> {

    @Override
    public Response toResponse(InstantRenderingException exception) {
        return Response.ok(exception.getViewable()).build();
    }

}

package de.chkal.rsmvc.demo.validation;

import de.chkal.rsmvc.core.Model;
import de.chkal.rsmvc.core.Viewable;
import de.chkal.rsmvc.core.annotation.View;
import de.chkal.rsmvc.demo.DemoApplication;

import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;

@Path("/validation")
@View("/validation.jsp")
public class ValidationController {

    @Context
    private Application application;

    @GET
    public Viewable get() {
        return new Model();
    }

    @POST
    public Viewable post(@BeanParam ValidationForm form) {

        return new Model()
                .with("form", form)
                .with("msg", "Hello " + form.getName());

    }

}

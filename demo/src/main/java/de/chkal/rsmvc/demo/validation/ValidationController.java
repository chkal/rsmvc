package de.chkal.rsmvc.demo.validation;

import de.chkal.rsmvc.core.Model;
import de.chkal.rsmvc.core.Viewable;
import de.chkal.rsmvc.core.annotation.View;
import de.chkal.rsmvc.core.validation.FormValidator;

import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/validation")
@View("/validation.jsp")
public class ValidationController {

    @Inject
    private FormValidator validator;

    @GET
    public Viewable get() {
        return new Model();
    }

    @POST
    public Viewable post(@BeanParam ValidationForm form) {

        validator.onError(form)
                .with("form", form)
                .render();

        return new Model()
                .with("msg", "Hello " + form.getName());

    }

}

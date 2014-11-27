package de.chkal.rsmvc.demo.form;

import de.chkal.rsmvc.core.ModelAndView;

import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/form")
public class FormController {

    @GET
    public ModelAndView get() {
        return new ModelAndView("/form.jsp");
    }

    @POST
    public ModelAndView post(@BeanParam FormBean form) {

        String name = "Doe";
        if (form.getName() != null && !form.getName().trim().isEmpty()) {
            name = form.getName();
        }

        StringBuilder msg = new StringBuilder();
        if (form.getCasual() != null && !form.getCasual().booleanValue()) {
            msg.append("Yo ");
        } else {
            msg.append("Hello ");
        }

        if (FormBean.Gender.MALE.equals(form.getGender())) {
            msg.append("Mr. ");
        } else if (FormBean.Gender.FEMALE.equals(form.getGender())) {
            msg.append("Mrs. ");
        } else {
            msg.append("Sir or Madam ");
        }
        msg.append(name);

        return new ModelAndView("/form.jsp")
                .with("msg", msg.toString())
                .with("form", form);

    }

}


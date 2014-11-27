package de.chkal.rsmvc.demo.simple;

import de.chkal.rsmvc.core.ModelAndView;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/simple")
public class SimpleController {

    @GET
    @Path("/{name}")
    public ModelAndView get(@PathParam("name") String name) {

        return new ModelAndView("/simple.jsp")
                .with("name", name.toUpperCase());

    }

}
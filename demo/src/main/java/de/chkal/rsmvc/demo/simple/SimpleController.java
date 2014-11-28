package de.chkal.rsmvc.demo.simple;

import de.chkal.rsmvc.core.Model;
import de.chkal.rsmvc.core.ModelAndView;
import de.chkal.rsmvc.core.annotation.View;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/simple")
public class SimpleController {

    @GET
    @Path("/1/{name}")
    public ModelAndView get1(@PathParam("name") String name) {

        return new ModelAndView("/simple.jsp")
                .with("name", name.toUpperCase());

    }

    @GET
    @Path("/2/{name}")
    @View("/simple.jsp")
    public Model get2(@PathParam("name") String name) {

        return new Model()
                .with("name", name.toUpperCase());

    }
}
package de.chkal.rsmvc.demo.validation;

import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;

public class ValidationForm {

    @FormParam("name")
    @Size(min = 5, message = "The name must have at least 5 characters")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

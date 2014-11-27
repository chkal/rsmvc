package de.chkal.rsmvc.demo.form;

import javax.ws.rs.FormParam;

public class FormBean {

    public enum Gender {
        UNKNOWN, MALE, FEMALE
    }

    @FormParam("name")
    public String name;

    @FormParam("gender")
    public Gender gender;

    @FormParam("casual")
    public Boolean casual;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Boolean getCasual() {
        return casual;
    }

    public void setCasual(Boolean casual) {
        this.casual = casual;
    }

}

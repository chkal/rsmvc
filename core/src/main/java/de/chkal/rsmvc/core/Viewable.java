package de.chkal.rsmvc.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Viewable {

    void render(HttpServletRequest request, HttpServletResponse response) throws IOException;

}

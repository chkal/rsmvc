package de.chkal.rsmvc.core;

public class InstantRenderingException extends RuntimeException {

    private final Viewable viewable;

    public InstantRenderingException(Viewable viewable) {
        this.viewable = viewable;
    }

    public Viewable getViewable() {
        return viewable;
    }

}

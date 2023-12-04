package backend;

import backend.model.Figure;

import java.util.ArrayList;

public class CanvasState extends ArrayList<Figure> {

    public Iterable<Figure> figures() {
        return new ArrayList<>(this);
    }

}

package backend.model;

import java.util.ArrayList;
import java.util.List;

public interface Figure {
    void move(double diffX, double diffY);
    boolean belongs(Point point);
    boolean isInRectangle(Rectangle rectangle);
    default List<Figure> getFigures() {
        List<Figure> figures = new ArrayList<>();
        figures.add(this);
        return figures;
    }
}

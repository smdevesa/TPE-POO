package backend.model;

import java.util.ArrayList;
import java.util.Collection;

public class GroupedFigure implements Figure {

    private final Collection<Figure> figures;

    public GroupedFigure(Collection<Figure> figures) {
        this.figures = figures;
    }

    @Override
    public void move(double diffX, double diffY) {
        for (Figure figure : figures) {
            figure.move(diffX, diffY);
        }
    }

    @Override
    public boolean belongs(Point point) {
        for (Figure figure : figures) {
            if (figure.belongs(point)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isInRectangle(Rectangle rectangle) {
        boolean isInRectangle = true;
        for (Figure figure : figures) {
            isInRectangle = isInRectangle && figure.isInRectangle(rectangle);
        }
        return isInRectangle;
    }

    @Override
    public Collection<Figure> getFigures() {
        return new ArrayList<>(figures);
    }
}

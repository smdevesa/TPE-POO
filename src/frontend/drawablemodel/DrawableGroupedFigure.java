package frontend.drawablemodel;

import backend.model.Figure;
import backend.model.GroupedFigure;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Collection;
import java.util.Map;

public class DrawableGroupedFigure extends GroupedFigure implements DrawableFigure {

    public DrawableGroupedFigure(Collection<Figure> figures) {
        super(figures);
    }

    @Override
    public void draw(GraphicsContext gc, Map<Figure, Color> colorMap) {
        for(Figure figure : getFigures()) {
            ((DrawableFigure) figure).draw(gc, colorMap);
        }
    }
}

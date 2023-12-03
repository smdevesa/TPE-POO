package frontend.drawablemodel;

import backend.model.Figure;
import backend.model.GroupedFigure;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Collection;
import java.util.Map;
import java.util.SortedSet;

public class DrawableGroupedFigure extends GroupedFigure implements DrawableFigure {

    public DrawableGroupedFigure(Collection<Figure> figures) {
        super(figures);
    }

    @Override
    public void draw(GraphicsContext gc, Map<Figure, Color> colorMap, Map<Figure, SortedSet<Effect>> effectMap) {
        for(Figure figure : getFigures()) {
            ((DrawableFigure) figure).draw(gc, colorMap, effectMap);
        }
    }

    @Override
    public void drawGradient(GraphicsContext gc, Color color) {

    }

    @Override
    public void drawBeveled(GraphicsContext gc, Color color) {

    }

    @Override
    public void drawShadow(GraphicsContext gc, Color color) {

    }
}


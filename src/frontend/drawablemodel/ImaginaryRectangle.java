package frontend.drawablemodel;

import backend.model.Figure;
import backend.model.Point;
import backend.model.Rectangle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

public class ImaginaryRectangle extends Rectangle implements DrawableFigure {

    public ImaginaryRectangle(Point topLeft, Point bottomRight) {
        super(topLeft, bottomRight);
    }

    @Override
    public void draw(GraphicsContext gc, Map<Figure, Color> colorMap, Map<Figure, SortedSet<Effect>> effectMap) {
        gc.strokeRect(getTopLeft().getX(), getTopLeft().getY(),
                Math.abs(getTopLeft().getX() - getBottomRight().getX()), Math.abs(getTopLeft().getY() - getBottomRight().getY()));
    }

    @Override
    public void drawBeveled(GraphicsContext gc, Color color) {

    }

    @Override
    public void drawGradient(GraphicsContext gc, Color color) {

    }

    @Override
    public void drawShadow(GraphicsContext gc, Color color) {

    }
}

package frontend.drawablemodel;

import backend.model.Figure;
import backend.model.Point;
import backend.model.Square;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

public class DrawableSquare extends Square implements DrawableFigure {

    DrawableRectangle drawableRectangle = new DrawableRectangle(getTopLeft(), getBottomRight());

    public DrawableSquare(Point topLeft, double size) {
        super(topLeft, size);
    }

    @Override
    public void draw(GraphicsContext gc, Map<Figure, Color> colorMap, Map<Figure, SortedSet<Effect>> effectMap) {
        colorMap.put(drawableRectangle, colorMap.get(this));
        effectMap.put(drawableRectangle, effectMap.get(this));
        drawableRectangle.draw(gc, colorMap, effectMap);
    }

    @Override
    public void drawShadow(GraphicsContext gc, Color color) {
        drawableRectangle.drawShadow(gc, color);
    }

    @Override
    public void drawBeveled(GraphicsContext gc, Color color) {
        drawableRectangle.drawBeveled(gc, color);
    }

    @Override
    public void drawGradient(GraphicsContext gc, Color color) {
        drawableRectangle.drawGradient(gc, color);
    }
}

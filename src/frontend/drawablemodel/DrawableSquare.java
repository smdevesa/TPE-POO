package frontend.drawablemodel;

import backend.model.Point;
import backend.model.Square;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawableSquare extends Square implements DrawableStyleableFigure {

    private final DrawableRectangle drawableRectangle = new DrawableRectangle(getTopLeft(), getBottomRight());

    public DrawableSquare(Point topLeft, double size) {
        super(topLeft, size);
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

    @Override
    public void drawFillWithOffset(GraphicsContext gc, double x, double y) {
        drawableRectangle.drawFillWithOffset(gc, x, y);
    }

    @Override
    public void drawBorder(GraphicsContext gc) {
        drawableRectangle.drawBorder(gc);
    }
}

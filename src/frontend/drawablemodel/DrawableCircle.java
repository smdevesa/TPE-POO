package frontend.drawablemodel;

import backend.model.Circle;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawableCircle extends Circle implements DrawableStyleableFigure {

    private final DrawableEllipse drawableEllipse = new DrawableEllipse(getCenterPoint(), getsMayorAxis(), getsMinorAxis());

    public DrawableCircle(Point centerPoint, double radius) {
        super(centerPoint, radius);
    }

    @Override
    public void drawShadow(GraphicsContext gc, Color color) {
        drawableEllipse.drawShadow(gc, color);
    }

    @Override
    public void drawGradient(GraphicsContext gc, Color color) {
        drawableEllipse.drawGradient(gc, color);
    }

    @Override
    public void drawBeveled(GraphicsContext gc, Color color) {
        drawableEllipse.drawBeveled(gc, color);
    }

    public void drawFillWithOffset(GraphicsContext gc, double offsetX, double offsetY) {
        drawableEllipse.drawFillWithOffset(gc, offsetX, offsetY);
    }

    public void drawBorder(GraphicsContext gc) {
        drawableEllipse.drawBorder(gc);
    }

}

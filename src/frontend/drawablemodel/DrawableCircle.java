package frontend.drawablemodel;

import backend.model.Circle;
import backend.model.Figure;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Map;
import java.util.SortedSet;

public class DrawableCircle extends Circle implements DrawableStyleableFigure {

    private final DrawableEllipse drawableEllipse = new DrawableEllipse(getCenterPoint(), getsMayorAxis(), getsMinorAxis());

    public DrawableCircle(Point centerPoint, double radius) {
        super(centerPoint, radius);
    }

    @Override
    public void draw(GraphicsContext gc, Map<Figure, Color> colorMap, Map<Figure, SortedSet<Effect>> effectMap, boolean selectionBorder) {
        // Actualizo los atributos de la elipse por si se modificaron al reescalar el circulo
        drawableEllipse.setsMayorAxis(getsMayorAxis());
        drawableEllipse.setsMinorAxis(getsMinorAxis());
        DrawableStyleableFigure.super.draw(gc, colorMap, effectMap, selectionBorder);
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

    @Override
    public void drawFillWithOffset(GraphicsContext gc, double offsetX, double offsetY) {
        drawableEllipse.drawFillWithOffset(gc, offsetX, offsetY);
    }

    @Override
    public void drawBorder(GraphicsContext gc) {
        drawableEllipse.drawBorder(gc);
    }

}

package frontend.drawablemodel;

import backend.model.Ellipse;
import backend.model.Figure;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

public class DrawableEllipse extends Ellipse implements DrawableStyleableFigure {

    public DrawableEllipse(Point centerPoint, double sMayorAxis, double sMinorAxis) {
        super(centerPoint, sMayorAxis, sMinorAxis);
    }

    @Override
    public void draw(GraphicsContext gc, Map<Figure, Color> colorMap, Map<Figure, SortedSet<Effect>> effectMap, boolean selectionBorder) {
        gc.setFill(colorMap.get(this));
        gc.strokeOval(getCenterPoint().getX() - (getsMayorAxis() / 2), getCenterPoint().getY() - (getsMinorAxis() / 2), getsMayorAxis(), getsMinorAxis());
        gc.fillOval(getCenterPoint().getX() - (getsMayorAxis() / 2), getCenterPoint().getY() - (getsMinorAxis() / 2), getsMayorAxis(), getsMinorAxis());
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

    @Override
    public void drawFillWithOffset(GraphicsContext gc, double x, double y) {

    }

    @Override
    public void drawBorder(GraphicsContext gc) {

    }
}

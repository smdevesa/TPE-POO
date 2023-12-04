package frontend.drawablemodel;

import backend.model.Circle;
import backend.model.Figure;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.ArcType;

import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

public class DrawableCircle extends Circle implements DrawableStyleableFigure {

    private double diameter = getRadius() * 2;

    public DrawableCircle(Point centerPoint, double radius) {
        super(centerPoint, radius);
    }

    @Override
    public void drawShadow(GraphicsContext gc, Color color) {
        gc.setFill(Color.GREY);
        drawFillWithOffset(gc, 10, 10);
    }

    @Override
    public void drawGradient(GraphicsContext gc, Color color) {
        RadialGradient radialGradient = new RadialGradient(0, 0, 0.5, 0.5, 0.5, true,
                CycleMethod.NO_CYCLE,
                new Stop(0, color),
                new Stop(1, color.invert()));
        gc.setFill(radialGradient);
        drawFillWithOffset(gc, 0, 0);
    }

    @Override
    public void drawBeveled(GraphicsContext gc, Color color) {
        double arcX = getCenterPoint().getX() - getRadius();
        double arcY = getCenterPoint().getY() - getRadius();
        gc.setLineWidth(10);
        gc.setStroke(Color.LIGHTGRAY);
        gc.strokeArc(arcX, arcY, diameter, diameter, 45, 180, ArcType.OPEN);
        gc.setStroke(Color.BLACK);
        gc.strokeArc(arcX, arcY, diameter, diameter, 225, 180, ArcType.OPEN);
        gc.setLineWidth(1);
    }

    public void drawFillWithOffset(GraphicsContext gc, double offsetX, double offsetY) {
        gc.fillOval(getCenterPoint().getX() - getRadius() + offsetX, getCenterPoint().getY() - getRadius() + offsetY, diameter, diameter);
    }

    public void drawBorder(GraphicsContext gc) {
        gc.strokeOval(getCenterPoint().getX() - getRadius(), getCenterPoint().getY() - getRadius(), diameter, diameter);
    }
}

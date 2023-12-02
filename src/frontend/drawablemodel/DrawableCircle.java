package frontend.drawablemodel;

import backend.model.Circle;
import backend.model.Figure;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Map;

public class DrawableCircle extends Circle implements DrawableFigure {

    public DrawableCircle(Point centerPoint, double radius) {
        super(centerPoint, radius);
    }

    @Override
    public void draw(GraphicsContext gc, Map<Figure, Color> colorMap) {
        gc.setFill(colorMap.get(this));
        double diameter = getRadius() * 2;
        gc.fillOval(getCenterPoint().getX() - getRadius(), getCenterPoint().getY() - getRadius(), diameter, diameter);
        gc.strokeOval(getCenterPoint().getX() - getRadius(), getCenterPoint().getY() - getRadius(), diameter, diameter);
    }
}

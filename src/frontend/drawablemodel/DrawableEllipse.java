package frontend.drawablemodel;

import backend.model.Ellipse;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;

public class DrawableEllipse extends Ellipse implements DrawableFigure {

    public DrawableEllipse(Point centerPoint, double sMayorAxis, double sMinorAxis) {
        super(centerPoint, sMayorAxis, sMinorAxis);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.strokeOval(getCenterPoint().getX() - (getsMayorAxis() / 2), getCenterPoint().getY() - (getsMinorAxis() / 2), getsMayorAxis(), getsMinorAxis());
        gc.fillOval(getCenterPoint().getX() - (getsMayorAxis() / 2), getCenterPoint().getY() - (getsMinorAxis() / 2), getsMayorAxis(), getsMinorAxis());
    }

}

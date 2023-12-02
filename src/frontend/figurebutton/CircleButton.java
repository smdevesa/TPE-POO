package frontend.figurebutton;

import backend.model.Figure;
import backend.model.Point;
import frontend.drawablemodel.DrawableCircle;

public class CircleButton extends FigureToggleButton {

    public CircleButton(String name) {
        super(name);
    }

    @Override
    public Figure createFigure(Point startPoint, Point endPoint) {
        double circleRadius = Math.abs(endPoint.getX() - startPoint.getX());
        return new DrawableCircle(startPoint, circleRadius);
    }
}

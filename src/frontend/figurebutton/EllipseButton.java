package frontend.figurebutton;

import backend.model.Figure;
import backend.model.Point;
import frontend.drawablemodel.DrawableEllipse;

public class EllipseButton extends FigureToggleButton {

        public EllipseButton(String name) {
            super(name);
        }

    @Override
    public Figure createFigure(Point startPoint, Point endPoint) {
        Point centerPoint = new Point(Math.abs(endPoint.getX() + startPoint.getX()) / 2, (Math.abs((endPoint.getY() + startPoint.getY())) / 2));
        double sMayorAxis = Math.abs(endPoint.getX() - startPoint.getX());
        double sMinorAxis = Math.abs(endPoint.getY() - startPoint.getY());
        return new DrawableEllipse(centerPoint, sMayorAxis, sMinorAxis);
    }
}

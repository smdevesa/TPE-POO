package frontend.figurebutton;

import backend.model.Figure;
import backend.model.Point;
import frontend.drawablemodel.DrawableRectangle;

public class RectangleButton extends FigureToggleButton {

    public RectangleButton(String name) {
        super(name);
    }

    @Override
    public Figure createFigure(Point startPoint, Point endPoint) {
        return new DrawableRectangle(startPoint, endPoint);
    }
}

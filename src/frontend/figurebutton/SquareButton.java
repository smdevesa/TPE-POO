package frontend.figurebutton;

import backend.model.Figure;
import backend.model.Point;
import frontend.drawablemodel.DrawableSquare;

public class SquareButton extends FigureToggleButton {

        public SquareButton(String name) {
            super(name);
        }

    @Override
    public Figure createFigure(Point startPoint, Point endPoint) {
        double size = Math.abs(endPoint.getX() - startPoint.getX());
        return new DrawableSquare(startPoint, size);
    }
}

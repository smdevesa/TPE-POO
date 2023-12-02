package frontend.figurebutton;

import backend.model.Figure;
import backend.model.Point;
import javafx.scene.control.ToggleButton;

public abstract class FigureToggleButton extends ToggleButton {

    FigureToggleButton(String name) {
        super(name);
    }

    public abstract Figure createFigure(Point startPoint, Point endPoint);
}

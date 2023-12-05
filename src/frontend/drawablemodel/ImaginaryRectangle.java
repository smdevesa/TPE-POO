package frontend.drawablemodel;

import backend.model.Figure;
import backend.model.Point;
import backend.model.Rectangle;
import frontend.Status;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Map;
import java.util.SortedSet;

public class ImaginaryRectangle extends Rectangle implements DrawableFigure {

    public ImaginaryRectangle(Point topLeft, Point bottomRight) {
        super(topLeft, bottomRight);
    }

    @Override
    public void draw(GraphicsContext gc, Map<Figure, Color> colorMap, Map<Figure, SortedSet<Effect>> effectMap, boolean selectionBorder) {
        gc.setStroke(Color.RED);
        gc.strokeRect(getTopLeft().getX(), getTopLeft().getY(),
                Math.abs(getTopLeft().getX() - getBottomRight().getX()), Math.abs(getTopLeft().getY() - getBottomRight().getY()));
    }

    @Override
    public Status getStatus(Effect effect, Map<Figure, SortedSet<Effect>> effectMap) {
        return Status.UNSELECTED;
    }
}

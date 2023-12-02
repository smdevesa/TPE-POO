package frontend.drawablemodel;

import backend.model.Figure;
import backend.model.Point;
import backend.model.Square;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Map;

public class DrawableSquare extends Square implements DrawableFigure {

    public DrawableSquare(Point topLeft, double size) {
        super(topLeft, size);
    }

    @Override
    public void draw(GraphicsContext gc, Map<Figure, Color> colorMap) {
        gc.setFill(colorMap.get(this));
        gc.fillRect(getTopLeft().getX(), getTopLeft().getY(),
                Math.abs(getTopLeft().getX() - getBottomRight().getX()), Math.abs(getTopLeft().getY() - getBottomRight().getY()));
        gc.strokeRect(getTopLeft().getX(), getTopLeft().getY(),
                Math.abs(getTopLeft().getX() - getBottomRight().getX()), Math.abs(getTopLeft().getY() - getBottomRight().getY()));
    }
}

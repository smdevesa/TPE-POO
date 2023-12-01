package frontend.drawablemodel;

import backend.model.Point;
import backend.model.Square;
import javafx.scene.canvas.GraphicsContext;

public class DrawableSquare extends Square implements DrawableFigure {

    public DrawableSquare(Point topLeft, double size) {
        super(topLeft, size);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.fillRect(getTopLeft().getX(), getTopLeft().getY(),
                Math.abs(getTopLeft().getX() - getBottomRight().getX()), Math.abs(getTopLeft().getY() - getBottomRight().getY()));
        gc.strokeRect(getTopLeft().getX(), getTopLeft().getY(),
                Math.abs(getTopLeft().getX() - getBottomRight().getX()), Math.abs(getTopLeft().getY() - getBottomRight().getY()));
    }
}

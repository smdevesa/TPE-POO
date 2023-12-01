package backend.model;

import javafx.scene.canvas.GraphicsContext;

public interface Figure {
    void move(double diffX, double diffY);
    boolean belongs(Point point);
    boolean isInRectangle(Rectangle rectangle);
}

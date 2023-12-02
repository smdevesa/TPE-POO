package frontend.drawablemodel;

import backend.model.Figure;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Map;

public interface DrawableFigure extends Figure {
    void draw(GraphicsContext gc, Map<Figure, Color> colorMap);
}

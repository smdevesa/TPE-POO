package frontend.drawablemodel;

import backend.model.Figure;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

public interface DrawableFigure extends Figure {
    void draw(GraphicsContext gc, Map<Figure, Color> colorMap, Map<Figure, SortedSet<Effect>> effectMap);
    void drawShadow(GraphicsContext gc, Color color);
    void drawGradient(GraphicsContext gc, Color color);
    void drawBeveled(GraphicsContext gc, Color color);
}

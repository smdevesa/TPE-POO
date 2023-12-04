package frontend.drawablemodel;

import backend.model.Figure;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

public interface DrawableFigure extends Figure {
    void draw(GraphicsContext gc, Map<Figure, Color> colorMap, Map<Figure, SortedSet<Effect>> effectMap, boolean selectionBorder);
    default Status getStatus(Effect effect, Map<Figure, SortedSet<Effect>> effectMap) {
        return effectMap.get(this).contains(effect) ? Status.SELECTED : Status.UNSELECTED;
    }
}

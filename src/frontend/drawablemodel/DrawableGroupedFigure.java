package frontend.drawablemodel;

import backend.model.Figure;
import backend.model.GroupedFigure;
import frontend.Status;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Collection;
import java.util.Map;
import java.util.SortedSet;

public class DrawableGroupedFigure extends GroupedFigure implements DrawableFigure {

    public DrawableGroupedFigure(Collection<Figure> figures) {
        super(figures);
    }

    @Override
    public void draw(GraphicsContext gc, Map<Figure, Color> colorMap, Map<Figure, SortedSet<Effect>> effectMap, boolean selectionBorder) {
        for(Figure figure : getFigures()) {
            ((DrawableFigure) figure).draw(gc, colorMap, effectMap, selectionBorder);
        }
    }

    @Override
    public Status getStatus(Effect effect, Map<Figure, SortedSet<Effect>> effectMap) {
        Status firstStatus = ((DrawableFigure)getFigures().get(0)).getStatus(effect, effectMap);
        for(Figure figure : getFigures()) {
            if(((DrawableFigure) figure).getStatus(effect, effectMap) != firstStatus) {
                return Status.UNDETERMINED;
            }
        }
        return firstStatus;
    }
}


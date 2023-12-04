package frontend.drawablemodel;

import backend.model.Figure;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Map;
import java.util.SortedSet;

public interface DrawableStyleableFigure extends DrawableFigure {

    default void draw(GraphicsContext gc, Map<Figure, Color> colorMap, Map<Figure, SortedSet<Effect>> effectMap, boolean selectionBorder) {
        Color fillColor = colorMap.get(this);
        for(Effect effect : effectMap.get(this)) {
            effect.apply(this, gc, fillColor);
        }
        gc.setFill(fillColor);
        if(!effectMap.get(this).contains(Effect.GRADIENT)) {
            drawFillWithOffset(gc, 0, 0);
        }
        if(!effectMap.get(this).contains(Effect.BEVELED) || selectionBorder) {
            Color borderColor = selectionBorder ? Color.RED : Color.BLACK;
            gc.setStroke(borderColor);
            drawBorder(gc);
        }
    }
    void drawShadow(GraphicsContext gc, Color color);
    void drawGradient(GraphicsContext gc, Color color);
    void drawBeveled(GraphicsContext gc, Color color);
    void drawFillWithOffset(GraphicsContext gc, double x, double y);
    void drawBorder(GraphicsContext gc);
}

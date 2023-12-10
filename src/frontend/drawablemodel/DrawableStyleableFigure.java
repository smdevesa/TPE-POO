package frontend.drawablemodel;

import backend.model.Figure;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Map;
import java.util.SortedSet;

public interface DrawableStyleableFigure extends DrawableFigure {


    //Metodo que se encarga de aplicar los efectos a la figura y dibujarla.
    default void draw(GraphicsContext gc, Map<Figure, Color> colorMap, Map<Figure, SortedSet<Effect>> effectMap, boolean selectionBorder) {
        Color fillColor = colorMap.get(this);
        for(Effect effect : effectMap.get(this)) {
            effect.apply(this, gc, fillColor);
        }
        if(!effectMap.get(this).contains(Effect.GRADIENT)) {
            gc.setFill(fillColor);
            drawFillWithOffset(gc, 0, 0);
        }
        Color borderColor = selectionBorder ? Color.RED : Color.BLACK;
        gc.setStroke(borderColor);
        drawBorder(gc);
    }
    default void drawShadow(GraphicsContext gc, Color color){
        gc.setFill(Color.GREY);
        drawFillWithOffset(gc, 10, 10);
    }
    void drawGradient(GraphicsContext gc, Color color);
    void drawBeveled(GraphicsContext gc, Color color);
    void drawFillWithOffset(GraphicsContext gc, double x, double y);
    void drawBorder(GraphicsContext gc);
}

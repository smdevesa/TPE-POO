package frontend.drawablemodel;

import backend.model.Figure;
import backend.model.Point;
import backend.model.Rectangle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

public class DrawableRectangle extends Rectangle implements DrawableFigure {

    public DrawableRectangle(Point topLeft, Point bottomRight) {
        super(topLeft, bottomRight);
    }

    @Override
    public void draw(GraphicsContext gc, Map<Figure, Color> colorMap, Map<Figure, SortedSet<Effect>> effectMap) {
        Color fillColor = colorMap.get(this);
        for(Effect effect : effectMap.get(this)) {
            effect.apply(this, gc, fillColor);
        }
        gc.setFill(fillColor);
        if(!effectMap.get(this).contains(Effect.GRADIENT)) {
            drawFillWithOffset(gc, 0, 0);
        }
        if(!effectMap.get(this).contains(Effect.BEVELED)) {
            drawBorder(gc);
        }
    }

    @Override
    public void drawShadow(GraphicsContext gc, Color color) {
        gc.setFill(Color.GREY);
        drawFillWithOffset(gc, 10, 10);
    }

    @Override
    public void drawGradient(GraphicsContext gc, Color color) {
        LinearGradient linearGradient = new LinearGradient(0, 0, 1, 0, true,
                CycleMethod.NO_CYCLE,
                new Stop(0, color),
                new Stop(1, color.invert()));
        gc.setFill(linearGradient);
        drawFillWithOffset(gc, 0, 0);
    }

    public void drawBeveled(GraphicsContext gc, Color color) {
        double x = getTopLeft().getX();
        double y = getTopLeft().getY();
        double width = Math.abs(x - getBottomRight().getX());
        double height = Math.abs(y - getBottomRight().getY());
        gc.setLineWidth(10);
        gc.setStroke(Color.LIGHTGRAY);
        gc.strokeLine(x, y, x + width, y);
        gc.strokeLine(x, y, x, y + height);
        gc.setStroke(Color.BLACK);
        gc.strokeLine(x + width, y, x + width, y + height);
        gc.strokeLine(x, y + height, x + width, y + height);
        gc.setLineWidth(1);
    }

    private void drawFillWithOffset(GraphicsContext gc, double x, double y) {
        gc.fillRect(getTopLeft().getX() + x, getTopLeft().getY() + y,
                Math.abs(getTopLeft().getX() - getBottomRight().getX()), Math.abs(getTopLeft().getY() - getBottomRight().getY()));
    }

    private void drawBorder(GraphicsContext gc) {
        gc.strokeRect(getTopLeft().getX(), getTopLeft().getY(),
                Math.abs(getTopLeft().getX() - getBottomRight().getX()), Math.abs(getTopLeft().getY() - getBottomRight().getY()));
    }
}

package frontend.drawablemodel;

import backend.model.Ellipse;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.ArcType;

public class DrawableEllipse extends Ellipse implements DrawableStyleableFigure {

    public DrawableEllipse(Point centerPoint, double sMayorAxis, double sMinorAxis) {
        super(centerPoint, sMayorAxis, sMinorAxis);
    }

/* CON ESTE METODO NO SE LE PUEDEN APLICAR EFECTOS A LAS ELIPSES (Y AHORA TAMPCOO A LOS CIRCLES). Tampoco se le dibuja el borde negro a elipse
    @Override
    public void draw(GraphicsContext gc, Map<Figure, Color> colorMap, Map<Figure, SortedSet<Effect>> effectMap, boolean selectionBorder) {
        gc.setFill(colorMap.get(this));
        gc.strokeOval(getCenterPoint().getX() - (getsMayorAxis() / 2), getCenterPoint().getY() - (getsMinorAxis() / 2), getsMayorAxis(), getsMinorAxis());
        gc.fillOval(getCenterPoint().getX() - (getsMayorAxis() / 2), getCenterPoint().getY() - (getsMinorAxis() / 2), getsMayorAxis(), getsMinorAxis());
    }*/

    @Override
    public void drawBeveled(GraphicsContext gc, Color color) {
        double arcX = getCenterPoint().getX() - getsMayorAxis()/2;
        double arcY = getCenterPoint().getY() - getsMinorAxis()/2;
        gc.setLineWidth(10);
        gc.setStroke(Color.LIGHTGRAY);
        gc.strokeArc(arcX, arcY, getsMayorAxis(), getsMinorAxis(), 45, 180, ArcType.OPEN);
        gc.setStroke(Color.BLACK);
        gc.strokeArc(arcX, arcY, getsMayorAxis(), getsMinorAxis(), 225, 180, ArcType.OPEN);
        gc.setLineWidth(1);
    }

    @Override
    public void drawGradient(GraphicsContext gc, Color color) {
        RadialGradient radialGradient = new RadialGradient(0, 0, 0.5, 0.5, 0.5, true,
                CycleMethod.NO_CYCLE,
                new Stop(0, color),
                new Stop(1, color.invert()));
        gc.setFill(radialGradient);
        drawFillWithOffset(gc, 0, 0);
    }

    @Override
    public void drawShadow(GraphicsContext gc, Color color) {
        gc.setFill(Color.GREY);
        drawFillWithOffset(gc, 10, 10);
    }

    @Override
    public void drawFillWithOffset(GraphicsContext gc, double x, double y) {
        gc.fillOval(getCenterPoint().getX() - (getsMayorAxis() / 2) + x, getCenterPoint().getY() - (getsMinorAxis() / 2) + y, getsMayorAxis(), getsMinorAxis());
    }

    @Override
    public void drawBorder(GraphicsContext gc) {
        gc.strokeOval(getCenterPoint().getX() - (getsMayorAxis() / 2), getCenterPoint().getY() - (getsMinorAxis() / 2), getsMayorAxis(), getsMinorAxis());
    }
}

package frontend.drawablemodel;

import backend.model.Figure;
import frontend.Status;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Map;
import java.util.SortedSet;

//Interfaz que se encarga de dibujar las figuras.
public interface DrawableFigure extends Figure {
    void draw(GraphicsContext gc, Map<Figure, Color> colorMap, Map<Figure, SortedSet<Effect>> effectMap, boolean selectionBorder);

    //Cuando la figura es simple, devuelve si tiene un efecto o no.
    default Status getStatus(Effect effect, Map<Figure, SortedSet<Effect>> effectMap) {
        return effectMap.get(this).contains(effect) ? Status.SELECTED : Status.UNSELECTED;
    }
}

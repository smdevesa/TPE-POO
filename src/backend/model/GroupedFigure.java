package backend.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;


//Clase para la Implementacion de Figuras Agrupadas.
public class GroupedFigure implements Figure {

    private final Collection<Figure> figures;

    public GroupedFigure(Collection<Figure> figures) {
        this.figures = figures;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Grupo [");
        doToAllComponents(figure -> {
            s.append(figure.toString());
            s.append(", ");
        });
        s.setLength(s.length() - 2);
        s.append("]");
        return s.toString();
    }


    //Override de metodo move para mover todas las figuras de la agrupacion en simultaneo.
    @Override
    public void move(double diffX, double diffY) {
        doToAllComponents(figure -> {
            figure.move(diffX,diffY);
        });
    }

    @Override
    public boolean belongs(Point point) {
        for (Figure figure : figures) {
            if (figure.belongs(point)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isInRectangle(Rectangle rectangle) {
        boolean isInRectangle = true;
        for (Figure figure : figures) {
            isInRectangle = isInRectangle && figure.isInRectangle(rectangle);
        }
        return isInRectangle;
    }

    @Override
    public List<Figure> getFigures() {
        return new ArrayList<>(figures);
    }


    //Override de los metodos para implementar las distintas funcionalidades del punto 3.
    @Override
    public void rotate(){
        doToAllComponents(Figure::rotate);
    }
    @Override
    public void scale(double size) {
        doToAllComponents(figure -> {
            figure.scale(size);
        });
    }

    @Override
    public void flipV() {
        doToAllComponents(Figure::flipV);
    }

    @Override
    public void flipH() {
        doToAllComponents(Figure::flipH);
    }

    private void doToAllComponents(Consumer<Figure>f){
        for(Figure figure : figures){
            f.accept(figure);
        }
    }
}

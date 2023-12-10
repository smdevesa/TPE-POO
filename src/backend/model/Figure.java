package backend.model;

import java.util.ArrayList;
import java.util.List;

//Interfaz Figure que se implementara en las distintas figuras.
public interface Figure {

    //Metodo para mover la figura.
    void move(double diffX, double diffY);

    //Metodo que se fija si el punto pertenece a la figura.
    boolean belongs(Point point);

    //Metodo que se fija si la figura esta dentro del rectangulo imaginario.
    boolean isInRectangle(Rectangle rectangle);

    //Retorna una lista con los componentes de la figura.
    default List<Figure> getFigures() {
        List<Figure> figures = new ArrayList<>();
        figures.add(this);
        return figures;
    }

    //Todos los metodos para implementar las distintas funcionalidades del punto 3.
     void rotate();

     void flipH();

    void flipV();

    void scale(double size);

}

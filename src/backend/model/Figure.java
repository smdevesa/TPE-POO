package backend.model;

public interface Figure {
    void move(double diffX, double diffY);

    boolean belongs(Point point);
}

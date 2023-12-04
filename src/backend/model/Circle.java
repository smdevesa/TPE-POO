package backend.model;

public class Circle extends Ellipse {

    public Circle(Point centerPoint, double radius) {
        super(centerPoint, radius*2, radius*2);
    }

    @Override
    public String toString() {
        return String.format("Círculo [Centro: %s, Radio: %.2f]", getCenterPoint(), getRadius());
    }

    public double getRadius() {
        return getsMayorAxis() / 2;
    }

    public boolean belongs(Point point){
        return Math.sqrt(Math.pow(getCenterPoint().getX() - point.getX(), 2) +
                Math.pow(getCenterPoint().getY() - point.getY(), 2)) < getRadius();
    }

    public boolean isInRectangle(Rectangle rectangle) {
        return (rectangle.getTopLeft().getX() < (getCenterPoint().getX() - getRadius()) && rectangle.getBottomRight().getX() > (getCenterPoint().getX() + getRadius())
                && rectangle.getTopLeft().getY() < (getCenterPoint().getY() - getRadius()) && rectangle.getBottomRight().getY() > (getCenterPoint().getY() + getRadius()));
    }


}

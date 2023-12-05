package backend.model;

public class Ellipse implements Figure {

    private final Point centerPoint;
    private double sMayorAxis, sMinorAxis;

    public Ellipse(Point centerPoint, double sMayorAxis, double sMinorAxis) {
        this.centerPoint = centerPoint;
        this.sMayorAxis = sMayorAxis;
        this.sMinorAxis = sMinorAxis;
    }

    @Override
    public String toString() {
        return String.format("Elipse [Centro: %s, DMayor: %.2f, DMenor: %.2f]", centerPoint, sMayorAxis, sMinorAxis);
    }

    public Point getCenterPoint() {
        return centerPoint;
    }

    public double getsMayorAxis() {
        return sMayorAxis;
    }

    public double getsMinorAxis() {
        return sMinorAxis;
    }

    public void setsMayorAxis(double sMayorAxis) {
        this.sMayorAxis = sMayorAxis;
    }

    public void setsMinorAxis(double sMinorAxis) {
        this.sMinorAxis = sMinorAxis;
    }

    @Override
    public boolean belongs(Point point){
        return (Math.pow(point.getX() - centerPoint.getX(), 2) / Math.pow(sMayorAxis, 2)) +
                (Math.pow(point.getY() - centerPoint.getY(), 2) / Math.pow(sMinorAxis, 2)) <= 0.30;
    }

    @Override
    public void move(double diffX, double diffY){
        centerPoint.move(diffX, diffY);
    }

    public boolean isInRectangle(Rectangle rectangle){
        return (rectangle.getTopLeft().getX() < (centerPoint.getX() - (sMayorAxis/2)) && rectangle.getBottomRight().getX() > (centerPoint.getX() + (sMayorAxis/2))
                && rectangle.getTopLeft().getY() < (centerPoint.getY() - (sMinorAxis/2)) && rectangle.getBottomRight().getY() > (centerPoint.getY() + (sMinorAxis/2)));
    }

    @Override
    public void rotate(){
        double aux = sMayorAxis;
        sMayorAxis = sMinorAxis;
        sMinorAxis = aux;
    }

    @Override
    public void flipH(){
       move(sMayorAxis,0);
    }

    @Override
    public void flipV() {
        move(0, sMinorAxis);
    }

    @Override
    public void scale(double size){
        sMinorAxis *= size;
        sMayorAxis *= size;
    }
}

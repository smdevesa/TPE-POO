package backend.model;

import java.util.Objects;

public class Rectangle implements Figure {

    private Point topLeft, bottomRight; //era final

    public Rectangle(Point topLeft, Point bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public Point getBottomRight() {
        return bottomRight;
    }

    public void move(double diffX, double diffY){
        topLeft.move(diffX, diffY);
        bottomRight.move(diffX, diffY);
    }

    public boolean belongs(Point point){
        return point.getX() > topLeft.getX() && point.getX() < bottomRight.getX() &&
                point.getY() > topLeft.getY() && point.getY() < bottomRight.getY();
    }

    @Override
    public String toString() {
        return String.format("Rectángulo [ %s , %s ]", topLeft, bottomRight);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        if(obj instanceof Rectangle r) {
            return topLeft.equals(r.topLeft) && bottomRight.equals(r.bottomRight);
        }
        return false;
    }

    public boolean isInRectangle(Rectangle rectangle){
        return rectangle.getTopLeft().getX() < topLeft.getX() && rectangle.getTopLeft().getY() < topLeft.getY()
                && rectangle.getBottomRight().getX() > bottomRight.getX() && rectangle.getBottomRight().getY() > bottomRight.getY();
    }

    @Override
    public void rotate(){
        topLeft.move(getDisX()/2 + getDisY()/2 - getDisY(), -(getDisX()/2 - getDisY()/2));
        bottomRight.move(-(getDisX()/2 + getDisY()/2) + getDisY(), getDisX()/2 - getDisY()/2);
    }

    public void flipH(){
        move(getDisX(),0 );
    }

    public void flipV(){
        move(0, getDisY());
    }

    public void scale(double size ){
        double mult = Math.abs((1-size)/4);
        if(size >= 1){
            topLeft.move(-getDisX()* mult,-getDisY()* mult);
            bottomRight.move(getDisX()* mult,getDisY()* mult);
        }
        else{
            topLeft.move(getDisX()* mult,getDisY()* mult);
            bottomRight.move(-getDisX()*mult,-getDisY()* mult);
        }

    }

    private double getDisX(){
        return bottomRight.getX() - topLeft.getX();
    }
    private double getDisY(){
        return bottomRight.getY() - topLeft.getY();
    }
}

package memento;

import java.awt.geom.Point2D;

public class CircleMemento implements GraphicObjectMemento {
    private final Point2D position;
    private final double radius;

    public CircleMemento(Point2D position, double radius) {
        this.position = position;
        this.radius = radius;
    }

    public Point2D getPosition() {
        return position;
    }

    public double getRadius() {
        return radius;
    }
}
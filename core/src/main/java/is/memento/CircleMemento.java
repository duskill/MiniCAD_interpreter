package is.memento;

import is.shapes.model.groups.Group;

import java.awt.geom.Point2D;

public class CircleMemento implements GraphicObjectMemento {
    private final Point2D position;
    private final double radius;
    private final Group parent;

    public CircleMemento(Point2D position, double radius, Group parent) {
        this.position = position;
        this.radius = radius;
        this.parent = parent;
    }

    public Point2D getPosition() {
        return position;
    }

    public double getRadius() {
        return radius;
    }

    public Group getParent() {
        return parent;
    }

}
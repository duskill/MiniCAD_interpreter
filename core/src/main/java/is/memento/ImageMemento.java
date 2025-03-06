package is.memento;

import is.shapes.model.groups.Group;

import java.awt.geom.Point2D;

public class ImageMemento implements GraphicObjectMemento {
    private final Point2D position;
    private final double scaleFactor;
    private final Group parent;

    public ImageMemento(Point2D position, double scaleFactor, Group parent) {
        this.position = position;
        this.scaleFactor = scaleFactor;
        this.parent = parent;
    }

    public Point2D getPosition() {
        return position;
    }

    public double getScaleFactor() {
        return scaleFactor;
    }

    public Group getParent() {
        return parent;
    }
}
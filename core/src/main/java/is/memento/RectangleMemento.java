package is.memento;

import is.shapes.model.groups.Group;

import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;

public class RectangleMemento implements GraphicObjectMemento {
    private final Point2D position;
    private final Dimension2D dim;
    private final Group parent;

    public RectangleMemento(Point2D position, Dimension2D dim, Group parent ) {
        this.position = position;
        this.dim = dim;
        this.parent = parent;
    }

    public Point2D getPosition() {
        return position;
    }

    public Dimension2D getDimension() {
        return dim;
    }

    public Group getParent() {
        return parent;
    }
}
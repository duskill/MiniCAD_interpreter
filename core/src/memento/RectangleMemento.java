package memento;

import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;

public class RectangleMemento implements GraphicObjectMemento {
    private final Point2D position;
    private final Dimension2D dim;

    public RectangleMemento(Point2D position, Dimension2D dim ) {
        this.position = position;
        this.dim = dim;
    }

    public Point2D getPosition() {
        return position;
    }

    public Dimension2D getDimension() {
        return dim;
    }

}
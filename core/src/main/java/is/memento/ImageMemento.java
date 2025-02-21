package is.memento;

import java.awt.geom.Point2D;

public class ImageMemento implements GraphicObjectMemento {
    private final Point2D position;
    private final double scaleFactor;

    public ImageMemento(Point2D position, double scaleFactor) {
        this.position = position;
        this.scaleFactor = scaleFactor;
    }

    public Point2D getPosition() {
        return position;
    }

    public double getScaleFactor() {
        return scaleFactor;
    }
}
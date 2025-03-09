package is.shapes.model;

import is.shapes.calculationStrategy.RectangleImageCalculationStrategy;
import is.memento.GraphicObjectMemento;
import is.memento.RectangleMemento;

import java.awt.Dimension;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;

public final class RectangleObject extends AbstractGraphicObject {

	private Point2D position;
	private Dimension2D dim;

	public RectangleObject(Point2D pos, double w, double h) {
		super(new RectangleImageCalculationStrategy(w, h)); // aggiunta la strategy per il calcolo di area e perimetro
		if (w <= 0 || h <= 0)
			throw new IllegalArgumentException();
		dim = new Dimension();
		dim.setSize(w, h);
		position = new Point2D.Double(pos.getX(), pos.getY());
	}

	@Override
	public boolean contains(Point2D p) {
		double w = dim.getWidth() / 2;
		double h = dim.getHeight() / 2;
		double dx = Math.abs(p.getX() - position.getX());
		double dy = Math.abs(p.getY() - position.getY());
		return dx <= w && dy <= h;

	}

	@Override
	public void moveTo(Point2D p) {
		position.setLocation(p);
		notifyListeners(new GraphicEvent(this));
	}

	@Override
	public Point2D getPosition() {

		return new Point2D.Double(position.getX(), position.getY());
	}

	@Override
	public void scale(double factor) {
		if (factor <= 0)
			throw new IllegalArgumentException();
		dim.setSize(dim.getWidth() * factor, dim.getHeight() * factor);
		notifyListeners(new GraphicEvent(this));
	}

	@Override
	public Dimension2D getDimension() {
		Dimension2D d = new Dimension();
		d.setSize(dim);
		return d;
	}

	@Override
	public RectangleObject clone() {
		RectangleObject cloned = (RectangleObject) super.clone();
		cloned.position = (Point2D) position.clone();
		cloned.dim = (Dimension2D) dim.clone();
		return cloned;
	}

	@Override
	public String getType() {

		return "Rectangle";
	}

	@Override
	public GraphicObjectMemento saveState() {
		return new RectangleMemento(getPosition(), getDimension(), getParent());
	}

	@Override
	public void restoreState(GraphicObjectMemento memento) {
		if (!(memento instanceof RectangleMemento)) {
			throw new IllegalArgumentException("Invalid Memento for Rectangle");
		}
		RectangleMemento rectangleMemento = (RectangleMemento) memento;
		this.position = (rectangleMemento.getPosition());
		this.dim = rectangleMemento.getDimension();
		this.setParent(rectangleMemento.getParent());
		notifyListeners(new GraphicEvent(this));
	}

	@Override
	public String toString() {
		return "Oggetto id: "+ getId() + " di tipo " + getType() + " in posizione (" + getPosition().getX() + "," + getPosition().getY() +") e dimensioni " + getDimension().getWidth() + "x" + getDimension().getHeight();
	}
}

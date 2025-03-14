package is.shapes.model;

import is.shapes.calculationStrategy.CircleCalculationStrategy;
import is.memento.CircleMemento;
import is.memento.GraphicObjectMemento;

import java.awt.Dimension;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;

public final  class CircleObject extends AbstractGraphicObject {

	private Point2D position;
	private double radius;


	public CircleObject(Point2D pos, double r) {
		super(new CircleCalculationStrategy(r)); // aggiunta la strategy per il calcolo di area e perimetro
		if (r <= 0)
			throw new IllegalArgumentException();
		position = new Point2D.Double(pos.getX(), pos.getY());
		radius = r;
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
		radius *= factor;
		notifyListeners(new GraphicEvent(this));
	}

	@Override
	public Dimension2D getDimension() {
		Dimension d = new Dimension();
		d.setSize(2 * radius, 2 * radius);

		return d;
	}

	@Override
	public boolean contains(Point2D p) {
		return (position.distance(p) <= radius);

	}

	@Override
	public CircleObject clone() {
		CircleObject cloned = (CircleObject) super.clone();
		cloned.position = (Point2D) position.clone();
		return cloned;
	}

	@Override
	public String getType() {

		return "Circle";
	}

	public double getRadius() {
		return radius;
	}

	@Override
	public GraphicObjectMemento saveState() {
		return new CircleMemento(getPosition(), radius, getParent());
	}

	@Override
	public void restoreState(GraphicObjectMemento memento) {
		if (memento instanceof CircleMemento) {
			CircleMemento circleMemento = (CircleMemento) memento;
			this.position = circleMemento.getPosition();
			this.radius = circleMemento.getRadius();
			this.setParent(circleMemento.getParent());
			notifyListeners(new GraphicEvent(this));
		} else {
			throw new IllegalArgumentException("Invalid Memento for Circle");
		}
	}

	@Override
	public String toString() {
		return "Oggetto id: "+ getId() + " di tipo " + getType() + " in posizione (" + getPosition().getX() + "," + getPosition().getY() +") e raggio " + getRadius();
	}
}

package is.shapes.model;

import is.shapes.calculationStrategy.RectangleImageCalculationStrategy;
import is.memento.GraphicObjectMemento;
import is.memento.ImageMemento;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;

import javax.swing.ImageIcon;

public final class ImageObject extends AbstractGraphicObject {
	private double factor = 1.0;

	private final Image image;

	private Point2D position;

	public Image getImage() {
		return image;
	}

	public ImageObject(ImageIcon img, Point2D pos) {
        super(new RectangleImageCalculationStrategy(0,0)); // aggiunta la strategy per il calcolo di area e perimetro
		RectangleImageCalculationStrategy s = (RectangleImageCalculationStrategy) this.getCalculationStrategy();
		image = img.getImage();
		s.setWidth(this.getDimension().getWidth());
		s.setHeight(this.getDimension().getHeight());
        position = new Point2D.Double(pos.getX(), pos.getY());
	}

	@Override
	public boolean contains(Point2D p) {
		double w = (factor * image.getWidth(null)) / 2;
		double h = (factor * image.getHeight(null)) / 2;
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
	public ImageObject clone() {
		ImageObject cloned = (ImageObject) super.clone();
		cloned.position = (Point2D) position.clone();
		return cloned;

	}

	@Override
	public Point2D getPosition() {

		return new Point2D.Double(position.getX(), position.getY());
	}

	@Override
	public void scale(double factor) {
		if (factor <= 0)
			throw new IllegalArgumentException();
		this.factor *= factor;
		notifyListeners(new GraphicEvent(this));
	}

	@Override
	public Dimension2D getDimension() {
		Dimension dim = new Dimension();
		dim.setSize(factor * image.getWidth(null),
				factor * image.getHeight(null));
		return dim;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see is.shapes.GraphicObject#getType()
	 */
	@Override
	public String getType() {

		return "Image";
	}

	@Override
	public GraphicObjectMemento saveState() {
		return new ImageMemento(getPosition(), factor, getParent());
	}

	@Override
	public void restoreState(GraphicObjectMemento memento) {
		if (!(memento instanceof ImageMemento)) {
			throw new IllegalArgumentException("Invalid Memento for Image");
		}
		ImageMemento imageMemento = (ImageMemento) memento;
		this.position = imageMemento.getPosition();
		this.factor = imageMemento.getScaleFactor();
		this.setParent(imageMemento.getParent());
		notifyListeners(new GraphicEvent(this));
	}

	public String toString() {
		return "Oggetto id: "+ getId() + " di tipo " + getType() + " in posizione (" + getPosition().getX() + "," + getPosition().getY() +") e dimensioni " + getDimension().getWidth() + "x" + getDimension().getHeight();
	}

}

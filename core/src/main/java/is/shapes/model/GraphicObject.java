package is.shapes.model;

import is.shapes.calculationStrategy.ShapeCalculationStrategy;
import is.memento.GraphicObjectMemento;
import is.shapes.model.groups.Group;

import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;

public interface GraphicObject {

	void addGraphicObjectListener(GraphicObjectListener l);

	void removeGraphicObjectListener(GraphicObjectListener l);

	void moveTo(Point2D p);

	default void moveTo(double x, double y){
		moveTo(new Point2D.Double(x, y));
	}

	Point2D getPosition();

	Integer getId();

	Dimension2D getDimension();

	void scale(double factor);

	boolean contains(Point2D p);

	String getType();

	GraphicObjectMemento saveState();

	void restoreState(GraphicObjectMemento memento);

	ShapeCalculationStrategy getCalculationStrategy();

	Group getParent();
	void setParent(Group parent);
}

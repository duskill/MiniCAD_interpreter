package is.shapes.model;



import is.shapes.calculationStrategy.ShapeCalculationStrategy;
import is.shapes.model.groups.Group;


import java.util.LinkedList;
import java.util.List;

public abstract class AbstractGraphicObject implements GraphicObject, Cloneable {

	private  List<GraphicObjectListener> listeners = new LinkedList<>();
	private final ShapeCalculationStrategy calculationStrategy; // aggiunta la strategy per il calcolo di area e perimetro
	private static int idCounter = 0;  // Contatore globale degli ID
	private final int id;  // ID univoco dell'oggetto
	private Group parent = null; //mantengo un riferimento al genitore per i composite

	public AbstractGraphicObject(ShapeCalculationStrategy calculationStrategy) {
		this.calculationStrategy = calculationStrategy;
		this.id = idCounter++;
	}

	public Integer getId() {
		return id;
	}

	public Group getParent() {
		return parent;
	}

	public void setParent(Group parent) {
		this.parent = parent;
	}

	@Override
	public void addGraphicObjectListener(GraphicObjectListener l) {
		if (listeners.contains(l))
			return;
		listeners.add(l);
	}

	@Override
	public void removeGraphicObjectListener(GraphicObjectListener l) {
		listeners.remove(l);

	}

	protected void notifyListeners(GraphicEvent e) {

		for (GraphicObjectListener gol : listeners)

			gol.graphicChanged(e);

	}


	@Override
	public GraphicObject clone() {
		try {
			AbstractGraphicObject go = (AbstractGraphicObject) super.clone();
			go.listeners = new LinkedList<>();
			return go;
		} catch (CloneNotSupportedException e) {
			throw new Error(e);
		}
	}

	public double getArea() {
		return calculationStrategy.calculateArea();
	}

	public double getPerimeter() {
		return calculationStrategy.calculatePerimeter();
	}

	public ShapeCalculationStrategy getCalculationStrategy() {
		return calculationStrategy;
	}

	@Override
	public String toString() {
		return this.getId().toString();
	}
}

package is.shapes.specificcommand;

import is.command.Command;
import is.shapes.model.GraphicObject;
import is.memento.GraphicObjectMemento;
import java.awt.geom.Point2D;

public class MoveCommand implements Command {
	private final GraphicObject object;
	private final Point2D newPos;
	private GraphicObjectMemento prevState; // Stato precedente

	public MoveCommand(GraphicObject obj, Point2D pos) {
		this.object = obj;
		this.newPos = pos;
		this.prevState = createMemento(); // Salva lo stato iniziale
	}

	@Override
	public boolean doIt() {
		object.moveTo(newPos);
		return true;
	}

	@Override
	public boolean undoIt() {
		if (prevState != null) {
			object.restoreState(prevState);
			return true;
		}
		return false;
	}

	@Override
	public GraphicObjectMemento createMemento() {
		return object.saveState();
	}

	@Override
	public void restoreMemento(GraphicObjectMemento memento) {
		this.prevState = memento;
	}
}
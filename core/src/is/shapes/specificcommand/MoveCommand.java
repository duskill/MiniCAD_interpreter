package is.shapes.specificcommand;

import is.command.Command;
import is.shapes.model.GraphicObject;
import memento.GraphicObjectMemento;

import java.awt.geom.Point2D;

public class MoveCommand implements Command {
	private final GraphicObject object;
	private final Point2D newPos;
	private GraphicObjectMemento prevState; // Stato precedente

	public MoveCommand(GraphicObject obj, Point2D pos) {
		this.object = obj;
		this.newPos = pos;
		this.prevState = obj.saveState();
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
	public GraphicObjectMemento getMemento() {
		return prevState;
	}

	@Override
	public void setMemento(GraphicObjectMemento memento) {
		this.prevState = memento;
	}
}
package is.shapes.specificcommand;

import is.command.Command;
import is.shapes.model.GraphicObject;
import is.memento.GraphicObjectMemento;

public class ZoomCommand implements Command {
	private final GraphicObject object;
	private final double factor;
	private GraphicObjectMemento prevState;

	public ZoomCommand(GraphicObject obj, double factor) {
		this.object = obj;
		this.factor = factor;
		this.prevState = createMemento();
	}

	@Override
	public boolean doIt() {
		object.scale(factor);
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
package is.shapes.specificcommand;

import is.command.Command;
import is.shapes.model.GraphicObject;
import memento.GraphicObjectMemento;

public class ScaleCommand implements Command {
    private final GraphicObject object;
    private final double scaleFactor;
    private GraphicObjectMemento prevState;

    public ScaleCommand(GraphicObject obj, double scaleFactor) {
        this.object = obj;
        this.scaleFactor = scaleFactor;
        this.prevState = obj.saveState();
    }

    @Override
    public boolean doIt() {
        object.scale(scaleFactor);
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
        object.restoreState(memento);
    }
}
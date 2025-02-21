package is.shapes.specificcommand;

import is.command.Command;
import is.shapes.model.GraphicObject;
import memento.GraphicObjectMemento;

import java.awt.geom.Point2D;

public class MoveOffsetCommand implements Command {
    private final GraphicObject object;
    private final Point2D offset;
    private GraphicObjectMemento prevState;

    public MoveOffsetCommand(GraphicObject obj, Point2D offset) {
        this.object = obj;
        this.offset = offset;
        this.prevState = obj.saveState();
    }

    @Override
    public boolean doIt() {
        object.moveTo(new Point2D.Double(
                object.getPosition().getX() + offset.getX(),
                object.getPosition().getY() + offset.getY()
        ));
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
package is.shapes.specificcommand;

import is.command.Command;
import is.shapes.model.groups.Group;
import is.shapes.model.GraphicObject;
import is.memento.GraphicObjectMemento;

import java.util.List;

public class GroupCommand implements Command {
    private final List<GraphicObject> objects;
    private Group group;
    private GraphicObjectMemento prevState;

    public GroupCommand(List<GraphicObject> objects) {
        this.objects = objects;
    }

    @Override
    public boolean doIt() {
        group = new Group();
        for (GraphicObject object : objects) {
            group.add(object);
        }
        prevState = group.saveState();
        return true;
    }

    @Override
    public boolean undoIt() {
        if (prevState != null) {
            group.restoreState(prevState);
            return true;
        }
        return false;
    }

    @Override
    public GraphicObjectMemento createMemento() {
        return group.saveState();
    }

    @Override
    public void restoreMemento(GraphicObjectMemento memento) {
        group.restoreState(memento);
    }
}
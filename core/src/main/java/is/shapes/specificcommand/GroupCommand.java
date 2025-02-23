package is.shapes.specificcommand;

import is.command.Command;
import is.shapes.model.groups.Group;
import is.shapes.model.GraphicObject;
import is.memento.GraphicObjectMemento;
import is.shapes.model.groups.GroupManager;

import java.util.List;

public class GroupCommand implements Command {
    private final List<GraphicObject> objects;
    private Group group;
    private GraphicObjectMemento prevState;
    private final GroupManager groupManager;

    public GroupCommand(List<GraphicObject> objects, GroupManager groupManager) {
        this.objects = objects;
        this.groupManager = groupManager;
    }

    @Override
    public boolean doIt() {
        int gid = groupManager.createGroup();
        group = groupManager.getGroup(gid);
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
    public void restoreMemento() {
        group.restoreState(prevState);
    }

    public GraphicObjectMemento getMemento() {
        return this.prevState;
    }
}
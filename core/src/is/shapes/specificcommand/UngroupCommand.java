package is.shapes.specificcommand;

import is.command.Command;
import is.shapes.model.groups.Group;
import is.shapes.model.groups.GroupManager;
import memento.GraphicObjectMemento;

public class UngroupCommand implements Command {
    private final int groupID;
    private final GroupManager groupManager;
    private GraphicObjectMemento prevState;

    public UngroupCommand(int groupID, GroupManager groupManager) {
        this.groupID = groupID;
        this.groupManager = groupManager;
        Group group = groupManager.getGroup(groupID);
        if (group != null) {
            this.prevState = group.saveState();
        } else {
            this.prevState = null;
        }
    }

    @Override
    public boolean doIt() {
        return groupManager.deleteGroup(groupID);
    }

    @Override
    public boolean undoIt() {
        if (prevState != null) {
            int restoredID = groupManager.createGroup(); // Crea un nuovo gruppo con un nuovo ID
            Group restoredGroup = groupManager.getGroup(restoredID);
            restoredGroup.restoreState(prevState); // Ripristina lo stato del gruppo eliminato
            return true;
        }
        return false;
    }

    @Override
    public GraphicObjectMemento createMemento() {
        Group group = groupManager.getGroup(groupID);
        return (group != null) ? group.saveState() : null;
    }

    @Override
    public void restoreMemento(GraphicObjectMemento memento) {
        if (memento != null) {
            int restoredID = groupManager.createGroup();
            Group restoredGroup = groupManager.getGroup(restoredID);
            restoredGroup.restoreState(memento);
        }
    }
}
package is.shapes.specificcommand;

import is.command.Command;
import is.shapes.model.GraphicObject;
import is.shapes.model.groups.Group;
import is.shapes.view.GraphicObjectPanel;
import is.memento.GraphicObjectMemento;

import java.util.Iterator;

public class DeleteCommand implements Command {
    private final GraphicObjectPanel panel;
    private final GraphicObject object;
    private GraphicObjectMemento prevState;


    public DeleteCommand(GraphicObjectPanel panel, GraphicObject object) {
        this.panel = panel;
        this.object = object;
        this.prevState = object.saveState();
    }

    @Override
    public boolean doIt() {
        if (object.getType().equalsIgnoreCase("group")){
            Group g = (Group) object;
            Iterator<GraphicObject> it = g.createIterator();
            while (it.hasNext()){
                panel.remove((GraphicObject) it.next());
            }
        }
        panel.remove(object);
        return true;
    }

    @Override
    public boolean undoIt() {
        if (prevState != null) {
            object.restoreState(prevState);
            panel.add(object);
            return true;
        }
        return false;
    }

    @Override
    public GraphicObjectMemento createMemento() {
        return object.saveState();
    }

    @Override
    public void restoreMemento() {
        object.restoreState(prevState);
    }

    public GraphicObjectMemento getMemento() {
        return this.prevState;
    }
}
package is.interpreter;

import is.command.Command;
import is.shapes.model.GraphicObject;
import is.shapes.model.groups.GroupManager;
import is.shapes.specificcommand.GroupCommand;
import is.shapes.view.GraphicObjectPanel;

import java.util.ArrayList;
import java.util.List;

public class GroupExpression implements Expression {
    private final List<Integer> objectIds;
    private final GroupManager groupManager;
    private final GraphicObjectPanel panel;

    public GroupExpression(List<Integer> objectIds, GraphicObjectPanel panel) {
        this.objectIds = objectIds;
        this.groupManager = GroupManager.getInstance(panel);
        this.panel = panel;
    }

    @Override
    public Command interpret() {
        List<GraphicObject> objects = new ArrayList<>();

        for (Integer id : objectIds) {
            GraphicObject obj = panel.getObjectById(id);
            if (obj != null) {
                objects.add(obj);
            } else {
                throw new IllegalArgumentException("Oggetto non trovato con ID: " + id);
            }
        }

        return new GroupCommand(objects, groupManager);
    }
}

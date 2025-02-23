package is.interpreter;

import is.command.Command;
import is.shapes.model.GraphicObject;
import is.shapes.model.groups.GroupManager;
import is.shapes.specificcommand.GroupCommand;
import is.shapes.view.GraphicObjectPanel;

import java.util.ArrayList;
import java.util.List;

public class GroupExpression implements Expression {
    private final List<String> objectIds;
    private final GroupManager groupManager;
    private final GraphicObjectPanel panel;

    public GroupExpression(String objectIds, GraphicObjectPanel panel) {
        this.objectIds = parseIds(objectIds);
        this.groupManager = GroupManager.getInstance();
        this.panel = panel;
    }

    @Override
    public Command interpret() {
        List<GraphicObject> objects = new ArrayList<>();

        for (String id : objectIds) {
            GraphicObject obj = panel.getObjects().get(Integer.parseInt(id));
            if (obj != null) {
                objects.add(obj);
            } else {
                throw new IllegalArgumentException("Oggetto non trovato con ID: " + id);
            }
        }

        return new GroupCommand(objects, groupManager);
    }

    private List<String> parseIds(String ids) {
        String[] splitIds = ids.split(",");
        List<String> idList = new ArrayList<>();
        for (String id : splitIds) {
            idList.add(id.trim()); // Rimuove eventuali spazi
        }
        return idList;
    }
}

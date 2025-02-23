package is.shapes.specificcommand;

import is.command.Command;
import is.shapes.model.GraphicObject;
import is.shapes.model.groups.GroupManager;
import is.shapes.model.groups.Group;
import is.shapes.view.GraphicObjectPanel;
import is.memento.GraphicObjectMemento;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ListCommand implements Command {
    private final String argument;
    private final GroupManager groupManager;
    private final GraphicObjectPanel panel;

    public ListCommand(String argument, GroupManager groupManager, GraphicObjectPanel panel) {
        this.argument = argument;
        this.groupManager = groupManager;
        this.panel = panel;
    }

    @Override
    public boolean doIt() {
        String result;
        switch (argument.toLowerCase()) {
            case "all": // se l'input è all chiama la funzione per generare la lista di tutti gli oggetti
                result = listAllObjects();
                break;
            case "groups":
                result = listAllGroups(); // se l'input è groups chiama la funzione per generare la lista di tutti i gruppi
                break;
            default:
                if (isNumeric(argument)) { // se l'input è un numero lo valuta come id di un oggetto
                    result = listById(Integer.parseInt(argument));
                } else {
                    result = listByType(argument); // altrimenti assume che sia un tipo di oggetto
                }
        }
        System.out.println(result);
        return true;
    }

    @Override
    public boolean undoIt() {
        return false; // Il comando ls è di sola lettura, quindi non ha undo
    }

    @Override
    public GraphicObjectMemento createMemento() {
        return null; // Non necessario per ls
    }

    @Override
    public void restoreMemento() {
        // Non necessario per ls
    }

    private String listById(int id) {
        if (groupManager.getAllGroupIds().contains(id)) {
            Group group = groupManager.getGroup(id);
            return "Gruppo " + id + ": " + group.getChildren();
        } else {
                if (panel.getObjects().size() <= id) {
                    return "Oggetto " + id + ": " + panel.getObjects().get(id);
                    }
                }
        return "Nessun oggetto o gruppo trovato con ID " + id;
    }


    private String listByType(String type) {
        LinkedList<GraphicObject> ret = new LinkedList<>();
        for(GraphicObject object : panel.getObjects()) {
            if (object.getType().equals(type)) {
                ret.add(object);
            }
        }
        return "Tutti gli oggetti di tipo" + type + ": " + ret;
    }

    private String listAllObjects() {
        List<GraphicObject> objects = panel.getObjects();
        return objects.isEmpty() ? "Nessun oggetto presente" : "Tutti gli oggetti: " + objects;
    }

    private String listAllGroups() {
        Set<Integer> groupIds = groupManager.getAllGroupIds();
        return groupIds.isEmpty() ? "Nessun gruppo presente" : "Tutti i gruppi: " + groupIds;
    }

    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public GraphicObjectMemento getMemento() {
        return null;
    }
}
package is.memento;
import is.shapes.model.groups.Group;

import java.util.List;

public class GroupMemento implements GraphicObjectMemento {
    private final List<GraphicObjectMemento> childrenMementos;
    private final Group parent;

    public GroupMemento(List<GraphicObjectMemento> childrenMementos, Group parent) {
        this.childrenMementos = List.copyOf(childrenMementos); // Copia immutabile per sicurezza
        this.parent = parent;
    }

    public List<GraphicObjectMemento> getChildrenMementos() {
        return childrenMementos;
    }
    public Group getParent() {
        return parent;
    }
}
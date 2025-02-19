package memento;
import java.util.List;

public class GroupMemento implements GraphicObjectMemento {
    private final List<GraphicObjectMemento> childrenMementos;

    public GroupMemento(List<GraphicObjectMemento> childrenMementos) {
        this.childrenMementos = List.copyOf(childrenMementos); // Copia immutabile per sicurezza
    }

    public List<GraphicObjectMemento> getChildrenMementos() {
        return childrenMementos;
    }
}
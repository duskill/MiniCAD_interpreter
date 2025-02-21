package is.shapes.model.groups;

import is.shapes.model.GraphicObject;

import java.util.*;

/**
 * Iteratore per attraversare i gruppi (o gruppi di gruppi) composite in ordine depth-first,
 * in modo che le operazioni avvengano gruppo per gruppo
 */
public class GroupIterator implements Iterator<GraphicObject> { //implemento l'interfaccia Iterator
    private final Stack<Iterator<GraphicObject>> stack = new Stack<>(); //definisco uno stack utile per tenere traccia degli iteratori dei vari gruppi in ordine
    private GraphicObject nextObject; //mantiene il prossimo oggetto o gruppo da restituire durante l'iterazione

    public GroupIterator(Group radice) {
        if (radice != null) {
            stack.push(radice.getChildren().iterator()); //inserisco nello stack il contenuto del gruppo di partenza
            avanza(); //avanzo trovadno il primo oggetto da restituire
        }
    }//costruttore

    private void avanza() {
        nextObject = null;
        while (!stack.isEmpty()) {
            Iterator<GraphicObject> iterator = stack.peek(); //se lo stack non è vuoto prelevo il primo iteratore
            if (!iterator.hasNext()) {
                stack.pop();// se non rimangono oggetti nel gruppo relativo, elimino l'iteratore dallo stack
            } else {
                GraphicObject obj = iterator.next(); //valuto il caso in cui ci siano ancora oggetti nel gruppo
                if (obj instanceof Group) {
                    stack.push(((Group) obj).getChildren().iterator()); //se l'oggetto è un gruppo aggiungo il suo iteratore allo stack
                } else {
                    nextObject = obj; //se l'oggetto è una foglia allora diventa il next object
                    return;
                }
            }
        }
    }//avanza-> avanza nella visita dell'albero trovando il prossimo elemento da restituire

    @Override
    public boolean hasNext() {
        return nextObject != null;
    }//hasNext-> restituisce true se esiste un prossimo elemento, false altrimenti

    @Override
    public GraphicObject next() {
        if (!hasNext()) {
            throw new NoSuchElementException("Non ci sono più oggetti su cui iterare."); //se non esiste un prossimo lancio un'eccezione
        }
        GraphicObject result = nextObject; //salvo l'elemento da restituire prima di avanzare
        avanza();
        return result;
    }//next-> restituisce il prossimo oggetto
}

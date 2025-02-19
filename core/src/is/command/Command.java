package is.command;

import memento.GraphicObjectMemento;

public interface Command {
	boolean doIt();
	boolean undoIt();
	GraphicObjectMemento createMemento();  // metodo per salvare lo stato prima di eseguire il comando
	void restoreMemento(GraphicObjectMemento memento);  // metodo per ripristinare lo stato

}
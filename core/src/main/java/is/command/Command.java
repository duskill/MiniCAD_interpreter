package is.command;

import is.memento.GraphicObjectMemento;

public interface Command {
	boolean doIt();
	boolean undoIt();
	GraphicObjectMemento createMemento();  // metodo per salvare lo stato prima di eseguire il comando
	void restoreMemento();  // metodo per ripristinare lo stato
	GraphicObjectMemento getMemento();
}
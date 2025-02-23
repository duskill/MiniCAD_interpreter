package is.shapes.specificcommand;

import is.command.Command;
import is.shapes.model.GraphicObject;
import is.shapes.view.GraphicObjectPanel;
import is.memento.GraphicObjectMemento;

public class NewObjectCmd implements Command {
	private final GraphicObjectPanel panel;
	private final GraphicObject go;
	private boolean added = false;
	private GraphicObjectMemento prevState;

	public NewObjectCmd(GraphicObjectPanel panel, GraphicObject go) {
		this.panel = panel;
		this.go = go;
		this.prevState = createMemento(); // Salva lo stato iniziale (potrebbe non servire ma lo rende coerente agli altri comandi)
	}

	@Override
	public boolean doIt() {
		panel.add(go);
		added = true;
		return true;
	}

	@Override
	public boolean undoIt() {
		// Se il comando viene annullato controlla se l'elemento è stato effettivamente aggiunto e in caso lo rimuove
		if (added) {
			panel.remove(go);
			added = false;
			return true;
		}
		return false;
	}

	@Override
	public GraphicObjectMemento createMemento() {
		return go.saveState();
	}

	@Override
	public void restoreMemento() {
		go.restoreState(prevState);
	}

	public GraphicObjectMemento getMemento() {
		return this.prevState;
	}
}
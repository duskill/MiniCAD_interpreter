package is.command;
import is.memento.GraphicObjectMemento;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class HistoryCommandHandler implements CommandHandler {

	private int maxHistoryLength = 100;

	private final LinkedList<Command> history = new LinkedList<>();
	private final LinkedList<Command> redoList = new LinkedList<>();
	private final Map<Command, GraphicObjectMemento> mementoMap = new HashMap<>();

	public HistoryCommandHandler() {
		this(100);
	}

	public HistoryCommandHandler(int maxHistoryLength) {
		if (maxHistoryLength < 0)
			throw new IllegalArgumentException();
		this.maxHistoryLength = maxHistoryLength;
	}

	@Override
	public void handle(Command cmd) {
		// Salviamo lo stato prima dell'esecuzione
		GraphicObjectMemento memento = cmd.createMemento();
		if (memento != null) {
			mementoMap.put(cmd, memento);
		}

		if (cmd.doIt()) {
			addToHistory(cmd);
		} else {
			history.clear();
		}

		if (!redoList.isEmpty()) {
			redoList.clear();
		}
	}

	public void redo() {
		if (!redoList.isEmpty()) {
			Command redoCmd = redoList.removeFirst();
			handle(redoCmd);
		}
	}

	public void undo() {
		if (!history.isEmpty()) {
			Command undoCmd = history.removeFirst();
			GraphicObjectMemento memento = mementoMap.get(undoCmd);

			if (memento != null) {
				undoCmd.restoreMemento(memento);
				mementoMap.remove(undoCmd); // Rimuoviamo il memento dopo l'uso
			}

			redoList.addFirst(undoCmd);
		}
	}

	private void addToHistory(Command cmd) {
		history.addFirst(cmd);
		if (history.size() > maxHistoryLength) {
			history.removeLast();
		}
	}
}
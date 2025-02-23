package is.command;

import java.util.LinkedList;


public class HistoryCommandHandler implements CommandHandler {

	private int maxHistoryLength = 100;

	private final LinkedList<Command> history = new LinkedList<>();
	private final LinkedList<Command> redoList = new LinkedList<>();

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
			if (undoCmd.getMemento() != null) {
				undoCmd.undoIt();
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
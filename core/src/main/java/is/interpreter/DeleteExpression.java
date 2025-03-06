package is.interpreter;

import is.command.Command;
import is.shapes.specificcommand.DeleteCommand;
import is.shapes.view.GraphicObjectPanel;

public class DeleteExpression implements Expression {
    private final Integer argument;
    private final GraphicObjectPanel panel;


    public DeleteExpression(Integer argument, GraphicObjectPanel panel) {
        this.argument = argument;
        this.panel = panel;
    }

    @Override
    public Command interpret() {
        return new DeleteCommand(panel, panel.getObjectById(argument));
    }
}

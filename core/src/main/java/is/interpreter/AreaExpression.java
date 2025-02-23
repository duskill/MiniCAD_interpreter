package is.interpreter;

import is.command.Command;
import is.shapes.specificcommand.AreaCommand;
import is.shapes.view.GraphicObjectPanel;

public class AreaExpression implements Expression {
    private final Integer argument;
    private final GraphicObjectPanel panel;

    public AreaExpression(Integer argument, GraphicObjectPanel panel) {
        this.argument = argument;
        this.panel = panel;
    }


    @Override
    public Command interpret() {
        return new AreaCommand(panel.getObjects().get(argument));
    }
}

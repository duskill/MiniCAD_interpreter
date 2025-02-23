package is.interpreter;

import is.command.Command;
import is.shapes.specificcommand.PerimeterCommand;
import is.shapes.view.GraphicObjectPanel;

public class PerimeterExpression implements Expression {
    private final Integer argument;
    private final GraphicObjectPanel panel;

    public PerimeterExpression(Integer argument, GraphicObjectPanel panel) {
        this.argument = argument;
        this.panel = panel;
    }


    @Override
    public Command interpret() {
        return new PerimeterCommand(panel.getObjects().get(argument));
    }
}
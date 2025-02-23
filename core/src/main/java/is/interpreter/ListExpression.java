package is.interpreter;

import is.command.Command;
import is.shapes.model.groups.GroupManager;
import is.shapes.specificcommand.ListCommand;
import is.shapes.view.GraphicObjectPanel;

public class ListExpression implements Expression {
    private final String argument;
    private final GroupManager groupManager;
    private final GraphicObjectPanel panel;


    public ListExpression(String argument, GraphicObjectPanel panel) {
        this.argument = argument;
        this.groupManager = GroupManager.getInstance();
        this.panel = panel;
    }

    @Override
    public Command interpret() {
        return new ListCommand(argument, groupManager, panel);
    }
}
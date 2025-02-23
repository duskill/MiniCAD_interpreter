package is.interpreter;

import is.command.Command;
import is.shapes.model.groups.GroupManager;
import is.shapes.specificcommand.UngroupCommand;
import is.shapes.view.GraphicObjectPanel;

public class UngroupExpression implements Expression {
    private final Integer argument;
    private final GroupManager groupManager;



    public UngroupExpression(Integer argument, GraphicObjectPanel panel) {
        this.argument = argument;
        this.groupManager = GroupManager.getInstance(panel);
    }


    @Override
    public Command interpret() {
        return new UngroupCommand(argument, groupManager);
    }
}

package is.interpreter;

import is.command.Command;
import is.shapes.model.GraphicObject;
import is.shapes.specificcommand.NewObjectCmd;
import is.shapes.view.GraphicObjectPanel;

class NewExpression implements Expression {
    private final GraphicObject object;
    private final GraphicObjectPanel panel;


    public NewExpression(GraphicObject object, GraphicObjectPanel panel) {
        this.object = object;
        this.panel = panel;
    }

    @Override
    public Command interpret() {
        return new NewObjectCmd(panel, object);
    }
}
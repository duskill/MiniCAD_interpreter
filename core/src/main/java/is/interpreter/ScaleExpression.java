package is.interpreter;

import is.command.Command;
import is.shapes.model.GraphicObject;
import is.shapes.specificcommand.ScaleCommand;
import is.shapes.view.GraphicObjectPanel;

public class ScaleExpression implements Expression {
    private final Integer argument;
    private final double factor;
    private final GraphicObjectPanel panel;

    public ScaleExpression(Integer argument, double factor, GraphicObjectPanel panel) {
        this.argument = argument;
        this.factor = factor;
        this.panel = panel;
    }


    @Override
    public Command interpret() {
        GraphicObject obj = panel.getObjectById(argument);
        if (obj == null) throw new IllegalArgumentException("Oggetto non trovato: " + argument);
        return new ScaleCommand(obj, factor);
    }
}


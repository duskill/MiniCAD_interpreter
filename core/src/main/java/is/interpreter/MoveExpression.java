package is.interpreter;

import is.command.Command;
import is.shapes.model.*;
import is.shapes.specificcommand.*;
import is.shapes.view.GraphicObjectPanel;
import java.awt.geom.Point2D;


public class MoveExpression implements Expression {
    private final Integer argument;
    private final Point2D position;
    private final boolean isOffset;
    private final GraphicObjectPanel panel;

    public MoveExpression(Integer argument, Point2D position, boolean isOffset, GraphicObjectPanel panel) {
        this.argument = argument;
        this.position = position;
        this.isOffset = isOffset;
        this.panel = panel;
    }

    @Override
    public Command interpret() {
        GraphicObject obj = panel.getObjectById(argument);
        if (obj == null) throw new IllegalArgumentException("Oggetto non trovato: " + argument);

        return isOffset ?
                new MoveOffsetCommand(obj, position) :
                new MoveCommand(obj, position);
    }
}
package is.shapes.specificcommand;

import is.command.Command;
import is.shapes.model.GraphicObject;
import is.shapes.calculationStrategy.ShapeCalculationStrategy;
import is.memento.GraphicObjectMemento;

import java.util.List;

public class AreaCommand implements Command {
    private final List<GraphicObject> objects;
    private double result = 0;
    private GraphicObjectMemento prevState;

    public AreaCommand(List<GraphicObject> objects) {
        this.objects = objects;
    }

    @Override
    public boolean doIt() {
        for (GraphicObject object : objects) {
            ShapeCalculationStrategy strategy = object.getCalculationStrategy();
            if (strategy != null) {
                result += strategy.calculateArea();
            }else {
                return false;
            }
        }
        System.out.println("Area: " + result);
        return true;
    }


    @Override
    public boolean undoIt() {
        return true;
    }

    @Override
    public GraphicObjectMemento createMemento() {
        return null; //non necessario
    }

    @Override
    public void restoreMemento() {}

    @Override
    public GraphicObjectMemento getMemento() {
        return null;
    }

}
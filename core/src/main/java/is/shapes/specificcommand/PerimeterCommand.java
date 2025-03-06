package is.shapes.specificcommand;

import is.command.Command;
import is.shapes.model.GraphicObject;
import is.shapes.calculationStrategy.ShapeCalculationStrategy;
import is.memento.GraphicObjectMemento;

import java.util.List;

public class PerimeterCommand implements Command {
    private final List<GraphicObject> objects;
    private double result = 0;
    private GraphicObjectMemento prevState;

    public PerimeterCommand(List<GraphicObject> objects) {
        this.objects = objects;
        }

    @Override
    public boolean doIt() {
        double result = 0;
        for (GraphicObject object : objects) {
            ShapeCalculationStrategy strategy = object.getCalculationStrategy();
            if (strategy != null) {
                result += strategy.calculatePerimeter();
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
    public GraphicObjectMemento createMemento() {return null;}

    @Override
    public void restoreMemento() {
    }

    public GraphicObjectMemento getMemento() {
        return null;
    }

}
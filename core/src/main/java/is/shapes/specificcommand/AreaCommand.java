package is.shapes.specificcommand;

import is.command.Command;
import is.shapes.model.GraphicObject;
import is.shapes.calculationStrategy.ShapeCalculationStrategy;
import is.memento.GraphicObjectMemento;

public class AreaCommand implements Command {
    private final GraphicObject object;
    private final ShapeCalculationStrategy strategy;
    private double result;
    private GraphicObjectMemento prevState;

    public AreaCommand(GraphicObject object) {
        this.object = object;
        this.strategy = object.getCalculationStrategy(); // Usa la strategia associata all'oggetto
        this.prevState = object.saveState();
    }

    @Override
    public boolean doIt() {
        if (strategy != null) {
            result = strategy.calculateArea();
            System.out.println("Area: " + result);
            return true;
        }
        return false;
    }

    @Override
    public boolean undoIt() {
        if (prevState != null) {
            object.restoreState(prevState);
            return true;
        }
        return false;
    }

    @Override
    public GraphicObjectMemento createMemento() {
        return null; //non necessario
    }

    @Override
    public void restoreMemento() {}

    @Override
    public GraphicObjectMemento getMemento() {
        return this.prevState;
    }

}
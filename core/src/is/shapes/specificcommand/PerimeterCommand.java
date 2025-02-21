package is.shapes.specificcommand;

import is.command.Command;
import is.shapes.model.GraphicObject;
import is.shapes.calculationStrategy.ShapeCalculationStrategy;
import memento.GraphicObjectMemento;

public class PerimeterCommand implements Command {
    private final GraphicObject object;
    private final ShapeCalculationStrategy strategy;
    private double result;
    private GraphicObjectMemento prevState;

    public PerimeterCommand(GraphicObject object) {
        this.object = object;
        this.strategy = object.getCalculationStrategy(); // Usa la strategia associata all'oggetto
        this.prevState = object.saveState();
    }

    @Override
    public boolean doIt() {
        if (strategy != null) {
            result = strategy.calculatePerimeter();
            System.out.println("Perimetro: " + result);
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
        return prevState;
    }

    @Override
    public void restoreMemento(GraphicObjectMemento memento) {
        this.prevState = memento;
    }

}
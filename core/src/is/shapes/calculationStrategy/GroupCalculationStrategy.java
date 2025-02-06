package is.shapes.calculationStrategy;

import is.shapes.model.AbstractGraphicObject;
import java.util.Set;

public class GroupCalculationStrategy implements ShapeCalculationStrategy {
    Set<AbstractGraphicObject> components;

    public GroupCalculationStrategy(Set<AbstractGraphicObject> components) {
        this.components = components;
    }

    @Override
    public double calculateArea() {
       double sum = 0;
       for (AbstractGraphicObject component : components) {
           sum += component.getCalculationStrategy().calculateArea();
       }
       return sum;
    }

    @Override
    public double calculatePerimeter() {
        double sum = 0;
        for (AbstractGraphicObject component : components) {
            sum += component.getCalculationStrategy().calculateArea();
        }
        return sum;
    }

    public void setChildren(Set<AbstractGraphicObject> children){
        this.components = children;
    }
}

package is.shapes.calculationStrategy;

import is.shapes.model.GraphicObject;


import java.util.Set;

public class GroupCalculationStrategy implements ShapeCalculationStrategy {
    Set<GraphicObject> components;

    public GroupCalculationStrategy(Set<GraphicObject> components) {
        this.components = components;
    }

    @Override
    public double calculateArea() {
       double sum = 0;
       for (GraphicObject component : components) {
           sum += component.getCalculationStrategy().calculateArea();
       }
       return sum;
    }

    @Override
    public double calculatePerimeter() {
        double sum = 0;
        for (GraphicObject component : components) {
            sum += component.getCalculationStrategy().calculatePerimeter();
        }
        return sum;
    }

    public void setChildren(Set<GraphicObject> children){
        this.components = children;
    }
}

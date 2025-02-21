package is.shapes.calculationStrategy;

public class CircleCalculationStrategy implements ShapeCalculationStrategy{
    private double radius;

    public CircleCalculationStrategy(double radius) {
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * Math.pow(radius, 2);
    }

    @Override
    public double calculatePerimeter() {
        return 2 * Math.PI * radius;
    }
}

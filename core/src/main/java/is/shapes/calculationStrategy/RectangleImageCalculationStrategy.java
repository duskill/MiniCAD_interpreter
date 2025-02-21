package is.shapes.calculationStrategy;

public class RectangleImageCalculationStrategy implements ShapeCalculationStrategy{
    private double width;
    private double height;

    public RectangleImageCalculationStrategy(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public double calculateArea() {
        return height * width;
    }

    @Override
    public double calculatePerimeter() {
        return 2 * (height + width);
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}

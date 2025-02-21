import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import is.shapes.calculationStrategy.CircleCalculationStrategy;

public class CircleCalculationStrategyTest {

    @Test
    void testCalculateArea() {
        CircleCalculationStrategy circle = new CircleCalculationStrategy(2);
        assertEquals(Math.PI * 4, circle.calculateArea(), 0.001); // Permettiamo una tolleranza di 0.001
    }

    @Test
    void testCalculatePerimeter() {
        CircleCalculationStrategy circle = new CircleCalculationStrategy(2);
        assertEquals(2 * Math.PI * 2, circle.calculatePerimeter(), 0.001);
    }

}
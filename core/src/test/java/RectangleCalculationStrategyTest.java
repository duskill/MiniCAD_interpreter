import is.shapes.calculationStrategy.RectangleImageCalculationStrategy;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


    public class RectangleCalculationStrategyTest {

        @Test
        void testCalculateArea() {
            RectangleImageCalculationStrategy rectangle = new RectangleImageCalculationStrategy(2, 4);
            assertEquals(2*4, rectangle.calculateArea());
        }

        @Test
        void testCalculatePerimeter() {
            RectangleImageCalculationStrategy circle = new RectangleImageCalculationStrategy(2, 4);
            assertEquals(2*(2+4), circle.calculatePerimeter());
        }
    }


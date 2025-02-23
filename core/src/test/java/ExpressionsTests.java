import is.command.Command;
import is.interpreter.*;
import is.shapes.model.CircleObject;
import is.shapes.model.GraphicObject;
import is.shapes.specificcommand.*;
import is.shapes.view.GraphicObjectPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.*;

class ExpressionTests {
    private GraphicObjectPanel panel;
    private GraphicObject testObject;

    @BeforeEach
    void setUp() {
        panel = new GraphicObjectPanel();
        testObject = new CircleObject(new Point2D.Double(2,5), 4); // Assicurati di avere un costruttore valido per GraphicObject
        panel.add(testObject);
    }

    @Test
    void testAreaExpression() {
        AreaExpression areaExpression = new AreaExpression(0, panel);
        Command command = areaExpression.interpret();
        assertInstanceOf(AreaCommand.class, command);
    }

    @Test
    void testDeleteExpression() {
        DeleteExpression deleteExpression = new DeleteExpression(0, panel);
        Command command = deleteExpression.interpret();
        assertInstanceOf(DeleteCommand.class, command);
    }

    @Test
    void testGroupExpression() {
        GroupExpression groupExpression = new GroupExpression("0", panel);
        Command command = groupExpression.interpret();
        assertInstanceOf(GroupCommand.class, command);
    }

    @Test
    void testListExpression() {
        ListExpression listExpression = new ListExpression("all", panel);
        Command command = listExpression.interpret();
        assertInstanceOf(ListCommand.class, command);
    }

    @Test
    void testMoveExpression() {
        Point2D position = new Point2D.Double(10, 10);
        MoveExpression moveExpression = new MoveExpression(0, position, false, panel);
        Command command = moveExpression.interpret();
        assertInstanceOf(MoveCommand.class, command);
    }

    @Test
    void testMoveOffsetExpression() {
        Point2D position = new Point2D.Double(5, 5);
        MoveExpression moveOffsetExpression = new MoveExpression(0, position, true, panel);
        Command command = moveOffsetExpression.interpret();
        assertInstanceOf(MoveOffsetCommand.class, command);
    }

    @Test
    void testNewExpression() {
        NewExpression newExpression = new NewExpression(testObject, panel);
        Command command = newExpression.interpret();
        assertInstanceOf(NewObjectCmd.class, command);
    }

    @Test
    void testPerimeterExpression() {
        PerimeterExpression perimeterExpression = new PerimeterExpression(0, panel);
        Command command = perimeterExpression.interpret();
        assertInstanceOf(PerimeterCommand.class, command);
    }

    @Test
    void testScaleExpression() {
        double scaleFactor = 2.0;
        ScaleExpression scaleExpression = new ScaleExpression(0, scaleFactor, panel);
        Command command = scaleExpression.interpret();
        assertInstanceOf(ScaleCommand.class, command);
    }

    @Test
    void testUngroupExpression() {
        UngroupExpression ungroupExpression = new UngroupExpression(0, panel);
        Command command = ungroupExpression.interpret();
        assertInstanceOf(UngroupCommand.class, command);
    }
}
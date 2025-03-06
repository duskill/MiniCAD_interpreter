import is.shapes.model.GraphicObject;
import is.shapes.model.RectangleObject;
import is.shapes.model.groups.GroupManager;
import is.shapes.view.GraphicObjectPanel;
import is.shapes.specificcommand.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommandTests {
    private GraphicObjectPanel panel = new GraphicObjectPanel();
    private GroupManager groupManager;
    private GraphicObject testObject;

    @BeforeEach
    void setUp() {
        panel = new GraphicObjectPanel();
        groupManager = GroupManager.getInstance(panel);
        testObject = new RectangleObject(new Point2D.Double(2,5), 4, 3);
    }

    @Test
    void testAreaCommand() {
        List<GraphicObject> ls = new LinkedList<>();
        ls.add(testObject);
        ls.add(new RectangleObject(new Point2D.Double(2,5), 4, 3));
        AreaCommand areaCommand = new AreaCommand(ls);
        assertTrue(areaCommand.doIt());
        assertTrue(areaCommand.undoIt());
    }

    @Test
    void testDeleteCommand() {
        panel.add(testObject);
        DeleteCommand deleteCommand = new DeleteCommand(panel, testObject);
        assertTrue(deleteCommand.doIt());
        assertFalse(panel.getObjects().contains(testObject));
        assertTrue(deleteCommand.undoIt());
        assertTrue(panel.getObjects().contains(testObject));
    }

    @Test
    void testGroupCommand() {
        LinkedList<GraphicObject> objects = new LinkedList<>();
        objects.add(testObject);
        GroupCommand groupCommand = new GroupCommand(objects, groupManager);
        assertTrue(groupCommand.doIt());
        assertNotNull(groupCommand.createMemento());
        assertTrue(groupCommand.undoIt());
    }

    @Test
    void testListCommand() {
        panel.add(testObject);
        ListCommand listCommand = new ListCommand("all", groupManager, panel);
        assertTrue(listCommand.doIt());
        assertFalse(panel.getObjects().isEmpty());
    }

    @Test
    void testMoveCommand() {
        MoveCommand moveCommand = new MoveCommand(testObject, new Point2D.Double(10, 10));
        assertTrue(moveCommand.doIt());
        assertEquals(new Point2D.Double(10, 10), testObject.getPosition());
        assertTrue(moveCommand.undoIt());
        assertEquals(new Point2D.Double(2.0, 5.0), testObject.getPosition());
    }

    @Test
    void testMoveOffsetCommand() {
        MoveOffsetCommand moveOffsetCommand = new MoveOffsetCommand(testObject, new Point2D.Double(5, 5));
        assertTrue(moveOffsetCommand.doIt());
        assertEquals(new Point2D.Double(5+2, 5+5), testObject.getPosition());
        assertTrue(moveOffsetCommand.undoIt());
    }

    @Test
    void testNewObjectCmd() {
        NewObjectCmd newObjectCmd = new NewObjectCmd(panel, testObject);
        assertTrue(newObjectCmd.doIt());
        assertTrue(panel.getObjects().contains(testObject));
        assertTrue(newObjectCmd.undoIt());
        assertFalse(panel.getObjects().contains(testObject));
    }

    @Test
    void testPerimeterCommand() {
        List<GraphicObject> ls = new LinkedList<>();
        ls.add(testObject);
        PerimeterCommand perimeterCommand = new PerimeterCommand(ls);
        assertTrue(perimeterCommand.doIt());
        assertNotNull(perimeterCommand.createMemento());
        assertTrue(perimeterCommand.undoIt());
    }

    @Test
    void testScaleCommand() {
        ScaleCommand scaleCommand = new ScaleCommand(testObject, 2.0);
        assertTrue(scaleCommand.doIt());
        assertNotNull(scaleCommand.createMemento());
        assertTrue(scaleCommand.undoIt());
    }

    @Test
    void testUngroupCommand() {
        int gid = groupManager.createGroup();
        UngroupCommand ungroupCommand = new UngroupCommand(gid, groupManager);
        assertTrue(ungroupCommand.doIt());
        assertTrue(ungroupCommand.undoIt());
    }

}

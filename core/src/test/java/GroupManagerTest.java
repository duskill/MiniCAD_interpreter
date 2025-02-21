import is.shapes.model.AbstractGraphicObject;
import is.shapes.model.CircleObject;
import is.shapes.model.groups.GroupManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.geom.Point2D;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class GroupManagerTest {

    private GroupManager manager;

    @BeforeEach
    void setUp() {
        manager = GroupManager.getInstance();
        manager.clearAll(); // Reset prima di ogni test
    }

    @Test
    void testSingletonInstance() {
        GroupManager anotherInstance = GroupManager.getInstance();
        assertSame(manager, anotherInstance);
    }

    @Test
    void testCreateGroup() {
        int groupId = manager.createGroup();
        assertTrue(manager.getAllGroupIds().contains(groupId));
    }

    @Test
    void testDeleteGroup() {
        int groupId = manager.createGroup();
        assertTrue(manager.deleteGroup(groupId));
        assertFalse(manager.getAllGroupIds().contains(groupId));
    }

    @Test
    void testAddToGroup() {
        int groupId = manager.createGroup();
        AbstractGraphicObject circle = new CircleObject(new Point2D.Double(5,0),2);
        assertTrue(manager.addToGroup(groupId, circle));
        assertTrue(manager.getGroupObjects(groupId).contains(circle));
    }


    @Test
    void testRemoveFromGroup() {
        int groupId = manager.createGroup();
        AbstractGraphicObject circle = new CircleObject(new Point2D.Double(5,0), 2);
        manager.addToGroup(groupId, circle);
        assertTrue(manager.removeFromGroup(groupId, circle));
        // Ora verifichiamo che il gruppo sia stato eliminato del tutto
        assertFalse(manager.getAllGroupIds().contains(groupId));
        assertTrue(manager.getGroupObjects(groupId).isEmpty()); // Il set deve essere vuoto
    }

    @Test
    void testRemoveFromEmptyGroupDeletesIt() {
        int groupId = manager.createGroup();
        AbstractGraphicObject circle = new CircleObject(new Point2D.Double(5,0),2);
        manager.addToGroup(groupId, circle);
        manager.removeFromGroup(groupId, circle);
        assertFalse(manager.getAllGroupIds().contains(groupId));
    }

    @Test
    void testGetAllGroupIds() {
        int group1 = manager.createGroup();
        int group2 = manager.createGroup();
        Set<Integer> groupIds = manager.getAllGroupIds();
        assertEquals(2, groupIds.size());
        assertTrue(groupIds.contains(group1));
        assertTrue(groupIds.contains(group2));
    }
}
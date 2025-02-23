import is.shapes.model.CircleObject;
import is.shapes.model.GraphicObject;
import is.shapes.model.groups.GroupManager;
import is.shapes.view.GraphicObjectPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.geom.Point2D;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GroupManagerTest {
    private GroupManager groupManager;
    private GraphicObjectPanel panel = new GraphicObjectPanel();
    private CircleObject circle1;
    private CircleObject circle2;

    @BeforeEach
    void setUp() {
        groupManager = GroupManager.getInstance(panel);
        panel = new GraphicObjectPanel();
        circle1 = new CircleObject(new Point2D.Double(1.0, 1.0), 5.0);
        circle2 = new CircleObject(new Point2D.Double(2.0, 2.0), 3.0);

        panel.add(circle1);
        panel.add(circle2);
        groupManager.clearAll(); // Pulizia per test indipendenti
    }

    @Test
    void testCreateGroupAndSyncWithPanel() {
        int groupId = groupManager.createGroup();
        assertTrue(groupManager.getAllGroupIds().contains(groupId), "Il gruppo dovrebbe essere stato creato");
        assertNotNull(groupManager.getGroup(groupId), "Il gruppo dovrebbe esistere nel manager");

        // Il gruppo deve essere presente anche nel panel
        panel.add(groupManager.getGroup(groupId));
        assertTrue(panel.getObjects().contains(groupManager.getGroup(groupId)), "Il gruppo dovrebbe essere nel pannello");
    }

    @Test
    void testAddObjectsToGroupAndSyncWithPanel() {
        int groupId = groupManager.createGroup();
        assertTrue(groupManager.addToGroup(groupId, circle1), "circle1 dovrebbe essere stato aggiunto al gruppo");
        assertTrue(groupManager.addToGroup(groupId, circle2), "circle2 dovrebbe essere stato aggiunto al gruppo");

        Set<GraphicObject> groupObjects = groupManager.getGroupObjects(groupId);
        assertTrue(groupObjects.contains(circle1), "Il gruppo dovrebbe contenere circle1");
        assertTrue(groupObjects.contains(circle2), "Il gruppo dovrebbe contenere circle2");

        // Sincronizzazione con il pannello
        panel.add(groupManager.getGroup(groupId));
        assertTrue(panel.getObjects().contains(groupManager.getGroup(groupId)), "Il gruppo dovrebbe essere nel pannello");
    }

    @Test
    void testRemoveGroupAndSyncWithPanel() {
        int groupId = groupManager.createGroup();
        groupManager.addToGroup(groupId, circle1);
        groupManager.addToGroup(groupId, circle2);

        panel.add(groupManager.getGroup(groupId)); // Aggiungo il gruppo al pannello

        assertTrue(groupManager.deleteGroup(groupId), "Il gruppo dovrebbe essere eliminato");
        assertFalse(groupManager.getAllGroupIds().contains(groupId), "Il gruppo non dovrebbe più esistere");

        // Verifica che il gruppo sia stato rimosso anche dal pannello
        assertFalse(panel.getObjects().contains(groupManager.getGroup(groupId)), "Il gruppo non dovrebbe più essere nel pannello");
    }

    @Test
    void testRemoveObjectFromGroupAndSyncWithPanel() {
        int groupId = groupManager.createGroup();
        groupManager.addToGroup(groupId, circle1);
        groupManager.addToGroup(groupId, circle2);

        assertTrue(groupManager.removeFromGroup(groupId, circle1), "circle1 dovrebbe essere stato rimosso dal gruppo");

        Set<GraphicObject> groupObjects = groupManager.getGroupObjects(groupId);
        assertFalse(groupObjects.contains(circle1), "Il gruppo non dovrebbe più contenere circle1");
        assertTrue(groupObjects.contains(circle2), "Il gruppo dovrebbe ancora contenere circle2");

        // Se il gruppo diventa vuoto, deve essere rimosso anche dal pannello
        groupManager.removeFromGroup(groupId, circle2);
        assertFalse(groupManager.getAllGroupIds().contains(groupId), "Il gruppo dovrebbe essere eliminato se vuoto");
        assertFalse(panel.getObjects().contains(groupManager.getGroup(groupId)), "Il gruppo non dovrebbe più essere nel pannello");
    }
}
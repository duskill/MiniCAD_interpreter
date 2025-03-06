import is.command.Command;
import is.command.HistoryCommandHandler;
import is.shapes.model.CircleObject;
import is.shapes.model.GraphicObject;
import is.shapes.specificcommand.MoveCommand;
import is.shapes.view.GraphicObjectPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.*;

class MementoTests {
    private HistoryCommandHandler historyCommandHandler;
    private GraphicObjectPanel panel;
    private GraphicObject testObject;

    @BeforeEach
    void setUp() {
        panel = new GraphicObjectPanel();
        historyCommandHandler = new HistoryCommandHandler(10);
        testObject = new CircleObject(new Point2D.Double(2,5), 4); // Assicurati di avere un costruttore valido per GraphicObject
        panel.add(testObject);
    }

    @Test
    void testUndoRedo() {
        // Esegui un comando di spostamento
        Command moveCommand = new MoveCommand(testObject, new Point2D.Double(10, 10));
        historyCommandHandler.handle(moveCommand);


        // Verifica che l'oggetto sia stato spostato
        assertEquals(new Point2D.Double(10, 10), testObject.getPosition());

        // Fai undo e verifica che lo stato torni corretto
        historyCommandHandler.undo();
        assertNotEquals(new Point2D.Double(10, 10), testObject.getPosition());

        // Fai redo e controlla che lo stato torni corretto
        historyCommandHandler.redo();
        assertEquals(new Point2D.Double(10, 10), testObject.getPosition());
    }

    @Test
    void testMultipleCommandsUndoRedo() {
        // Esegui una serie di comandi
        Command moveCommand1 = new MoveCommand(testObject, new Point2D.Double(10, 10));

        historyCommandHandler.handle(moveCommand1);
        assertEquals(new Point2D.Double(10, 10), testObject.getPosition());

        Command moveCommand2 = new MoveCommand(testObject, new Point2D.Double(20, 20));


        historyCommandHandler.handle(moveCommand2);
        assertEquals(new Point2D.Double(20, 20), testObject.getPosition());

        // Fai undo e verifica lo stato
        historyCommandHandler.undo();
        assertEquals(new Point2D.Double(10, 10), testObject.getPosition());


        // Fai redo e controlla che lo stato torni corretto
        historyCommandHandler.redo();
        assertEquals(new Point2D.Double(20, 20), testObject.getPosition());
    }
}
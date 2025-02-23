package is.interpreter;

import is.command.Command;
import is.command.HistoryCommandHandler;
import is.shapes.view.GraphicObjectPanel;
import java.io.IOException;

public class Interpreter {
    private final HistoryCommandHandler commandHandler;
    private final GraphicObjectPanel panel;

    public Interpreter(HistoryCommandHandler commandHandler, GraphicObjectPanel panel) {
        this.commandHandler = commandHandler;
        this.panel = panel;
    }

    public void interpret(String input) {
        try {
            Tokenizer tokenizer = new Tokenizer(input);
            Parser parser = new Parser(tokenizer, panel);
            Expression expression = parser.parse();
            if (expression != null) {
                Command command = expression.interpret();
                commandHandler.handle(command);
            } else {
                System.out.println("Comando non riconosciuto: " + input);
            }
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Errore nell'interpretazione del comando: " + e.getMessage());
        }
    }
}
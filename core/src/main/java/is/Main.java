package is;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import is.command.HistoryCommandHandler;
import is.interpreter.Interpreter;
import is.shapes.model.CircleObject;
import is.shapes.model.ImageObject;
import is.shapes.model.RectangleObject;
import is.shapes.model.groups.Group;
import is.shapes.view.*;

public class Main {

	public static void main(String[] args) {

		JFrame f = new JFrame();

		JToolBar toolbar = new JToolBar();
		JButton undoButt = new JButton("Undo");
		JButton redoButt = new JButton("Redo");

		HistoryCommandHandler historyHandler = new HistoryCommandHandler();



		undoButt.addActionListener(evt -> {
			System.out.println("Tentativo di eseguire undo...");
			System.out.println("Dimensione storico: " + historyHandler.getHistoryLength()); // Metodo ipotetico, verifica se esiste
			historyHandler.undo();
		});

		redoButt.addActionListener(evt -> {
			System.out.println("Tentativo di eseguire redo...");
			System.out.println("Dimensione storico redo: " + historyHandler.getRedoLength()); // Metodo ipotetico, verifica se esiste
			historyHandler.redo();
		});

		toolbar.add(undoButt);
		toolbar.add(redoButt);

		GraphicObjectLogger logger = new GraphicObjectLogger();

		GraphicObjectPanel gpanel = new GraphicObjectPanel();

		gpanel.setPreferredSize(new Dimension(400, 400));
		GraphicObjectViewFactory.FACTORY.installView(RectangleObject.class, new RectangleObjectView());
		GraphicObjectViewFactory.FACTORY.installView(CircleObject.class, new CircleObjectView());
		GraphicObjectViewFactory.FACTORY.installView(ImageObject.class, new ImageObjectView());
		GraphicObjectViewFactory.FACTORY.installView(Group.class, new GroupObjectView());





		f.add(toolbar, BorderLayout.NORTH);
		f.add(gpanel, BorderLayout.CENTER);

		JPanel controlPanel = new JPanel(new FlowLayout());

		f.getContentPane().add(new JScrollPane(controlPanel), BorderLayout.SOUTH);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack();
		f.setVisible(true);


		Interpreter interpreter = new Interpreter(historyHandler, gpanel);

		// Lettura dei comandi da console
		Scanner scanner = new Scanner(System.in);
		System.out.println("Inserisci un comando (oppure 'exit' per uscire):");

		while (true) {
			System.out.print("> ");
			String input = scanner.nextLine();

			if (input.equalsIgnoreCase("exit")) {
				System.out.println("Chiusura del programma.");
				break;
			} else if (input.equalsIgnoreCase("undo")) {
				historyHandler.undo();
				System.out.println("Undo eseguito.");
			} else if (input.equalsIgnoreCase("redo")) {
				historyHandler.redo();
				System.out.println("Redo eseguito.");
			} else {
				try {
					interpreter.interpret(input);
					System.out.println("Comando eseguito: " + input);
				} catch (Exception e) {
					System.err.println("Errore: " + e.getMessage());
				}
			}
		}

		scanner.close();
	}
}
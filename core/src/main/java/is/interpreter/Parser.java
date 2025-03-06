package is.interpreter;

import is.shapes.model.CircleObject;
import is.shapes.model.ImageObject;
import is.shapes.model.RectangleObject;
import is.shapes.view.GraphicObjectPanel;

import javax.swing.*;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    private final Tokenizer tokenizer;
    private final GraphicObjectPanel panel;

    public Parser(Tokenizer tokenizer, GraphicObjectPanel panel) {
        this.tokenizer = tokenizer;
        this.panel = panel;
    }


    //valuto innanzitutto il primo token che indica il comando desiderato e lancio il metodo opportuno
    public Expression parse() throws IOException {
        if (!tokenizer.nextToken()) {
            throw new IllegalArgumentException("Input vuoto");
        }

        String command = tokenizer.getCurrentToken();
        return switch (command) {
            case "new" -> parseCreate();
            case "del" -> parseDelete();
            case "mv" -> parseMove(false);
            case "mvoff" -> parseMove(true);
            case "scale" -> parseScale();
            case "ls" -> parseList();
            case "grp" -> parseGroup();
            case "ungrp" -> parseUngroup();
            case "area" -> parseArea();
            case "perimeter" -> parsePerimeter();
            default -> throw new IllegalArgumentException("Comando sconosciuto: " + command);
        };
    }

    //gestisco ogni caso nel modo appropriato in base ai diversi input richiesti

    private Expression parseCreate() throws IOException {
        if (!tokenizer.nextToken()) {
            throw new IllegalArgumentException("Tipo di oggetto mancante dopo 'new'");
        }
        String type = tokenizer.getCurrentToken();

        return switch (type) {
            case "circle" -> new NewExpression(parseCircle(), panel);
            case "rectangle" -> new NewExpression(parseRectangle(), panel);
            case "img" -> new NewExpression(parseImage(), panel);
            default -> throw new IllegalArgumentException("Tipo di oggetto sconosciuto: " + type);
        };
    }

    private CircleObject parseCircle() throws IOException {
        tokenizer.nextToken();
        expectToken("(");
        double radius = parseDouble("Atteso valore numerico per il raggio del cerchio");
        expectToken(")");
        Point2D position = parsePosition();
        System.out.println(position);
        return new CircleObject(position, radius);
    }


    private RectangleObject parseRectangle() throws IOException {
        tokenizer.nextToken();
        expectToken("(");
        double width = parseDouble("Atteso valore numerico per la larghezza del rettangolo");
        expectToken(",");
        double height = parseDouble("Atteso valore numerico per l'altezza del rettangolo");
        expectToken(")");
        Point2D position = parsePosition();
        return new RectangleObject(position, width, height);
    }

    private ImageObject parseImage() throws IOException {
        tokenizer.nextToken();
        expectToken("(");
        String path = tokenizer.consume();
        expectToken(")");
        Point2D position = parsePosition();
        return new ImageObject(new ImageIcon(path), position);
    }

    private Point2D parsePosition() throws IOException {
        expectToken("(");
        double x = parseDouble("Atteso valore numerico per la coordinata X");
        expectToken(",");
        double y = parseDouble("Atteso valore numerico per la coordinata Y");
        return new Point2D.Double(x, y);
    }

    private Expression parseDelete() throws IOException {
        expectNextToken("Atteso identificatore per il comando 'del'");
        Integer id = parseInteger("L'ID deve essere un numero intero valido");
        return new DeleteExpression(id, panel);
    }

    private Expression parseMove(boolean isOffset) throws IOException {
        expectNextToken("Atteso identificatore per il comando 'mv' o 'mvoff'");
        int id = parseInteger("L'ID deve essere un numero intero valido");
        Point2D pos = parsePosition();
        return new MoveExpression(id, pos, isOffset, panel);
    }

    private Expression parseScale() throws IOException {
        expectNextToken("Atteso identificatore per il comando 'scale'");
        int id = parseInteger("L'ID deve essere un numero intero valido");
        double factor = parseDouble("Atteso valore numerico per il fattore di scala");
        return new ScaleExpression(id, factor, panel);
    }

    private Expression parseList() throws IOException {
        expectNextToken("Atteso parametro per il comando 'ls'");
        return new ListExpression(tokenizer.getCurrentToken(), panel);
    }

    private Expression parseGroup() throws IOException {
        expectNextToken("Atteso elenco di ID per il comando 'grp'");

        // Crea una lista per memorizzare gli ID
        List<Integer> ids = new ArrayList<>();

        // Aggiungi il primo ID
        ids.add(parseInteger("Atteso un ID valido per il comando 'grp'"));

        // Legge i token separati da virgola
        while (true) {
            if (tokenizer.match(",")) { // Se il token è una virgola, significa che c'è un altro ID
                ids.add(parseInteger("Atteso un ID valido per il comando 'grp'"));
            } else {
                break; // Esce dal ciclo quando non trova più virgole
            }
        }

        // Verifica che almeno un ID sia stato trovato
        if (ids.isEmpty()) {
            throw new IllegalArgumentException("Nessun ID valido trovato per il comando 'grp'");
        }

        // Restituisce un'espressione che rappresenta il gruppo
        return new GroupExpression(ids, panel);
    }

    private Expression parseUngroup() throws IOException {
        expectNextToken("Atteso identificatore di gruppo per il comando 'ungrp'");
        int groupId = parseInteger("L'ID del gruppo deve essere un numero intero valido");
        return new UngroupExpression(groupId, panel);
    }

    private Expression parseArea() throws IOException {
        expectNextToken("Atteso identificatore o tipo di oggetto per il comando 'area'");
        return new AreaExpression(tokenizer.getCurrentToken(), panel);
    }

    private Expression parsePerimeter() throws IOException {
        expectNextToken("Atteso identificatore o tipo di oggetto per il comando 'perimeter'");
        return new PerimeterExpression(tokenizer.getCurrentToken(), panel);
    }


    //Verifica che il token corrente corrisponda a quello atteso. In caso contrario, lancia un'eccezione.
    private void expectToken(String expected) throws IOException {
        if (!tokenizer.match(expected)) {
            throw new IllegalArgumentException("Atteso token '" + expected + "', trovato '" + tokenizer.getCurrentToken() + "'");
        }
    }


    //Avanza al prossimo token e verifica che esista, altrimenti lancia un'eccezione.
    private void expectNextToken(String errorMessage) throws IOException {
        if (!tokenizer.nextToken()) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    //Converte il token corrente in un numero intero e gestisce eventuali errori di conversione.
    private int parseInteger(String errorMessage) throws IOException {
        try {
            return Integer.parseInt(tokenizer.consume());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(errorMessage);
        }
    }


    //Converte il token corrente in un numero double e gestisce eventuali errori di conversione.
    private double parseDouble(String errorMessage) throws IOException {
        try {
            return Double.parseDouble(tokenizer.consume());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
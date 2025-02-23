package is.interpreter;

import is.shapes.model.CircleObject;
import is.shapes.model.ImageObject;
import is.shapes.model.RectangleObject;
import is.shapes.view.GraphicObjectPanel;

import javax.swing.*;
import java.awt.geom.Point2D;
import java.io.IOException;

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
        expectToken("(");
        double radius = parseDouble("Atteso valore numerico per il raggio del cerchio");
        expectToken(")");
        Point2D position = parsePosition();
        return new CircleObject(position, radius);
    }

    private RectangleObject parseRectangle() throws IOException {
        Point2D position = parsePosition();
        double width = parseDouble("Atteso valore numerico per la larghezza del rettangolo");
        double height = parseDouble("Atteso valore numerico per l'altezza del rettangolo");
        return new RectangleObject(position, width, height);
    }

    private ImageObject parseImage() throws IOException {
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
        expectToken(")");
        return new Point2D.Double(x, y);
    }

    private Expression parseDelete() throws IOException {
        expectNextToken("Atteso identificatore per il comando 'del'");
        int id = parseInteger("L'ID deve essere un numero intero valido");
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
        return new GroupExpression(tokenizer.getCurrentToken(), panel);
    }

    private Expression parseUngroup() throws IOException {
        expectNextToken("Atteso identificatore di gruppo per il comando 'ungrp'");
        int groupId = parseInteger("L'ID del gruppo deve essere un numero intero valido");
        return new UngroupExpression(groupId);
    }

    private Expression parseArea() throws IOException {
        expectNextToken("Atteso identificatore o tipo di oggetto per il comando 'area'");
        return new AreaExpression(parseInteger("L'ID deve essere un numero intero valido"), panel);
    }

    private Expression parsePerimeter() throws IOException {
        expectNextToken("Atteso identificatore o tipo di oggetto per il comando 'perimeter'");
        return new PerimeterExpression(parseInteger("L'ID deve essere un numero intero valido"), panel);
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
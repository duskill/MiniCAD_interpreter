package is.interpreter;

import java.io.*;

public class Tokenizer {
    private final StreamTokenizer tokenizer;
    private String currentToken;

    public Tokenizer(String input) {
        //definisco i vari tipi di input
        tokenizer = new StreamTokenizer(new StringReader(input));
        tokenizer.ordinaryChar('.');
        tokenizer.wordChars('_', '_');
        tokenizer.quoteChar('"'); // Stringhe tra virgolette
        tokenizer.parseNumbers();
        tokenizer.ordinaryChar('(');
        tokenizer.ordinaryChar(')');
        tokenizer.ordinaryChar(',');
    }

    public boolean nextToken() throws IOException {
        int tokenType = tokenizer.nextToken();
        switch (tokenType) {
            case StreamTokenizer.TT_EOF:
                currentToken = null;
                return false;
            case StreamTokenizer.TT_WORD:
                currentToken = tokenizer.sval;
                break;
            case StreamTokenizer.TT_NUMBER:
                if (isInteger(tokenizer.nval)) {
                    // Se è intero, memorizza come stringa l'intero
                    currentToken = String.valueOf((int) tokenizer.nval);
                } else {
                    // Se è decimale, memorizza come stringa il numero decimale
                    currentToken = String.valueOf(tokenizer.nval);
                }
                break;
            case '"':
                currentToken = tokenizer.sval;
                break;
            default:
                currentToken = String.valueOf((char) tokenType);
        }
        return true;
    }

    private boolean isInteger(double value) {
        // Verifica se il numero è un intero
        return value == (int) value;
    }

    public String getCurrentToken() {
        return currentToken;
    }

    public boolean match(String expected) throws IOException {
        if (expected.equals(currentToken)) {
            return nextToken();
        }
        return false;
    }

    public String consume() throws IOException {
        String token = currentToken;
        nextToken();
        return token;
    }
}

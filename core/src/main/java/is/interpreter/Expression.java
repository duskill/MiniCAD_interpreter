package is.interpreter;

import is.command.Command;

public interface Expression {
    Command interpret(); //implemento le espressioni in modo che lancino il comando adeguato
}

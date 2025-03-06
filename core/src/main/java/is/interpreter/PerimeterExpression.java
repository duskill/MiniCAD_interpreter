package is.interpreter;

import is.command.Command;
import is.shapes.model.GraphicObject;
import is.shapes.specificcommand.PerimeterCommand;
import is.shapes.view.GraphicObjectPanel;

import java.util.ArrayList;
import java.util.List;

public class PerimeterExpression implements Expression {
    private final String argument;
    private final GraphicObjectPanel panel;

    public PerimeterExpression(String argument, GraphicObjectPanel panel) {
        this.argument = argument;
        this.panel = panel;
    }

    private List<GraphicObject> getObjects(){
        List<GraphicObject> objects = new ArrayList<>();
        if(argument.equalsIgnoreCase("all")){
            return panel.getObjects();
        } else if (isNumeric(argument)) {
            objects.add(panel.getObjectById(Integer.parseInt(argument)));
        }else{
            for(GraphicObject object : panel.getObjects()){
                if (object.getType().equalsIgnoreCase(argument)){
                    objects.add(object);
                }
            }
        }
        return objects;
    }

    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    @Override
    public Command interpret() {
        return new PerimeterCommand(getObjects());
    }
}
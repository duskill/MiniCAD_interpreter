package is.shapes.model.groups;

import is.shapes.calculationStrategy.GroupCalculationStrategy;
import is.shapes.model.AbstractGraphicObject;
import is.shapes.model.GraphicEvent;
import is.shapes.model.GraphicObject;
import is.memento.GraphicObjectMemento;
import is.memento.GroupMemento;

import java.awt.*;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.List;

/**
 * Classe composite per la gestione dei gruppi
 */
public class Group extends AbstractGraphicObject {
    private final Set<GraphicObject> children; // lista dei componenti del gruppo
    private final Point2D position;

    public Group() {
        super(new GroupCalculationStrategy(new HashSet<>()));
        GroupCalculationStrategy s = (GroupCalculationStrategy) this.getCalculationStrategy(); // aggiunta la strategy per il calcolo di area e perimetro
        s.setChildren(this.getChildren());
        children = new HashSet<>();
        position = new Point2D.Double();


    }

    public boolean add(GraphicObject graphicObject) {
        if(children.add(graphicObject)){
            graphicObject.setParent(this);
            updatePosition();
            notifyListeners(new GraphicEvent(this));
            return true;
        }
           return false;
    }//add -> Aggiunge un oggetto al gruppo e aggiorna la posizone restituendo true, altrimenti se già presente restituisce false


    public boolean remove(GraphicObject graphicObject) {
        if(children.remove(graphicObject)){
            updatePosition();
            graphicObject.setParent(this.getParent());
            notifyListeners(new GraphicEvent(this));
            return true;
        }
        return false;

    }//remove -> rimuove l'oggetto dal gruppo aggiornando la posizione e restituisce true se era presente, altrimenti restituisce false


    public Set<GraphicObject> getChildren() {
        return children;
    }//getChildren -> restituisce la lista dei componenti del gruppo


    @Override
    public void moveTo(Point2D p) {
        Point2D posizioneAttuale = this.getPosition();

        // Calcolo il vettore di traslazione
        double deltaX = p.getX() - posizioneAttuale.getX();
        double deltaY = p.getY() - posizioneAttuale.getY();

        //lo applico a tutti gli oggetti
        GroupIterator it = new GroupIterator(this);
        while(it.hasNext()){
            GraphicObject obj = it.next();
            Point2D vecchiaPosizioneObj = obj.getPosition();
            obj.moveTo(new Point2D.Double(vecchiaPosizioneObj.getX() + deltaX, vecchiaPosizioneObj.getY() + deltaY));
        }

        //ricalcolo il centro del gruppo
        updatePosition();
        notifyListeners(new GraphicEvent(this));
    }

    @Override
    public Point2D getPosition() {
        return position;
    }//gePosition -> restituisce la posizione del gruppo come media dei centri dei componenti

    @Override
    public Dimension2D getDimension() {
        if(children.isEmpty())
            return new Dimension(0, 0);

        double minX = Double.MAX_VALUE, minY = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE, maxY = Double.MIN_VALUE;

        GroupIterator it = new GroupIterator(this);
        while(it.hasNext()){
            GraphicObject obj = it.next();
            Point2D position = obj.getPosition();
            Dimension2D dim = obj.getDimension();

            minX = Math.min(minX, position.getX() - dim.getWidth() / 2);
            minY = Math.min(minY, position.getY() - dim.getHeight() / 2);
            maxX = Math.max(maxX, position.getX() + dim.getWidth() / 2);
            maxY = Math.max(maxY, position.getY() + dim.getHeight() / 2);
        }

        double width = maxX - minX;
        double height = maxY - minY;

        Dimension2D dimension = new Dimension();
        dimension.setSize(width, height);
        return dimension;
        }//getDimension -> calcola e restituisce la dimensione del bounding box, se il gruppo è vuoto restituisce 0

    @Override
    public void scale(double factor) {
        GroupIterator it = new GroupIterator(this);
        while(it.hasNext()){
            GraphicObject obj = it.next();
            obj.scale(factor);

            //aggiorno la posizione rispetto al centro del gruppo
            Point2D objPosition = obj.getPosition();
            double deltaX = objPosition.getX() - this.getPosition().getX();
            double deltaY = objPosition.getY() - this.getPosition().getY();
            obj.moveTo(this.getPosition().getX() + deltaX * factor, this.getPosition().getY() + deltaY * factor);
        }
        notifyListeners(new GraphicEvent(this));

    }//scale -> scala di un determinato fattore tutti gli oggetti del gruppo e li riposiziona in modo da rispettare il centro

    @Override
    public boolean contains(Point2D p) {
        for (GraphicObject graphicObject : children) {
            if (graphicObject.contains(p)) {
                return true;
            }
        }
        return false;
    }//contains -> restituisce true se uno dei componenti contiene il punto desiderato

    @Override
    public String getType() {
        return "Group";
    }//getType -> restituisce il tipo dell'oggetto

    public void updatePosition() {
        double newHeight = this.getDimension().getHeight()/2.0;
        double newWidth = this.getDimension().getWidth()/2.0;
        position.setLocation(newWidth, newHeight);
    }//updatePosition -> aggiorna la posizione calcolando il centro del bounding box


    @Override
    public GraphicObjectMemento saveState() {
        List<GraphicObjectMemento> savedStates = new ArrayList<>();
        GroupIterator iterator = new GroupIterator(this);
        while (iterator.hasNext()) {
            savedStates.add(iterator.next().saveState());
        }
        return new GroupMemento(savedStates, this.getParent());
    }

    @Override
    public void restoreState(GraphicObjectMemento memento) {
        if (!(memento instanceof GroupMemento)) {
            throw new IllegalArgumentException("Invalid Memento for Group");
        }
        GroupMemento groupMemento = (GroupMemento) memento;
        setParent(groupMemento.getParent());
        GroupIterator iterator = new GroupIterator(this);
        Iterator<GraphicObjectMemento> mementoIterator = groupMemento.getChildrenMementos().iterator();

        while (iterator.hasNext() && mementoIterator.hasNext()) {
            iterator.next().restoreState(mementoIterator.next());
        }
    }

    public String toString() {
        return "Oggetto id: "+ getId() + " di tipo " + getType() + " in posizione " + getPosition() + "\n" + "Contenuto: " + getChildren();
    }
}

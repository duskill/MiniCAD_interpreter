package is.shapes.model.groups;

import is.shapes.model.AbstractGraphicObject;
import is.shapes.model.GraphicObject;
import is.shapes.view.GraphicObjectPanel;

import java.util.*;

//Classe Singleton per gestire i gruppi di oggetti grafici
public class GroupManager {
    /** istanza singleton: privata e statica, volatile per l'ottimizzazione in caso di uso multithread ->
     evita il riferimento a un'istanza costruita solo parzialmente */

    private static volatile GroupManager instance;
    //Mappa che associa ad ogni id il gruppo corrispondente
    private final Map<Integer, Group> groups;
    //Counter usato per incrementare di volta in volta l'id (non è necessario che sia statico perché l'istanza è unica)
    //private int groupIdCounter;
    // Riferimento al pannello grafico per gestire gli oggetti visibili
    private final GraphicObjectPanel panel;

    private GroupManager(GraphicObjectPanel panel) {
        this.groups = new HashMap<>();
       // this.groupIdCounter = 0; // Gli id partono da 0
        this.panel = panel;
    }//costruttore


    public static GroupManager getInstance(GraphicObjectPanel panel) { //non sincronizzo l'intero metodo
        if (instance == null) {//uso piuttosto il double-checked logic idiom
            synchronized (GroupManager.class) {
                if (instance == null) {
                    instance = new GroupManager(panel);
                }
            }
        }
        return instance; //così facendo se l'istanza esiste già i thread non devono attendere
    }//getInstance -> restituisce l'istanza della classe, se non esiste la crea prima di restituirla



    public int createGroup() {
        //int newGroupId = groupIdCounter++;
        Group newGroup = new Group();
        groups.put(newGroup.getId(), newGroup);
        panel.add(newGroup); // Aggiungo il gruppo anche al pannello grafico
        return newGroup.getId();
    }//createGroup -> crea un nuovo gruppo e ne restituisce l'id


    public boolean deleteGroup(int groupId) {
        Group group = groups.remove(groupId);
        if (group != null) {
            panel.remove(group); // Rimuove il gruppo dal pannello grafico
            return true;
        }
        return false; // restituisce true se l'eliminazione va a buon fine, falso se il gruppo non esiste
    }//deleteGroup -> elimina un gruppo esistente (non ciò che contiene)



    public boolean addToGroup(int groupId, AbstractGraphicObject object) {
        Group group = groups.get(groupId);
        if (group != null) {
            boolean added = group.add(object);
            if (added) {
                panel.remove(object); // Rimuovo l'oggetto dalla scena singola
            }
            return added;
        }
        return false; //restituisce true se l'oggetto è stato aggiunto, false altrimenti
    }//addToGroup -> aggiunge un oggetto a un gruppo


    public boolean removeFromGroup(int groupId, GraphicObject object) {
        Group group = groups.get(groupId);
        if (group != null) {
            group.remove(object);
            panel.add(object); // Reinserisco l'oggetto nella scena
            if (group.getChildren().isEmpty()){
                deleteGroup(groupId);
            }
            return true;// restituisce true se l'oggetto è stato rimosso, inoltre se il gruppo diventa vuoto lo elimina,
        }
        return false;// restituisce false se non era presente o il gruppo non esiste

    }//removeFromGroup -> rimuove un oggetto da un gruppo e lo reinserisce nella scena


    public Set<GraphicObject> getGroupObjects(int groupId) {
        Group group = groups.get(groupId);
        if (group == null) {
            return Collections.emptySet(); // Restituisce un set vuoto invece di generare un NullPointerException
        }
        return group.getChildren();
    }//getGroupObjects -> restituisce una lista degli oggetti presenti nel gruppo


    public Set<Integer> getAllGroupIds() {
        return groups.keySet();
    }//getAllGroupIds -> restituisce un set di tutti gli id esistenti


    public void clearAll() {
        for (Group group : groups.values()) {
            panel.remove(group); // Rimuove tutti i gruppi dal pannello
        }
        groups.clear();
        //groupIdCounter = 1;
    }//clearAll -> elimina tutti i gruppi e resetta il manager

    public Group getGroup(int GroupID){
        return groups.get(GroupID);
    }

}
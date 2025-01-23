package is.shapes.model.groups;

import is.shapes.model.GraphicObject;

import java.util.*;

//Classe Singleton per gestire i gruppi di oggetti grafici

public class GroupManager {
    /** istanza singleton: privata e statica, volatile per l'ottimizzazione in caso di uso multithread ->
     evita il riferimento a un'istanza costruita solo parzialmente */

    private static volatile GroupManager instance;
    //Mappa che associa ad ogni id il gruppo corrispondente
    private final Map<Integer, List<GraphicObject>> groups;
    //Counter usato per incrementare di volta in volta l'id (non è necessario che sia statico perché l'istanza è unica)
    private int groupIdCounter;


    private GroupManager() {
        groups = new HashMap<>();
        groupIdCounter = 0; // Gli id partono da 0
    }//costruttore


    public static GroupManager getInstance() { //non sincronizzo l'intero metodo
        if (instance == null) {//uso piuttosto il double-checked logic idiom
            synchronized (GroupManager.class) {
                if (instance == null) {
                    instance = new GroupManager();
                }
            }
        }
        return instance; //così facendo se l'istanza esiste già i thread non devono attendere
    }//getInstance -> restituisce l'stanza della classe, se non esiste la crea prima di restituirla



    public int createGroup() {
        int newGroupId = groupIdCounter++;
        groups.put(newGroupId, new ArrayList<>());
        return newGroupId;
    }//createGroup -> crea un nuovo gruppo e ne restituisce l'id


    public boolean deleteGroup(int groupId) {
        return groups.remove(groupId) != null; // restituisce true se l'eliminazione va a buon fine, falso se il gruppo non esiste
    }//deleteGroup -> elimina un gruppo esistente (non ciò che contiene)



    public boolean addToGroup(int groupId, GraphicObject object) {
        List<GraphicObject> group = groups.get(groupId);
        if (group != null) {
            return group.add(object);
        }
        return false; //restituisce true se l'oggetto è stato aggiunto, false altrimenti
    }//addToGroup -> aggiunge un oggetto a un gruppo


    public boolean removeFromGroup(int groupId, GraphicObject object) {
        List<GraphicObject> group = groups.get(groupId);
        if (group != null) {
            return group.remove(object);
        }
        return false; //restituisce true se l'oggetto è stato rimosso, false se non era presente o il gruppo non esiste

    }//removeFromGroup -> rimuove un oggetto a un gruppo


    public List<GraphicObject> getGroupObjects(int groupId) {
        return groups.get(groupId);
    }//getGroupObjects -> restituisce una lista degli oggetti presetni nel gruppo


    public Set<Integer> getAllGroupIds() {
        return groups.keySet();
    }//getAllGroupIds -> restituisce un set di tutti gli id esistenti


    public void clearAll() {
        groups.clear();
        groupIdCounter = 1;
    }//clearAll -> elimina tutti i gruppi e resetta il manager
}

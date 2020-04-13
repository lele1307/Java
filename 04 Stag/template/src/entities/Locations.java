package entities;

import java.util.ArrayList;

/**
 * @author dukehan
 */
public class Locations extends Entities {
    public String strArtefacts;
    public String strCharacters;
    public String strFurniture;
    private String cluster;
    private ArrayList<String> places;
    private ArrayList<Artefacts> artefacts;
    private ArrayList<Characters> characters;
    private ArrayList<Furniture> furniture;

    public Locations(String name, String description){
        super(name,description);
        strArtefacts = entityName.ARTEFACTS.getTypeName();
        strCharacters = entityName.CHARACTERS.getTypeName();
        strFurniture = entityName.FURNITURE.getTypeName();
        this.places = new ArrayList<String>();
        this.artefacts = new ArrayList<Artefacts>();
        this.characters = new ArrayList<Characters>();
        this.furniture = new ArrayList<Furniture>();
    }

    public void setCluster(String cluster){
        this.cluster = cluster;
    }

    public String getCluster(){
        return cluster;
    }

    public int setTypeArr(String type,String name,String description){
        if (strArtefacts.equals(type)){
            Artefacts a = new Artefacts(name,description);
            artefacts.add(a);
            return 1;
        } else if (strCharacters.equals(type)){
            Characters c = new Characters(name,description);
            characters.add(c);
            return 2;
        } else if (strFurniture.equals(type)){
            Furniture f = new Furniture(name,description);
            furniture.add(f);
            return 3;
        }
        return 0;
    }

    public ArrayList<String> getPlaces(){
        return places;
    }

    public void addPlaces(String target) { places.add(target); }

    public void decPlace(int index){ places.remove(index); }

    public ArrayList<Artefacts> getArtefacts(){
        return artefacts;
    }

    public void addArtefacts(Artefacts artefact){
        artefacts.add(artefact);
    }

    public void removeArtefacts(int index){
        artefacts.remove(index);
    }

    public ArrayList<Characters> getCharacters(){
        return characters;
    }

    public void addCharacters(Characters character){
        characters.add(character);
    }

    public void removeCharacters(int index){
        characters.remove(index);
    }

    public ArrayList<Furniture> getFurniture(){
        return furniture;
    }

    public void addFurniture(Furniture furniture1){
        furniture.add(furniture1);
    }

    public void removeFurniture(int index){
        furniture.remove(index);
    }


}


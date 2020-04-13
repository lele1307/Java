package environments;
import entities.*;
import java.util.ArrayList;
/**
 * @author dukehan
 */
public interface Printout {

    static String printArtefactsType(ArrayList<Artefacts> name){
        String line = new String();
        if (!name.isEmpty()){
            for (int i = 0; i <name.size(); i++){
                line = line.concat("\tArtefacts"+(i+1)+"\n" +
                        "\t\tname:"+name.get(i).getName()+"\n" +
                        "\t\tdescription:"+name.get(i).getDescription()+"\n");
            }

        } else {
            line = "\tNo Artefacts in this location!!\n";
        }
        return line;
    }

    static String printCharactersType(ArrayList<Characters> name){
        String line = new String();
        if (!name.isEmpty()){
            for (int i = 0; i <name.size(); i++){
                line = line.concat("\tCharacters"+(i+1)+"\n" +
                        "\t\tname:"+name.get(i).getName()+"\n" +
                        "\t\tdescription:"+name.get(i).getDescription()+"\n");
            }
        } else {
            line = "\tNo Characters in this location!!\n";
        }
        return line;
    }

    static String printFurnitureType(ArrayList<Furniture> name){
        String line = new String();
        if (!name.isEmpty()){
            for (int i = 0; i <name.size(); i++){
                line = line.concat("\tFurniture"+(i+1)+"\n" +
                        "\t\tname:"+name.get(i).getName()+"\n" +
                        "\t\tdescription:"+name.get(i).getDescription()+"\n");
            }
        } else {
            line = "\tNo Furniture in this location!!\n";
        }
        return line;
    }

    static String printPathType(ArrayList<String> name){
        String line = new String();
        if (!name.isEmpty()){
            for (int i = 0; i <name.size(); i++){
                line = line.concat("\tPath"+(i+1)+"\n"+
                        "\t\tCan go to:"+name.get(i)+"\n");
            }
        } else {
            line = "\tNo Path to other locations!!\n";
        }
        return line;
    }

    /*
    static void printStrArr(ArrayList<String> type){
        for (String s : type) {
            System.out.println("\t" + s);
        }
    }

    static void printPlayer(ArrayList<Player> players){
        System.out.println("============== World Player ==============");
        for (int i=0;i<players.size(); i++){
            System.out.println("PlayerName: "+players.get(i).getName());
            System.out.println("PlayerLevel: "+players.get(i).getLevel());
            System.out.println("PlayerLocation: "+players.get(i).getLocationInd());
            System.out.println("------------------------------");
        }
        System.out.println("============== World Player END ==============\n");
    }

    static String printLocationsType(ArrayList<Locations> entities){
        String line = new String();
        for (int i=0; i<entities.size(); i++){
            line = line.concat("Location"+(i+1)+" "+entities.get(i).getCluster()+"\n"+
                    "name:"+entities.get(i).getName()+"\n"+
                    "description:"+entities.get(i).getDescription()+"\n"+
                    printArtefactsType(entities.get(i).getArtefacts())+
                    printCharactersType(entities.get(i).getCharacters())+
                    printFurnitureType(entities.get(i).getFurniture())+
                    printPathType(entities.get(i).getPlaces())+
                    "---------------------------------------\n");
        }
        return line;
    }

    static void printPathsType(ArrayList<Paths> road){
        System.out.println("Paths");
        if (!road.isEmpty()) {
            for (Paths paths : road) {
                System.out.println("\tPath from " + paths.getStart() + " to " + paths.getTarget());
            }
        }else {
            System.out.println("wrong!!!!!");
        }
    }

    static void printActionsType(ArrayList<Actions> name){
        for (Actions action : name) {
            System.out.println("triggers:");
            printStrArr(action.getFixAction("triggers"));
            System.out.println("subjects:");
            printStrArr(action.getFixAction("subjects"));
            System.out.println("consumed:");
            printStrArr(action.getFixAction("consumed"));
            System.out.println("produced:");
            printStrArr(action.getFixAction("produced"));
            System.out.println("narration:");
            System.out.println("\t" + action.getNarration());
            System.out.println("---------------------------------------");
        }
    }
    */

}


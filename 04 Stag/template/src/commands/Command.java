package commands;
import entities.*;
import environments.World;
import java.util.ArrayList;
/**
 * @author dukehan
 */
public class Command {
    private String[] input;
    private String playerName;
    private int inputLen;
    public ArrayList<String> fixCommand;
    public int currLocationIndex;
    public int playerIndex;

    public void initCommand(String cmd, World world) {
        fixCommand = new ArrayList<>();
        setFixCommand();
        input = splitInput(cmd);
        inputLen = input.length;
        playerIndex = setCurrPlayer(world);
        //System.out.println("Cmd======currPlayerIndex: "+playerIndex+"\n");
    }

    public void setFixCommand(){
        fixCommand.add("inventory");
        fixCommand.add("look");
        fixCommand.add("health");
        fixCommand.add("get");
        fixCommand.add("drop");
        fixCommand.add("goto");
        fixCommand.add("exit");
    }

    public String[] splitInput(String cmd){
        return cmd.split(" ");
    }

    public void setPlayerName(){
        playerName = input[0].substring(0,input[0].length()-1);
    }

    public int setCurrPlayer(World world){
        if (inputLen > 1) {
            setPlayerName();
            for (int i = 0; i < world.getPlayers().size(); i++) {
                if (world.getPlayers().get(i).getName().equals(playerName)) {
                    currLocationIndex = world.getPlayers().get(i).getLocationInd();
                    return i;
                }
            }
        }
        return -1;
    }

    public  Locations currLocation(int playerIndex, World world){
        return world.getLocations().get(world.getPlayers().get(playerIndex).getLocationInd());
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public int getCurrLocationIndex() {
        return currLocationIndex;
    }

    public String[] getInput() {
        return input;
    }

    public String getLowCaseStrInput(int inputInd) {
        String inputStr = input[inputInd].toLowerCase();
        return inputStr;
    }

    public int getInputLen() {
        return inputLen;
    }

    public int[] findEntities(World world,String name){
        int [] index = {-1,-1};
        //places-0 artefact-1 character-2 furniture-3 inventory-4
        ArrayList<String> places = world.getLocations().get(currLocationIndex).getPlaces();
        ArrayList<Artefacts> artefacts = world.getLocations().get(currLocationIndex).getArtefacts();
        ArrayList<Characters> characters = world.getLocations().get(currLocationIndex).getCharacters();
        ArrayList<Furniture> furniture = world.getLocations().get(currLocationIndex).getFurniture();
        ArrayList<Inventory> inventories = world.getPlayers().get(playerIndex).getInventories();
        for(int i=0; i< places.size(); i++){
            if(places.get(i).equals(name)){
                index[0] = 0;
                index[1] = i;
                return index;
            }
        }
        for(int i=0; i< artefacts.size(); i++){
            if(artefacts.get(i).getName().equals(name)){
                index[0] = 1;
                index[1] = i;
                return index;
            }
        }
        for(int i=0; i< characters.size(); i++){
            if(characters.get(i).getName().equals(name)){
                index[0] = 2;
                index[1] = i;
                return index;
            }
        }
        for(int i=0; i< furniture.size(); i++){
            if(furniture.get(i).getName().equals(name)){
                index[0] = 3;
                index[1] = i;
                return index;
            }
        }
        for(int i=0; i< inventories.size(); i++){
            if(inventories.get(i).getName().equals(name)){
                index[0] = 4;
                index[1] = i;
                return index;
            }
        }
        return index;
    }

    public int getFixCmdTargetIndex(String action){
        int targetIndex;
        for (int i=0; i<getInputLen();i++){
            if(input[i].equals(action)){
                targetIndex = i+1;
                if (targetIndex>getInputLen()){
                    return -1;
                }
                return targetIndex;
            }
        }
        return -1;
    }

    public int getTriggerTarget(int action, ArrayList<String> subject){
        int flag = 0;
        if (getInputLen()>action+1){
            for (int j = 0; j<subject.size();j++){
                for (int i = action+1; i<input.length; i++){
                    if (input[i].equals(subject.get(j))){
                        flag++;
                    }
                }
            }
        }
        return flag;
    }


}

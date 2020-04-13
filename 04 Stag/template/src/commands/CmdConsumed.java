package commands;
import environments.World;
import java.util.ArrayList;

/**
 * @author dukehan
 */
public class CmdConsumed extends Command{
    private int playerIndex;
    static final String HEALTH = "health";

    CmdConsumed(int playerIndex){
        this.playerIndex =playerIndex;
    }

    public World cmdConsumed(World world, int currActionIndex, int[][] subjectsLocation, ArrayList<String> subject){
        int subjectsLocationIndex;
        ArrayList<String> currConsumed = world.getActions().get(currActionIndex).getFixAction("consumed");
        for (int i=0; i<currConsumed.size(); i++){
            if((currConsumed.get(i).equals(HEALTH))){
                world.getPlayers().get(playerIndex).decLevel();
            }else{
                for (int j=0; j<subject.size(); j++){
                    if(currConsumed.get(i).equals(subject.get(j))){
                        subjectsLocationIndex = j;
                        world = processConsumed(world,subjectsLocationIndex,subjectsLocation);
                    }
                }
            }
        }
        return world;
    }

    public World processConsumed(World world,int subjectsLocationIndex,int[][] subjectsLocation){
        System.out.println("currLocationIndex "+ getCurrLocationIndex());
        switch (subjectsLocation[subjectsLocationIndex][0]){
            case 0:
                currLocation(playerIndex,world).decPlace(subjectsLocation[subjectsLocationIndex][1]);
                break;
            case 1:
                currLocation(playerIndex,world).removeArtefacts(subjectsLocation[subjectsLocationIndex][1]);
                break;
            case 2:
                currLocation(playerIndex,world).removeCharacters(subjectsLocation[subjectsLocationIndex][1]);
                break;
            case 3:
                currLocation(playerIndex,world).removeFurniture(subjectsLocation[subjectsLocationIndex][1]);
                break;
            case 4:
                world.getPlayers().get(playerIndex).removeInventory(subjectsLocation[subjectsLocationIndex][1]);
                break;
            default:
                break;
        }
        return world;
    }

}

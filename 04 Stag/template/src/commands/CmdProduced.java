package commands;
import entities.Locations;
import environments.World;

import java.util.ArrayList;

/**
 * @author dukehan
 */
public class CmdProduced extends Command {
    private int playerIndex;

    CmdProduced(int playerIndex){
        this.playerIndex = playerIndex;
    }

    static final String HEALTH = "health";

    static final String UNPLACED = "unplaced";

    public World cmdProduced(World world, int currActionIndex){
        ArrayList<String> currProduced = world.getActions().get(currActionIndex).getFixAction("produced");
        for (String produced : currProduced) {
            if (produced.equals(HEALTH)) {
                world.getPlayers().get(playerIndex).addLevel();
            } else {
                world = cmdProducedLocation(world,produced);
                world = cmdProducedUnplaced(world,produced);
            }
        }
        return world;
    }

    public World cmdProducedUnplaced(World world,String produced){
        int unplacedIndex = -1;
        for (int i=0; i<world.getLocations().size(); i++){
            if (world.getLocations().get(i).getName().equals(UNPLACED)){
                unplacedIndex = i;
            }
        }
        if (unplacedIndex>=0){
            Locations unplaced = world.getUnplaced(unplacedIndex);
            for(int i=0; i< unplaced.getArtefacts().size(); i++){
                if(unplaced.getArtefacts().get(i).getName().equals(produced)){
                    currLocation(playerIndex,world).addArtefacts(unplaced.getArtefacts().get(i));
                    world.getUnplaced(unplacedIndex).removeArtefacts(i);
                    return world;
                }
            }
            for(int i=0; i< unplaced.getCharacters().size(); i++){
                if(unplaced.getCharacters().get(i).getName().equals(produced)){
                    currLocation(playerIndex,world).addCharacters(unplaced.getCharacters().get(i));
                    world.getUnplaced(unplacedIndex).removeCharacters(i);
                    return world;
                }
            }
            for(int i=0; i< unplaced.getFurniture().size(); i++){
                if(unplaced.getFurniture().get(i).getName().equals(produced)){
                    currLocation(playerIndex,world).addFurniture(unplaced.getFurniture().get(i));
                    world.getUnplaced(unplacedIndex).removeFurniture(i);
                    return world;
                }
            }
        }
        return world;
    }

    public World cmdProducedLocation(World world,String produced){
        for (int i=0; i<world.getLocations().size(); i++){
            if (world.getLocations().get(i).getName().equals(produced)){
                currLocation(playerIndex,world).addPlaces(produced);
                return world;
            }
        }
        return world;
    }


}

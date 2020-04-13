package commands;
import environments.World;
/**
 * @author dukehan
 */
public class CmdGoto extends Command {
    private int playerIndex;

    CmdGoto(int playerIndex){
        this.playerIndex = playerIndex;
    }

    public World processGoto(String place,World world){
        int targetLocationIndex = -1;
        for (int i=0;i<currLocation(playerIndex,world).getPlaces().size();i++){
            if (place.equals(currLocation(playerIndex,world).getPlaces().get(i))){
                targetLocationIndex = i;
            }
        }
        if (targetLocationIndex<0){
            world.setDirections("You can't goto \'"+place+"\'! Invalid Place!");
        } else {
            for (int i=0;i<world.getLocations().size();i++){
                if (place.equals(world.getLocations().get(i).getName())){
                    world.getPlayers().get(playerIndex).setLocationInd(i);
                    world.setDirections("You can use 'look' checking your current location.");
                }
            }

        }
        return world;
    }

}

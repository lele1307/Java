package commands;
import entities.Inventory;
import environments.World;
/**
 * @author dukehan
 */
public class CmdGet extends Command {
    private int playerIndex;

    CmdGet(int playerIndex){
        this.playerIndex = playerIndex;
    }

    public World processGet(String artefact,World world){
        Inventory inventory;
        for (int i=0; i<currLocation(playerIndex,world).getArtefacts().size(); i++){
            if (artefact.equals(currLocation(playerIndex,world).getArtefacts().get(i).getName())){
                inventory = new Inventory(artefact,currLocation(playerIndex,world).getArtefacts().get(i).getDescription());
                world.getPlayers().get(playerIndex).addInventories(inventory);
                currLocation(playerIndex,world).removeArtefacts(i);
                world.setDirections("You get \'"+artefact+"\' !");
                return world;
            }
        }
        world.setDirections("You cannot get \'"+artefact+"\' ! It is not in this location.");
        return world;
    }

}

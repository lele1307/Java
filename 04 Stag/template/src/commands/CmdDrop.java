package commands;
import entities.Artefacts;
import environments.World;
/**
 * @author dukehan
 */
public class CmdDrop extends Command {
    private int playerIndex;

    CmdDrop(int playerIndex){
        this.playerIndex = playerIndex;
    }

    public World processDrop(String artefact,World world){
        Artefacts artefacts;
        for (int i=0; i<world.getPlayers().get(playerIndex).getInventories().size(); i++){
            if (artefact.equals(world.getPlayers().get(playerIndex).getInventories().get(i).getName())){
                artefacts = new Artefacts(artefact,world.getPlayers().get(playerIndex).getInventories().get(i).getDescription());
                currLocation(playerIndex,world).addArtefacts(artefacts);
                world.getPlayers().get(playerIndex).removeInventory(i);
                world.setDirections("You drop \'"+artefact+"\' !");
                return world;
            }
        }
        world.setDirections("You cannot drop \'"+artefact+"\' ! It is not in your Inventory.");
        return world;
    }

}

package commands;
import environments.*;
/**
 * @author dukehan
 */
public class CmdInventory extends Command {
    private int playerIndex;

    CmdInventory(int playerIndex){
        this.playerIndex = playerIndex;
    }

    public World processInventory(World world){
        String line = new String();
        if (world.getPlayers().get(playerIndex).getInventories().size()<=0){
            line = "Nothing in your Inventory!!\n";
        }else {
            line = "Your Inventory List: \n";
            System.out.println("Your Inventory List: ");
            for (int i=0; i<world.getPlayers().get(playerIndex).getInventories().size(); i++){
                line = line.concat("name: "+world.getPlayers().get(playerIndex).getInventories().get(i).getName()+"\n"+
                        "description: "+world.getPlayers().get(playerIndex).getInventories().get(i).getDescription()+"\n"+
                        "----------------------------\n");
            }
        }
        world.setDirections(line);
        return world;
    }
}

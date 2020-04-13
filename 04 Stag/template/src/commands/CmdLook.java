package commands;
import environments.*;
/**
 * @author dukehan
 */
public class CmdLook extends Command {
    private int playerIndex;

    CmdLook(int playerIndex){
        this.playerIndex = playerIndex;
    }

    public World processLook(World world){
        String line = new String();
        line = "Current Location: "+currLocation(playerIndex,world).getName()+"\n"+
                "description: "+currLocation(playerIndex,world).getDescription()+"\n"+
                Printout.printArtefactsType(currLocation(playerIndex,world).getArtefacts())+
                Printout.printCharactersType(currLocation(playerIndex,world).getCharacters())+
                Printout.printFurnitureType(currLocation(playerIndex,world).getFurniture())+
                Printout.printPathType(currLocation(playerIndex,world).getPlaces());
        world.setDirections(line);
        return world;
    }
}

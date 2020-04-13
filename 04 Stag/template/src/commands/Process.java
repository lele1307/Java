package commands;
import environments.World;
import java.util.ArrayList;
/**
 * @author dukehan
 */
public class Process extends Command {

    static final int SINGLE_CMD = 2;

    static final int DOUBLE_CMD = 3;

    public void setProcess(String cmd, World world) {
        super.initCommand(cmd,world);
    }

    public String interpreterCmd(World world){
        String action;
        int actionIndex = -1;
        ArrayList<Integer> currActionIndex = new ArrayList<>();
        int currSubjectSize;
        ArrayList<String> subject;
        if (getPlayerIndex() >= 0){
            for (int i=1; i<getInput().length; i++){
                action = cmpFixCmd(getLowCaseStrInput(i));
                if (action.equals(getLowCaseStrInput(i))){
                    world = runFixCmd(action,world);
                    return world.getDirections();
                    //FixCmd
                }else{
                    for (int j=0; j<world.getActions().size();j++){
                        for (int k=0; k<world.getActions().get(j).getFixAction("triggers").size();k++){
                            if (getLowCaseStrInput(i).equals(world.getActions().get(j).getFixAction("triggers").get(k))){
                                actionIndex = i;
                                currActionIndex.add(j);
                            }
                        }
                    }
                    if (currActionIndex.size()>0){
                        for (int h=0; h<currActionIndex.size();h++){
                            int currIndex = currActionIndex.get(h);
                            currSubjectSize = world.getActions().get(currIndex).getFixAction("subjects").size();
                            subject = world.getActions().get(currIndex).getFixAction("subjects");
                            world = checkInputCmd(actionIndex,subject,world);
                            world = runTriggerCmd(currSubjectSize, currIndex, subject, world);
                            if (world.getTriggerStatus() != 3) {
                                return world.getDirections();
                            }
                        }
                        return world.getDirections();
                    }
                    //TriggerCmd
                }
            }
        }
        world.setDirections("Invalid Command!");
        return world.getDirections();
    }

    public World checkInputCmd(int actionIndex,ArrayList<String> subject,World world){
        if (getInputLen()>SINGLE_CMD){
            int flag = getTriggerTarget(actionIndex,subject);
            if (flag==0){
                world.setInputStatus(1);
            }else {
                world.setInputStatus(0);
            }
        } else {
            world.setInputStatus(-1);
        }
        return world;
    }

    public String cmpFixCmd(String keyword){
        for (String s : fixCommand) {
            if (s.equals(keyword)) {
                return keyword;
            }
        }
        return "";
    }

    public World runFixCmd(String action,World world){
        if (getPlayerIndex()>=0){
            if (getInputLen()>=SINGLE_CMD) {
                switch (action) {
                    case "inventory":
                        CmdInventory inventory = new CmdInventory(getPlayerIndex());
                        return inventory.processInventory(world);
                    case "look":
                        CmdLook look = new CmdLook(getPlayerIndex());
                        return look.processLook(world);
                    case "health":
                        world.setDirections("Health level: " + world.getPlayers().get(getPlayerIndex()).getLevel());
                        break;
                    case "exit":
                        world.setDirections("Bye~");
                        System.exit(0);
                    default:
                        world.setDirections("Invalid Command! " + "'" + action + "' is a command but cannot use it without target.");
                        break;
                }
            }

            if (getInputLen() >= DOUBLE_CMD){
                int targetIndex = getFixCmdTargetIndex(action);
                switch (action) {
                    case "goto":
                        CmdGoto goTo = new CmdGoto(getPlayerIndex());
                        return goTo.processGoto(getLowCaseStrInput(targetIndex),world);
                    case "get":
                        CmdGet geT = new CmdGet(getPlayerIndex());
                        return geT.processGet(getLowCaseStrInput(targetIndex),world);
                    case "drop":
                        CmdDrop droP = new CmdDrop(getPlayerIndex());
                        return droP.processDrop(getLowCaseStrInput(targetIndex),world);
                    default:
                        world.setDirections("Invalid Command!");
                        break;
                }
            }

        }
        return world;
    }

    public World runTriggerCmd(int currSubjectSize,int currActionIndex,ArrayList<String> subject,World world){
        int[][] subjectsLocation = new int[currSubjectSize][];
        //CMP With Location entities and Inventory
        for (int i=0; i<subject.size(); i++){
            subjectsLocation[i] = findEntities(world,subject.get(i));
        }
        CmdTrigger trigger = new CmdTrigger();
        if (trigger.cmpTriggerCmd(subjectsLocation)&&world.getInputStatus()<1){
            CmdConsumed consumed = new CmdConsumed(getPlayerIndex());
            world = consumed.cmdConsumed(world,currActionIndex,subjectsLocation,subject);
            CmdProduced produced = new CmdProduced(getPlayerIndex());
            world = produced.cmdProduced(world,currActionIndex);
            if (world.getPlayers().get(getPlayerIndex()).getLevel()<=0){
                world.getPlayers().get(getPlayerIndex()).initLevel();
                world.setDirections("Your health level run out, you should go back to start places.\n");
                world.setTriggerStatus(1);
            }else {
                world.setDirections(world.getActions().get(currActionIndex).getNarration());
                world.setTriggerStatus(2);
            }
        }else {
            world.setDirections("Your cannot execute this instruction! Please check your input or status.\n");
            world.setTriggerStatus(3);
        }
        return world;
    }

}

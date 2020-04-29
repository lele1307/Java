package process;
import commandtype.*;
import commandtype.create.*;
import java.util.Map;

import static java.lang.System.*;

/**
 * @author dukehan
 */
public class Command  {
    private Map<String,CommonHandler> allAvailableTrigger;

    private String[] input;

    public Command(String cmd, Terminal terminal) {
        Input newCmd = new Input(cmd);
        this.input = newCmd.getInput();
        allAvailableTrigger = Map.of(
                "CREATE", new Create(), "USE", new Use(),
                "DROP", new Drop(), "ALTER", new Alter(),
                "INSERT", new Insert(), "JOIN", new Join(),
                "DELETE", new Delete(), "UPDATE", new Update(),
                "SELECT",new Select());
        if (input!=null){
            if (input.length<=0){
                terminal.setOutput("");
                return;
            }else if ("error".equals(input[0])){
                terminal.setOutput("ERROR Missing ;");
                return;
            }else {
                executeThisCommand(terminal,input[0]);
            }
        }
    }

    public Terminal executeThisCommand(Terminal terminal,String trigger){
        trigger = trigger.toUpperCase();
        CommonHandler commonHandler = allAvailableTrigger.get(trigger);
        if (commonHandler==null){
            terminal.setOutput("ERROR: Please check your input command!");
            return terminal;
        }
        return commonHandler.runCommand(terminal,input);

    }

}

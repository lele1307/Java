package process;
import commandtype.*;
import commandtype.create.*;
import commandtype.drop.*;
import content.Condition;

import static java.lang.System.*;

/**
 * @author dukehan
 */
public class Command extends Input {
    private String[] input;
    public Command(String cmd, Terminal terminal) {
        super(cmd);
        this.input = getInput();
        if (input.length<=0){
            terminal.setOutput("");
            return;
        }else if (input[0].equals("error")){
            terminal.setOutput("ERROR Missing ;");
            return;
        }else {
            printInput();
            processCmd(terminal);
        }

    }

    public void printInput(){
        for (int i=0; i<input.length; i++) {
            out.println(input[i]);
        }
        System.out.println("------ printInput() END------");
    }

    public String upperString(String target){
        return  target.toUpperCase();
    }

    public Terminal createCmd(Terminal terminal,String[] cmd){
        if (cmd.length>=3){
            String target = upperString(cmd[1]);
            String fileName = cmd[2];
            if (target.equals("DATABASE")){
                CreateDatabase actionCd = new CreateDatabase(fileName);
                terminal = actionCd.setCurrTerminal(terminal);
                return terminal;
            }else if (target.equals("TABLE")){
                CreateTable actionCt = new CreateTable(cmd,terminal.getCurrentPath());
                terminal = actionCt.setTable(terminal);
                return terminal;
            }else{
                terminal.setOutput("Input Error!");
                return terminal;
            }
        }
        terminal.setOutput("Input Error! Please use right 'Create' query. ");
        return terminal;
    }

    public Terminal processCmd(Terminal terminal){
        String trigger = upperString(input[0]);
        switch (trigger){
            case "CREATE":
                Create parseC = new Create();
                if (parseC.parseCreate(terminal,input)){
                    return createCmd(terminal,input);
                } else {
                    terminal.setOutput("Create parse fail!!");
                    return terminal;
                }
            case "USE":
                Use actionUse = new Use();
                return actionUse.cmdUse(terminal,input);
            case "DROP":
                Drop actionDrop = new Drop();
                return actionDrop.cmdDrop(terminal,input);
            case "ALTER":
                Alter actionAlter = new Alter();
                return actionAlter.cmdAlter(terminal,input);
            case "INSERT":
                Insert actionInsert = new Insert();
                return actionInsert.cmdInsert(terminal,input);
            case "JOIN":
                Join actionJoin = new Join();
                return actionJoin.cmdJoin(terminal,input);
            case "DELETE":
                Delete actionDelete = new Delete();
                return actionDelete.cmdDelete(terminal,input);
            default:
                Condition condition = new Condition(input);
                condition.printCondition();
                System.out.println(condition.parseCondition());
                terminal.setOutput("Please check your input command!");
                return terminal;
        }
    }
}

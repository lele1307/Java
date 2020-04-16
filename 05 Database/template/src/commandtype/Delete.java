package commandtype;
import content.Condition;
import process.Terminal;

public class Delete {
    final static int CMDLEN = 7;
    final static int FROM = 1;
    final static int WHERE = 3;
    public Terminal cmdDelete(Terminal terminal, String[] command){
        if (parseCmd(command)){
            terminal.setOutput("Delete parse OK");
            return terminal;
        }
        terminal.setOutput("Delete parse fail!!");
        return terminal;
    }

    public boolean parseCmd(String[] command){
        if (command.length==CMDLEN){
            String where = command[WHERE];
            String from = command[FROM];
            if ("WHERE".equals(where) && "FROM".equals(from)){
                Condition condition = new Condition(command);
                if(condition.parseCondition()){
                    return true;
                }
            }
        }
        return false;
    }
}

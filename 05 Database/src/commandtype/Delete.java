package commandtype;
import content.Condition;
import content.Name;
import process.Terminal;
/**
 * @author dukehan
 */
public class Delete {
    final static int CMDLEN = 5;
    final static int FROM = 1;
    final static int WHERE = 3;
    final static int TABLENAME = 2;
    private String[] condition;
    public Terminal cmdDelete(Terminal terminal, String[] command){
        if (parseCmd(command)){
            terminal.setOutput("Delete parse OK");
            return terminal;
        }
        terminal.setOutput("Delete parse fail!!");
        return terminal;
    }

    public boolean parseCmd(String[] command){
        if (command.length==CMDLEN && Name.parseName(command[TABLENAME])){
            String where = command[WHERE];
            String from = command[FROM];
            if ("WHERE".equals(where) && "FROM".equals(from)){
                Condition condition = new Condition(command);
                if(condition.parseConditionStr(condition.getConditionStr())){
                    return true;
                }
            }
        }
        return false;
    }
}

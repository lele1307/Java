package commandtype;
import process.Terminal;
/**
 * @author dukehan
 */
public class Alter {
    final static int CMDLEN = 5;
    final static int TABLE = 1;
    final static int ALTTYPE = 3;
    public Terminal cmdAlter(Terminal terminal, String[] command){
        if (parseCmd(command)){
            terminal.setOutput("Alter parse OK");
            return terminal;
        }
        terminal.setOutput("Alter parse fail!!");
        return terminal;
    }

    public boolean parseCmd(String[] command){
        if (command.length==CMDLEN && command[TABLE].toUpperCase().equals("TABLE")){
            String AlterationType = command[ALTTYPE].toUpperCase();
            if ("ADD".equals(AlterationType)|| "DROP".equals(AlterationType)){
                return true;
            }
        }
        return false;
    }
}

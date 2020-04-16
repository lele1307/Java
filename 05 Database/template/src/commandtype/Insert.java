package commandtype;
import process.Terminal;
import content.Value;

/**
 * @author dukehan
 */
public class Insert extends Value {
    final static int CMDLEN = 5;
    final static int VAL = 4;
    final static int VALUES = 3;
    final static int INTO = 1;

    public Insert() {
        super();
    }

    public Terminal cmdInsert(Terminal terminal, String[] command){
        if (parseCmd(command)){
            terminal.setOutput("Insert parse OK");
            return terminal;
        }
        terminal.setOutput("Insert parse fail!!");
        return terminal;
    }

    public boolean parseCmd(String[] command){
        if (command.length==CMDLEN){
            String into = command[INTO].toUpperCase();
            String values = command[VALUES].toUpperCase();
            if ("INTO".equals(into)&& "VALUES".equals(values)){
                setOrgValueList(command,VAL);
                parseValueList();
                if (getValueList()!=null){
                    return true;
                }
            }
        }
        return false;
    }
}

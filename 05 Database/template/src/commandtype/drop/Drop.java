package commandtype.drop;
import process.Terminal;

/**
 * @author dukehan
 */
public class Drop {
    final static int CMDLEN = 3;
    final static int STRUCTURE = 1;
    final static int FILENAME = 2;
    public Terminal cmdDrop(Terminal terminal, String[] command){
        if (parseCmd(command)){
            terminal.setOutput("Drop parse OK");
            return terminal;
        }
        terminal.setOutput("Drop parse fail!!");
        return terminal;
    }

    public boolean parseCmd(String[] command){
        if (command.length==CMDLEN){
            String structure = command[STRUCTURE].toUpperCase();
            if ("DATABASE".equals(structure)|| "TABLE".equals(structure)){
                return true;
            }
        }
        return false;
    }

    public Terminal compileDrop(Terminal terminal,String[] command){
        if (command[STRUCTURE].equals("DATABASE")){
            terminal.setOutput("Drop DATABASE OK");
        }
        else if (command[STRUCTURE].equals("TABLE")){
            terminal.setOutput("Drop DATABASE OK");
        }
        return terminal;
    }

}

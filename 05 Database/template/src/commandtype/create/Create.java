package commandtype.create;
import process.Terminal;
/**
 * @author dukehan
 */
public class Create {
    final static int CMDLEN = 3;
    final static int STRUCTURE = 1;
    public boolean parseCreate(Terminal terminal, String[] command){
        if (parseCmd(command)){
            terminal.setOutput("Create parse OK");
            return true;
        }
        return false;
    }

    public boolean parseCmd(String[] command){
        if (command.length>=CMDLEN){
            String structure = command[STRUCTURE].toUpperCase();
            if ("DATABASE".equals(structure)|| "TABLE".equals(structure)){
                if (command.length>CMDLEN){
                    return parseAttributeList(command);
                }
                return true;
            }
        }
        return false;
    }

    public boolean parseAttributeList(String[] command){
        int brackets = 0;
        for (int i=CMDLEN;i<command.length;i++){
            for (int j=0;j<command[i].length();j++){
                if (command[i].charAt(j)=='('||command[i].charAt(j)==')'){
                    brackets++;
                }
            }
        }
        if (brackets==2){
            return true;
        }
        return false;
    }
}

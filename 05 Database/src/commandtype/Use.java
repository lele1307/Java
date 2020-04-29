package commandtype;
import content.Name;
import data.Database;
import process.Terminal;
import java.io.File;
import java.util.ArrayList;

/**
 * @author dukehan
 */
public class Use extends CommonHandler {
    static final int CMDLEN = 2;
    static final int NAME = 1;

    @Override
    public Terminal runCommand(Terminal terminal, String[] command) {
        if (parseCommand(command)){
            executeCommand(terminal,command);
        }else {
        terminal.setOutput("ERROR: Please check your input! 'USE <DatabaseName>' ");
        }
        return terminal;
    }

    @Override
    public boolean parseCommand(String[] command) {
        if (command.length==CMDLEN && Name.parseName(command[NAME])){
            return true;
        }
        return false;
    }

    @Override
    public Terminal executeCommand(Terminal terminal, String[] command) {
        String dBname = command[1];
        String path = terminal.getRootPath()+dBname+"/";
        File database = new File(path);
        if (database.exists()){
            terminal.setCurrentDB(dBname);
            terminal.setCurrentPath(path);
            terminal.setCurrentDbIndex(currDbIndex(dBname,terminal.getDatabases()));
            terminal.setOutput("OK");
            return terminal;
        }
        terminal.setOutput("ERROR: Use change fail! Database not exist!");
        return terminal;
    }

    public int currDbIndex(String dBname, ArrayList<Database> databases){
        for (int i=0; i<databases.size(); i++){
            if (dBname.equals(databases.get(i).getName())){
                return i;
            }
        }
        return -1;
    }


}

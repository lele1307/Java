package commandtype;
import data.Database;
import process.Terminal;
import java.io.File;
import java.util.ArrayList;

/**
 * @author dukehan
 */
public class Use {
    final static int CMDLEN = 2;
    public  Terminal cmdUse(Terminal terminal,String[] command){
        String dBname = command[1];
        String path = terminal.getRootPath()+dBname+"/";
        if (parseCmd(command)){
            File database = new File(path);
            if (database.exists()){
                terminal.setCurrentDB(dBname);
                terminal.setCurrentPath(path);
                terminal.setCurrentDbIndex(currDbIndex(dBname,terminal.getDatabases()));
                terminal.setOutput("Use change OK");
                return terminal;
            }
            terminal.setOutput("Use change fail! Database not exist!");
            return terminal;
        }
        terminal.setOutput("Please check your input! 'USE <DatabaseName>' ");
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

    public boolean parseCmd(String[] command){
        if (command.length==CMDLEN){
            return true;
        }
        return false;
    }
}

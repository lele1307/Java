package commandtype.create;
import java.io.File;
import process.Terminal;
import data.Database;
/**
 * @author dukehan
 */
public class CreateDatabase {
    private String dbName;
    private String dbPath;
    private boolean result;

    public CreateDatabase(String dbName){
        this.dbName = dbName;
        this.dbPath = "data/"+ dbName;
        this.result = makeDirectory(dbPath);
    }

    public Terminal setCurrTerminal(Terminal terminal){
        if (result){
            Database database = new Database(dbName,dbPath+"/");
            terminal.setDatabases(database);
            terminal.setCurrentDB(database.getName());
            terminal.setCurrentPath(database.getPath());
            terminal.setOutput("OK");
            return terminal;
        }
        terminal.setOutput("ERROR : CreateDatabase fail!");
        return terminal;
    }

    public boolean makeDirectory(String path) {
        File database = new File(path);
        //if exist | create new
        if(!database.exists()){
            database.mkdir();
            return true;
        }
        return false;
    }

}

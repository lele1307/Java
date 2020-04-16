package process;
import data.Database;
import data.Table;
import java.util.ArrayList;

/**
 * @author dukehan
 */
public class Terminal {
    private String rootPath;
    private String output;
    private String currentDB;
    private int currentDbIndex;

    private String currentTable;
    private int currentTableIndex;

    private String currentPath;
    private ArrayList<Database> Databases;

    public Terminal(){
        this.rootPath = "data/";
        this.Databases = new ArrayList<Database>();
        this.currentDbIndex = -1;
        setOutput("Welcome to use Home brew database System!");
    }

    public String getRootPath(){
        return rootPath;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getOutput() {
        return output;
    }

    public String getCurrentDB() {
        return currentDB;
    }

    public void setCurrentDB(String currentDB) {
        this.currentDB = currentDB;
    }

    public String getCurrentTable() {
        return currentTable;
    }

    public void setCurrentTable(String currentTable) {
        this.currentTable = currentTable;
    }

    public String getCurrentPath() {
        return currentPath;
    }

    public void setCurrentPath(String currentPath) {
        this.currentPath = currentPath;
    }

    public ArrayList<Database> getDatabases() {
        return Databases;
    }

    public void addDatabaseTable(Table table){
        Databases.get(currentDbIndex).setTables(table);
    }

    public void delDatabaseTable(String tableName){
        Databases.get(currentDbIndex).delTable(tableName);
    }

    public void setDatabases(Database database) {
        Databases.add(database);
        currentDbIndex = Databases.size()-1;
    }

    public int getCurrentDbIndex() {
        return currentDbIndex;
    }

    public void setCurrentDbIndex(int currentDbIndex) {
        this.currentDbIndex = currentDbIndex;
    }

}

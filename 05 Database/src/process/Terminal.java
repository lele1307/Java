package process;
import commandtype.inout.Reader;
import data.Database;
import data.Table;

import java.io.File;
import java.util.ArrayList;

/**
 * @author dukehan
 */
public class Terminal {
    private String rootPath;
    private String output;

    private String currentDB;
    private int currentDbIndex;

    private boolean fixTableStatus;

    private String currentPath;
    private ArrayList<Database> Databases;

    public Terminal(){
        this.rootPath = "data/";
        File root = new File(rootPath);
        //if exist | create new
        if(!root.exists()){
            root.mkdir();
        }
        this.Databases = new ArrayList<Database>();
        this.currentDbIndex = 0;
        setOutput("Welcome to use Home brew database System!");
        this.fixTableStatus = false;
        getFile(rootPath);
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

    public int getCurrentDbIndex() {
        return currentDbIndex;
    }

    public void setCurrentDbIndex(int currentDbIndex) {
        this.currentDbIndex = currentDbIndex;
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

    public void setDatabases(Database database) {
        if (getDatabasesSize()!=0){
            for (int i=0; i<getDatabasesSize(); i++){
                if (Databases.get(i).getName().equals(database.getName())){
                    return;
                }
            }
        }
        Databases.add(database);
        currentDbIndex = Databases.size()-1;

    }

    public int getDatabasesSize(){
        return Databases.size();
    }

    public String delDatabase(String databaseName){
        String pathname = "";
        if (getDatabasesSize()>0){
            for (int i=0; i<Databases.size();i++){
                if (Databases.get(i).getName().equals(databaseName)){
                    pathname = Databases.get(i).getPath();
                    Databases.remove(i);
                    currentDbIndex--;
                }
            }
        }
        return pathname;
    }

    public void addDatabaseTable(Table table){
        Databases.get(currentDbIndex).setTables(table);
    }

    public String delDatabaseTable(String tableName){
        String pathname = "";
        if(Databases.get(currentDbIndex).delTable(tableName)){
            pathname = Databases.get(currentDbIndex).getPath()+tableName+".csv";
        }
        return pathname;
    }

    public boolean fixCurrTableAttributes(String tableName,String Attribute,String alterType){
        ArrayList<Table> tables = Databases.get(currentDbIndex).getTables();
        String table = tableName+".csv";
        for (int i=0;i<tables.size();i++){
            if(tables.get(i).getName().equals(table)){
                if (alterType.equals("ADD")){
                    Databases.get(currentDbIndex).getTables().get(i).getAttributes().add(Attribute);
                    Databases.get(currentDbIndex).getTables().get(i).addNumAttributes();
                    return true;
                }else if(alterType.equals("DROP")){
                    Databases.get(currentDbIndex).getTables().get(i).getAttributes().remove(Attribute);
                    Databases.get(currentDbIndex).getTables().get(i).dropNumAttributes();
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isFixTableStatus() {
        return fixTableStatus;
    }

    public void setFixTableStatus(boolean fixTableStatus) {
        this.fixTableStatus = fixTableStatus;
    }

    public void getFile(String path){
        // get file list where the path has   
        File file = new File(path);
        // get the folder list   
        File[] array = file.listFiles();
        for(int i=0;i<array.length;i++){
            if(array[i].isFile()){
                if (!array[i].getName().equals(".DS_Store")){
                    initTable(array[i].getPath(),array[i].getName());
                }
            }else if(array[i].isDirectory()){
                initDb(array[i].toString());
                getFile(array[i].getPath());
            }
        }
    }

    public void initDb(String dBPath){
        String databaseName = dBPath.substring(rootPath.length());
        if (databaseName.equals(".DS_Store")){
            return;
        }
        String databasePath = dBPath+"/";
        Database newDb = new Database(databaseName,databasePath);
        setDatabases(newDb);
    }

    public void initTable(String tablePath,String tableName){
        Reader reader = new Reader(tablePath);
        String[] attributes = reader.readFirstLine();
        Table newTable = new Table(tableName,attributes);
        addDatabaseTable(newTable);
    }

}

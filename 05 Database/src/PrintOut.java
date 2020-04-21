import data.*;
import process.Terminal;

import java.util.ArrayList;
/**
 * @author dukehan
 */
public interface PrintOut {
    public static void printCurrent(Terminal terminal){
        System.out.println("CurrentDB: "+terminal.getCurrentDB());
        System.out.println("CurrentPath: "+terminal.getCurrentPath());
        System.out.println("CurrentDBIndex: "+terminal.getCurrentDbIndex());
    }
    public static void printAllDb(Terminal terminal){
        ArrayList<Database> databases = terminal.getDatabases();
        for (int i=0; i<databases.size();i++){
            System.out.println("---------");
            System.out.println("databaseName: "+databases.get(i).getName());
            System.out.println("databasePath: "+databases.get(i).getPath());
            System.out.println("databaseTables: "+databases.get(i).getTableNum());
            printAllTable(databases.get(i));
        }
    }
    public static void printAllTable(Database database){
        ArrayList<Table> tables = database.getTables();
        for (int i=0; i<tables.size();i++){
            System.out.println("TableName: "+tables.get(i).getName());
            System.out.println("TableNumAttributes: "+tables.get(i).getNumAttributes());
            if (tables.get(i).getNumAttributes()>0){
                ArrayList<String> attributes = tables.get(i).getAttributes();
                for (int j=0; j<attributes.size(); j++){
                    System.out.println("index: "+j +" "+ attributes.get(j));
                }
            }
        }
    }
}

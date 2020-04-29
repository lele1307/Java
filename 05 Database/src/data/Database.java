package data;
import java.util.ArrayList;

/**
 * @author dukehan
 */
public class Database {

    private String name;

    private String path;

    private int tableNum;

    ArrayList<Table> tables;

    public Database(String name,String path){
        this.name = name;
        this.path = path;
        this.tableNum = 0;
        tables = new ArrayList<Table>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setTables(Table table){
        tables.add(table);
        tableNum++;
    }

    public boolean delTable(String tableName){
        String table = tableName+".csv";
        for (int i=0; i<tables.size(); i++){
            if (table.equals(tables.get(i).getName())){
                tables.remove(i);
                tableNum--;
                return true;
            }
        }
        return false;
    }

    public ArrayList<Table> getTables(){
        return tables;
    }

    public int getTableNum() {
        return tableNum;
    }

}

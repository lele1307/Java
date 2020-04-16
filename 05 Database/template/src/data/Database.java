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

    public void setPath(String path) {
        this.path = path;
    }

    public void setTables(Table table){
        tables.add(table);
        tableNum++;
    }

    public void delTable(String tableName){
        for (int i=0; i<tables.size(); i++){
            if (tableName.equals(tables.get(i).getName())){
                tables.remove(i);
                tableNum--;
            }
        }
    }

    public ArrayList<Table> getTables(){
        return tables;
    }

    public int getTableNum() {
        return tableNum;
    }

    public void setTableNum(int tableNum) {
        this.tableNum = tableNum;
    }
}

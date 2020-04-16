package commandtype.create;
import data.*;
import process.Terminal;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author dukehan
 */
public class CreateTable {
    final static int CMD = 3;
    private String[] attributes;
    private String tableName;
    private String tablePath;
    private boolean result;
    public CreateTable(String[] command,String tablePath){
        this.tableName = command[2]+".txt";
        this.tablePath = tablePath;
        if (command.length > CMD){
            this.attributes = setAttribute(command);
        }else {
            this.attributes = new String[0];
        }
        this.result = mkTable();
    }

    public boolean mkTable(){
        String path = tablePath+tableName;
        try {
            File table = new File(path);
            if(!table.exists()){
                table.createNewFile();
                return true;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public Terminal setTable(Terminal terminal){
        if (result==true){
            Table table = new Table(tableName,attributes);
            terminal.addDatabaseTable(table);
            terminal.setOutput("CreateTable OK");
            return terminal;
        }
        terminal.setOutput("CreateTable fail!");
        return terminal;
    }

    public String [] setAttribute(String [] command){
        String regEx="[()]";
        String attribute = command[command.length-1].replaceAll(regEx,"");
        return attribute.split(",");
    }

    public void addTableAtr(File table,String art){
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(table));
            out.write("content");
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isResult() {
        return result;
    }
}

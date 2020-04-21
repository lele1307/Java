package commandtype.create;
import commandtype.inout.Writer;
import data.Table;
import process.Terminal;
import java.io.*;

/**
 * @author dukehan
 */
public class CreateTable {
    final static int CMD = 3;
    private String attributesStr;
    private String[] attributes;
    private String tableName;
    private String tablePath;
    private boolean result;
    public CreateTable(String[] command,String tablePath,Create parseC){
        this.tableName = command[2]+".csv";
        this.tablePath = tablePath;
        if (command.length > CMD){
            this.attributes = parseC.getAttributes();
        }else {
            this.attributes = new String[]{"id"};
        }
        this.attributesStr = parseC.getAttributesStr();
        this.result = mkTable();
    }

    public boolean mkTable(){
        String path = tablePath+tableName;
        try {
            File table = new File(path);
            if(!table.exists()){
                table.createNewFile();
                addTableAtr();
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
            terminal.setOutput("OK CreateTable ");
            return terminal;
        }
        terminal.setOutput("ERROR: CreateTable fail!");
        return terminal;
    }

    public void addTableAtr(){
        Writer writer = new Writer(tablePath+tableName);
        writer.writeOneLineInFile(attributesStr);
    }

}

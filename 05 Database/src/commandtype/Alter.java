package commandtype;
import commandtype.inout.Reader;
import commandtype.inout.Writer;
import content.Name;
import process.Terminal;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author dukehan
 */
public class Alter {
    final static int CMDLEN = 5;
    final static int TABLE = 1;
    final static int ALTTYPE = 3;
    private String tableName;
    private String attributeName;
    private String[][] tableContent;
    public Terminal cmdAlter(Terminal terminal, String[] command){
        if(command.length==CMDLEN){
            this.tableName = command[2];
            this.attributeName = command[4];
            if (parseCmd(command)){
                terminal.setOutput("Alter parse OK");
                compileAlter(terminal,command);
                return terminal;
            }
        }
        terminal.setOutput("Alter parse fail!!");
        return terminal;
    }

    public boolean parseCmd(String[] command) {
        if (command[TABLE].toUpperCase().equals("TABLE")){
            String AlterationType = command[ALTTYPE].toUpperCase();
            if ("ADD".equals(AlterationType)|| "DROP".equals(AlterationType)){
                if (Name.parseName(tableName) && Name.parseName(attributeName)){
                    return true;
                }
            }
        }
        return false;
    }

    public Terminal compileAlter(Terminal terminal,String[] command){
        String pathName = terminal.getCurrentPath()+tableName+".csv";
        String AlterationType = command[ALTTYPE].toUpperCase();
        Reader reader = new Reader(pathName);
        if(reader.isExists()){
            reader.readAllTable();
            this.tableContent = reader.getTableContent();
            //System.out.println("tableName: "+tableName+" attributeName: "+attributeName);
            ArrayList<String> list = new ArrayList<String>();
            if ("ADD".equals(AlterationType)){
                alterAdd(terminal,tableName,attributeName,list);
            } else {
                alterDrop(terminal,list,attributeName);
            }
            reader.printTable(tableContent);
            Writer writer = new Writer(pathName);
            writer.emptyFile();
            writer.writeTableInFile(tableContent);
            if(terminal.isFixTableStatus()){
                terminal.setOutput("Alter OK!!");
            }else {
                terminal.setOutput("Alter fail!!");
            }
            terminal.setFixTableStatus(false);
        } else {
            terminal.setOutput("ERROR Unknown table "+"'"+tableName+"'.");
        }
        return terminal;
    }

    public Terminal alterAdd(Terminal terminal,String tableName,String attributeName,ArrayList<String> list){
        if (checkAttribute(tableContent[0],attributeName)==-1){
            //ADD in terminal
            boolean status = terminal.fixCurrTableAttributes(tableName,attributeName,"ADD");
            System.out.println("STATUS: "+status);
            terminal.setFixTableStatus(status);
            //ADD in array list
            tableContent[0]= addEleInLine(list,tableContent[0],attributeName);
            list = new ArrayList<String>();
            for (int i=1; i<tableContent.length;i++){
                tableContent[i]=addEleInLine(list,tableContent[i],"NULL");
                list = new ArrayList<String>();
            }
        }
        return terminal;
    }

    public int checkAttribute(String[] arr, String targetValue) {
        if(Arrays.asList(arr).contains(targetValue)){
            return Arrays.asList(arr).indexOf(targetValue);
        }
        return -1;
    }

    public String[] addEleInLine(ArrayList<String> list,String[] str,String target) {
        for (int i=0; i<str.length; i++) {
            list.add(str[i]);
        }
        list.add(target);
        return list.toArray(new String[0]);
    }

    public String[] dropEleInLine(ArrayList<String> list,String[] str,int targetIndex) {
        for (int i=0; i<str.length; i++) {
            list.add(str[i]);
        }
        list.remove(targetIndex);
        return list.toArray(new String[0]);
    }

    public Terminal alterDrop(Terminal terminal,ArrayList<String> list,String attributeName){
        int targetIndex = checkAttribute(tableContent[0],attributeName);
        if (targetIndex!=-1){
            terminal.setFixTableStatus(terminal.fixCurrTableAttributes(tableName,attributeName,"DROP"));
            for (int i=0; i<tableContent.length;i++){
                tableContent[i]=dropEleInLine(list,tableContent[i],targetIndex);
                list = new ArrayList<String>();
            }
        }
        return terminal;
    }


}

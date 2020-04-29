package commandtype;
import commandtype.inout.Reader;
import content.Name;
import process.Terminal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author dukehan
 */
public class Join extends CommonHandler {
    static final int CMDLEN = 8;
    static final int ON = 4;
    static final int[] AND = {2,6};
    private List <String> tableName = new ArrayList<>();
    private List <String> attributeName = new ArrayList<>();
    private String[][] table1;
    private String[][] table2;

    @Override
    public Terminal runCommand(Terminal terminal, String[] command) {
        if (parseCommand(command)){
            return executeCommand(terminal,command);
        }
        terminal.setOutput("Join parse fail!!");
        return terminal;
    }

    @Override
    public boolean parseCommand(String[] command) {
        if (command.length!=CMDLEN){
            return false;
        }
        String and1 = command[AND[0]];
        String and2 = command[AND[1]];
        String on = command[ON];
        if (!"AND".equals(and1) || !"ON".equals(on) || !"AND".equals(and2)){
            return false;
        }
        if (!parseName(command)){
            return false;
        }
        tableName.add(command[1]);
        tableName.add(command[3]);
        attributeName.add(command[5]);
        attributeName.add(command[7]);
        return true;
    }

    public boolean parseName(String[] command){
        for (int i=1; i<command.length; i+=2){
            if (!Name.parseName(command[i])){
                return false;
            }
        }
        return true;
    }

    @Override
    public Terminal executeCommand(Terminal terminal, String[] command) {
        String pathName1 = setPathName(tableName.get(0),terminal);
        String pathName2 = setPathName(tableName.get(1),terminal);
        if (getTargetTables(pathName1,pathName2)){
            List<Integer> match = isAttributeFind();
            if (match.size()!=0){
                String[] title = buildNewAttributeNameList();
                String[][] newTable = resetTable(match,title);
                String result="";
                for (int i=0; i<newTable.length;i++){
                    for (int j=0;j<newTable[i].length;j++){
                        result = result.concat(newTable[i][j]+"  ");
                    }
                    result = result.concat("\n");
                }
                result = result.replaceAll("'","");
                if (result!=""){
                    terminal.setOutput(result);
                }else {
                    terminal.setOutput("ERROR: JOIN Fail!");
                }
            }
        }
        return terminal;
    }

    public String setPathName(String table,Terminal terminal){
        return terminal.getCurrentPath()+table+".csv";
    }

    public boolean getTargetTables(String pathName1,String pathName2){
        Reader reader1 = new Reader(pathName1);
        Reader reader2 = new Reader(pathName2);
        if (reader1.isExists()&&reader2.isExists()){
            reader1.readAllTable();
            reader2.readAllTable();
            table1 = reader1.getTableContent();
            table2 = reader2.getTableContent();
            if (table1.length!=0&&table2.length!=0){
                return true;
            }
        }
        return false;
    }

    public String[] buildNewAttributeNameList(){
        String root = "id,";
        String firstTable = buildOneAttributeNameList(table1[0],tableName.get(0));
        String secondTable = buildOneAttributeNameList(table2[0],tableName.get(1));
        String connect = root+firstTable+","+secondTable;
        return connect.split(",");
    }

    public String buildOneAttributeNameList(String[] attributes,String tableName){
        String target = tableName+".";
        for (int i=1;i<attributes.length;i++){
            target = target.concat(attributes[i]);
            if (i!=attributes.length-1){
                target = target.concat(","+tableName+".");
            }
        }
        return target;
    }

    public List<Integer> isAttributeFind(){
        List<String> attribute1 = Arrays.asList(table1[0]);
        List<String> attribute2 = Arrays.asList(table2[0]);
        List<Integer> match = new ArrayList<>();
        if (!attributeName.get(0).equals(attributeName.get(1))){
            for (int i=0;i<attributeName.size();i++){
                if (!"id".equals(attributeName.get(i))){
                    int colIndex1 = getColIndex(attribute1,attributeName.get(i));
                    int colIndex2 = getColIndex(attribute2,attributeName.get(i));
                    match.add(i);
                    match.add(colIndex1);
                    match.add(colIndex2);
                }
            }
        }
        return match;
    }

    public int getColIndex(List<String> attribute,String name){
        int col = -1;
        for (int i=0;i<attribute.size();i++){
            if (name.equals(attribute.get(i))){
                col=i;
            }
        }
        return col;
    }

    public List<Integer> getTargetIds(List<Integer> match){
        List<String> rows = new ArrayList<>();
        if (match.get(1)!=-1){
            rows.add("1");
            for (int i=1;i<table1.length;i++){
                rows.add(table1[i][match.get(1)]);
            }
        }else if (match.get(2)!=-1){
            rows.add("2");
            for (int i=1;i<table2.length;i++){
                rows.add(table2[i][match.get(2)]);
            }
        }
        List<Integer> intList = new ArrayList<>();
        for(String str : rows) {
            Integer i = Integer.parseInt(str);
            intList.add(i);
        }
        return intList;
    }

    public List<Integer> getMainRows(String[][] table){
        List<Integer> mainRows = new ArrayList<>();

        for (int i=1;i<table.length;i++){
            mainRows.add(Integer.parseInt(table[i][0]));
        }
        return mainRows;
    }

    public String[][] resetTable (List<Integer> match,String[] title) {
        List<Integer> relationRows = getTargetIds(match);
        int table = relationRows.get(0);
        relationRows = relationRows.subList(1,relationRows.size());
        List<Integer> relationRows2 = getTargetIds(match);
        relationRows2 = relationRows2.subList(1,relationRows2.size());
        List<Integer> relationRows3 = getTargetIds(match);
        relationRows3 = relationRows3.subList(1,relationRows3.size());
        List<Integer> relationRows4 = getTargetIds(match);
        relationRows4 = relationRows4.subList(1,relationRows4.size());
        List<Integer> mainRows = new ArrayList<>();
        if (table==1){
            mainRows = getMainRows(table2);
        }else if (table==2){
            mainRows =getMainRows(table1);
        }
        if (!mainRows.isEmpty()){
            relationRows.retainAll(mainRows);
            relationRows3.removeAll(mainRows);
        }
        relationRows2.removeAll(relationRows3);
        List<Integer> main = relationRows2;
        String[][] connectTable = new String[main.size()+1][title.length];
        connectTable[0] = title;

        for (int i=1;i<connectTable.length;i++){
            connectTable[i][0] = String.valueOf(i);
            int targetRow = relationRows4.indexOf(main.get(i-1))+i;
            if (table==1){
                for (int j=1; j<table1[targetRow].length;j++){
                    connectTable[i][j] = table1[targetRow][j];
                }
                relationRows4.remove(main.get(i-1));
            }else if (table==2){
                for (int j=1; j<table2[targetRow].length;j++){
                    connectTable[i][table1[0].length+j-1] = table2[targetRow][j];
                }
                relationRows4.remove(main.get(i-1));
            }
        }
        for (int i=0;i<main.size();i++){
            int targetRow = mainRows.indexOf(main.get(i))+1;
            if (table==1){
                for (int j=1; j<table2[targetRow].length;j++){
                    connectTable[i+1][table1[0].length+j-1] = table2[targetRow][j];
                }
            }else if (table==2){
                for (int j=1; j<table1[targetRow].length;j++){
                    connectTable[i+1][j] = table1[targetRow][j];
                }
            }
        }

        return connectTable;
    }

}

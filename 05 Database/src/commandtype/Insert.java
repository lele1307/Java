package commandtype;
import commandtype.inout.Reader;
import commandtype.inout.Writer;
import content.Name;
import process.Terminal;
import content.Value;

import java.util.ArrayList;

/**
 * @author dukehan
 */
public class Insert extends CommonHandler {
    static final int CMDLEN = 5;
    static final int VAL = 4;
    static final int VALUES = 3;
    static final int INTO = 1;
    private Value value = new Value();

    @Override
    public Terminal runCommand(Terminal terminal, String[] command) {
        if (parseCommand(command)){
            terminal.setOutput("Insert parse OK");
            executeCommand(terminal,command);
            return terminal;
        }
        terminal.setOutput("Insert parse fail!!");
        return terminal;
    }

    @Override
    public boolean parseCommand(String[] command) {
        if (command.length!=CMDLEN){
            return false;
        }
        String into = command[INTO].toUpperCase();
        String values = command[VALUES].toUpperCase();
        String tableName = command[2];
        if (!Name.parseName(tableName) || !"INTO".equals(into) || !"VALUES".equals(values)){
            return false;
        }
        value.setOrgValueList(command,VAL);
        value.parseValueList();
        if (value.getValueList()==null){
            return false;
        }
        return true;

    }

    @Override
    public Terminal executeCommand(Terminal terminal,String[] command){
        String tableName = command[2];
        String pathName = terminal.getCurrentPath()+tableName+".csv";
        String[] valueList = value.getValueList();
        int valNum = valueList.length;
        Reader reader = new Reader(pathName);
        if (reader.isExists()){
            String[] attributesList = reader.readFirstLine();
            int currentRowIndex = getCurrentRowIndex(reader);
            if (valNum+1!=attributesList.length){
                terminal.setOutput("ERROR : your input values are not match attributeLists!!");
            } else {
                //Insert
                Writer writer = new Writer(pathName);
                String newInsertLine = insertNewLine(currentRowIndex,valueList,writer);
                writer.writeOneLineInFile(newInsertLine);
                terminal.setOutput("OK");
            }
        } else {
            terminal.setOutput("ERROR Unknown table "+"'"+tableName+"'.");
        }
        return terminal;
    }

    public int getCurrentRowIndex(Reader reader){
        reader.readAllTable();
        String[][] table = reader.getTableContent();
        int rows = table.length;
        int currIndex = 0;
        if ("id".equals(table[rows-1][0])){
            currIndex = 1;
        } else {
            currIndex = Integer.parseInt(table[rows-1][0])+1;
        }
        return currIndex;
    }

    public String insertNewLine(int currentRawIndex,String[] valueList,Writer writer){
        ArrayList<String> newInset = new ArrayList<String>();
        newInset.add(Integer.toString(currentRawIndex));
        for (String target:valueList){
            newInset.add(target);
        }
        String[] newStr = newInset.toArray(new String[0]);
        String newIn =  writer.buildStrLine(newStr);
        //newIn = newIn.replaceAll("'","");
        return newIn;
    }


}

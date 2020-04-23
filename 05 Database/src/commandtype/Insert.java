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
public class Insert extends Value {
    final static int CMDLEN = 5;
    final static int VAL = 4;
    final static int VALUES = 3;
    final static int INTO = 1;
    public Insert() {
        super();
    }

    public Terminal cmdInsert(Terminal terminal, String[] command){
        if (parseCmd(command)){
            terminal.setOutput("Insert parse OK");
            compileInsert(terminal,command);
            return terminal;
        }
        terminal.setOutput("Insert parse fail!!");
        return terminal;
    }

    public boolean parseCmd(String[] command){
        if (command.length==CMDLEN){
            String into = command[INTO].toUpperCase();
            String values = command[VALUES].toUpperCase();
            String tableName = command[2];
            if (Name.parseName(tableName) &&"INTO".equals(into)&& "VALUES".equals(values)){
                setOrgValueList(command,VAL);
                parseValueList();
                if (getValueList()!=null){
                    return true;
                }
            }
        }
        return false;
    }

    public Terminal compileInsert(Terminal terminal,String[] command){
        String tableName = command[2];
        String pathName = terminal.getCurrentPath()+tableName+".csv";
        String[] valueList = getValueList();
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
                System.out.println(newInsertLine);
                writer.writeOneLineInFile(newInsertLine);
                terminal.setOutput("OK Insert");
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
        int currIndex = -1;
        if ("id".equals(table[rows-1][0])){
            currIndex = 0;
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

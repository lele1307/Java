package commandtype;
import commandtype.inout.Reader;
import content.Condition;
import content.ConditionExe;
import content.Name;
import process.Terminal;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dukehan
 */
public class Select {
    final static int CMDLEN = 4;
    private int where = -1;
    private int from = -1;
    private String tableName;
    private String AttribList;
    private String[] AttribLists;
    private String condition;
    public Terminal cmdSelect(Terminal terminal, String[] command){
        if (parseCmd(command)){
            terminal.setOutput("OK Select parse");
            compileSelect(terminal,command);
            return terminal;
        }
        terminal.setOutput("ERROR: Select parse");
        return terminal;
    }

    public boolean parseCmd(String[] command){
        int flag=0;
        if (command.length>=CMDLEN){
            from = getSpecialStr(command,"FROM");
            where = getSpecialStr(command,"WHERE");
            tableName = command[from+1];
            if (from!=-1&&Name.parseName(tableName)){
                int len = from+2;
                setAttribList(command,from);
                printAttribList();
                if ((AttribLists[0].equals("*")&&AttribLists.length==1)){
                    flag++;
                }else if (Name.parseNameList(AttribLists)){
                    flag++;
                }else {
                    flag=-1;
                }
                if (flag>0){
                    if (len==command.length){
                        return true;
                    }else if (len<command.length){
                        return checkCondition(where,command);
                    }
                }
            }
        }
        return false;
    }

    public boolean checkCondition(int where, String[] command){
        if (where!=-1){
            Condition con = new Condition(command);
            if (con.parseConditionStr(con.getConditionStr())){
                condition = con.getConditionStr();
                return true;
            }
        }
        return false;
    }

    public void setAttribList(String[] command,int from) {
        AttribList = "";
        for (int i=1; i<from; i++){
            AttribList = AttribList.concat(command[i]);
        }
        AttribLists = AttribList.split(",");
    }

    public void printAttribList(){
        for (int i = 0; i<AttribLists.length; i++){
            System.out.println(AttribLists[i]);
        }
    }

    public int getSpecialStr(String[] command,String str){
        for (int i=0; i<command.length; i++){
            if (command[i].toUpperCase().equals(str)){
                return i;
            }
        }
        return -1;
    }

    public Terminal compileSelect(Terminal terminal,String[] command){
        String pathName = terminal.getCurrentPath()+tableName+".csv";
        Reader reader = new Reader(pathName);
        if (reader.isExists()){
            String get = feedbackSelect(reader,AttribLists,condition,command);
            if (get!=""){
                terminal.setOutput(get);
            }else{
                terminal.setOutput("ERROR: Invalid query.");
            }
        } else {
            terminal.setOutput("ERROR: Unknown table "+"'"+tableName+"'.");
        }
        return terminal;
    }

    public String feedbackSelect(Reader reader,String[]attribLists,String condition,String[] command) {
        String result = "";
        if (condition!=null){
            ConditionExe conditionExe = new ConditionExe(command,reader);
            List<Integer> rows = conditionExe.exeCondition(conditionExe.getConditionStr());
            System.out.println(rows);
            result = getConditionSelectResult(attribLists,rows,reader);
        } else {
            result = reader.readColumn(attribLists);
        }
        return result;
    }

    public String getConditionSelectResult(String[]attribLists,List<Integer> rows,Reader reader){
        ArrayList<Integer> attributesIndex = reader.getAttributesIndex(attribLists);
        String result = "";
        if(attributesIndex.size()!=0 && rows.size()!=0){
            rows.add(0,0);
            System.out.println("cols: "+attributesIndex);
            System.out.println("rows: "+rows);
            reader.readAllTable();
            String[][] tableContent = reader.getTableContent();
            for (int i=0;i<rows.size();i++){
                for (int j=0;j<attributesIndex.size();j++){
                    result = result.concat(tableContent[rows.get(i)][attributesIndex.get(j)]+" ");
                }
                result = result.concat("\n");
            }
        }
        return result.replaceAll("'","");
    }


}

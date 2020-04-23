package commandtype;
import commandtype.inout.Reader;
import content.Condition;
import content.ConditionExe;
import content.Name;
import process.Terminal;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author dukehan
 */
public class Update extends Condition {
    final static int CMDLEN = 6;
    final static int SET = 2;
    final static int TABLENAME = 1;
    private String tableName;
    private String setTarget;
    private String[] setFinalTarget;

    public Update(String[] cmd) {
        super(cmd);
    }

    public Terminal cmdUpdate(Terminal terminal, String[] command){
        if (parseCmd(command)){
            System.out.println(tableName +" finalSet: "+setTarget);
            terminal.setOutput("Update parse OK");
            return terminal;
        }
        terminal.setOutput("Update parse fail!!");
        return terminal;
    }

    public boolean parseCmd(String[] command){
        int where = -1;
        if (command[SET].equals("SET") && command.length>=CMDLEN && Name.parseName(command[TABLENAME])){
            tableName = command[TABLENAME];
            where = getWhere(command);
            if (where != -1){
                setSetTarget(command,where);
                System.out.println("parseSet"+setTarget);
                if(parseAllNameValList()){
                    if(parseConditionStr(getConditionStr())){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean parseAllNameValList(){
        for (int i=0; i<setFinalTarget.length;i++){
            if (!parseNameValList(setFinalTarget[i])){
                return false;
            }
        }
        return true;
    }

    public boolean parseNameValList(String target){
        if (target.contains("=")){
            String name = target.substring(0,target.indexOf("="));
            String val = target.substring(target.indexOf("=")+1);
            if(!name.equals("")&&!val.equals("")){
                if (Name.parseName(name) && parseVal(val)){
                    return true;
                }
            }
        }
        return false;
    }

    public int getWhere(String[] command){
        for (int i=0; i<command.length; i++){
            if (command[i].toUpperCase().equals("WHERE")){
                return i;
            }
        }
        return -1;
    }

    public void setSetTarget(String[] command,int where) {
        this.setTarget = "";
        for (int i=SET+1; i<where; i++){
            this.setTarget = this.setTarget.concat(command[i]);
        }
        this.setFinalTarget = this.setTarget.split(",");
    }

    public Terminal compileUpdate(Terminal terminal,String[] command){
        String pathName = terminal.getCurrentPath()+tableName+".csv";
        Reader reader = new Reader(pathName);
        if (reader.isExists()){
            ConditionExe conditionExe = new ConditionExe(command,reader);
            conditionExe.exeCondition(conditionExe.getConditionStr());
            List<Integer> rows = conditionExe.getTargetRows();
            System.out.println(rows);
            if (rows.size()!=0){
                String[][] updateNewTable = updateTable(reader,rows);

            }else{
                terminal.setOutput("ERROR: Invalid query.");
            }
        }else {
            terminal.setOutput("ERROR: Unknown table "+"'"+tableName+"'.");
        }
        return terminal;
    }

    public String[][] updateTable(Reader reader,List<Integer> rows){
        reader.readAllTable();
        String[][] table = reader.getTableContent();
        List<String> attributes = Arrays.asList(table[0]);
        List<String> dataType = Arrays.asList(table[1]);






        return table;
    }
}

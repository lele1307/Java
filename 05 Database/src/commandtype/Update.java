package commandtype;
import commandtype.inout.Reader;
import commandtype.inout.Writer;
import content.Condition;
import content.ConditionExe;
import content.Name;
import process.Terminal;
import java.util.Arrays;
import java.util.List;

/**
 * @author dukehan
 */
public class Update extends CommonHandler {
    final static int CMDLEN = 6;
    final static int SET = 2;
    final static int TABLE_NAME = 1;
    private String tableName;
    private String setTarget;
    private String[] setFinalTarget;

    @Override
    public Terminal runCommand(Terminal terminal, String[] command) {
        if (parseCommand(command)){
            terminal.setOutput("Update parse OK");
            executeCommand(terminal,command);
            return terminal;
        }
        terminal.setOutput("Update parse fail!!");
        return terminal;
    }

    @Override
    public boolean parseCommand(String[] command) {
        int where = -1;
        if (!"SET".equals(command[SET]) || command.length<CMDLEN || !Name.parseName(command[TABLE_NAME])){
            return false;
        }
        tableName = command[TABLE_NAME];
        where = getWhere(command);
        if (where == -1){
            return false;
        }
        setSetTarget(command,where);
        Condition condition = new Condition(command);
        if(!parseAllNameValList(condition)){
            return false;
        }
        if(!condition.parseConditionStr(condition.getConditionStr())){
            return false;
        }
        return true;
    }

    @Override
    public Terminal executeCommand(Terminal terminal, String[] command) {
        String pathName = terminal.getCurrentPath()+tableName+".csv";
        Reader reader = new Reader(pathName);
        if (reader.isExists()){
            ConditionExe conditionExe = new ConditionExe(command,reader);
            List<Integer> rows = conditionExe.exeCondition(conditionExe.getConditionStr());
            if (rows.size()!=0){
                String[][] updateNewTable = updateTable(reader,rows,conditionExe);
                Writer writer = new Writer(pathName);
                writer.emptyFile();
                if(writer.writeTableInFile(updateNewTable)){
                    terminal.setOutput("OK");
                }else {
                    terminal.setOutput("ERROR: UPDATE write IN table!");
                }
            }else{
                terminal.setOutput("ERROR: Invalid query.");
            }
        }else {
            terminal.setOutput("ERROR: Unknown table "+"'"+tableName+"'.");
        }
        return terminal;
    }

    public boolean parseAllNameValList(Condition condition){
        for (int i=0; i<setFinalTarget.length;i++){
            if (!parseNameValList(setFinalTarget[i],condition)){
                return false;
            }
        }
        return true;
    }

    public boolean parseNameValList(String target,Condition condition){
        if (target.contains("=")){
            String name = target.substring(0,target.indexOf("="));
            String val = target.substring(target.indexOf("=")+1);
            if(!name.equals("")&&!val.equals("")){
                if (Name.parseName(name) && condition.parseSingleValue(val)){
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

    public String[][] updateTable(Reader reader,List<Integer> rows,ConditionExe conditionExe){
        reader.readAllTable();
        String[][] table = reader.getTableContent();
        List<String> attributes = Arrays.asList(table[0]);
        List<String> dataType = Arrays.asList(table[rows.get(0)]);
        if (setFinalTarget.length!=0){
            for (int i=0;i<setFinalTarget.length;i++){
                String[] set = setFinalTarget[i].split("=");
                String attribute = set[0];
                String val = set[1];
                int col = attributes.indexOf(attribute);
                if (col!=-1){
                    int originalDataType = conditionExe.getValueType(dataType.get(col));
                    int inputDataType = conditionExe.getValueType(val);
                    if (originalDataType==inputDataType || "NULL".equals(dataType.get(col))){
                        table = updateVal(table,col,rows,val);
                    }
                }
            }
        }
        return table;
    }

    public String[][] updateVal(String[][] table,int col,List<Integer> rows,String val){
        for (int i=0;i<rows.size();i++){
            table[rows.get(i)][col] = val;
        }
        return table;
    }
}

package commandtype;
import commandtype.inout.Reader;
import commandtype.inout.Writer;
import content.Condition;
import content.ConditionExe;
import content.Name;
import process.Terminal;

import java.util.List;

/**
 * @author dukehan
 */
public class Delete {
    final static int CMDLEN = 5;
    final static int FROM = 1;
    final static int WHERE = 3;
    final static int TABLENAME = 2;
    private String tableName;
    public Terminal cmdDelete(Terminal terminal, String[] command){
        if (parseCmd(command)){
            terminal.setOutput("Delete parse OK");
            compileDelete(terminal,command);
            return terminal;
        }
        terminal.setOutput("Delete parse fail!!");
        return terminal;
    }

    public boolean parseCmd(String[] command){
        if (command.length==CMDLEN && Name.parseName(command[TABLENAME])){
            tableName = command[TABLENAME];
            String where = command[WHERE];
            String from = command[FROM];
            if ("WHERE".equals(where) && "FROM".equals(from)){
                Condition condition = new Condition(command);
                if(condition.parseConditionStr(condition.getConditionStr())){
                    return true;
                }
            }
        }
        return false;
    }

    public Terminal compileDelete(Terminal terminal,String[] command){
        String pathName = terminal.getCurrentPath()+tableName+".csv";
        Reader reader = new Reader(pathName);
        if (reader.isExists()){
            ConditionExe conditionExe = new ConditionExe(command,reader);
            List<Integer> rows = conditionExe.exeCondition(conditionExe.getConditionStr());
            if (rows.size()!=0){
                String[][] newTable = delEleTable(reader,rows);
                reader.printTable(newTable);
                Writer writer = new Writer(pathName);
                writer.emptyFile();
                if(writer.writeTableInFile(newTable)){
                    terminal.setOutput("OK");
                }else {
                    terminal.setOutput("ERROR: del update table!");
                }
            }else{
                terminal.setOutput("ERROR: Invalid query.");
            }
        }else {
            terminal.setOutput("ERROR: Unknown table "+"'"+tableName+"'.");
        }
        return terminal;
    }

    public String[][] delEleTable(Reader reader,List<Integer> rows){
        reader.readAllTable();
        String[][] table = reader.getTableContent();
        int colLen = table[0].length;
        for (int i=0;i<rows.size();i++){
            for (int j=0;j<colLen;j++){
                table[rows.get(i)][j] = "";
            }
        }
        return table;
    }

}

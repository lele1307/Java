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
public class Delete extends CommonHandler {
    static final int CMDLEN = 5;
    static final int FROM = 1;
    static final int WHERE = 3;
    static final int TABLE_NAME = 2;

    private String tableName;

    @Override
    public Terminal runCommand(Terminal terminal, String[] command) {
        if (parseCommand(command)){
            terminal.setOutput("Delete parse OK");
            executeCommand(terminal,command);
            return terminal;
        }
        terminal.setOutput("Delete parse fail!!");
        return terminal;
    }

    @Override
    public boolean parseCommand(String[] command) {
        if (command.length!=CMDLEN || !Name.parseName(command[TABLE_NAME])){
            return false;
        }
        tableName = command[TABLE_NAME];
        String where = command[WHERE];
        String from = command[FROM];
        if (!"WHERE".equals(where) || !"FROM".equals(from)){
            return false;
        }
        Condition condition = new Condition(command);
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
                String[][] newTable = delEleTable(reader,rows);
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

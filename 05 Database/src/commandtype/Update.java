package commandtype;
import content.Condition;
import content.Name;
import process.Terminal;
/**
 * @author dukehan
 */
public class Update extends Condition {
    final static int CMDLEN = 8;
    final static int SET = 2;
    final static int TABLENAME = 3;
    private String[] condition;
    private String setTarget;
    private String[] setFinalTarget;

    public Update(String[] cmd) {
        super(cmd);
    }

    public Terminal cmdUpdate(Terminal terminal, String[] command){
        if (parseCmd(command)){
            terminal.setOutput("Update parse OK");
            return terminal;
        }
        terminal.setOutput("Update parse fail!!");
        return terminal;
    }

    public boolean parseCmd(String[] command){
        int where = -1;
        if (command[SET].equals("SET") && command.length==CMDLEN && Name.parseName(command[TABLENAME])){
            where = getWhere(command);
            if (where != -1){
                setSetTarget(command,where);
                System.out.println(setTarget);
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
            System.out.println(setFinalTarget[i]);
            if (!parseNameValList(setFinalTarget[i])){
                return false;
            }
        }
        return true;
    }

    public boolean parseNameValList(String target){
        if (target.contains("=")){
            String name = target.substring(0,target.indexOf("="));
            System.out.println("！！！！|"+name);
            String val = target.substring(target.indexOf("=")+1);
            if (Name.parseName(name) && parseVal(val)){
                return true;
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

}

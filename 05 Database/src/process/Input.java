package process;
import content.Name;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author dukehan
 */
public class Input {

    static final String SEMI_ERROR = "semicolonError";

    private String[] input;

    public Input(String cmd){
        if (cmd!=null){
            cmd = cmd.trim();
            if (cmd.length()<=0){
                this.input = new String[0];
            } else {
                cmd = isSemicolon(cmd);
                if (cmd.equals(SEMI_ERROR)){
                    this.input = new String[] {"error"};
                }else {
                    int rightBracket = Name.appearNumber(cmd,")");
                    int leftBracket =Name.appearNumber(cmd,"(");;
                    String[] split;
                    if (rightBracket==1&&leftBracket==1){
                        split = newCMD(cmd);
                    } else {
                        if (cmd.contains("where")||cmd.contains("WHERE")){
                            split = newWhereCMD(cmd);
                        }else {
                            split = splitInput(cmd);
                        }
                    }
                    this.input = split;
                }
            }
        }
    }

    public String[] newWhereCMD(String cmd){
        int whereStart = getWhereIndex(cmd);
        int conditionStart = whereStart+6;
        String beforeCondition = cmd.substring(0,whereStart+5);
        String condition = cmd.substring(conditionStart);
        String[] before = beforeCondition.split(" ");
        ArrayList<String> split = new ArrayList<String>();
        for (String s:before){
            split.add(s);
        }
        split.add(condition);
        String[] result = split.toArray(new String[0]);
        return result;
    }

    public int getWhereIndex(String cmd){
        int where = -1;
        if (cmd.contains("where")){
            where = cmd.indexOf("where");
        }else if (cmd.contains("WHERE")){
            where = cmd.indexOf("WHERE");
        }
        return where;
    }

    public String[] newCMD(String cmd){
        int start = cmd.indexOf('(');
        int end = cmd.indexOf(')');
        String beforeBracket = cmd.substring(0,start);
        String bracketContent = cmd.substring(start,end+1);
        List<String> splitCmd = new ArrayList<>();
        String[] beforeContent = beforeBracket.split(" ");
        splitCmd.addAll(Arrays.asList(beforeContent));
        splitCmd.add(bracketContent);
        String[] result = splitCmd.toArray(new String[0]);
        return result;
    }

    public String[] getInput() {
        return input;
    }

    public String isSemicolon(String cmd){
        char semicolon = cmd.charAt(cmd.length()-1);
        if (semicolon==';'){
            return cmd.substring(0,cmd.length()-1);
        }
        return SEMI_ERROR;
    }

    public String[] splitInput(String cmd){
        return cmd.split(" ");
    }

}

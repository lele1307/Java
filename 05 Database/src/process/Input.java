package process;

import content.Name;

import java.util.ArrayList;

/**
 * @author dukehan
 */
public class Input {
    final static String semiError = "semicolonError";
    private String[] input;
    public Input(String cmd){
        cmd = cmd.trim();
        if (cmd.length()<=0){
            this.input = new String[0];
        } else {
            cmd = isSemicolon(cmd);
            if (cmd.equals(semiError)){
                this.input = new String[] {"error"};
            }else {
                int rightBracket = Name.appearNumber(cmd,")");
                int leftBracket =Name.appearNumber(cmd,"(");;
                String[] split;
                if (rightBracket==1&&leftBracket==1){
                    split = splitInput(newCMD(cmd));
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

    public String newCMD(String cmd){
        int start = cmd.indexOf('(');
        int end = cmd.lastIndexOf(')');
        int start1 = -1;
        int end1 = -1;
        String orgContent = cmd.substring(0,start);;
        String bracketContent = new String();
        String bracketContent1 = new String();
        String middle = new String();
        if (start != -1 && end != -1) {
            start1 = cmd.indexOf('(',start+1);
            end1 = cmd.indexOf(')',end+1);
        }
        if (start1 != -1 && end1 != -1){
            bracketContent1 = cmd.substring(start1,end1+1).replaceAll(" ","");
            middle = cmd.substring(end+1,start1);
        }
        bracketContent = cmd.substring(start,end+1).replaceAll(" ","");
        String newCmd = orgContent+bracketContent+middle+bracketContent1;
        return newCmd;
    }

    public String[] getInput() {
        return input;
    }

    public String isSemicolon(String cmd){
        char semicolon = cmd.charAt(cmd.length()-1);
        if (semicolon==';'){
            return cmd.substring(0,cmd.length()-1);
        }
        return semiError;
    }

    public String[] splitInput(String cmd){
        return cmd.split(" ");
    }



}

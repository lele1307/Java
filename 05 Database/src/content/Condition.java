package content;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author dukehan
 */
public class Condition extends Value {
    private boolean isSetCondition;
    private String conditionStr;

    public Condition(String[] cmd){
        super();
        this.isSetCondition = setConditionStr(cmd);
    }

    public String getConditionStr(){
        return conditionStr;
    }

    public String checkOperators(String opt){
        opt = opt.toUpperCase();
        String pattern = "(==|>=|<=|>|<|!=|LIKE)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(opt);
        if (m.find()){
            /*System.out.println(m.start());
            System.out.println(m.end());
            System.out.println(m.group());*/
            return m.group();
            //return first opt str
        } else {
            return "";
        }
    }

    public String checkConditionType(String target){
        String pattern = "(AND|OR)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(target);
        if (m.find()){
            return m.group();
        }
        return "";
    }

    public boolean setConditionStr(String[] cmd) {
        int where = -1;
        if (cmd!=null){
            for (int i=0; i<cmd.length; i++){
                if(cmd[i].toUpperCase().equals("WHERE")){
                    where = i;
                }
            }
            if (where != -1){
                String conStr = cmd[where+1];
                this.conditionStr = conStr;
                return true;
            }
        }
        return false;
    }

    public boolean parseConditionStr(String str){
        ArrayList<String> pairCondition = new ArrayList<>();
        String ConditionType = checkConditionType(str);
        boolean result = false;
        if (ConditionType!=""){
            pairCondition = parseConnectCondition(str);
            for (int i=0; i<pairCondition.size();i++){
                result = parseConditionStr(pairCondition.get(i));
                if (result==false){
                    break;
                }
            }
        } else {
             result = parseNormalCondition(str);
        }
        return result;
    }

    public boolean parseNormalCondition(String str){
        String regex = checkOperators(str);
        String AttributeName;
        String Value;
        if (regex!=""&&str!=""){
            str = str.replaceAll(" ","");
            //System.out.println(str);
            String[] split = str.split(regex);
            if (split.length==2){
                AttributeName = split[0];
                Value = split[1];
                if (Name.parseName(AttributeName) && parseVal(Value)){
                    return true;
                }
            }
        }
        return false;
    }

    public ArrayList<String> parseConnectCondition(String str){
        ArrayList<String> pairCondition = new ArrayList<>();
        String pattern = "(AND|OR)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);
        while (m.find()){
            int start = m.start();
            int end = m.end();
            System.out.println(m.start());
            System.out.println(m.end());
            System.out.println(m.group());
            System.out.println("-------------------");
            String left = getNormalCondition(str,0,start);
            String right = getNormalCondition(str,end,str.length());
            System.out.println("left: |"+left+"|");
            System.out.println("right: |"+right+"|");
            if (left!=""&&right!=""){
                left = trimBracket(left);
                right = trimBracket(right);
                pairCondition.add(left);
                pairCondition.add(right);
            }
        }
        return pairCondition;
    }

    public String trimBracket(String str){
        return str.substring(1,str.length()-1);
    }

    public String getNormalCondition(String str,int start,int end){
        String raw = str.substring(start,end);
        String result = "";
        int left = Name.appearNumber(raw,"(");
        int right = Name.appearNumber(raw,")");
        if (left==right){
            result = raw.substring(raw.indexOf("("),raw.lastIndexOf(")")+1);
            //System.out.println(result);
        }
        return result;
    }

    public boolean parseVal(String target){
        if (!parseString(target)){
            if (!parseBoolean(target)){
                if (!parseInteger(target)){
                    if (!parseFloat(target)){
                        return false;
                    }
                }
            }
        }
        return true;
    }



}

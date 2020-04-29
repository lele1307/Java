package content;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * @author dukehan
 */
public class Condition extends Value {
    private String conditionStr;

    public Condition(String[] cmd){
        super();
        setConditionStr(cmd);
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
        int count = 0;
        String conType = "";
        while (m.find()){
            conType = m.group();
            count++;
        }
        if (count>1){
            Matcher m1 = r.matcher(target);
            while (m1.find()){
                int start = m1.start();
                int end = m1.end();
                String left = getNormalCondition(target,0,start);
                String right = getNormalCondition(target,end,target.length());
                if (left!=""&&right!=""){
                    conType=m1.group();
                }
            }
        }
        return conType;
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
            if("LIKE".equals(regex)&&str.contains("like")){
                str = str.replaceAll(" like ","like");
            }else {
                str = str.replaceAll(" "+regex+" ",regex);
            }
            String[] split;
            if (regex.equals("LIKE")&&str.contains("like")){
                split = str.split("like");
            } else {
                split = str.split(regex);
            }
            if (split.length==2){
                AttributeName = split[0];
                Value = split[1];
                if (Name.parseName(AttributeName) && parseSingleValue(Value)){
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
            String left = getNormalCondition(str,0,start);
            String right = getNormalCondition(str,end,str.length());
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
        return str.substring(1,str.length()-1).trim();
    }

    public String getNormalCondition(String str,int start,int end){
        String raw = str.substring(start,end);
        String result = "";
        int left = Name.appearNumber(raw,"(");
        int right = Name.appearNumber(raw,")");
        if (left==right){
            result = raw.substring(raw.indexOf("("),raw.lastIndexOf(")")+1);
        }
        return result;
    }

    public boolean parseSingleValue(String target){
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

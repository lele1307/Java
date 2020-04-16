package content;
/**
 * @author dukehan
 */
public class Condition extends Value {

    public boolean setResult;
    private String[] condition;
    private String[] operators;
    public Condition(String[] cmd){
        super();
        this.setResult = setCondition(cmd);
        this.operators = new String[]{">","<","==",">=","<=","!=","LIKE"};
    }
    public boolean setCondition(String[] cmd) {
        int where = -1;
        for (int i=0; i<cmd.length; i++){
            if(cmd[i].toUpperCase().equals("WHERE")){
                where = i;
            }
        }
        System.out.println("int=i: "+where);
        if (where != -1){
            int j=0;
            String[] newCmd = new String[cmd.length-where-1];
            for (int i = where+1; i<cmd.length; i++){
                newCmd[j] = cmd[i];
                j++;
            }
            this.condition = newCmd;
            return true;
        }
        return false;
    }

    public String resetNormalCondition(String target){
        String newTarget = "";
        int flag = -1;
        for (int i=0; i<operators.length;i++){
            if (target.contains(operators[i])){
                flag = i;
            }
        }
        String replacement = " "+operators[flag]+" ";
        if (flag == -1){
            return newTarget;
        }
        newTarget = target.replaceAll(operators[flag],replacement);
        int valStart = newTarget.lastIndexOf(" ")+1;
        int valEnd = newTarget.length()-1;
        String value = newTarget.substring(valStart,valEnd);
        /**System.out.println("flag: "+flag+" newTarget: "+newTarget+"Val:|"+value+"|");*/
        parseVal(value);
        return newTarget;
    }

    public boolean parseCondition(){
        if (isBrackets()){
            String connect = condition[1].toUpperCase();
            if (connect.equals("AND") || connect.equals("OR")){
                String con1 = resetNormalCondition(condition[0]);
                String con2 = resetNormalCondition(condition[2]);
                if (con1!="" && con2!=""){
                    condition[0] = con1;
                    condition[2] = con2;
                    return true;
                }
            }
        } else {
            if(parseNormalCondition(condition)){
                return true;
            }
        }
        return false;
    }

    /**is contains 2 pair of brackets?*/
    public boolean isBrackets(){
        int flag=0;
        for (int i = 0; i<condition.length; i++){
            if(condition[i].contains("(")&&condition[i].contains(")")){
                flag++;
            }
        }
        if (flag!=2){
            return false;
        }
        return true;
    }

    public boolean parseNormalCondition(String[] target){
        if (cmpOperator(target[1])){
            if(parseVal(target[2])){
                return true;
            }
        }
        return false;
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

    public boolean cmpOperator(String opt){
        for (int i=0; i<operators.length;i++){
            if (operators[i].equals(opt)){
                return true;
            }
        }
        return true;
    }

    public void printCondition(){
        System.out.println("------------printCondition-----------");
        for (int i = 0; i<condition.length; i++){
            System.out.println(condition[i]);
        }
        System.out.println("------------printCondition END--------");
    }

}

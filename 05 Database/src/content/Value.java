package content;

public class Value {
    private String[] valueList;
    private String orgValueList;
    public Value(){
    }
    public void setOrgValueList(String[] cmd,int index){
        this.orgValueList = cmd[index];
    }

    public boolean parseValueList(){
        int brackets = 0;
        for (int j=0;j<orgValueList.length();j++){
            if (orgValueList.charAt(j)=='('||orgValueList.charAt(j)==')'){
                brackets++;
            }
        }
        if (brackets==2 && parseValue()){
            this.valueList = splitValList();
            return true;
        }
        this.valueList = null;
        return false;
    }

    public boolean parseValue(){
        String[] valList = splitValList();
        for (int i=0;i<valList.length;i++){
            if (!parseString(valList[i])){
                if (!parseBoolean(valList[i])){
                    if (!parseInteger(valList[i])){
                        if (!parseFloat(valList[i])){
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean parseString(String target){
        if (target.charAt(0)=='\''&&target.charAt(target.length()-1)=='\''){
            return true;
        }
        return false;
    }

    public boolean parseBoolean(String target){
        if (target.equals("true")||target.equals("false")){
            return true;
        }
        return false;
    }

    public boolean parseInteger(String target){
        if (target.charAt(0)!='-' && !Character.isDigit(target.charAt(0))){
            return false;
        } else {
            for (int i = 1; i < target.length(); i++){
                if (!Character.isDigit(target.charAt(i))){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean parseFloat(String target){
        char first = target.charAt(0);
        char last = target.charAt(target.length()-1);
        if (!target.contains(".")){
            return false;
        } else {
            if (first!='-' && !Character.isDigit(first)){
                return false;
            } else if (last=='.'){
                return false;
            } else {
                for (int i = 1; i < target.length()-1; i++){
                    if (!Character.isDigit(target.charAt(i)) && target.charAt(i)!='.'){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public String[] splitValList(){
        String regEx="[()]";
        String ValList = orgValueList.replaceAll(regEx,"");
        return ValList.split(",");
    }

    public String[] getValueList(){
        return valueList;
    }
}


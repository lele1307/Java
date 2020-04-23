package content;
import commandtype.inout.Reader;
import java.util.*;

/**
 * @author dukehan
 */
public class ConditionExe extends Condition {
    private List<Integer> targetRows;
    private List<String> tableAttribute;
    private String conditionStr;
    private Value value;
    private Reader reader;
    private int optValType;
    public ConditionExe(String[] command, Reader reader){
        super(command);
        this.reader = reader;
        this.value = new Value();
        this.conditionStr = getConditionStr();
        this.tableAttribute = setTableAttribute(reader.readFirstLine());
        this.optValType = -1;
        this.targetRows = new ArrayList<>();
    }

    public List<String> setTableAttribute(String[] target){
        return Arrays.asList(target);
    }

    public boolean exeCondition(String targetConditionStr){
        List<String> pairCondition = new ArrayList<>();
        String ConditionType = checkConditionType(targetConditionStr);
        boolean result = false;
        if (ConditionType!=""){
            pairCondition = parseConnectCondition(targetConditionStr);
            for (int i=0; i<pairCondition.size();i++){
                result = exeCondition(pairCondition.get(i));
            }
            if (ConditionType.equals("AND")){
                targetRows = getRepeat(targetRows);
            }else if (ConditionType.equals("OR")){
                targetRows = delRepeat(targetRows);
            }
        } else {
            if (ConditionType == ""){
                result = exeNormalCondition(targetConditionStr);
            }
        }
        return result;
    }

    public boolean exeNormalCondition(String targetConditionStr){
        System.out.println(tableAttribute);
        System.out.println("exeNormalCondition():|"+targetConditionStr+"|");
        int valType = 0;
        String attributeFirstElement = "";
        String opt = checkOperators(targetConditionStr);
        if (opt!=""&&targetConditionStr!=""){
            String attributeName;
            String val;
            targetConditionStr = targetConditionStr.replaceAll(" ","");
            //System.out.println(str);
            String[] split;
            if (opt.equals("LIKE")&&targetConditionStr.contains("like")){
                split = targetConditionStr.split("like");
            }else {
                split = targetConditionStr.split(opt);
            }
            if (split.length==2){
                attributeName = split[0];
                val = split[1];
                System.out.println("opt:|"+checkOperators(targetConditionStr)+"|"
                        +"AttributeName :|"+attributeName+"| Value :|"+val+"|");
                valType = getValueType(val);
                attributeFirstElement = getAttributeType(attributeName,reader);
                if (isConditionExe(attributeFirstElement,valType,opt)){
                    targetRows.addAll(getTargetRow(attributeName,opt,val,valType));
                    System.out.println(targetRows);
                    return true;
                }
            }
        }
        return false;
    }

    public String getAttributeType(String attributeName,Reader reader){
        //whether can find in tableAttributeList
        String attributeFirstElement = "";
        if (tableAttribute.contains(attributeName)){
            int col = tableAttribute.indexOf(attributeName)+1;
            //col from index 0 ,must add 1 to use !!!
            attributeFirstElement = reader.getElement(2,col);
            System.out.println("col!!!!!: "+col+"   "+attributeFirstElement);
        }
        return attributeFirstElement;
    }

    public int getValueType(String val){
        if (value.parseString(val)){
            return 1;
        }
        if (value.parseBoolean(val)){
            return 2;
        }
        if (value.parseInteger(val)){
            return 3;
        }
        if (value.parseFloat(val)){
            return 4;
        }
        return 0;
    }

    public boolean isConditionExe(String attributeFirstElement, int valType,String opt){
        if (!"".equals(attributeFirstElement)){
            int attributeVal = getValueType(attributeFirstElement);
            if (attributeVal==valType){
                optValType = isOptCondition(opt,valType);
                return optValType != -1;
            }
        }
        return false;
    }

    public int isOptCondition(String opt,int valType){
        if ("==".equals(opt)|| "!=".equals(opt)){
            return 1;
        } else if (">".equals(opt)|| "<".equals(opt)|| ">=".equals(opt)|| "<=".equals(opt)){
            if (valType==3||valType==4){
                return 2;
            }
        } else if ("LIKE".equals(opt)){
            if (valType==1){
                return 3;
            }
        }
        return -1;
    }

    public List<Integer> getTargetRow(String attributeName,String opt,String val,int valType){
        List<String> colVal = getTargetColVal(attributeName,reader);
        List<Integer> rows = new ArrayList<>();
        if (colVal.size()!=0){
            if (optValType==1){
                //opt-> ==/!=
                rows = isEqualExe(colVal,val,opt);
            }else if (optValType==2){
                //opt-> >=/<=/>/<
                rows = isNumExe(colVal,val,opt,valType);
            }else if (optValType==3){
                //opt-> LIKE
                rows = isLikeExe(colVal,val,opt);
            }
        }
        return rows;
    }

    public List<String> getTargetColVal(String attributeName,Reader reader){
        int col = tableAttribute.indexOf(attributeName);
        List<String> colVal= new ArrayList<>();
        reader.readAllTable();
        String[][] tableContent = reader.getTableContent();
        for (int i=1; i<tableContent.length;i++){
            if (tableContent[i]!=null) {
                colVal.add(tableContent[i][col]);
            }
        }
        return colVal;
    }

    public List<Integer> isEqualExe(List<String> colVal,String val,String opt){
        List<Integer> rows = new ArrayList<>();
        for (int i=0; i<colVal.size();i++){
            String target = colVal.get(i);
            if (opt.equals("==") && target.equals(val)){
                rows.add(i+1);
            }else if (opt.equals("!=") && !target.equals(val)){
                rows.add(i+1);
            }
        }
        return rows;
    }

    public List<Integer> isNumExe(List<String> colVal,String val,String opt,int valType){
        List<Integer> rows = new ArrayList<Integer>();
        List<Integer> newColValInt = new ArrayList<Integer>();
        List<Float> newColValFloat = new ArrayList<Float>();
        int newValInt = -1;
        float newValFloat = -1;
        if (valType==3){
            //int
            newValInt = Integer.parseInt(val);
            newColValInt = changeIntType(colVal);
        }else if (valType==4){
            //float
            newValFloat = Float.parseFloat(val);
            newColValFloat = changeFloatType(colVal);
        }

        for (int i=0; i<colVal.size();i++){

            if (">".equals(opt)){
                if (valType==3 && newColValInt.get(i) >newValInt){
                    rows.add(i+1);
                }else if (valType==4 && newColValFloat.get(i) >newValFloat){
                    rows.add(i+1);
                }
            }else if ("<".equals(opt)){
                if (valType==3 && newColValInt.get(i) <newValInt){
                    rows.add(i+1);
                }else if (valType==4 && newColValFloat.get(i) <newValFloat){
                    rows.add(i+1);
                }
            }else if (">=".equals(opt)){
                if (valType==3 && newColValInt.get(i) >=newValInt){
                    rows.add(i+1);
                }else if (valType==4 && newColValFloat.get(i) >=newValFloat){
                    rows.add(i+1);
                }
            }else if ("<=".equals(opt)){
                if (valType==3 && newColValInt.get(i)<=newValInt){
                    rows.add(i+1);
                }else if (valType==4 && newColValFloat.get(i)<=newValFloat){
                    rows.add(i+1);
                }
            }
        }
        return rows;
    }

    public List<Integer> isLikeExe(List<String> colVal,String val,String opt){
        List<Integer> rows = new ArrayList<>();
        for (int i=0; i<colVal.size();i++){
            String target = colVal.get(i).replaceAll("'","");
            val = val.replaceAll("'","");
            if (target.contains(val)){
                rows.add(i+1);
            }
        }
        return rows;
    }

    public List<Integer> changeIntType(List<String> colVals){
        List<Integer> tmp = new ArrayList<>();
        for (String change : colVals){
            int newChange = Integer.parseInt(change);
            tmp.add(newChange);
        }
        return tmp;
    }

    public List<Float> changeFloatType(List<String> colVals){
        List<Float> tmp = new ArrayList<>();
        for (String change : colVals) {
            float newChange = Float.parseFloat(change);
            tmp.add(newChange);
        }
        return tmp;
    }

    public List<Integer> delRepeat(List<Integer> target){
        HashSet<Integer> hashSet = new LinkedHashSet<>(target);
        List<Integer> listWithoutDuplicates = new ArrayList<>(hashSet);
        return listWithoutDuplicates;
    }

    public List<Integer> getRepeat(List<Integer> target){
        List<Integer> tmp = target;
        List<Integer> delRepeatTarget = delRepeat(target);
        for (int j=0;j<delRepeatTarget.size();j++){
            tmp.remove(delRepeatTarget.get(j));
        }
        return tmp;
    }

    public List<Integer> getTargetRows(){
        return targetRows;
    }


}
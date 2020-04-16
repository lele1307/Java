package process;
/**
 * @author dukehan
 */
public class Input {
    final static String semiError = "semicolonError";
    private String[] input;
    public Input(String cmd){
        if (cmd.length()<=0){
            this.input = new String[0];
        } else {
            cmd = isSemicolon(cmd);
            if (cmd.equals(semiError)){
                this.input = new String[] {"error"};
            }else {
                String[] split;
                if (cmd.contains("(")&&cmd.contains(")")){
                    split = splitInput(newCMD(cmd));
                }else {
                    split = splitInput(cmd);
                }
                this.input = split;
            }
        }
    }

    public String newCMD(String cmd){
        int start = cmd.indexOf('(');
        int end = cmd.indexOf(')');
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



    public String[] delPunctuation(String[] input){
        String regEx="[(),]";
        String nothing = "";
        String[] replace = input;
        int nullIndex = 0;
        for (int i=0; i<replace.length; i++) {
            replace[i] = replace[i].replaceAll(regEx,nothing);
            if (replace[i].equals("")){
                nullIndex++;
            }
        }
        return replaceInput(nullIndex,replace);
    }

    public String[] replaceInput(int nullIndex,String[] replace){
        if (nullIndex==0){
            return replace;
        }
        //del null value
        String[] replace1 = new String[replace.length-nullIndex];
        int j=0;
        for (int i=0; i<replace.length; i++) {
            if (replace[i]!=null&&!replace[i].equals("")){
                replace1[j] = replace[i];
                j++;
            }
        }
        return replace1;
    }


}

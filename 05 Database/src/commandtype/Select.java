package commandtype;
import commandtype.inout.Reader;
import content.Condition;
import content.Name;
import process.Terminal;

/**
 * @author dukehan
 */
public class Select {
    final static int CMDLEN = 4;
    final static int STAR = 1;
    final static int FROM = 2;
    private int where = -1;
    private int from = -1;
    private String tableName;
    private String AttribList;
    private String[] AttribLists;
    private String[] condition;
    public Terminal cmdSelect(Terminal terminal, String[] command){
        if (parseCmd(command)){
            terminal.setOutput("Select parse OK");
            //compileSelect(terminal,command);
            return terminal;
        }
        terminal.setOutput("Select parse fail!!");
        return terminal;
    }

    public boolean parseCmd(String[] command){
        int flag=0;
        if (command.length>=CMDLEN){
            from = getSpecialStr(command,"FROM");
            where = getSpecialStr(command,"WHERE");
            tableName = command[from+1];
            int len = from+2;
            setAttribList(command,from);
            printAttribList();
            if ((AttribLists[0].equals("*")&&AttribLists.length==1)){
                flag++;
            }else if (Name.parseNameList(AttribLists)){
                flag++;
            }else {
                flag=-1;
            }
            if (flag>0){
                if (len==command.length){
                    return true;
                }else if (len<command.length){
                    return checkCondition(where,command);
                }
            }
        }
        return false;
    }

    public boolean checkCondition(int where, String[] command){
        if (where!=-1){
            Condition con = new Condition(command);
            if (con.parseConditionStr(con.getConditionStr())){
                return true;
            }
        }
        return false;
    }

    public void setAttribList(String[] command,int from) {
        this.AttribList = "";
        for (int i=1; i<from; i++){
            this.AttribList = this.AttribList.concat(command[i]);
        }
        this.AttribLists = this.AttribList.split(",");
    }

    public void printAttribList(){
        for (int i = 0; i<AttribLists.length; i++){
            System.out.println(AttribLists[i]);
        }
    }

    public int getSpecialStr(String[] command,String str){
        for (int i=0; i<command.length; i++){
            if (command[i].toUpperCase().equals(str)){
                return i;
            }
        }
        return -1;
    }

    public Terminal compileSelect(Terminal terminal,String[] command){
        System.out.println(tableName);
        String pathName = terminal.getCurrentPath()+tableName+".csv";
        Reader reader = new Reader("data/"+tableName+".csv");
        String get = feedbackSelect(reader,AttribLists,condition);
        System.out.println(get);
        /*if (reader.isExists()){

        } else {
            terminal.setOutput("ERROR Unknown table "+"'"+tableName+"'.");
        }*/
        return terminal;
    }

    public String feedbackSelect(Reader reader,String[]attribLists,String[] condition) {
        String result = "";
        if (condition!=null){
            if (condition[0].contains("(")){
                //<AttributeName> <Operator> <Value>
            }else {
                //( <Condition> ) AND/OR ( <Condition> )
            }
        } else {
            result = reader.readColumn(attribLists);
        }
        return result;
    }


}

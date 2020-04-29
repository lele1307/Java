package commandtype.create;
import commandtype.CommonHandler;
import content.Name;
import process.Terminal;
/**
 * @author dukehan
 */
public class Create extends CommonHandler implements Name {
    static final  int CMDLEN = 3;
    static final  int STRUCTURE = 1;
    static final  int NAME = 2;
    private String[] attributes;
    private String attributesStr;

    @Override
    public Terminal runCommand(Terminal terminal, String[] command) {
        this.attributesStr = "id\t,";
        if (parseCommand(command)){
            return executeCommand(terminal,command);
        }else {
            terminal.setOutput("ERROR: create parse fail");
        }
        return terminal;
    }

    @Override
    public boolean parseCommand(String[] command) {
        if (command.length>=CMDLEN && Name.parseName(command[NAME])){
            String structure = command[STRUCTURE].toUpperCase();
            if ("DATABASE".equals(structure)|| "TABLE".equals(structure)){
                if (command.length>CMDLEN){
                    return parseAttributeList(command);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public Terminal executeCommand(Terminal terminal, String[] command) {
        if (command.length>=3){
            String target = command[1].toUpperCase();
            String fileName = command[2];
            if (target.equals("DATABASE")){
                CreateDatabase actionCd = new CreateDatabase(fileName);
                terminal = actionCd.setCurrTerminal(terminal);
                return terminal;
            }else if (target.equals("TABLE")){
                CreateTable actionCt = new CreateTable(command,terminal.getCurrentPath(),attributes,attributesStr);
                terminal = actionCt.setTable(terminal);
                return terminal;
            }else{
                terminal.setOutput("Input Error!");
                return terminal;
            }
        }
        terminal.setOutput("Input Error! Please use right 'Create' query. ");
        return terminal;
    }

    public boolean parseAttributeList(String[] command){
        String attributeList = command[CMDLEN];
        int leftBrackets = attributeList.indexOf('(');
        int rightBrackets = attributeList.lastIndexOf(')');
        if (leftBrackets==0 && rightBrackets==attributeList.length()-1){
            this.attributes = setAttribute(attributeList);
            if (Name.parseNameList(attributes)){
                return true;
            }
        }
        return false;
    }

    public String [] setAttribute(String attributeList){
        String regEx="[()]";
        String attribute = attributeList.replaceAll(regEx,"");
        attribute = attribute.replace(",","\t,").replace(" ","");
        this.attributesStr = this.attributesStr.concat(attribute);
        return attributesStr.split("\t,");
    }

    public String[] getAttributes() {
        return attributes;
    }

    public String getAttributesStr(){
        return attributesStr;
    }

}

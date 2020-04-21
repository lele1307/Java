package commandtype.create;
import content.Name;
import process.Terminal;
/**
 * @author dukehan
 */
public class Create implements Name {
    final static int CMDLEN = 3;
    final static int STRUCTURE = 1;
    final static int NAME = 2;
    private String[] attributes;
    private String attributesStr;
    public boolean parseCreate(Terminal terminal, String[] command){
        this.attributesStr = "id\t,";
        if (parseCmd(command)){
            terminal.setOutput("Create parse OK");
            return true;
        }
        return false;
    }

    public boolean parseCmd(String[] command){
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
        attribute = attribute.replaceAll(",","\t,");
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

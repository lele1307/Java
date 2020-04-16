package data;

/**
 * @author dukehan
 */
public class Table {
    private String name;
    private String [] attributes;
    private int numAttributes;
    public Table(String name,String [] attributes){
        this.name = name;
        this.attributes = attributes;
        this.numAttributes = attributes.length;
    }

    public String getName() {
        return name;
    }

    public String[] getAttributes() {
        return attributes;
    }

    public int getNumAttributes(){
        return numAttributes;
    }
}

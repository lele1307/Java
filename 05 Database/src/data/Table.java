package data;

import java.util.ArrayList;

/**
 * @author dukehan
 */
public class Table {

    private String name;

    private ArrayList<String> attributes;

    private int numAttributes;

    public Table(String name,String[] attribute){
        this.name = name;
        setAttributes(attribute);
        this.numAttributes = attributes.size();
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getAttributes() {
        return attributes;
    }

    public void setAttributes(String[] attribute){
        this.attributes = new ArrayList<String>();
        for (int i=0;i<attribute.length;i++){
            attributes.add(attribute[i]);
        }
    }

    public int getNumAttributes(){
        return numAttributes;
    }

    public void addNumAttributes(){
        numAttributes++;
    }

    public void dropNumAttributes(){
        numAttributes--;
    }

}

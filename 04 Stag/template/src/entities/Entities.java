package entities;

/**
 * @author dukehan
 */
public class Entities {
    private String name;
    private String description;

    public Entities(String name,String description) {
        this.name = name;
        this.description = description;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

}

package entities;

/**
 * @author dukehan
 */

public enum entityName {
    /**
     * name of entity
     */
    ARTEFACTS("artefacts"),CHARACTERS("characters"),
    FURNITURE("furniture");
    private String typeName;
    entityName(String typeName) {
        this.typeName = typeName;
    }
    public String getTypeName() {
        return typeName;
    }

}

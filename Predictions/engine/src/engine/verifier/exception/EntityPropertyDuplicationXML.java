package engine.verifier.exception;

public class EntityPropertyDuplicationXML extends Exception {
    private String EntityName;
    private String PropertyName;


    public EntityPropertyDuplicationXML(String EntityName,String propertyName){
        setEntityName(EntityName);
        setPropertyName(propertyName);
    }
    public String getPropertyName() {
        return PropertyName;
    }

    public void setPropertyName(String propertyName) {
        PropertyName = propertyName;
    }

    public String getEntityName() {
        return EntityName;
    }

    public void setEntityName(String entityName) {
        EntityName = entityName;
    }

    @Override
    public String getMessage() {
        return "Entity - " + getEntityName() + " has a duplicate property - " + getPropertyName();

    }
}

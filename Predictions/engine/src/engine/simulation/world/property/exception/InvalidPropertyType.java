package engine.simulation.world.property.exception;

public class InvalidPropertyType extends Exception{
    private String InsertedType;
    private String DesiredType;

    public InvalidPropertyType(String insertedType, String desiredType) {
        setInsertedType(insertedType);
        setDesiredType(desiredType);
    }

    public String getInsertedType() {
        return this.InsertedType;
    }

    public String getDesiredType() {
        return this.DesiredType;
    }

    public void setInsertedType(String insertedType) {
        this.InsertedType = insertedType;
    }

    public void setDesiredType(String desiredType) {
        this.DesiredType = desiredType;
    }

    @Override
    public String getMessage() {
        return "Invalid property type - " + getInsertedType() +
                " should be " + getDesiredType() + ".";
    }
}
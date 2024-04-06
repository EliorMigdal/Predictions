package engine.verifier.exception;

public class EnvPropertyDuplicationXML extends Exception {
    private final String propName;

    public EnvPropertyDuplicationXML(String name){
        this.propName = name;
    }

    @Override
    public String getMessage() {
        return "Environment property: " + this.propName +" is not unique, hence the XML isn't valid.";
    }
}
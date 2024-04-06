package engine.serilaize.exception;

public class NoneDataToSave extends Exception{


    @Override
    public String getMessage() {
        return "no data to save for file.";
    }
}

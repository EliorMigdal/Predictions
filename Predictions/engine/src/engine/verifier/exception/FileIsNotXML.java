package engine.verifier.exception;

public class FileIsNotXML extends Exception{
    private String filePath;

    public FileIsNotXML(String filePath) {
        setFilePath(filePath);
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String getMessage() {
        return "Exception: File in given path - " + getFilePath() + " is not an XML file.";
    }
}
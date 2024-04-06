package engine.verifier.exception;

public class FileDoesNotExist extends Exception {
    private String filePath;

    public FileDoesNotExist(String filePath) {
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
        return "Exception: File in given path - " + getFilePath() + " does not exist!";
    }
}
package engine.manager.exception;

public class UserAlreadyExists extends Exception {
    private final String userName;

    public UserAlreadyExists(String userName) {
        this.userName = userName;
    }

    @Override
    public String getMessage() {
        return "Username " + userName + " already exists in our system!";
    }
}

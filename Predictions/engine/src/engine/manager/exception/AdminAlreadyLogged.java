package engine.manager.exception;

public class AdminAlreadyLogged extends Exception {
    @Override
    public String getMessage() {
        return "Error: an admin is already logged in our system!";
    }
}

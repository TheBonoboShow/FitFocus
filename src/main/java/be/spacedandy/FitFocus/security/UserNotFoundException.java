package be.spacedandy.FitFocus.security;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String s) {
        super(s);
    }
}

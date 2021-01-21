package be.spacedandy.FitFocus.security;

public class SessionInPastException extends Exception {
    public SessionInPastException(String s) {
        super(s);
    }
}

package be.spacedandy.FitFocus.security;

public class SessionOverlapException extends Exception {
    public SessionOverlapException(String s) {
        super(s);
    }
}

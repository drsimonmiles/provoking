package uk.ac.kcl.inf.provoking.serialise;

public class DeserialisationException extends Exception {
    public DeserialisationException (Throwable cause) {
        super (cause);
    }
    
    public DeserialisationException (String message) {
        super (message);
    }
}

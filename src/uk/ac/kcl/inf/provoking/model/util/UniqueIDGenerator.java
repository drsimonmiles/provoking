package uk.ac.kcl.inf.provoking.model.util;

public class UniqueIDGenerator {
    public static final UniqueIDGenerator defaultGenerator = new UniqueIDGenerator ();
    private int _counter;
    private String _prefix;

    public UniqueIDGenerator (String prefix) {
        _prefix = prefix;
        _counter = 0;
    }
    
    public UniqueIDGenerator () {
        this ("");
    }
    
    public String generateID () {
        _counter += 1;
        return _prefix + _counter;
    }

    public void setPrefix (String prefix) {
        _prefix = prefix;
    }
}

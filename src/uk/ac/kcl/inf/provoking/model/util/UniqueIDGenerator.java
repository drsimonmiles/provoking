package uk.ac.kcl.inf.provoking.model.util;

public class UniqueIDGenerator implements IDGenerator {
    private int _counter;
    private String _prefix;
    private String _origination;

    public UniqueIDGenerator (String prefix) {
        _prefix = prefix;
        _origination = Long.toString (System.currentTimeMillis (), 36);
        _counter = 0;
    }
    
    public UniqueIDGenerator () {
        this ("");
    }
    
    @Override
    public String generateID () {
        _counter += 1;
        return _prefix + _origination + _counter;
    }

    public void setPrefix (String prefix) {
        _prefix = prefix;
    }
}

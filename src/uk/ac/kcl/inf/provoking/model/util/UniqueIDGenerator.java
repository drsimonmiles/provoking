package uk.ac.kcl.inf.provoking.model.util;

public class UniqueIDGenerator implements IDGenerator {
    private int _counter;
    private String _start;
    private String _origination;

    public UniqueIDGenerator (String start) {
        _start = start;
        _origination = Long.toString (System.currentTimeMillis (), 36);
        _counter = 0;
    }
    
    public UniqueIDGenerator () {
        this ("");
    }
    
    @Override
    public String generateID (String descriptive) {
        _counter += 1;
        return _start + descriptive + _origination + "-" + _counter;
    }

    public void setPrefix (String prefix) {
        _start = prefix;
    }
}

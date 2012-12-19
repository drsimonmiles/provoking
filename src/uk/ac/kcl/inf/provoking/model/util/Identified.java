package uk.ac.kcl.inf.provoking.model.util;

public class Identified {
    private Object _identifier;
    private boolean _isReference;

    public Identified () {
        _identifier = null;
        _isReference = false;
    }

    public Identified (Object identifier) {
        _identifier = identifier;
        _isReference = false;
    }
    
    public Object getIdentifier () {
        return _identifier;
    }

    public boolean hasIdentifier () {
        return _identifier != null;
    }
    
    public boolean isReference () {
        return _isReference;
    }
    
    public void setIdentifier (Object identifier) {
        _identifier = identifier;
    }
   
    public void setIsReference (boolean isReference) {
        _isReference = isReference;
    }
}

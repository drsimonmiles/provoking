package uk.ac.kcl.inf.provoking.model.util;

import java.net.URI;
import java.util.Collections;
import java.util.Map;

public class Identified {
    private Generator<? extends URI> _identifier;
    private boolean _isReference;

    public Identified () {
        _identifier = UniqueIDGenerator.singleton;
        _isReference = false;
    }
    
    public Identified (Object identifier, boolean isReference) {
        _identifier = new ValueGenerator (identifier);
        _isReference = isReference;
    }

    public Generator<? extends URI> getIdentifierGenerator () {
        return _identifier;
    }
    
    public URI getIdentifier () {
        return getIdentifier ();
    }

    public boolean isReference () {
        return _isReference;
    }
    
    public void setIdentifier (Object identifier) {
        _identifier = new ValueGenerator (identifier);
    }

    public void setIdentifierGenerator (Generator identifierGenerator) {
        _identifier = identifierGenerator;
    }
    
    public void setIsReference (boolean isReference) {
        _isReference = isReference;
    }
}

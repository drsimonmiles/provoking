package uk.ac.kcl.inf.provoking.model.util;

import java.net.URI;
import java.util.Collections;
import java.util.Map;

public class Identified {
    private Generator<? extends URI> _identifier;

    public Identified () {
        _identifier = UniqueIDGenerator.singleton;
    }
    
    public Identified (Object identifier) {
        _identifier = new ValueGenerator (identifier);
    }

    public Generator<? extends URI> getIdentifierGenerator () {
        return _identifier;
    }

    public URI getIdentifier (Map<String, Object> context) {
        return _identifier.generateValue (context);
    }
    
    public URI getIdentifier () {
        return getIdentifier (Collections.EMPTY_MAP);
    }
    
    public void setIdentifier (Object identifier) {
        _identifier = new ValueGenerator (identifier);
    }

    public void setIdentifierGenerator (Generator identifierGenerator) {
        _identifier = identifierGenerator;
    }
}

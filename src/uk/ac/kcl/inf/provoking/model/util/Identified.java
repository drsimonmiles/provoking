package uk.ac.kcl.inf.provoking.model.util;

import java.util.Objects;
import uk.ac.kcl.inf.provoking.model.Description;

/**
 * Superclass for all PROV descriptions that can have an identifier.
 * An identifier may be any Object, and is optional (null if not present).
 * 
 * @author Simon Miles
 */
public abstract class Identified implements Description {
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
    
    public abstract Term[] getClassTerms ();
    
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
    
    protected static Term[] terms (Term... terms) {
        return terms;
    }
}

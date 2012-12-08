package uk.ac.kcl.inf.provoking.model.util;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;
import uk.ac.kcl.inf.provoking.model.Attribute;

public class AttributeHolder extends Identified {
    private final Set<Attribute> _attributes;

    protected AttributeHolder () {
        _attributes = new HashSet<> ();
    }

    protected AttributeHolder (Object identifier, boolean isReference) {
        super (identifier, isReference);
        _attributes = new HashSet<> ();
    }
    
    public void addAttribute (Attribute attribute) {
        checkAttributesAllowed ();
        
        _attributes.add (attribute);
    }

    public void addAttribute (URI key, Object value) {
        checkAttributesAllowed ();
        
        addAttribute (new Attribute (key, value));
    }

    public void addGeneratedAttribute (URI key, Generator generator) {
        checkAttributesAllowed ();
        
        Attribute newAttribute = new Attribute (key);
        
        newAttribute.setValueGenerator (generator);
        addAttribute (newAttribute);
    }

    private void checkAttributesAllowed () {
        if (isReference ()) {
            throw new UnsupportedOperationException ("No attributes allowed on this type");
        }
    }
    
    public Set<Attribute> getAttributes () {
        checkAttributesAllowed ();
        
        return _attributes;
    }
    
    public Object getAttributeValue (URI key) {
        checkAttributesAllowed ();
        
        Set<Object> all = getAllAttributeValues (key);

        if (all.isEmpty ()) {
            return null;
        } else {
            return all.iterator ().next ();
        }
    }

    public Set<Object> getAllAttributeValues (URI key) {
        checkAttributesAllowed ();
        
        Set<Object> values = new HashSet<> ();

        for (Attribute attribute : getAllAttributes (key)) {
            values.add (attribute.getValue ());
        }

        return values;
    }

    public Set<Attribute> getAllAttributes (URI key) {
        checkAttributesAllowed ();
        
        Set<Attribute> found = new HashSet<> ();

        for (Attribute attribute : _attributes) {
            if (attribute.getKey ().equals (key)) {
                found.add (attribute);
            }
        }

        return found;
    }
    
    public void removeAttributes (URI key) {
        checkAttributesAllowed ();
        
        Set<Attribute> toRemove = new HashSet<> ();

        for (Attribute attribute : _attributes) {
            if (attribute.getKey ().equals (key)) {
                toRemove.add (attribute);
            }
        }
        _attributes.removeAll (toRemove);
    }

    public void removeAttribute (Attribute attribute) {
        checkAttributesAllowed ();
        
        _attributes.remove (attribute);
    }
}

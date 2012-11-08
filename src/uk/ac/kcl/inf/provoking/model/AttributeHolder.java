package uk.ac.kcl.inf.provoking.model;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

public class AttributeHolder {

    private final Set<Attribute> _attributes;
    private Object _identifier;

    public AttributeHolder () {
        _attributes = new HashSet<> ();
        _identifier = IdentifierGenerator.singleton;
    }

    public AttributeHolder (Object identifier) {
        this ();
        _identifier = identifier;
    }

    public Object getIdentifier () {
        return _identifier;
    }

    public void setIdentifier (Object identifier) {
        identifier = identifier;
    }
    
    public Set<Attribute> getAttributes () {
        return _attributes;
    }
    
    public Object getAttributeValue (URI key) {
        Set<Object> all = getAllAttributeValues (key);

        if (all.isEmpty ()) {
            return null;
        } else {
            return all.iterator ().next ();
        }
    }

    public Set<Object> getAllAttributeValues (URI key) {
        Set<Object> found = new HashSet<> ();

        for (Attribute attribute : _attributes) {
            if (attribute._key.equals (key)) {
                found.add (attribute._value);
            }
        }

        return found;
    }

    public void removeAttributes (URI key) {
        Set<Attribute> toRemove = new HashSet<> ();

        for (Attribute attribute : _attributes) {
            if (attribute._key.equals (key)) {
                toRemove.add (attribute);
            }
        }
        _attributes.removeAll (toRemove);
    }

    public void removeAttribute (URI key, Object value) {
        removeAttribute (new Attribute (key, value));
    }

    public void removeAttribute (Attribute attribute) {
        _attributes.remove (attribute);
    }

    public void setAttribute (Attribute attribute) {
        _attributes.add (attribute);
    }

    public void setAttribute (URI key, Object value) {
        setAttribute (new Attribute (key, value));
    }
}

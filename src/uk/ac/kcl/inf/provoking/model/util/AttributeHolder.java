package uk.ac.kcl.inf.provoking.model.util;

import java.util.HashSet;
import java.util.Set;
import uk.ac.kcl.inf.provoking.model.Attribute;

public abstract class AttributeHolder extends Identified {
    private final Set<Attribute> _attributes;

    protected AttributeHolder () {
        _attributes = new HashSet<> ();
    }

    protected AttributeHolder (Object identifier) {
        super (identifier);
        _attributes = new HashSet<> ();
    }

    public void addAttribute (Attribute attribute) {
        checkAttributesAllowed ();

        _attributes.add (attribute);
    }

    public void addAttribute (Object key, Object value) {
        checkAttributesAllowed ();

        addAttribute (new Attribute (key, value));
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

    public Object getAttributeValue (Object key) {
        checkAttributesAllowed ();

        Set<Object> all = getAllAttributeValues (key);

        if (all.isEmpty ()) {
            return null;
        } else {
            return all.iterator ().next ();
        }
    }

    public Set<Object> getAllAttributeValues (Object key) {
        checkAttributesAllowed ();

        Set<Object> values = new HashSet<> ();

        for (Attribute attribute : getAllAttributes (key)) {
            values.add (attribute.getValue ());
        }

        return values;
    }

    public Set<Attribute> getAllAttributes (Object key) {
        checkAttributesAllowed ();

        Set<Attribute> found = new HashSet<> ();

        for (Attribute attribute : _attributes) {
            if (attribute.getKey ().equals (key)) {
                found.add (attribute);
            }
        }

        return found;
    }

    public boolean hasAttributes () {
        return !_attributes.isEmpty ();
    }

    public void removeAttributes (Object key) {
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

    public void subtype (Term type) {
        addAttribute (Term.type.uri (), type.uri ());
    }
    
    @Override
    public String toString () {
        return toString (this, getAttributes (), null);
    }
}

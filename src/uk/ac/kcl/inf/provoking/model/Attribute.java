package uk.ac.kcl.inf.provoking.model;

import java.net.URI;

public class Attribute {
    public final URI _key;
    public final Object _value;
    
    public Attribute (URI key, Object value) {
        _key = key;
        _value = value;
    }

    @Override
    public boolean equals (Object other) {
        return (other instanceof Attribute) &&
                (((Attribute) other)._key).equals (_key) &&
                (((Attribute) other)._value).equals (_value);
    }

    @Override
    public int hashCode () {
        return _key.hashCode ();
    }
}

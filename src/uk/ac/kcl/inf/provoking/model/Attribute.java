package uk.ac.kcl.inf.provoking.model;

public class Attribute {
    private Object _key;
    private Object _value;
    
    public Attribute (Object key, Object value) {
        _key = key;
        _value = value;
    }
    
    @Override
    public boolean equals (Object other) {
        return (other instanceof Attribute) &&
                (((Attribute) other)._key).equals (_key) &&
                (((Attribute) other)._value).equals (_value);
    }

    public Object getKey () {
        return _key;
    }

    public Object getValue () {
        return _value;
    }

    @Override
    public int hashCode () {
        return _key.hashCode ();
    }

    public void setKey (Object key) {
        _key = key;
    }

    public void setValue (Object value) {
        _value = value;
    }
}

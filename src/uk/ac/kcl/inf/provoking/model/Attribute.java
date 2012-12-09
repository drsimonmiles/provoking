package uk.ac.kcl.inf.provoking.model;

import uk.ac.kcl.inf.provoking.model.util.Generator;
import uk.ac.kcl.inf.provoking.model.util.ValueGenerator;

public class Attribute {
    private static ValueGenerator NULL_GENERATOR = new ValueGenerator (null);
    private Object _key;
    private Generator<? extends Object> _valueGenerator;
    
    public Attribute (Object key, Object value) {
        _key = key;
        _valueGenerator = new ValueGenerator (value);
    }

    public Attribute (Object key) {
        _key = key;
        _valueGenerator = NULL_GENERATOR;
    }
    
    @Override
    public boolean equals (Object other) {
        return (other instanceof Attribute) &&
                (((Attribute) other)._key).equals (_key) &&
                (((Attribute) other)._valueGenerator).equals (_valueGenerator);
    }

    public Object getKey () {
        return _key;
    }

    public Object getValue () {
        return _valueGenerator.generateValue ();
    }
    
    public Generator getValueGenerator () {
        return _valueGenerator;
    }

    @Override
    public int hashCode () {
        return _key.hashCode ();
    }

    public void setKey (Object key) {
        _key = key;
    }

    public void setValue (Object value) {
        _valueGenerator = new ValueGenerator (value);
    }
    
    public void setValueGenerator (Generator<? extends Object> value) {
        _valueGenerator = value;
    }
}

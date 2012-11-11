package uk.ac.kcl.inf.provoking.model.util;

import java.util.Map;

public class ValueGenerator<T> implements Generator<T> {
    private T _value;
    
    public ValueGenerator (T value) {
        _value = value;
    }

    @Override
    public T generateValue (Map<String, Object> context) {
        return _value;
    }

}

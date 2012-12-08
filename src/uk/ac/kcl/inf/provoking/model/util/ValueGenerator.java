package uk.ac.kcl.inf.provoking.model.util;

public class ValueGenerator<T> implements Generator<T> {
    private T _value;
    
    public ValueGenerator (T value) {
        _value = value;
    }

    @Override
    public T generateValue () {
        return _value;
    }

}

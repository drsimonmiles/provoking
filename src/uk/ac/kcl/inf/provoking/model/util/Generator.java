package uk.ac.kcl.inf.provoking.model.util;

public interface Generator<T> {
    public T generateValue () throws GenerationException;
}

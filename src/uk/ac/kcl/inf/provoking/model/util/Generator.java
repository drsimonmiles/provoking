package uk.ac.kcl.inf.provoking.model.util;

import java.util.Map;

public interface Generator<T> {
    public T generateValue (Map<String, Object> context) throws GenerationException;
}

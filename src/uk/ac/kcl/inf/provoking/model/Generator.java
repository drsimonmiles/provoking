package uk.ac.kcl.inf.provoking.model;

import java.util.Map;

public interface Generator {
    public Object generateValue (Map<String, Object> context);
}

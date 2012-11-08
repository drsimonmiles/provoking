package uk.ac.kcl.inf.provoking.model;

import java.util.Map;

public class IdentifierGenerator implements Generator {
    public static final IdentifierGenerator singleton = new IdentifierGenerator ();
    public static final String PREFIX = "IdentifierGenerator:prefix";
    public static final String LAST_VALUE = "IdentifierGenerator:lastValue";
    
    @Override
    public Object generateValue (Map<String, Object> context) {
        Integer last = (Integer) context.get (LAST_VALUE);
        String prefix = (String) context.get (PREFIX);
        
        if (last == null) {
            last = 0;
        }
        last += 1;
        context.put (LAST_VALUE, last);
        
        return prefix + last;
    }
}

package uk.ac.kcl.inf.provoking.model.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public class UniqueIDGenerator implements Generator<URI> {
    public static final UniqueIDGenerator singleton = new UniqueIDGenerator ();
    public static final String PREFIX = "IdentifierGenerator:prefix";
    public static final String LAST_VALUE = "IdentifierGenerator:lastValue";
    
    @Override
    public URI generateValue () throws GenerationException {
        Integer last = (Integer) context.get (LAST_VALUE);
        String prefix = (String) context.get (PREFIX);
        
        if (last == null) {
            last = 0;
        }
        last += 1;
        context.put (LAST_VALUE, last);
        
        try {
            return new URI (prefix + last);
        } catch (URISyntaxException badSyntax) {
            throw new GenerationException (badSyntax);
        }
    }
}

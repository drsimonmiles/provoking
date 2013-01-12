package uk.ac.kcl.inf.provoking.model.util;

import java.net.URI;
import java.net.URISyntaxException;
import uk.ac.kcl.inf.provoking.builder.ProvBuildException;

public class UniqueURIGenerator implements IDGenerator {
    private final UniqueIDGenerator _idgen;
    
    public UniqueURIGenerator (String uriStart) {
        _idgen = new UniqueIDGenerator (uriStart);
    }

    @Override
    public URI generateID (String descriptive) throws ProvBuildException {
        try {
            return new URI (_idgen.generateID (descriptive));
        } catch (URISyntaxException notURI) {
            throw new ProvBuildException (getStart () + " not a valid URI start or " + descriptive + " not a valid URI part");
        }
    }

    public String getStart () {
        return _idgen.getStart ();
    }
}

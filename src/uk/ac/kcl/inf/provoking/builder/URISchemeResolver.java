package uk.ac.kcl.inf.provoking.builder;

import java.net.URI;
import java.net.URISyntaxException;

public class URISchemeResolver implements AbbreviationResolver {
    private String _scheme;
    
    public URISchemeResolver (String scheme) {
        _scheme = scheme;
    }

    @Override
    public boolean appliesTo (String abbreviation) {
        return abbreviation.startsWith (_scheme + ":");
    }

    @Override
    public Object resolve (String abbreviation) throws ProvBuildException {
        try {
            return new URI (abbreviation);
        } catch (URISyntaxException ex) {
            throw new ProvBuildException (abbreviation + " is not a valid URI");
        }
    }

}

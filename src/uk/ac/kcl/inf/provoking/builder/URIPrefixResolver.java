package uk.ac.kcl.inf.provoking.builder;

import java.net.URI;
import java.net.URISyntaxException;

public class URIPrefixResolver implements AbbreviationResolver {
    private String _prefix;
    private String _vocabularyURI;
    
    public URIPrefixResolver (String prefix, String vocabularyURI) {
        _prefix = prefix;
        _vocabularyURI = vocabularyURI;
    }

    @Override
    public boolean appliesTo (String abbreviation) {
        return abbreviation.startsWith (_prefix);
    }

    @Override
    public Object resolve (String abbreviation) throws ProvBuildException {
        try {
            return new URI (_vocabularyURI + abbreviation.substring (_prefix.length ()));
        } catch (URISyntaxException ex) {
            throw new ProvBuildException (_vocabularyURI + abbreviation.substring (_prefix.length ()) + " is not a valid URI");
        }
    }

}

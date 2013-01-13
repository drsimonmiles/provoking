package uk.ac.kcl.inf.provoking.serialise.rdf;

import java.net.URI;
import java.net.URISyntaxException;

public class RDF {
    public static final String RDF_TYPE = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type";
    private static URI RDF_TYPE_URI = null;

    public static URI typeURI () {
        if (RDF_TYPE_URI == null) {
            try {
                RDF_TYPE_URI = new URI (RDF_TYPE);
            } catch (URISyntaxException ex) {
            }
        }
        return RDF_TYPE_URI;
    }
}

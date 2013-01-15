package uk.ac.kcl.inf.provoking.serialise.rdf;

import java.net.URI;
import java.net.URISyntaxException;

public class RDF {
    public static final String RDF_TYPE = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type";
    public static final String RDF_VALUE = "http://www.w3.org/1999/02/22-rdf-syntax-ns#value";
    public static final String RDFS_LABEL = "http://www.w3.org/2000/01/rdf-schema#label";
    private static URI RDF_TYPE_URI = null;
    private static URI RDF_VALUE_URI = null;
    private static URI RDFS_LABEL_URI = null;

    public static URI labelURI () {
        if (RDFS_LABEL_URI == null) {
            try {
                RDFS_LABEL_URI = new URI (RDFS_LABEL);
            } catch (URISyntaxException ex) {
            }
        }
        return RDFS_LABEL_URI;
    }

    public static URI typeURI () {
        if (RDF_TYPE_URI == null) {
            try {
                RDF_TYPE_URI = new URI (RDF_TYPE);
            } catch (URISyntaxException ex) {
            }
        }
        return RDF_TYPE_URI;
    }

    public static URI valueURI () {
        if (RDF_VALUE_URI == null) {
            try {
                RDF_VALUE_URI = new URI (RDF_VALUE);
            } catch (URISyntaxException ex) {
            }
        }
        return RDF_VALUE_URI;
    }
}

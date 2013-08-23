package uk.ac.kcl.inf.provoking.serialise.rdf;

import java.net.URI;

public interface TriplesListener {
    void triple (URI subject, URI predicate, URI object);
    void triple (URI subject, URI predicate, String blankObject);
    void triple (URI subject, URI predicate, Literal object);
    void triple (String blankSubject, URI predicate, URI object);
    void triple (String blankSubject, URI predicate, String blankObject);
    void triple (String blankSubject, URI predicate, Literal object);
    void setPrefix (String prefix, String namespaceURI);
}

package uk.ac.kcl.inf.provoking.serialise.rdf;

import java.net.URI;

public interface TriplesListener {
    void triple (URI subject, URI predicate, URI object);
    void triple (URI subject, URI predicate, String blankObject);
    void triple (URI subject, URI predicate, Object objectValue, String objectType);
    void triple (String blankSubject, URI predicate, URI object);
    void triple (String blankSubject, URI predicateURI, String blankObject);
    void triple (String blankSubject, URI predicateURI, Object objectValue, String objectType);
}

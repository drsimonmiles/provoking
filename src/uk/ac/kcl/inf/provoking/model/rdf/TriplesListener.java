package uk.ac.kcl.inf.provoking.model.rdf;

public interface TriplesListener {
    void triple (String subject, String predicate, String object);
}

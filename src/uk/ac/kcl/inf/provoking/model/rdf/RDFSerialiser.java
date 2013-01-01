package uk.ac.kcl.inf.provoking.model.rdf;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import uk.ac.kcl.inf.provoking.model.Activity;
import uk.ac.kcl.inf.provoking.model.Description;
import uk.ac.kcl.inf.provoking.model.Document;
import uk.ac.kcl.inf.provoking.model.util.Term;

public class RDFSerialiser {
    private static final String RDF_TYPE = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type";
    private List<TriplesListener> _listeners;
    
    public RDFSerialiser (TriplesListener... listeners) {
        _listeners = new LinkedList<> (Arrays.asList (listeners));
    }
    
    private void fire (Object subject, Object predicate, Object object) {
        for (TriplesListener listener : _listeners) {
            listener.triple (subject.toString (), predicate.toString (), object.toString ());
        }
    }
    
    public void serialise (Document document) {
        for (Description description : document) {
            serialise (description);
        }
    }
    
    public void serialise (Description description) {
        if (description instanceof Activity) {
            fire (((Activity) description).getIdentifier (), RDF_TYPE, Term.Activity.uri ());
        }
    }
}

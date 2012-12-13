package uk.ac.kcl.inf.provoking.model;

import uk.ac.kcl.inf.provoking.model.util.Term;

public class Bundle extends Entity {
    private Document _descriptions;
    
    public Bundle (Object identifier) {
        super (identifier);
        _descriptions = new Document ();
        subtype (Term.Bundle.uri ());
    }
    
    public Document getDescriptions () {
        return _descriptions;
    }
}

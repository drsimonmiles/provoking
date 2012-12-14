package uk.ac.kcl.inf.provoking.model;

import uk.ac.kcl.inf.provoking.model.util.Term;

public class Bundle extends Entity {
    private Document _descriptions;
    
    public Bundle (Object identifier, Document descriptions) {
        super (identifier);
        _descriptions = descriptions;
        subtype (Term.Bundle.uri ());
    }
    
    public Bundle (Object identifier) {
        this (identifier, new Document ());
    }
    
    public Document getDescriptions () {
        return _descriptions;
    }
}

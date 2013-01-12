package uk.ac.kcl.inf.provoking.model;

import uk.ac.kcl.inf.provoking.model.util.Term;

public class Bundle extends Entity {
    private static Term[] CLASS_TERMS = terms (Term.Entity, Term.Bundle);
    private Document _descriptions;
    
    public Bundle (Object identifier, Document descriptions) {
        super (identifier);
        _descriptions = descriptions;
        subtype (Term.Bundle);
    }
    
    public Bundle (Object identifier) {
        this (identifier, new Document ());
    }
    
    @Override
    public Term[] getClassTerms () {
        return CLASS_TERMS;
    }

    public Document getDescriptions () {
        return _descriptions;
    }
}

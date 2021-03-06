package uk.ac.kcl.inf.provoking.model;

import static uk.ac.kcl.inf.provoking.model.Description.toString;
import uk.ac.kcl.inf.provoking.model.util.Term;

public class Bundle extends Entity {
    private static Term[] CLASS_TERMS = terms (Term.Entity, Term.Bundle);
    private Document _descriptions;
    
    public Bundle (Object identifier, Document descriptions) {
        super (identifier);
        _descriptions = descriptions;
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

    @Override
    public String toString () {
        return toString (this, getAttributes (), new String[] {"contents"}, toString (_descriptions));
    }
}

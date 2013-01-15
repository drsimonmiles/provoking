package uk.ac.kcl.inf.provoking.model;

import uk.ac.kcl.inf.provoking.model.util.Term;

public class WasQuotedFrom extends WasDerivedFrom {
    public WasQuotedFrom (Entity quote, Entity source, Activity deriver, WasGeneratedBy generation, Used usage) {
        super (quote, source, deriver, generation, usage);
    }

    public WasQuotedFrom (Entity quote, Entity source, Activity deriver) {
        super (quote, source, deriver);
    }

    public WasQuotedFrom (Entity quote, Entity source) {
         super (quote, source);
   }

    public WasQuotedFrom (Object identifier, Entity quote, Entity source, Activity deriver, WasGeneratedBy generation, Used usage) {
        super (identifier, quote, source, deriver, generation, usage);
    }

    public WasQuotedFrom (Object identifier, Entity quote, Entity source, Activity deriver) {
        super (identifier, quote, source, deriver);
    }

    public WasQuotedFrom (Object identifier, Entity quote, Entity source) {
        super (identifier, quote, source);
    }
    
    public Entity getQuote () {
        return getDerived ();
    }

    public Entity getSource () {
        return getDerivedFrom ();
    }

    public void setQuote (Entity quote) {
        setDerived (quote);
    }
    
    public void setSource (Entity source) {
        setDerivedFrom (source);
    }
    
    private static Term[] CLASS_TERMS = terms (Term.Derivation, Term.Quotation);
    @Override
    public Term[] getClassTerms () {
        return CLASS_TERMS;
    }
}

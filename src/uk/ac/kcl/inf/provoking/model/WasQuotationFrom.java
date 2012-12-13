package uk.ac.kcl.inf.provoking.model;

import uk.ac.kcl.inf.provoking.model.util.Term;

public class WasQuotationFrom extends WasDerivedFrom {
    public WasQuotationFrom (Entity derived, Entity derivedFrom, Activity deriver, WasGeneratedBy generation, Used usage) {
        super (derived, derivedFrom, deriver, generation, usage);
        subtype (Term.Quotation.uri ());
    }

    public WasQuotationFrom (Entity derived, Entity derivedFrom, Activity deriver) {
        super (derived, derivedFrom, deriver);
        subtype (Term.Quotation.uri ());
    }

    public WasQuotationFrom (Entity derived, Entity derivedFrom) {
         super (derived, derivedFrom);
         subtype (Term.Quotation.uri ());
   }

    public WasQuotationFrom (Object identifier, Entity derived, Entity derivedFrom, Activity deriver, WasGeneratedBy generation, Used usage) {
        super (identifier, derived, derivedFrom, deriver, generation, usage);
        subtype (Term.Quotation.uri ());
    }

    public WasQuotationFrom (Object identifier, Entity derived, Entity derivedFrom, Activity deriver) {
        super (identifier, derived, derivedFrom, deriver);
        subtype (Term.Quotation.uri ());
    }

    public WasQuotationFrom (Object identifier, Entity derived, Entity derivedFrom) {
        super (identifier, derived, derivedFrom);
        subtype (Term.Quotation.uri ());
    }
}

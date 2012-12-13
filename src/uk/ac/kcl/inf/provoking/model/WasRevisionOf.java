package uk.ac.kcl.inf.provoking.model;

import uk.ac.kcl.inf.provoking.model.util.Term;

public class WasRevisionOf extends WasDerivedFrom {
    public WasRevisionOf (Entity derived, Entity derivedFrom, Activity deriver, WasGeneratedBy generation, Used usage) {
        super (derived, derivedFrom, deriver, generation, usage);
        subtype (Term.Revision.uri ());
    }

    public WasRevisionOf (Entity derived, Entity derivedFrom, Activity deriver) {
        super (derived, derivedFrom, deriver);
        subtype (Term.Revision.uri ());
    }

    public WasRevisionOf (Entity derived, Entity derivedFrom) {
         super (derived, derivedFrom);
         subtype (Term.Revision.uri ());
   }

    public WasRevisionOf (Object identifier, Entity derived, Entity derivedFrom, Activity deriver, WasGeneratedBy generation, Used usage) {
        super (identifier, derived, derivedFrom, deriver, generation, usage);
        subtype (Term.Revision.uri ());
    }

    public WasRevisionOf (Object identifier, Entity derived, Entity derivedFrom, Activity deriver) {
        super (identifier, derived, derivedFrom, deriver);
        subtype (Term.Revision.uri ());
    }

    public WasRevisionOf (Object identifier, Entity derived, Entity derivedFrom) {
        super (identifier, derived, derivedFrom);
        subtype (Term.Revision.uri ());
    }
}

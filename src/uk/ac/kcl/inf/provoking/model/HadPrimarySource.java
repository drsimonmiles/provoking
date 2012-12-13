package uk.ac.kcl.inf.provoking.model;

import uk.ac.kcl.inf.provoking.model.util.Term;

public class HadPrimarySource extends WasDerivedFrom {
    public HadPrimarySource (Entity derived, Entity derivedFrom, Activity deriver, WasGeneratedBy generation, Used usage) {
        super (derived, derivedFrom, deriver, generation, usage);
        subtype (Term.PrimarySource.uri ());
    }

    public HadPrimarySource (Entity derived, Entity derivedFrom, Activity deriver) {
        super (derived, derivedFrom, deriver);
        subtype (Term.PrimarySource.uri ());
    }

    public HadPrimarySource (Entity derived, Entity derivedFrom) {
         super (derived, derivedFrom);
         subtype (Term.PrimarySource.uri ());
   }

    public HadPrimarySource (Object identifier, Entity derived, Entity derivedFrom, Activity deriver, WasGeneratedBy generation, Used usage) {
        super (identifier, derived, derivedFrom, deriver, generation, usage);
        subtype (Term.PrimarySource.uri ());
    }

    public HadPrimarySource (Object identifier, Entity derived, Entity derivedFrom, Activity deriver) {
        super (identifier, derived, derivedFrom, deriver);
        subtype (Term.PrimarySource.uri ());
    }

    public HadPrimarySource (Object identifier, Entity derived, Entity derivedFrom) {
        super (identifier, derived, derivedFrom);
        subtype (Term.PrimarySource.uri ());
    }
}

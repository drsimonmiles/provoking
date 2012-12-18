package uk.ac.kcl.inf.provoking.model;

import uk.ac.kcl.inf.provoking.model.util.Term;

public class HadPrimarySource extends WasDerivedFrom {
    public HadPrimarySource (Entity derived, Entity primarySource, Activity deriver, WasGeneratedBy generation, Used usage) {
        super (derived, primarySource, deriver, generation, usage);
        subtype (Term.PrimarySource.uri ());
    }

    public HadPrimarySource (Entity derived, Entity primarySource, Activity deriver) {
        super (derived, primarySource, deriver);
        subtype (Term.PrimarySource.uri ());
    }

    public HadPrimarySource (Entity derived, Entity primarySource) {
         super (derived, primarySource);
         subtype (Term.PrimarySource.uri ());
   }

    public HadPrimarySource (Object identifier, Entity derived, Entity primarySource, Activity deriver, WasGeneratedBy generation, Used usage) {
        super (identifier, derived, primarySource, deriver, generation, usage);
        subtype (Term.PrimarySource.uri ());
    }

    public HadPrimarySource (Object identifier, Entity derived, Entity primarySource, Activity deriver) {
        super (identifier, derived, primarySource, deriver);
        subtype (Term.PrimarySource.uri ());
    }

    public HadPrimarySource (Object identifier, Entity derived, Entity primarySource) {
        super (identifier, derived, primarySource);
        subtype (Term.PrimarySource.uri ());
    }
    
    public Entity getPrimarySource () {
        return getDerivedFrom ();
    }

    public void setPrimarySource (Entity source) {
        setDerivedFrom (source);
    }
}
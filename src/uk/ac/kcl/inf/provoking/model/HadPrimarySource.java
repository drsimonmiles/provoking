package uk.ac.kcl.inf.provoking.model;

import uk.ac.kcl.inf.provoking.model.util.Term;

public class HadPrimarySource extends WasDerivedFrom {
    public HadPrimarySource (Entity derived, Entity primarySource, Activity deriver, WasGeneratedBy generation, Used usage) {
        super (derived, primarySource, deriver, generation, usage);
    }

    public HadPrimarySource (Entity derived, Entity primarySource, Activity deriver) {
        super (derived, primarySource, deriver);
    }

    public HadPrimarySource (Entity derived, Entity primarySource) {
         super (derived, primarySource);
   }

    public HadPrimarySource (Object identifier, Entity derived, Entity primarySource, Activity deriver, WasGeneratedBy generation, Used usage) {
        super (identifier, derived, primarySource, deriver, generation, usage);
    }

    public HadPrimarySource (Object identifier, Entity derived, Entity primarySource, Activity deriver) {
        super (identifier, derived, primarySource, deriver);
    }

    public HadPrimarySource (Object identifier, Entity derived, Entity primarySource) {
        super (identifier, derived, primarySource);
    }
    
    public Entity getPrimarySource () {
        return getDerivedFrom ();
    }

    public void setPrimarySource (Entity source) {
        setDerivedFrom (source);
    }
    
    private static Term[] CLASS_TERMS = terms (Term.Derivation, Term.PrimarySource);
    @Override
    public Term[] getClassTerms () {
        return CLASS_TERMS;
    }
    
    @Override
    public Term getClassTerm () {
        return Term.PrimarySource;
    }
}

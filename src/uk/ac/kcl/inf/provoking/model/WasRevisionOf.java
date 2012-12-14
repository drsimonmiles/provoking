package uk.ac.kcl.inf.provoking.model;

import uk.ac.kcl.inf.provoking.model.util.Term;

public class WasRevisionOf extends WasDerivedFrom {
    public WasRevisionOf (Entity later, Entity earlier, Activity deriver, WasGeneratedBy generation, Used usage) {
        super (later, earlier, deriver, generation, usage);
        subtype (Term.Revision.uri ());
    }

    public WasRevisionOf (Entity later, Entity earlier, Activity deriver) {
        super (later, earlier, deriver);
        subtype (Term.Revision.uri ());
    }

    public WasRevisionOf (Entity later, Entity earlier) {
         super (later, earlier);
         subtype (Term.Revision.uri ());
   }

    public WasRevisionOf (Object identifier, Entity later, Entity earlier, Activity deriver, WasGeneratedBy generation, Used usage) {
        super (identifier, later, earlier, deriver, generation, usage);
        subtype (Term.Revision.uri ());
    }

    public WasRevisionOf (Object identifier, Entity later, Entity earlier, Activity deriver) {
        super (identifier, later, earlier, deriver);
        subtype (Term.Revision.uri ());
    }

    public WasRevisionOf (Object identifier, Entity later, Entity earlier) {
        super (identifier, later, earlier);
        subtype (Term.Revision.uri ());
    }
    
    public Entity getEarlier () {
        return getDerivedFrom ();
    }

    public Entity getLater () {
        return getDerived ();
    }

    public void setEarlier (Entity earlier) {
        setDerivedFrom (earlier);
    }

    public void setLater (Entity later) {
        setDerived (later);
    }
}

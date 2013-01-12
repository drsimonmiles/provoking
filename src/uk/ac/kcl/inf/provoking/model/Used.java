package uk.ac.kcl.inf.provoking.model;

import java.util.Date;
import uk.ac.kcl.inf.provoking.model.util.Term;
import uk.ac.kcl.inf.provoking.model.util.TimestampedEdge;

public class Used extends TimestampedEdge {
    private Activity _user;
    private Entity _used;
    
    public Used (Activity user, Entity used) {
        _user = user;
        _used = used;
    }

    public Used (Activity user, Entity used, Date time) {
        super (time);
        _user = user;
        _used = used;
    }

    public Used (Object identifier, Activity user, Entity used) {
        super (identifier);
        _user = user;
        _used = used;
    }

    public Used (Object identifier, Activity user, Entity used, Date time) {
        super (identifier);
        _user = user;
        _used = used;
        setTime (time);
    }

    public Activity getUser () {
        return _user;
    }

    public void setUser (Activity user) {
        _user = user;
    }

    public Entity getUsed () {
        return _used;
    }

    public void setUsed (Entity used) {
        _used = used;
    }

    public static Used reference (Object identifier) {
        Used reference = new Used (identifier, null, null);
        reference.setIsReference (true);
        return reference;
    }

    private static Term[] CLASS_TERMS = terms (Term.Usage);
    @Override
    public Term[] getClassTerms () {
        return CLASS_TERMS;
    }
}

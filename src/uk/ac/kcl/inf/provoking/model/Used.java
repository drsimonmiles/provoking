package uk.ac.kcl.inf.provoking.model;

import java.util.Date;

public class Used extends AttributeHolder implements Description {
    public final Activity _user;
    public final Entity _used;
    
    public Used (Activity user, Entity used) {
        _user = user;
        _used = used;
    }

    public Used (Activity user, Entity used, Date time) {
        _user = user;
        _used = used;
        if (time != null) {
            setAttribute (Term.atTime.uri (), time);
        }
    }
}

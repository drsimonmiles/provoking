package uk.ac.kcl.inf.provoking.model;

import java.util.Date;

public class WasGeneratedBy extends AttributeHolder implements Description {
    public final Activity _generater;
    public final Entity _generated;
    
    public WasGeneratedBy (Entity generated, Activity generater) {
        _generater = generater;
        _generated = generated;
    }

    public WasGeneratedBy (Entity generated, Activity generater, Date time) {
        _generater = generater;
        _generated = generated;
        if (time != null) {
            setAttribute (Term.atTime.uri (), time);
        }
    }

}

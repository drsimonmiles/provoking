package uk.ac.kcl.inf.provoking.model;

import java.util.Date;
import uk.ac.kcl.inf.provoking.model.util.Term;

public class WasEndedBy extends InstantaneousEvent {
    private Activity _ended;
    private Activity _ender;
    private Entity _trigger;
    
    public WasEndedBy (Activity ended, Entity trigger, Activity ender) {
        _ended = ended;
        _trigger = trigger;
        _ender = ender;
    }

    public WasEndedBy (Activity ended, Activity ender) {
        this (ended, (Entity) null, ender);
    }

    public WasEndedBy (Activity ended, Entity trigger) {
        this (ended, trigger, (Activity) null);
    }

    public WasEndedBy (Object identifier, Activity ended, Entity trigger, Activity ender) {
        super (identifier);
        _ended = ended;
        _trigger = trigger;
        _ender = ender;
    }
    
    public WasEndedBy (Object identifier, Activity ended, Activity ender) {
        this (identifier, ended, null, ender);
    }

    public WasEndedBy (Object identifier, Activity ended, Entity trigger) {
        this (identifier, ended, trigger, (Activity) null);
    }

    public WasEndedBy (Object identifier, Activity ended, Entity trigger, Activity ender, Date time) {
        super (identifier);
        _ended = ended;
        _trigger = trigger;
        _ender = ender;
        setTime (time);
    }

    public WasEndedBy (Object identifier, Activity ended, Activity ender, Date time) {
        this (identifier, ended, null, ender, time);
    }

    public WasEndedBy (Object identifier, Activity ended, Entity trigger, Date time) {
        this (identifier, ended, trigger, null, time);
    }

    public WasEndedBy (Activity ended, Entity trigger, Activity ender, Date time) {
        _ended = ended;
        _trigger = trigger;
        _ender = ender;
        setTime (time);
    }
    
    public WasEndedBy (Activity ended, Activity ender, Date time) {
        this (ended, (Entity) null, ender, time);
    }

    public WasEndedBy (Activity ended, Entity trigger, Date time) {
        this (ended, trigger, null, time);
    }
    
    public Activity getEnded () {
        return _ended;
    }

    public Activity getEnder () {
        return _ender;
    }

    public Entity getTrigger () {
        return _trigger;
    }

    public void setEnded (Activity ended) {
        _ended = ended;
    }

    public void setEnder (Activity ender) {
        _ender = ender;
    }

    public void setTrigger (Entity trigger) {
        _trigger = trigger;
    }
    
    @Override
    public String toString () {
        return toString (this, getAttributes (), new String[] {"ender", "ended", "trigger", "location", "role", "time"},
                         _ender, _ended, _trigger, getLocation (), getRole (), getTime ());
    }
    
    public static WasEndedBy reference (Object identifier) {
        WasEndedBy reference = new WasEndedBy (identifier, null, null, (Activity) null);
        reference.setIsReference (true);
        return reference;
    }

    private static Term[] CLASS_TERMS = terms (Term.End);
    @Override
    public Term[] getClassTerms () {
        return CLASS_TERMS;
    }
}

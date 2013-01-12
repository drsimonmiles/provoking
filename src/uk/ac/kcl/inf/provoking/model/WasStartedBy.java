package uk.ac.kcl.inf.provoking.model;

import java.util.Date;
import uk.ac.kcl.inf.provoking.model.util.Term;
import uk.ac.kcl.inf.provoking.model.util.TimestampedEdge;

public class WasStartedBy extends TimestampedEdge implements Description {
    private Activity _started;
    private Activity _starter;
    private Entity _trigger;
    
    public WasStartedBy (Activity started, Entity trigger, Activity starter) {
        _started = started;
        _trigger = trigger;
        _starter = starter;
    }

    public WasStartedBy (Activity started, Activity starter) {
        this (started, (Entity) null, starter);
    }

    public WasStartedBy (Activity started, Entity trigger) {
        this (started, trigger, (Activity) null);
    }

    public WasStartedBy (Object identifier, Activity started, Entity trigger, Activity starter) {
        super (identifier);
        _started = started;
        _trigger = trigger;
        _starter = starter;
    }
    
    public WasStartedBy (Object identifier, Activity started, Activity starter) {
        this (identifier, started, null, starter);
    }

    public WasStartedBy (Object identifier, Activity started, Entity trigger) {
        this (identifier, started, trigger, (Activity) null);
    }

    public WasStartedBy (Object identifier, Activity started, Entity trigger, Activity starter, Date time) {
        super (identifier);
        _started = started;
        _trigger = trigger;
        _starter = starter;
        setTime (time);
    }

    public WasStartedBy (Object identifier, Activity started, Activity starter, Date time) {
        this (identifier, started, null, starter, time);
    }

    public WasStartedBy (Object identifier, Activity started, Entity trigger, Date time) {
        this (identifier, started, trigger, null, time);
    }

    public WasStartedBy (Activity started, Entity trigger, Activity starter, Date time) {
        _started = started;
        _trigger = trigger;
        _starter = starter;
        setTime (time);
    }
    
    public WasStartedBy (Activity started, Activity starter, Date time) {
        this (started, (Entity) null, starter, time);
    }

    public WasStartedBy (Activity started, Entity trigger, Date time) {
        this (started, trigger, null, time);
    }
    
    public Activity getStarted () {
        return _started;
    }

    public Activity getStarter () {
        return _starter;
    }

    public Entity getTrigger () {
        return _trigger;
    }

    public void setStarted (Activity started) {
        _started = started;
    }

    public void setStarter (Activity starter) {
        _starter = starter;
    }

    public void setTrigger (Entity trigger) {
        _trigger = trigger;
    }
    
    public static WasStartedBy reference (Object identifier) {
        WasStartedBy reference = new WasStartedBy (identifier, null, null, (Activity) null);
        reference.setIsReference (true);
        return reference;
    }

    private static Term[] CLASS_TERMS = terms (Term.Start);
    @Override
    public Term[] getClassTerms () {
        return CLASS_TERMS;
    }
}

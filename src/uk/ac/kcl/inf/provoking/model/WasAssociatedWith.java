package uk.ac.kcl.inf.provoking.model;

public class WasAssociatedWith implements Description {
    public final Agent _responsible;
    public final Activity _responsibleFor;
    
    public WasAssociatedWith (Activity responsibleFor, Agent responsible) {
        _responsible = responsible;
        _responsibleFor = responsibleFor;
    }
}

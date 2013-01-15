package uk.ac.kcl.inf.provoking.visualise;

import uk.ac.kcl.inf.provoking.model.*;

public class Graphs {
    public static Description getCause (Description edge) {
        if (edge instanceof ActedOnBehalfOf) {
            return ((ActedOnBehalfOf) edge).getOnBehalfOf ();
        }
        if (edge instanceof AlternateOf) {
            return ((AlternateOf) edge).getAlternateB ();
        }
        if (edge instanceof HadMember) {
            return ((HadMember) edge).getMember ();
        }
        if (edge instanceof SpecializationOf) {
            return ((SpecializationOf) edge).getGeneralEntity ();
        }
        if (edge instanceof Used) {
            return ((Used) edge).getUsed ();
        }
        if (edge instanceof WasAssociatedWith) {
            return ((WasAssociatedWith) edge).getResponsible ();
        }
        if (edge instanceof WasAttributedTo) {
            return ((WasAttributedTo) edge).getAttributedTo ();
        }
        if (edge instanceof WasDerivedFrom) {
            return ((WasDerivedFrom) edge).getDerivedFrom ();
        }
        if (edge instanceof WasEndedBy) {
            return ((WasEndedBy) edge).getTrigger ();
        }
        if (edge instanceof WasGeneratedBy) {
            return ((WasGeneratedBy) edge).getGenerater ();
        }
        if (edge instanceof WasInformedBy) {
            return ((WasInformedBy) edge).getInformer ();
        }
        if (edge instanceof WasInvalidatedBy) {
            return ((WasInvalidatedBy) edge).getInvalidater ();
        }
        if (edge instanceof WasStartedBy) {
            return ((WasStartedBy) edge).getTrigger ();
        }
        return null;
    }
    
    public static Description getEffect (Description edge) {
        if (edge instanceof ActedOnBehalfOf) {
            return ((ActedOnBehalfOf) edge).getActer ();
        }
        if (edge instanceof AlternateOf) {
            return ((AlternateOf) edge).getAlternateA ();
        }
        if (edge instanceof HadMember) {
            return ((HadMember) edge).getCollection ();
        }
        if (edge instanceof SpecializationOf) {
            return ((SpecializationOf) edge).getSpecificEntity ();
        }
        if (edge instanceof Used) {
            return ((Used) edge).getUser ();
        }
        if (edge instanceof WasAssociatedWith) {
            return ((WasAssociatedWith) edge).getResponsibleFor ();
        }
        if (edge instanceof WasAttributedTo) {
            return ((WasAttributedTo) edge).getAttributed ();
        }
        if (edge instanceof WasDerivedFrom) {
            return ((WasDerivedFrom) edge).getDerived ();
        }
        if (edge instanceof WasEndedBy) {
            return ((WasEndedBy) edge).getEnded ();
        }
        if (edge instanceof WasGeneratedBy) {
            return ((WasGeneratedBy) edge).getGenerated ();
        }
        if (edge instanceof WasInformedBy) {
            return ((WasInformedBy) edge).getInformed ();
        }
        if (edge instanceof WasInvalidatedBy) {
            return ((WasInvalidatedBy) edge).getInvalidated ();
        }
        if (edge instanceof WasStartedBy) {
            return ((WasStartedBy) edge).getStarted ();
        }
        return null;
    }
}

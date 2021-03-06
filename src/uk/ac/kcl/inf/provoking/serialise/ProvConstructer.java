package uk.ac.kcl.inf.provoking.serialise;

import java.net.URI;
import uk.ac.kcl.inf.provoking.model.*;
import uk.ac.kcl.inf.provoking.model.util.Influenceable;
import uk.ac.kcl.inf.provoking.model.util.Term;
import static uk.ac.kcl.inf.provoking.model.util.Term.*;

public class ProvConstructer {
    public static Activity activity (Object object, String position, String line) throws DeserialisationException {
        if (object instanceof Activity) {
            return (Activity) object;
        } else {
            throw new DeserialisationException ("Activity " + position + " expected, but found " + object.getClass ().getSimpleName () + " in " + line);
        }
    }

    public static Agent agent (Object object, String position, String line) throws DeserialisationException {
        if (object instanceof Agent) {
            return (Agent) object;
        } else {
            throw new DeserialisationException ("Agent " + position + " expected, but found " + object.getClass ().getSimpleName () + " in " + line);
        }
    }
    
    public static Description create (Term term, Object identifier) {
        switch (term) {
            case actedOnBehalfOf:
            case Delegation:
                return new ActedOnBehalfOf (identifier, null, null);
            case Activity:
                return new Activity (identifier);
            case Agent:
                return new Agent (identifier);
            case alternateOf:
                return new AlternateOf (null, null);
            case Bundle:
                return new Bundle (identifier);
            case Collection:
                return new Collection (identifier);
            case EmptyCollection:
                return new EmptyCollection (identifier);
            case Entity:
                return new Entity (identifier);
            case hadMember:
                return new HadMember (null, null);
            case hadPrimarySource:
            case PrimarySource:
                return new HadPrimarySource (identifier, null, null);
            case Location:
                return new Location (identifier);
            case Organization:
                return new Organization (identifier);
            case Person:
                return new Person (identifier);
            case Plan:
                return new Plan (identifier);
            case Role:
                return new Role (identifier);
            case SoftwareAgent:
                return new SoftwareAgent (identifier);
            case specializationOf:
                return new SpecializationOf (null, null);
            case used:
            case Usage:
                return new Used (identifier, null, null);
            case wasAssociatedWith:
            case Association:
                return new WasAssociatedWith (identifier, null, null);
            case wasAttributedTo:
            case Attribution:
                return new WasAttributedTo (identifier, null, null);
            case wasDerivedFrom:
            case Derivation:
                return new WasDerivedFrom (identifier, null, null);
            case wasEndedBy:
            case End:
                return new WasEndedBy (identifier, null, null, null, null);
            case wasGeneratedBy:
            case Generation:
                return new WasGeneratedBy (identifier, null, null);
            case wasInformedBy:
            case Communication:
                return new WasInformedBy (identifier, null, null);
            case wasInvalidatedBy:
            case Invalidation:
                return new WasInvalidatedBy (identifier, null);
            case wasQuotedFrom:
            case Quotation:
                return new WasQuotedFrom (identifier, null, null);
            case wasRevisionOf:
            case Revision:
                return new WasRevisionOf (identifier, null, null);
            case wasStartedBy:
            case Start:
                return new WasStartedBy (identifier, null, null, null, null);
            case wasInfluencedBy:
            case Influence:
                return new WasInfluencedBy (identifier, null, null);
            default:
                return null;
        }
    }

    public static Description create (URI type, Object identifier) {
        return create (Term.toTerm (type), identifier);
    }

    public static Description create (URI type) {
        return create (type, null);
    }
    
    public static Description createBinary (Term relation, Object subject, Object object, String line) throws DeserialisationException {
        Entity entitySubject = null, entityObject = null;
        Activity activitySubject = null, activityObject = null;
        Agent agentSubject = null, agentObject = null;
        
        switch (relation) {
            case alternateOf:
            case specializationOf:
            case hadMember:
            case hadPrimarySource:
            case wasDerivedFrom:
            case wasRevisionOf:
            case wasQuotedFrom:
                entitySubject = entity (subject, "subject", line);
                entityObject = entity (object, "object", line);
                break;
            case used:
            case wasEndedBy:
            case wasStartedBy:
                activitySubject = activity (subject, "subject", line);
                entityObject = entity (object, "object", line);
                break;
            case wasGeneratedBy:
            case wasInvalidatedBy:
                entitySubject = entity (subject, "subject", line);
                activityObject = activity (object, "object", line);
                break;
            case actedOnBehalfOf:
                agentSubject = agent (subject, "subject", line);
                agentObject = agent (object, "object", line);
                break;
            case wasAssociatedWith:
                activitySubject = activity (subject, "subject", line);
                agentObject = agent (object, "object", line);
                break;
            case wasAttributedTo:
                entitySubject = entity (subject, "subject", line);
                agentObject = agent (object, "object", line);
                break;
            case wasInformedBy:
                activitySubject = activity (subject, "subject", line);
                activityObject = activity (object, "object", line);
                break;
        }
        
        switch (relation) {
            case alternateOf:
                return new AlternateOf (entitySubject, entityObject);
            case specializationOf:
                return new SpecializationOf (entitySubject, entityObject);
            case hadMember:
                return new HadMember (entitySubject, entityObject);
            case actedOnBehalfOf:
                return new ActedOnBehalfOf (agentSubject, agentObject);
            case hadPrimarySource:
                return new HadPrimarySource (entitySubject, entityObject);
            case used:
                return new Used (activitySubject, entityObject);
            case wasAssociatedWith:
                return new WasAssociatedWith (activitySubject, agentObject);
            case wasAttributedTo:
                return new WasAttributedTo (entitySubject, agentObject);
            case wasDerivedFrom:
                return new WasDerivedFrom (entitySubject, entityObject);
            case wasEndedBy:
                return new WasEndedBy (activitySubject, entityObject);
            case wasGeneratedBy:
                return new WasGeneratedBy (entitySubject, activityObject);
            case wasInformedBy:
                return new WasInformedBy (activitySubject, activityObject);
            case wasInvalidatedBy:
                return new WasInvalidatedBy (entitySubject, activityObject);
            case wasRevisionOf:
                return new WasRevisionOf (entitySubject, entityObject);
            case wasQuotedFrom:
                return new WasQuotedFrom (entitySubject, entityObject);
            case wasStartedBy:
                return new WasStartedBy (activitySubject, entityObject);
            case wasInfluencedBy:
                return new WasInfluencedBy ((Influenceable) subject, (Influenceable) object);
            default:
                return null;
        }
    }
    
    public static Entity entity (Object object, String position, String line) throws DeserialisationException {
        if (object instanceof Entity) {
            return (Entity) object;
        } else {
            throw new DeserialisationException ("Entity " + position + " expected, but found " + object.getClass ().getSimpleName () + " in " + line);
        }
    }

    public static Term getDomain (Term relation) {
        switch (relation) {
            case actedOnBehalfOf:
            case qualifiedDelegation:
                return Agent;
            case wasAssociatedWith:
            case wasInformedBy:
            case used:
            case wasEndedBy:
            case wasStartedBy:
            case qualifiedUsage:
            case qualifiedAssociation:
            case qualifiedCommunication:
            case qualifiedEnd:
            case qualifiedStart:
                return Activity;
            case wasAttributedTo:
            case wasGeneratedBy:
            case wasInvalidatedBy:
            case alternateOf:
            case hadPrimarySource:
            case specializationOf:
            case wasDerivedFrom:
            case wasQuotedFrom:
            case wasRevisionOf:
            case qualifiedGeneration:
            case qualifiedAttribution:
            case qualifiedDerivation:
            case qualifiedInvalidation:
            case qualifiedPrimarySource:
            case qualifiedQuotation:
            case qualifiedRevision:
                return Entity;
            case hadMember:
                return Collection;
            case hadGeneration:
            case hadUsage:
                return Derivation;
            case hadPlan:
                return Association;
            default:
                return null;
        }
    }

    public static Term getRange (Term relation) {
        switch (relation) {
            case actedOnBehalfOf:
            case agent:
            case wasAssociatedWith:
            case wasAttributedTo:
                return Agent;
            case activity:
            case wasGeneratedBy:
            case wasInformedBy:
            case wasInvalidatedBy:
                return Activity;
            case alternateOf:
            case entity:
            case hadMember:
            case hadPrimarySource:
            case specializationOf:
            case used:
            case wasDerivedFrom:
            case wasEndedBy:
            case wasQuotedFrom:
            case wasRevisionOf:
            case wasStartedBy:
                return Entity;
            case atLocation:
            case location:
                return Location;
            case hadGeneration:
            case qualifiedGeneration:
                return Generation;
            case hadPlan:
                return Plan;
            case hadRole:
            case role:
                return Role;
            case hadUsage:
            case qualifiedUsage:
                return Usage;
            case qualifiedAssociation:
                return Association;
            case qualifiedAttribution:
                return Attribution;
            case qualifiedCommunication:
                return Communication;
            case qualifiedDelegation:
                return Delegation;
            case qualifiedDerivation:
                return Derivation;
            case qualifiedEnd:
                return End;
            case qualifiedInvalidation:
                return Invalidation;
            case qualifiedPrimarySource:
                return PrimarySource;
            case qualifiedQuotation:
                return Quotation;
            case qualifiedRevision:
                return Revision;
            case qualifiedStart:
                return Start;
            default:
                return null;
        }
    }

    public static void setActivity (Description description, Activity activity) {
        if (description instanceof ActedOnBehalfOf) {
            ((ActedOnBehalfOf) description).setActivity (activity);
        }
        if (description instanceof WasAssociatedWith) {
            ((WasAssociatedWith) description).setResponsibleFor (activity);
        }
        if (description instanceof WasDerivedFrom) {
            ((WasDerivedFrom) description).setDeriver (activity);
        }
        if (description instanceof WasEndedBy) {
            ((WasEndedBy) description).setEnder (activity);
        }
        if (description instanceof WasGeneratedBy) {
            ((WasGeneratedBy) description).setGenerater (activity);
        }
        if (description instanceof WasInformedBy) {
            ((WasInformedBy) description).setInformer (activity);
        }
        if (description instanceof WasInfluencedBy) {
            ((WasInfluencedBy) description).setInfluencer (activity);
        }
        if (description instanceof WasInvalidatedBy) {
            ((WasInvalidatedBy) description).setInvalidater (activity);
        }
        if (description instanceof WasStartedBy) {
            ((WasStartedBy) description).setStarter (activity);
        }
    }

    public static void setAgent (Description description, Agent agent) {
        if (description instanceof ActedOnBehalfOf) {
            ((ActedOnBehalfOf) description).setOnBehalfOf (agent);
        }
        if (description instanceof WasAssociatedWith) {
            ((WasAssociatedWith) description).setResponsible (agent);
        }
        if (description instanceof WasAttributedTo) {
            ((WasAttributedTo) description).setAttributedTo (agent);
        }
        if (description instanceof WasInfluencedBy) {
            ((WasInfluencedBy) description).setInfluencer (agent);
        }
    }

    public static void setEntity (Description description, Entity entity) {
        if (description instanceof AlternateOf) {
            ((AlternateOf) description).setAlternateB (entity);
        }
        if (description instanceof HadMember) {
            ((HadMember) description).setMember (entity);
        }
        if (description instanceof SpecializationOf) {
            ((SpecializationOf) description).setGeneralEntity (entity);
        }
        if (description instanceof Used) {
            ((Used) description).setUsed (entity);
        }
        if (description instanceof WasAssociatedWith) {
            ((WasAssociatedWith) description).setPlan (entity);
        }
        if (description instanceof WasDerivedFrom) {
            ((WasDerivedFrom) description).setDerivedFrom (entity);
        }
        if (description instanceof WasEndedBy) {
            ((WasEndedBy) description).setTrigger (entity);
        }
        if (description instanceof WasStartedBy) {
            ((WasStartedBy) description).setTrigger (entity);
        }
        if (description instanceof WasInfluencedBy) {
            ((WasInfluencedBy) description).setInfluencer (entity);
        }
    }

    public static void setInfluenceable (Description description, Influenceable influenceable) {
        if (description instanceof WasInfluencedBy) {
            ((WasInfluencedBy) description).setInfluencer (influenceable);
        }
    }

    public static void setLocation (Description description, Location location) {
        if (description instanceof Activity) {
            ((Activity) description).setLocation (location);
        }
        if (description instanceof Agent) {
            ((Agent) description).setLocation (location);
        }
        if (description instanceof Entity) {
            ((Entity) description).setLocation (location);
        }
        if (description instanceof InstantaneousEvent) {
            ((InstantaneousEvent) description).setLocation (location);
        }
    }

    public static void setRole (Description description, Role role) {
        if (description instanceof InstantaneousEvent) {
            ((InstantaneousEvent) description).setRole (role);
        }
        if (description instanceof WasAssociatedWith) {
            ((WasAssociatedWith) description).setRole (role);
        }
    }

    public static void setSubject (Description relation, Description subject) {
        if (relation instanceof ActedOnBehalfOf) {
            ((ActedOnBehalfOf) relation).setActer ((Agent) subject);
        }
        if (relation instanceof Used) {
            ((Used) relation).setUser ((Activity) subject);
        }
        if (relation instanceof WasAssociatedWith) {
            ((WasAssociatedWith) relation).setResponsibleFor ((Activity) subject);
        }
        if (relation instanceof WasAttributedTo) {
            ((WasAttributedTo) relation).setAttributed ((Entity) subject);
        }
        if (relation instanceof WasDerivedFrom) {
            ((WasDerivedFrom) relation).setDerived ((Entity) subject);
        }
        if (relation instanceof WasEndedBy) {
            ((WasEndedBy) relation).setEnded ((Activity) subject);
        }
        if (relation instanceof WasGeneratedBy) {
            ((WasGeneratedBy) relation).setGenerated ((Entity) subject);
        }
        if (relation instanceof WasInformedBy) {
            ((WasInformedBy) relation).setInformed ((Activity) subject);
        }
        if (relation instanceof WasInvalidatedBy) {
            ((WasInvalidatedBy) relation).setInvalidated ((Entity) subject);
        }
        if (relation instanceof WasStartedBy) {
            ((WasStartedBy) relation).setStarted ((Activity) subject);
        }
    }
}

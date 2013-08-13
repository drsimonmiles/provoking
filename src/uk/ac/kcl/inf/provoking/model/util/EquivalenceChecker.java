package uk.ac.kcl.inf.provoking.model.util;

import java.util.Date;
import java.util.Objects;
import uk.ac.kcl.inf.provoking.model.*;

public class EquivalenceChecker {
    protected boolean bothNull (Object object1, Object object2) {
        return object1 == null && object2 == null;
    }

    public boolean equivalentDocument (Document document1, Document document2) {
        return bothNull (document1, document2)
               || (neitherNull (document1, document2)
                   && document1.size () == document2.size ()
                   && containsEquivalents (document1, document2)
                   && containsEquivalents (document2, document1));
    }

    public boolean equivalentDescription (Description description1, Description description2) {
        if (bothNull (description1, description2)) {
            return true;
        }
        if (!neitherNull (description1, description2)) {
            return false;
        }
        if (!description1.getClass ().equals (description2.getClass ())) {
            return false;
        }
        // Check sub-type of Collection before Collection
        if (description1 instanceof EmptyCollection) {
            return equivalentEmptyCollection ((EmptyCollection) description1, (EmptyCollection) description2);
        }
        // Check sub-types of Entity before Entity
        if (description1 instanceof Bundle) {
            return equivalentBundle ((Bundle) description1, (Bundle) description2);
        }
        if (description1 instanceof Collection) {
            return equivalentCollection ((Collection) description1, (Collection) description2);
        }
        // Check sub-types of WasDerivedFrom before WasDerivedFrom
        if (description1 instanceof HadPrimarySource) {
            return equivalentHadPrimarySource ((HadPrimarySource) description1, (HadPrimarySource) description2);
        }
        if (description1 instanceof WasQuotedFrom) {
            return equivalentWasQuotedFrom ((WasQuotedFrom) description1, (WasQuotedFrom) description2);
        }
        if (description1 instanceof WasRevisionOf) {
            return equivalentWasRevisionOf ((WasRevisionOf) description1, (WasRevisionOf) description2);
        }
        // Check sub-types of Agent before Agent
        if (description1 instanceof Organization) {
            return equivalentOrganization ((Organization) description1, (Organization) description2);
        }
        if (description1 instanceof Person) {
            return equivalentPerson ((Person) description1, (Person) description2);
        }
        if (description1 instanceof SoftwareAgent) {
            return equivalentSoftwareAgent ((SoftwareAgent) description1, (SoftwareAgent) description2);
        }
        // Check the remaining description types
        if (description1 instanceof ActedOnBehalfOf) {
            return equivalentActedOnBehalfOf ((ActedOnBehalfOf) description1, (ActedOnBehalfOf) description2);
        }
        if (description1 instanceof Activity) {
            return equivalentActivity ((Activity) description1, (Activity) description2);
        }
        if (description1 instanceof Agent) {
            return equivalentAgent ((Agent) description1, (Agent) description2);
        }
        if (description1 instanceof AlternateOf) {
            return equivalentAlternateOf ((AlternateOf) description1, (AlternateOf) description2);
        }
        if (description1 instanceof Entity) {
            return equivalentEntity ((Entity) description1, (Entity) description2);
        }
        if (description1 instanceof HadMember) {
            return equivalentHadMember ((HadMember) description1, (HadMember) description2);
        }
        if (description1 instanceof Location) {
            return equivalentLocation ((Location) description1, (Location) description2);
        }
        if (description1 instanceof Role) {
            return equivalentRole ((Role) description1, (Role) description2);
        }
        if (description1 instanceof SpecializationOf) {
            return equivalentSpecializationOf ((SpecializationOf) description1, (SpecializationOf) description2);
        }
        if (description1 instanceof Used) {
            return equivalentUsed ((Used) description1, (Used) description2);
        }
        if (description1 instanceof WasAssociatedWith) {
            return equivalentWasAssociatedWith ((WasAssociatedWith) description1, (WasAssociatedWith) description2);
        }
        if (description1 instanceof WasAttributedTo) {
            return equivalentWasAttributedTo ((WasAttributedTo) description1, (WasAttributedTo) description2);
        }
        if (description1 instanceof WasDerivedFrom) {
            return equivalentWasDerivedFrom ((WasDerivedFrom) description1, (WasDerivedFrom) description2);
        }
        if (description1 instanceof WasEndedBy) {
            return equivalentWasEndedBy ((WasEndedBy) description1, (WasEndedBy) description2);
        }
        if (description1 instanceof WasInformedBy) {
            return equivalentWasInformedBy ((WasInformedBy) description1, (WasInformedBy) description2);
        }
        if (description1 instanceof WasInvalidatedBy) {
            return equivalentWasInvalidatedBy ((WasInvalidatedBy) description1, (WasInvalidatedBy) description2);
        }
        if (description1 instanceof WasGeneratedBy) {
            return equivalentWasGeneratedBy ((WasGeneratedBy) description1, (WasGeneratedBy) description2);
        }
        if (description1 instanceof WasStartedBy) {
            return equivalentWasStartedBy ((WasStartedBy) description1, (WasStartedBy) description2);
        }
        if (description1 instanceof WasInfluencedBy) {
            return equivalentWasInfluencedBy ((WasInfluencedBy) description1, (WasInfluencedBy) description2);
        }
        throw new RuntimeException ("Attempted to find equivalence of unknown type of Description: " + description1.getClass ().getSimpleName ());
    }

    public boolean equivalentAttributes (AttributeHolder description1, AttributeHolder description2) {
        return bothNull (description1, description2)
               || (neitherNull (description1, description2)
                   && description1.getAttributes ().equals (description2.getAttributes ()));
    }

    public boolean equivalentIdentifiers (Identified description1, Identified description2) {
        return bothNull (description1, description2)
               || (neitherNull (description1, description2)
                   && description1.isReference () == description2.isReference ()
                   && Objects.equals (description1.getIdentifier (), description2.getIdentifier ()));
    }

    public boolean equivalentEventArguments (InstantaneousEvent event1, InstantaneousEvent event2) {
        return bothNull (event1, event2)
               || (neitherNull (event1, event2)
                   && equivalentLocation (event1.getLocation (), event2.getLocation ())
                   && equivalentTime (event1.getTime (), event2.getTime ())
                   && equivalentRole (event1.getRole (), event2.getRole ()));
    }

    protected boolean containsEquivalents (Iterable<Description> container, Iterable<Description> comparison) {
        boolean found;

        for (Description description1 : container) {
            found = false;
            for (Description description2 : comparison) {
                if (equivalentDescription (description1, description2)) {
                    found = true;
                }
            }
            if (!found) {
                return false;
            }
        }

        return true;
    }

    public boolean equivalentActedOnBehalfOf (ActedOnBehalfOf description1, ActedOnBehalfOf description2) {
        return bothNull (description1, description2)
               || (neitherNull (description1, description2)
                   && equivalentIdentifiers (description1, description2)
                   && equivalentAttributes (description1, description2)
                   && equivalentAgent (description1.getActer (), description2.getActer ())
                   && equivalentAgent (description1.getOnBehalfOf (), description2.getOnBehalfOf ())
                   && equivalentActivity (description1.getActivity (), description2.getActivity ()));
    }

    public boolean equivalentActivity (Activity description1, Activity description2) {
        return bothNull (description1, description2)
               || (neitherNull (description1, description2)
                   && equivalentIdentifiers (description1, description2)
                   && equivalentAttributes (description1, description2)
                   && equivalentLocation (description1.getLocation (), description2.getLocation ())
                   && equivalentTime (description1.getStartedAt (), description2.getStartedAt ())
                   && equivalentTime (description1.getEndedAt (), description2.getEndedAt ()));
    }

    public boolean equivalentAgent (Agent description1, Agent description2) {
        return bothNull (description1, description2)
               || (neitherNull (description1, description2)
                   && equivalentIdentifiers (description1, description2)
                   && equivalentAttributes (description1, description2)
                   && equivalentLocation (description1.getLocation (), description2.getLocation ()));
    }

    public boolean equivalentAlternateOf (AlternateOf description1, AlternateOf description2) {
        return bothNull (description1, description2)
               || (neitherNull (description1, description2)
                   && equivalentEntity (description1.getAlternateA (), description2.getAlternateA ())
                   && equivalentEntity (description1.getAlternateB (), description2.getAlternateB ()));
    }

    public boolean equivalentBundle (Bundle description1, Bundle description2) {
        return bothNull (description1, description2)
               || (neitherNull (description1, description2)
                   && equivalentEntity (description1, description2)
                   && equivalentDocument (description1.getDescriptions (), description2.getDescriptions ()));
    }

    public boolean equivalentCollection (Collection description1, Collection description2) {
        return bothNull (description1, description2)
               || (neitherNull (description1, description2)
                   && equivalentEntity (description1, description2));
    }

    public boolean equivalentEmptyCollection (EmptyCollection description1, EmptyCollection description2) {
        return bothNull (description1, description2)
               || (neitherNull (description1, description2)
                   && equivalentCollection (description1, description2));
    }

    public boolean equivalentEntity (Entity description1, Entity description2) {
        return bothNull (description1, description2)
               || (neitherNull (description1, description2)
                   && equivalentIdentifiers (description1, description2)
                   && equivalentAttributes (description1, description2)
                   && equivalentLocation (description1.getLocation (), description2.getLocation ()));
    }

    public boolean equivalentHadMember (HadMember description1, HadMember description2) {
        return bothNull (description1, description2)
               || (neitherNull (description1, description2)
                   && equivalentEntity (description1.getCollection (), description2.getCollection ())
                   && equivalentEntity (description1.getMember (), description2.getMember ()));
    }

    public boolean equivalentHadPrimarySource (HadPrimarySource description1, HadPrimarySource description2) {
        return bothNull (description1, description2)
               || (neitherNull (description1, description2)
                   && equivalentWasDerivedFrom (description1, description2));
    }
    
    public boolean equivalentLocation (Location description1, Location description2) {
        return bothNull (description1, description2)
               || (neitherNull (description1, description2)
                   && equivalentAttributes (description1, description2)
                   && equivalentIdentifiers (description1, description2));
    }

    public boolean equivalentOrganization (Organization description1, Organization description2) {
        return bothNull (description1, description2)
               || (neitherNull (description1, description2)
                   && equivalentAgent (description1, description2));
    }
    
    public boolean equivalentPerson (Person description1, Person description2) {
        return bothNull (description1, description2)
               || (neitherNull (description1, description2)
                   && equivalentAgent (description1, description2));
    }

    public boolean equivalentRole (Role description1, Role description2) {
        return bothNull (description1, description2)
               || (neitherNull (description1, description2)
                   && equivalentAttributes (description1, description2)
                   && equivalentIdentifiers (description1, description2));
    }

    public boolean equivalentSoftwareAgent (SoftwareAgent description1, SoftwareAgent description2) {
        return bothNull (description1, description2)
               || (neitherNull (description1, description2)
                   && equivalentAgent (description1, description2));
    }
    
    public boolean equivalentSpecializationOf (SpecializationOf description1, SpecializationOf description2) {
        return bothNull (description1, description2)
               || (neitherNull (description1, description2)
                   && equivalentEntity (description1.getGeneralEntity (), description2.getGeneralEntity ())
                   && equivalentEntity (description1.getSpecificEntity (), description2.getSpecificEntity ()));
    }

    public boolean equivalentTime (Date time1, Date time2) {
        return bothNull (time1, time2)
               || (neitherNull (time1, time2)
                   && time1.equals (time2));
    }    

    public boolean equivalentUsed (Used description1, Used description2) {
        return bothNull (description1, description2)
               || (neitherNull (description1, description2)
                   && equivalentIdentifiers (description1, description2)
                   && equivalentAttributes (description1, description2)
                   && equivalentEventArguments (description1, description2)
                   && equivalentEntity (description1.getUsed (), description2.getUsed ())
                   && equivalentActivity (description1.getUser (), description2.getUser ()));
    }

    public boolean equivalentWasAssociatedWith (WasAssociatedWith description1, WasAssociatedWith description2) {
        return bothNull (description1, description2)
               || (neitherNull (description1, description2)
                   && equivalentIdentifiers (description1, description2)
                   && equivalentAttributes (description1, description2)
                   && equivalentAgent (description1.getResponsible (), description2.getResponsible ())
                   && equivalentEntity (description1.getPlan (), description2.getPlan ())
                   && equivalentActivity (description1.getResponsibleFor (), description2.getResponsibleFor ())
                   && equivalentRole (description1.getRole (), description2.getRole ()));
    }

    public boolean equivalentWasAttributedTo (WasAttributedTo description1, WasAttributedTo description2) {
        return bothNull (description1, description2)
               || (neitherNull (description1, description2)
                   && equivalentIdentifiers (description1, description2)
                   && equivalentAttributes (description1, description2)
                   && equivalentAgent (description1.getAttributedTo (), description2.getAttributedTo ())
                   && equivalentEntity (description1.getAttributed (), description2.getAttributed ()));
    }

    public boolean equivalentWasDerivedFrom (WasDerivedFrom description1, WasDerivedFrom description2) {
        return bothNull (description1, description2)
               || (neitherNull (description1, description2)
                   && equivalentIdentifiers (description1, description2)
                   && equivalentAttributes (description1, description2)
                   && equivalentEntity (description1.getDerived (), description2.getDerived ())
                   && equivalentEntity (description1.getDerivedFrom (), description2.getDerivedFrom ())
                   && equivalentActivity (description1.getDeriver (), description2.getDeriver ())
                   && equivalentWasGeneratedBy (description1.getGeneration (), description2.getGeneration ())
                   && equivalentUsed (description1.getUsage (), description2.getUsage ()));
    }
    
    public boolean equivalentWasEndedBy (WasEndedBy description1, WasEndedBy description2) {
        return bothNull (description1, description2)
               || (neitherNull (description1, description2)
                   && equivalentIdentifiers (description1, description2)
                   && equivalentAttributes (description1, description2)
                   && equivalentEventArguments (description1, description2)
                   && equivalentEntity (description1.getTrigger (), description2.getTrigger ())
                   && equivalentActivity (description1.getEnded (), description2.getEnded ())
                   && equivalentActivity (description1.getEnder (), description2.getEnder ()));
    }

    public boolean equivalentWasGeneratedBy (WasGeneratedBy description1, WasGeneratedBy description2) {
        return bothNull (description1, description2)
               || (neitherNull (description1, description2)
                   && equivalentIdentifiers (description1, description2)
                   && equivalentAttributes (description1, description2)
                   && equivalentEventArguments (description1, description2)
                   && equivalentEntity (description1.getGenerated (), description2.getGenerated ())
                   && equivalentActivity (description1.getGenerater (), description2.getGenerater ()));
    }
    
    public boolean equivalentWasInfluencedBy (WasInfluencedBy description1, WasInfluencedBy description2) {
        return bothNull (description1, description2)
               || (neitherNull (description1, description2)
                   && equivalentIdentifiers (description1, description2)
                   && equivalentAttributes (description1, description2)
                   && equivalentDescription (description1.getInfluenced (), description2.getInfluenced ())
                   && equivalentDescription (description1.getInfluencer (), description2.getInfluencer ()));
    }

    public boolean equivalentWasInformedBy (WasInformedBy description1, WasInformedBy description2) {
        return bothNull (description1, description2)
               || (neitherNull (description1, description2)
                   && equivalentIdentifiers (description1, description2)
                   && equivalentAttributes (description1, description2)
                   && equivalentActivity (description1.getInformed (), description2.getInformed ())
                   && equivalentActivity (description1.getInformer (), description2.getInformer ()));
    }

    public boolean equivalentWasInvalidatedBy (WasInvalidatedBy description1, WasInvalidatedBy description2) {
        return bothNull (description1, description2)
               || (neitherNull (description1, description2)
                   && equivalentIdentifiers (description1, description2)
                   && equivalentAttributes (description1, description2)
                   && equivalentEventArguments (description1, description2)
                   && equivalentEntity (description1.getInvalidated (), description2.getInvalidated ())
                   && equivalentActivity (description1.getInvalidater (), description2.getInvalidater ()));
    }

    public boolean equivalentWasQuotedFrom (WasQuotedFrom description1, WasQuotedFrom description2) {
        return bothNull (description1, description2)
               || (neitherNull (description1, description2)
                   && equivalentWasDerivedFrom (description1, description2));
    }

    public boolean equivalentWasRevisionOf (WasRevisionOf description1, WasRevisionOf description2) {
        return bothNull (description1, description2)
               || (neitherNull (description1, description2)
                   && equivalentWasDerivedFrom (description1, description2));
    }
    
    public boolean equivalentWasStartedBy (WasStartedBy description1, WasStartedBy description2) {
        return bothNull (description1, description2)
               || (neitherNull (description1, description2)
                   && equivalentIdentifiers (description1, description2)
                   && equivalentAttributes (description1, description2)
                   && equivalentEventArguments (description1, description2)
                   && equivalentEntity (description1.getTrigger (), description2.getTrigger ())
                   && equivalentActivity (description1.getStarted (), description2.getStarted ())
                   && equivalentActivity (description1.getStarter (), description2.getStarter ()));
    }

    protected boolean neitherNull (Object object1, Object object2) {
        return object1 != null && object2 != null;
    }
}

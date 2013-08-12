package uk.ac.kcl.inf.provoking.model.util;

import java.util.Objects;
import uk.ac.kcl.inf.provoking.model.*;

public class EquivalenceChecker {
    public boolean equivalentDocument (Document document1, Document document2) {
        return document1.size () == document2.size ()
                && containsEquivalents (document1, document2)
                && containsEquivalents (document2, document1);
    }

    public boolean equivalentDescription (Description description1, Description description2) {
        if (description1 == null && description2 == null) {
            return true;
        }
        if (description1 == null || description2 == null) {
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
            return equivalentPerson ((SoftwareAgent) description1, (SoftwareAgent) description2);
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
        return description1.getAttributes ().equals (description2.getAttributes ());
    }

    public boolean equivalentIdentifiers (Identified description1, Identified description2) {
        if (description1.hasIdentifier () != description2.hasIdentifier ()) {
            return false;
        }
        if (description1.isReference () != description2.isReference ()) {
            return false;
        }
        return Objects.equals (description1.getIdentifier (), description2.getIdentifier ());
    }

    public boolean equivalentEventArguments (InstantaneousEvent event1, InstantaneousEvent event2) {
        return equivalentLocation (event1.getLocation (), event2.getLocation ()) &&
                equivalentTime (event1.getTime (), event2.getTime ()) &&
                equivalentRole (event1.getRole (), event2.getRole ());
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
}

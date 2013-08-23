package uk.ac.kcl.inf.provoking.model.util;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import uk.ac.kcl.inf.provoking.model.*;

/**
 * This class implements methods for testing the equivalence of PROV documents
 * or individual PROV descriptions.
 *
 * Equivalence (equality) of provenance can have different criteria depending on
 * the application context. Therefore, this class should also be seen as a base
 * for other equivalence checkers to extend from. For example, this class
 * considers two entities without identifiers (and with the same attributes) are
 * equivalent, but this way be too lax for some contexts, as without
 * identifiers, we cannot know the entities represent the same thing.
 * Comparably, this class considers times to be the same if equal to the
 * millisecond (the Java default), but other applications may find this too
 * strict. Individual methods can be overridden in an extension of this class
 * while leaving others intact, so as to ease the process of defining a new
 * equivalence.
 *
 * @author Simon Miles
 */
public class EquivalenceChecker {
    /**
     * Convenience method for extending classes, returning true only if both
     * arguments are null.
     *
     * @param object1 First argument that may be null
     * @param object2 Second argument that may be null
     * @return True if both arguments are null, or false otherwise
     */
    protected boolean bothNull (Object object1, Object object2) {
        return object1 == null && object2 == null;
    }

    /**
     * Tests whether two documents are equivalent. They are equivalent if both
     * are null, or if neither are null, they are the same size, and for every
     * description in each document there is an equivalent description in the
     * other document. A list can provided in which to store all descriptions in
     * the first document found to have no equivalent in the other document.
     *
     * @param document1 One document to compare
     * @param document2 The other document to compare
     * @param inequivalences If non-null, descriptions with no found equivalent
     * are stored in this list
     * @return True only if the documents are equivalent
     */
    public boolean equivalentDocuments (Document document1, Document document2, List<Description> inequivalences) {
        if (bothNull (document1, document2)) {
            return true;
        }
        if (!neitherNull (document1, document2)) {
            return false;
        }
        if (inequivalences == null) { // Use the fast version if not looking for inequivalences
            return document1.size () == document2.size ()
                    && containsEquivalents (document1, document2, null)
                    && containsEquivalents (document2, document1, null);
        } else {
            boolean equivalent = true;

            if (document1.size () != document2.size ()) {
                equivalent = false;
            }
            if (!containsEquivalents (document1, document2, inequivalences)) {
                equivalent = false;
            }
            if (!containsEquivalents (document2, document1, inequivalences)) {
                equivalent = false;
            }

            return equivalent;
        }
    }

    /**
     * Tests whether two documents are equivalent. They are equivalent if both
     * are null, or if neither are null, they are the same size, and for every
     * description in each document there is an equivalent description in the
     * other document.
     *
     * @param document1 One document to compare
     * @param document2 The other document to compare
     * @return True only if the documents are equivalent
     */
    public boolean equivalentDocuments (Document document1, Document document2) {
        return equivalentDocuments (document1, document2, null);
    }

    /**
     * Tests whether two PROV descriptions are equivalent. They are equivalent
     * if both null, or neither null and of the same type and judged equivalent
     * by the specific method for that type.
     *
     * @param description1 One description to compare
     * @param description2 The other description to compare
     * @return True only if the descriptions are equivalent
     */
    public boolean equivalentDescriptions (Description description1, Description description2) {
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
            return equivalentActedOnBehalfOfs ((ActedOnBehalfOf) description1, (ActedOnBehalfOf) description2);
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

    /**
     * Tests whether two PROV descriptions have the same set of attributes
     * (without considering any other aspect of the descriptions). They are
     * equivalent if neither description is null, and the attribute sets are of
     * the same size, and all the elements of one set have an equal attribute
     * (same key and value) in the other (java.util.Set equality).
     *
     * @param description1 One description whose attributes to compare
     * @param description2 The other description whose attributes to compare
     * @return True only if the descriptions have the same set of attributes
     */
    public boolean equivalentAttributes (AttributeHolder description1, AttributeHolder description2) {
        return neitherNull (description1, description2)
                && description1.getAttributes ().equals (description2.getAttributes ());
    }

    /**
     * Tests whether two PROV descriptions have the same identifiers (without
     * considering any other aspect of the descriptions). They are equivalent if
     * neither description is null, the identifiers are either both or neither
     * references, and the identifiers are either both null or are equal
     * non-null values.
     *
     * @param description1 One description whose identifiers to compare
     * @param description2 The other description whose identifiers to compare
     * @return True only if the descriptions have equivalent identifiers
     */
    public boolean equivalentIdentifiers (Identified description1, Identified description2) {
        return neitherNull (description1, description2)
                && description1.isReference () == description2.isReference ()
                && Objects.equals (description1.getIdentifier (), description2.getIdentifier ());
    }

    /**
     * Tests whether two PROV descriptions denoting instantaneous events of some
     * kind have the same location, time and role (without considering any other
     * aspect of the descriptions). They are equivalent if neither description
     * is null, and the locations, times and roles are judged equivalent by the
     * respective specific methods.
     *
     * @param description1 One description whose time, location and role to
     * compare
     * @param description2 The other description whose time, location and role
     * to compare
     * @return True only if the descriptions have equivalent time, location and
     * role
     */
    public boolean equivalentEventArguments (InstantaneousEvent event1, InstantaneousEvent event2) {
        return neitherNull (event1, event2)
                && equivalentLocation (event1.getLocation (), event2.getLocation ())
                && equivalentTime (event1.getTime (), event2.getTime ())
                && equivalentRole (event1.getRole (), event2.getRole ());
    }

    /**
     * A convenience method which tests whether for each description in one
     * list, there is an equivalent in a second list. Equivalence of
     * descriptions is judged according to the equivalentDescriptions method.
     *
     * @param container The list of descriptions whose members should have
     * equivalents
     * @param comparison The list of descriptions in which to search for
     * equivalents
     * @param inequivalences If non-null, descriptions with no found equivalent
     * are stored in this list
     * @return True only if every member of the first list has an equivalent in
     * the second
     */
    protected boolean containsEquivalents (Iterable<Description> container, Iterable<Description> comparison, List<Description> inequivalences) {
        boolean failed = false;
        boolean found;

        for (Description description1 : container) {
            found = false;
            for (Description description2 : comparison) {
                if (equivalentDescriptions (description1, description2)) {
                    found = true;
                }
            }
            if (!found) {
                if (inequivalences == null) {
                    return false;
                } else {
                    failed = true;
                    inequivalences.add (description1);
                }
            }
        }

        return !failed;
    }

    /**
     * Tests whether two ActedOnBehalfOf objects are equivalent. They are
     * equivalent if both null, or neither null and have equivalent identifiers,
     * attributes, delegated and delegatee agents and delegated activities.
     *
     * @param description1 One description to compare
     * @param description2 The other description to compare
     * @return True only if the descriptions are equivalent
     */
    public boolean equivalentActedOnBehalfOfs (ActedOnBehalfOf description1, ActedOnBehalfOf description2) {
        return bothNull (description1, description2)
                || (neitherNull (description1, description2)
                && equivalentIdentifiers (description1, description2)
                && equivalentAttributes (description1, description2)
                && equivalentAgent (description1.getActer (), description2.getActer ())
                && equivalentAgent (description1.getOnBehalfOf (), description2.getOnBehalfOf ())
                && equivalentActivity (description1.getActivity (), description2.getActivity ()));
    }

    /**
     * Tests whether two Activity objects are equivalent. They are equivalent if
     * both null, or neither null and have equivalent identifiers, attributes,
     * locations, start and end times.
     *
     * @param description1 One description to compare
     * @param description2 The other description to compare
     * @return True only if the descriptions are equivalent
     */
    public boolean equivalentActivity (Activity description1, Activity description2) {
        return bothNull (description1, description2)
                || (neitherNull (description1, description2)
                && equivalentIdentifiers (description1, description2)
                && equivalentAttributes (description1, description2)
                && equivalentLocation (description1.getLocation (), description2.getLocation ())
                && equivalentTime (description1.getStartedAt (), description2.getStartedAt ())
                && equivalentTime (description1.getEndedAt (), description2.getEndedAt ()));
    }

    /**
     * Tests whether two Agent objects are equivalent. They are equivalent if
     * both null, or neither null and have equivalent identifiers, attributes,
     * and locations.
     *
     * @param description1 One description to compare
     * @param description2 The other description to compare
     * @return True only if the descriptions are equivalent
     */
    public boolean equivalentAgent (Agent description1, Agent description2) {
        return bothNull (description1, description2)
                || (neitherNull (description1, description2)
                && equivalentIdentifiers (description1, description2)
                && equivalentAttributes (description1, description2)
                && equivalentLocation (description1.getLocation (), description2.getLocation ()));
    }

    /**
     * Tests whether two AlternateOf objects are equivalent. They are equivalent
     * if both null, or neither null and have equivalent alternate entities. The
     * order of the alternate entities does not affect equivalence.
     *
     * @param description1 One description to compare
     * @param description2 The other description to compare
     * @return True only if the descriptions are equivalent
     */
    public boolean equivalentAlternateOf (AlternateOf description1, AlternateOf description2) {
        return bothNull (description1, description2)
                || (neitherNull (description1, description2)
                && (equivalentEntity (description1.getAlternateA (), description2.getAlternateA ())
                && equivalentEntity (description1.getAlternateB (), description2.getAlternateB ()))
                || (equivalentEntity (description1.getAlternateA (), description2.getAlternateB ())
                && equivalentEntity (description1.getAlternateB (), description2.getAlternateA ())));
    }

    /**
     * Tests whether two Bundle objects are equivalent. They are equivalent if
     * both null, or neither null and are equivalent entities, and contain
     * equivalent documents.
     *
     * @param description1 One description to compare
     * @param description2 The other description to compare
     * @return True only if the descriptions are equivalent
     */
    public boolean equivalentBundle (Bundle description1, Bundle description2) {
        return bothNull (description1, description2)
                || (neitherNull (description1, description2)
                && equivalentEntity (description1, description2)
                && equivalentDocuments (description1.getDescriptions (), description2.getDescriptions ()));
    }

    /**
     * Tests whether two Collection objects are equivalent. They are equivalent
     * if both null, or neither null and are equivalent entities.
     *
     * @param description1 One description to compare
     * @param description2 The other description to compare
     * @return True only if the descriptions are equivalent
     */
    public boolean equivalentCollection (Collection description1, Collection description2) {
        return bothNull (description1, description2)
                || (neitherNull (description1, description2)
                && equivalentEntity (description1, description2));
    }

    /**
     * Tests whether two EmptyCollection objects are equivalent. They are
     * equivalent if both null, or neither null and are equivalent collections.
     *
     * @param description1 One description to compare
     * @param description2 The other description to compare
     * @return True only if the descriptions are equivalent
     */
    public boolean equivalentEmptyCollection (EmptyCollection description1, EmptyCollection description2) {
        return bothNull (description1, description2)
                || (neitherNull (description1, description2)
                && equivalentCollection (description1, description2));
    }

    /**
     * Tests whether two Entity objects are equivalent. They are equivalent if
     * both null, or neither null and have equivalent identifiers, attributes
     * and locations.
     *
     * @param description1 One description to compare
     * @param description2 The other description to compare
     * @return True only if the descriptions are equivalent
     */
    public boolean equivalentEntity (Entity description1, Entity description2) {
        return bothNull (description1, description2)
                || (neitherNull (description1, description2)
                && equivalentIdentifiers (description1, description2)
                && equivalentAttributes (description1, description2)
                && equivalentLocation (description1.getLocation (), description2.getLocation ()));
    }

    /**
     * Tests whether two HadMember objects are equivalent. They are equivalent
     * if both null, or neither null and have equivalent container and member
     * entities.
     *
     * @param description1 One description to compare
     * @param description2 The other description to compare
     * @return True only if the descriptions are equivalent
     */
    public boolean equivalentHadMember (HadMember description1, HadMember description2) {
        return bothNull (description1, description2)
                || (neitherNull (description1, description2)
                && equivalentEntity (description1.getCollection (), description2.getCollection ())
                && equivalentEntity (description1.getMember (), description2.getMember ()));
    }

    /**
     * Tests whether two HadPrimarySource objects are equivalent. They are
     * equivalent if both null, or neither null and are equivalent derivations.
     *
     * @param description1 One description to compare
     * @param description2 The other description to compare
     * @return True only if the descriptions are equivalent
     */
    public boolean equivalentHadPrimarySource (HadPrimarySource description1, HadPrimarySource description2) {
        return bothNull (description1, description2)
                || (neitherNull (description1, description2)
                && equivalentWasDerivedFrom (description1, description2));
    }

    /**
     * Tests whether two Location objects are equivalent. They are equivalent if
     * both null, or neither null and have equivalent identifiers and
     * attributes.
     *
     * @param description1 One description to compare
     * @param description2 The other description to compare
     * @return True only if the descriptions are equivalent
     */
    public boolean equivalentLocation (Location description1, Location description2) {
        return bothNull (description1, description2)
                || (neitherNull (description1, description2)
                && equivalentAttributes (description1, description2)
                && equivalentIdentifiers (description1, description2));
    }

    /**
     * Tests whether two Organization objects are equivalent. They are
     * equivalent if both null, or neither null and are equivalent agents.
     *
     * @param description1 One description to compare
     * @param description2 The other description to compare
     * @return True only if the descriptions are equivalent
     */
    public boolean equivalentOrganization (Organization description1, Organization description2) {
        return bothNull (description1, description2)
                || (neitherNull (description1, description2)
                && equivalentAgent (description1, description2));
    }

    /**
     * Tests whether two Person objects are equivalent. They are equivalent if
     * both null, or neither null and are equivalent agents.
     *
     * @param description1 One description to compare
     * @param description2 The other description to compare
     * @return True only if the descriptions are equivalent
     */
    public boolean equivalentPerson (Person description1, Person description2) {
        return bothNull (description1, description2)
                || (neitherNull (description1, description2)
                && equivalentAgent (description1, description2));
    }

    /**
     * Tests whether two Role objects are equivalent. They are equivalent if
     * both null, or neither null and have equivalent identifiers and
     * attributes.
     *
     * @param description1 One description to compare
     * @param description2 The other description to compare
     * @return True only if the descriptions are equivalent
     */
    public boolean equivalentRole (Role description1, Role description2) {
        return bothNull (description1, description2)
                || (neitherNull (description1, description2)
                && equivalentAttributes (description1, description2)
                && equivalentIdentifiers (description1, description2));
    }

    /**
     * Tests whether two SoftwareAgent objects are equivalent. They are
     * equivalent if both null, or neither null and are equivalent agents.
     *
     * @param description1 One description to compare
     * @param description2 The other description to compare
     * @return True only if the descriptions are equivalent
     */
    public boolean equivalentSoftwareAgent (SoftwareAgent description1, SoftwareAgent description2) {
        return bothNull (description1, description2)
                || (neitherNull (description1, description2)
                && equivalentAgent (description1, description2));
    }

    /**
     * Tests whether two SpecializationOf objects are equivalent. They are
     * equivalent if both null, or neither null and have equivalent general and
     * specific entities.
     *
     * @param description1 One description to compare
     * @param description2 The other description to compare
     * @return True only if the descriptions are equivalent
     */
    public boolean equivalentSpecializationOf (SpecializationOf description1, SpecializationOf description2) {
        return bothNull (description1, description2)
                || (neitherNull (description1, description2)
                && equivalentEntity (description1.getGeneralEntity (), description2.getGeneralEntity ())
                && equivalentEntity (description1.getSpecificEntity (), description2.getSpecificEntity ()));
    }

    /**
     * Tests whether two times (Date objects) are equivalent. They are
     * equivalent if both null, or neither null and are equal to the millisecond
     * (as per Date's equals method).
     *
     * @param time1 One time to compare
     * @param time2 The other time to compare
     * @return True only if the times are equivalent
     */
    public boolean equivalentTime (Date time1, Date time2) {
        return bothNull (time1, time2)
                || (neitherNull (time1, time2)
                && time1.equals (time2));
    }

    /**
     * Tests whether two Used objects are equivalent. They are equivalent if
     * both null, or neither null and have equivalent identifiers, attributes,
     * used entities, user activities, times, roles, and locations.
     *
     * @param description1 One description to compare
     * @param description2 The other description to compare
     * @return True only if the descriptions are equivalent
     */
    public boolean equivalentUsed (Used description1, Used description2) {
        return bothNull (description1, description2)
                || (neitherNull (description1, description2)
                && equivalentIdentifiers (description1, description2)
                && equivalentAttributes (description1, description2)
                && equivalentEventArguments (description1, description2)
                && equivalentEntity (description1.getUsed (), description2.getUsed ())
                && equivalentActivity (description1.getUser (), description2.getUser ()));
    }

    /**
     * Tests whether two WasAssociatedWith objects are equivalent. They are
     * equivalent if both null, or neither null and have equivalent identifiers,
     * attributes, responsible agents, associated activities, plans, and roles.
     *
     * @param description1 One description to compare
     * @param description2 The other description to compare
     * @return True only if the descriptions are equivalent
     */
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

    /**
     * Tests whether two WasAttributedTo objects are equivalent. They are
     * equivalent if both null, or neither null and have equivalent identifiers,
     * attributes, responsible agents, and attributed entities.
     *
     * @param description1 One description to compare
     * @param description2 The other description to compare
     * @return True only if the descriptions are equivalent
     */
    public boolean equivalentWasAttributedTo (WasAttributedTo description1, WasAttributedTo description2) {
        return bothNull (description1, description2)
                || (neitherNull (description1, description2)
                && equivalentIdentifiers (description1, description2)
                && equivalentAttributes (description1, description2)
                && equivalentAgent (description1.getAttributedTo (), description2.getAttributedTo ())
                && equivalentEntity (description1.getAttributed (), description2.getAttributed ()));
    }

    /**
     * Tests whether two WasDerivedFrom objects are equivalent. They are
     * equivalent if both null, or neither null and have equivalent identifiers,
     * attributes, derived entities, deriving entities, deriving activities,
     * generation events and usage events.
     *
     * @param description1 One description to compare
     * @param description2 The other description to compare
     * @return True only if the descriptions are equivalent
     */
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

    /**
     * Tests whether two WasEndedBy objects are equivalent. They are equivalent
     * if both null, or neither null and have equivalent identifiers,
     * attributes, triggering entities, ended activities, ender activities,
     * times, locations and roles.
     *
     * @param description1 One description to compare
     * @param description2 The other description to compare
     * @return True only if the descriptions are equivalent
     */
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

    /**
     * Tests whether two WasGeneratedBy objects are equivalent. They are
     * equivalent if both null, or neither null and have equivalent identifiers,
     * attributes, generated entities, generating activities, times, locations
     * and roles.
     *
     * @param description1 One description to compare
     * @param description2 The other description to compare
     * @return True only if the descriptions are equivalent
     */
    public boolean equivalentWasGeneratedBy (WasGeneratedBy description1, WasGeneratedBy description2) {
        return bothNull (description1, description2)
                || (neitherNull (description1, description2)
                && equivalentIdentifiers (description1, description2)
                && equivalentAttributes (description1, description2)
                && equivalentEventArguments (description1, description2)
                && equivalentEntity (description1.getGenerated (), description2.getGenerated ())
                && equivalentActivity (description1.getGenerater (), description2.getGenerater ()));
    }

    /**
     * Tests whether two WasInfluencedBy objects are equivalent. They are
     * equivalent if both null, or neither null and have equivalent identifiers,
     * attributes, influencing and influenced descriptions.
     *
     * @param description1 One description to compare
     * @param description2 The other description to compare
     * @return True only if the descriptions are equivalent
     */
    public boolean equivalentWasInfluencedBy (WasInfluencedBy description1, WasInfluencedBy description2) {
        return bothNull (description1, description2)
                || (neitherNull (description1, description2)
                && equivalentIdentifiers (description1, description2)
                && equivalentAttributes (description1, description2)
                && equivalentDescriptions (description1.getInfluenced (), description2.getInfluenced ())
                && equivalentDescriptions (description1.getInfluencer (), description2.getInfluencer ()));
    }

    /**
     * Tests whether two WasInformedBy objects are equivalent. They are
     * equivalent if both null, or neither null and have equivalent identifiers,
     * attributes, informing and informed activities.
     *
     * @param description1 One description to compare
     * @param description2 The other description to compare
     * @return True only if the descriptions are equivalent
     */
    public boolean equivalentWasInformedBy (WasInformedBy description1, WasInformedBy description2) {
        return bothNull (description1, description2)
                || (neitherNull (description1, description2)
                && equivalentIdentifiers (description1, description2)
                && equivalentAttributes (description1, description2)
                && equivalentActivity (description1.getInformed (), description2.getInformed ())
                && equivalentActivity (description1.getInformer (), description2.getInformer ()));
    }

    /**
     * Tests whether two WasGeneratedBy objects are equivalent. They are
     * equivalent if both null, or neither null and have equivalent identifiers,
     * attributes, invalidated entities, invalidating activities, times,
     * locations and roles.
     *
     * @param description1 One description to compare
     * @param description2 The other description to compare
     * @return True only if the descriptions are equivalent
     */
    public boolean equivalentWasInvalidatedBy (WasInvalidatedBy description1, WasInvalidatedBy description2) {
        return bothNull (description1, description2)
                || (neitherNull (description1, description2)
                && equivalentIdentifiers (description1, description2)
                && equivalentAttributes (description1, description2)
                && equivalentEventArguments (description1, description2)
                && equivalentEntity (description1.getInvalidated (), description2.getInvalidated ())
                && equivalentActivity (description1.getInvalidater (), description2.getInvalidater ()));
    }

    /**
     * Tests whether two WasQuotedFrom objects are equivalent. They are
     * equivalent if both null, or neither null and are equivalent derivations.
     *
     * @param description1 One description to compare
     * @param description2 The other description to compare
     * @return True only if the descriptions are equivalent
     */
    public boolean equivalentWasQuotedFrom (WasQuotedFrom description1, WasQuotedFrom description2) {
        return bothNull (description1, description2)
                || (neitherNull (description1, description2)
                && equivalentWasDerivedFrom (description1, description2));
    }

    /**
     * Tests whether two WasRevisionOf objects are equivalent. They are
     * equivalent if both null, or neither null and are equivalent derivations.
     *
     * @param description1 One description to compare
     * @param description2 The other description to compare
     * @return True only if the descriptions are equivalent
     */
    public boolean equivalentWasRevisionOf (WasRevisionOf description1, WasRevisionOf description2) {
        return bothNull (description1, description2)
                || (neitherNull (description1, description2)
                && equivalentWasDerivedFrom (description1, description2));
    }

    /**
     * Tests whether two WasStartedBy objects are equivalent. They are
     * equivalent if both null, or neither null and have equivalent identifiers,
     * attributes, triggering entities, started activities, starter activities,
     * times, locations and roles.
     *
     * @param description1 One description to compare
     * @param description2 The other description to compare
     * @return True only if the descriptions are equivalent
     */
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

    /**
     * Convenience method that tests whether neither of the given objects are
     * null.
     *
     * @param object1 First object to test
     * @param object2 Second object to test
     * @return True only if neither object is null
     */
    protected boolean neitherNull (Object object1, Object object2) {
        return object1 != null && object2 != null;
    }
}

package uk.ac.kcl.inf.provoking.serialise.rdf;

import uk.ac.kcl.inf.provoking.model.ActedOnBehalfOf;
import uk.ac.kcl.inf.provoking.model.Description;
import uk.ac.kcl.inf.provoking.model.Used;
import uk.ac.kcl.inf.provoking.model.WasAssociatedWith;
import uk.ac.kcl.inf.provoking.model.WasAttributedTo;
import uk.ac.kcl.inf.provoking.model.WasDerivedFrom;
import uk.ac.kcl.inf.provoking.model.WasEndedBy;
import uk.ac.kcl.inf.provoking.model.WasGeneratedBy;
import uk.ac.kcl.inf.provoking.model.WasInfluencedBy;
import uk.ac.kcl.inf.provoking.model.WasInformedBy;
import uk.ac.kcl.inf.provoking.model.WasInvalidatedBy;
import uk.ac.kcl.inf.provoking.model.WasStartedBy;
import uk.ac.kcl.inf.provoking.model.util.EquivalenceChecker;

/**
 * An equivalence checker which judges relation descriptions to be equivalent if
 * their domain and range as binary relations are equivalent, ignoring other arguments.
 * This is used for testing whether a deserialised binary relation and a deserialised
 * qualified relation are representing the same relation.
 * 
 * @author Simon Miles
 */
public class EquivalentRelationsChecker extends EquivalenceChecker {
    /**
     * Tests whether two ActedOnBehalfOf objects are equivalent as binary relations,
     * ignoring other arguments.
     *
     * @param description1 One description to compare
     * @param description2 The other description to compare
     * @return True only if the descriptions are equivalent
     */
    @Override
    public boolean equivalentActedOnBehalfOfs (ActedOnBehalfOf description1, ActedOnBehalfOf description2) {
        return neitherNull (description1, description2)
                   && equivalentAgents (description1.getActer (), description2.getActer ())
                   && equivalentAgents (description1.getOnBehalfOf (), description2.getOnBehalfOf ());
    }

    /**
     * Tests whether two Used objects are equivalent as binary relations, ignoring
     * other arguments.
     *
     * @param description1 One description to compare
     * @param description2 The other description to compare
     * @return True only if the descriptions are equivalent
     */
    @Override
    public boolean equivalentUseds (Used description1, Used description2) {
        return neitherNull (description1, description2)
                   && equivalentEntities (description1.getUsed (), description2.getUsed ())
                   && equivalentActivities (description1.getUser (), description2.getUser ());
    }

    /**
     * Tests whether two WasAssociatedWith objects are equivalent as binary relations, ignoring other arguments.
     *
     * @param description1 One description to compare
     * @param description2 The other description to compare
     * @return True only if the descriptions are equivalent
     */
    @Override
    public boolean equivalentWasAssociatedWiths (WasAssociatedWith description1, WasAssociatedWith description2) {
        return neitherNull (description1, description2)
                   && equivalentAgents (description1.getResponsible (), description2.getResponsible ())
                   && equivalentActivities (description1.getResponsibleFor (), description2.getResponsibleFor ());
    }

    /**
     * Tests whether two WasAttributedTo objects are equivalent as binary relations, ignoring other arguments.
     *
     * @param description1 One description to compare
     * @param description2 The other description to compare
     * @return True only if the descriptions are equivalent
     */
    @Override
    public boolean equivalentWasAttributedTos (WasAttributedTo description1, WasAttributedTo description2) {
        return neitherNull (description1, description2)
                   && equivalentAgents (description1.getAttributedTo (), description2.getAttributedTo ())
                   && equivalentEntities (description1.getAttributed (), description2.getAttributed ());
    }

    /**
     * Tests whether two WasDerivedFrom objects are equivalent as binary relations, ignoring other arguments.
     *
     * @param description1 One description to compare
     * @param description2 The other description to compare
     * @return True only if the descriptions are equivalent
     */
    @Override
    public boolean equivalentWasDerivedFroms (WasDerivedFrom description1, WasDerivedFrom description2) {
        return neitherNull (description1, description2)
                   && equivalentEntities (description1.getDerived (), description2.getDerived ())
                   && equivalentEntities (description1.getDerivedFrom (), description2.getDerivedFrom ());
    }

    /**
     * Tests whether two WasEndedBy objects are equivalent as binary relations, ignoring other arguments.
     *
     * @param description1 One description to compare
     * @param description2 The other description to compare
     * @return True only if the descriptions are equivalent
     */
    @Override
    public boolean equivalentWasEndedBys (WasEndedBy description1, WasEndedBy description2) {
        return neitherNull (description1, description2)
                   && equivalentActivities (description1.getEnded (), description2.getEnded ())
                   && equivalentActivities (description1.getEnder (), description2.getEnder ());
    }

    /**
     * Tests whether two WasGeneratedBy objects are equivalent as binary relations, ignoring other arguments.
     *
     * @param description1 One description to compare
     * @param description2 The other description to compare
     * @return True only if the descriptions are equivalent
     */
    public boolean equivalentWasGeneratedBys (WasGeneratedBy description1, WasGeneratedBy description2) {
        return neitherNull (description1, description2)
                   && equivalentEntities (description1.getGenerated (), description2.getGenerated ())
                   && equivalentActivities (description1.getGenerater (), description2.getGenerater ());
    }

    /**
     * Tests whether two WasInfluencedBy objects are equivalent as binary relations, ignoring other arguments.
     *
     * @param description1 One description to compare
     * @param description2 The other description to compare
     * @return True only if the descriptions are equivalent
     */
    @Override
    public boolean equivalentWasInfluencedBys (WasInfluencedBy description1, WasInfluencedBy description2) {
        return neitherNull (description1, description2)
                && equivalentDescriptions ((Description) description1.getInfluenced (), (Description) description2.getInfluenced ())
                && equivalentDescriptions ((Description) description1.getInfluencer (), (Description) description2.getInfluencer ());
    }

    /**
     * Tests whether two WasInformedBy objects are equivalent as binary relations, ignoring other arguments.
     *
     * @param description1 One description to compare
     * @param description2 The other description to compare
     * @return True only if the descriptions are equivalent
     */
    @Override
    public boolean equivalentWasInformedBys (WasInformedBy description1, WasInformedBy description2) {
        return neitherNull (description1, description2)
                   && equivalentActivities (description1.getInformed (), description2.getInformed ())
                   && equivalentActivities (description1.getInformer (), description2.getInformer ());
    }

    /**
     * Tests whether two WasGeneratedBy objects are equivalent as binary relations, ignoring other arguments.
     *
     * @param description1 One description to compare
     * @param description2 The other description to compare
     * @return True only if the descriptions are equivalent
     */
    @Override
    public boolean equivalentWasInvalidatedBys (WasInvalidatedBy description1, WasInvalidatedBy description2) {
        return neitherNull (description1, description2)
                   && equivalentEntities (description1.getInvalidated (), description2.getInvalidated ())
                   && equivalentActivities (description1.getInvalidater (), description2.getInvalidater ());
    }

    /**
     * Tests whether two WasStartedBy objects are equivalent as binary relations, ignoring other arguments.
     *
     * @param description1 One description to compare
     * @param description2 The other description to compare
     * @return True only if the descriptions are equivalent
     */
    @Override
    public boolean equivalentWasStartedBys (WasStartedBy description1, WasStartedBy description2) {
        return neitherNull (description1, description2)
                   && equivalentActivities (description1.getStarted (), description2.getStarted ())
                   && equivalentActivities (description1.getStarter (), description2.getStarter ());
    }
}

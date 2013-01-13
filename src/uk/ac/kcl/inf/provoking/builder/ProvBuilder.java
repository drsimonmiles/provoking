package uk.ac.kcl.inf.provoking.builder;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.xml.bind.DatatypeConverter;
import uk.ac.kcl.inf.provoking.model.ActedOnBehalfOf;
import uk.ac.kcl.inf.provoking.model.Activity;
import uk.ac.kcl.inf.provoking.model.Agent;
import uk.ac.kcl.inf.provoking.model.AlternateOf;
import uk.ac.kcl.inf.provoking.model.Bundle;
import uk.ac.kcl.inf.provoking.model.Collection;
import uk.ac.kcl.inf.provoking.model.Description;
import uk.ac.kcl.inf.provoking.model.Document;
import uk.ac.kcl.inf.provoking.model.EmptyCollection;
import uk.ac.kcl.inf.provoking.model.Entity;
import uk.ac.kcl.inf.provoking.model.HadMember;
import uk.ac.kcl.inf.provoking.model.HadPrimarySource;
import uk.ac.kcl.inf.provoking.model.Organization;
import uk.ac.kcl.inf.provoking.model.Person;
import uk.ac.kcl.inf.provoking.model.Plan;
import uk.ac.kcl.inf.provoking.model.SoftwareAgent;
import uk.ac.kcl.inf.provoking.model.SpecializationOf;
import uk.ac.kcl.inf.provoking.model.Used;
import uk.ac.kcl.inf.provoking.model.WasAssociatedWith;
import uk.ac.kcl.inf.provoking.model.WasAttributedTo;
import uk.ac.kcl.inf.provoking.model.WasDerivedFrom;
import uk.ac.kcl.inf.provoking.model.WasEndedBy;
import uk.ac.kcl.inf.provoking.model.WasGeneratedBy;
import uk.ac.kcl.inf.provoking.model.WasInformedBy;
import uk.ac.kcl.inf.provoking.model.WasInvalidatedBy;
import uk.ac.kcl.inf.provoking.model.WasQuotedFrom;
import uk.ac.kcl.inf.provoking.model.WasRevisionOf;
import uk.ac.kcl.inf.provoking.model.WasStartedBy;
import uk.ac.kcl.inf.provoking.model.util.AttributeHolder;
import uk.ac.kcl.inf.provoking.model.util.IDGenerator;
import uk.ac.kcl.inf.provoking.model.util.Identified;
import uk.ac.kcl.inf.provoking.model.util.Term;
import uk.ac.kcl.inf.provoking.model.util.UniqueIDGenerator;
import uk.ac.kcl.inf.provoking.model.util.UniqueURIGenerator;
import uk.ac.kcl.inf.provoking.serialise.SerialisationHint;
import static uk.ac.kcl.inf.provoking.serialise.SerialisationHintType.*;

public class ProvBuilder {
    private Document _document;
    private Description _current, _prior;
    private List<AbbreviationResolver> _resolvers;
    private Map<String, Description> _bookmarks;
    private IDGenerator _idgen;
    private boolean _bookmarksAsLabels;

    public ProvBuilder (Document addToExisting, IDGenerator idgen) {
        _document = addToExisting;
        _current = null;
        _prior = null;
        _resolvers = new LinkedList<> ();
        _bookmarks = new HashMap<> ();
        _idgen = idgen;
        _bookmarksAsLabels = false;
        setPrefix ("prov:", Term.PROV_NS);
        recogniseURIScheme ("http");
        recogniseURIScheme ("mailto");
        recogniseURIScheme ("urn");
    }

    public ProvBuilder (IDGenerator idgen) {
        this (new Document (), idgen);
    }

    public ProvBuilder (Document addToExisting, String idStart) {
        this (addToExisting, new UniqueIDGenerator (idStart));
    }

    public ProvBuilder (String idStart) {
        this (new UniqueIDGenerator (idStart));
    }

    public ProvBuilder (String prefix, String idVocabulary) {
        this (new UniqueURIGenerator (idVocabulary));
        setPrefix (prefix, idVocabulary);
    }

    public ProvBuilder (Document addToExisting) {
        this (addToExisting, "");
    }

    public ProvBuilder () {
        this ("");
    }

    public ProvBuilder actedOnBehalfOf (String... attributes) throws ProvBuildException {
        return store (new ActedOnBehalfOf (idgen ("ActedOnBehalfOf", attributes), getAgent ("actedOnBehalfOf"), null), true, attributes);
    }

    public ProvBuilder activity (Date started, Date ended, String... attributes) throws ProvBuildException {
        return addActivity (new Activity (idgen ("Activity", attributes), started, ended), true, attributes);
    }

    public ProvBuilder activity (String... attributes) throws ProvBuildException {
        Description bookmarked;

        if (attributes.length >= 1) {
            bookmarked = _bookmarks.get (attributes[0]);
            if (bookmarked != null) {
                if (!(bookmarked instanceof Activity)) {
                    throw new ProvBuildException ("Bookmark reference " + attributes[0] + " is not an activity.");
                }
                return addActivity ((Activity) bookmarked, false);
            }
        }
        return activity ((Date) null, (Date) null, attributes);
    }

    public ProvBuilder activityRef (Object identifier) throws ProvBuildException {
        return addActivity (Activity.reference (identifier), false);
    }

    private ProvBuilder addActivity (Activity activity, boolean isNew, String... attributes) throws ProvBuildException {
        store (activity, isNew, attributes);
        if (_prior != null) {
            if (_prior instanceof ActedOnBehalfOf) {
                ((ActedOnBehalfOf) _prior).setActivity (activity);
            }
            if (_prior instanceof WasAssociatedWith) {
                ((WasAssociatedWith) _prior).setResponsibleFor (activity);
            }
            if (_prior instanceof WasDerivedFrom) {
                ((WasDerivedFrom) _prior).setDeriver (activity);
            }
            if (_prior instanceof WasEndedBy) {
                ((WasEndedBy) _prior).setEnder (activity);
            }
            if (_prior instanceof WasGeneratedBy) {
                ((WasGeneratedBy) _prior).setGenerater (activity);
            }
            if (_prior instanceof WasInformedBy) {
                ((WasInformedBy) _prior).setInformer (activity);
            }
            if (_prior instanceof WasInvalidatedBy) {
                ((WasInvalidatedBy) _prior).setInvalidater (activity);
            }
            if (_prior instanceof WasStartedBy) {
                ((WasStartedBy) _prior).setStarter (activity);
            }
        }
        return this;
    }

    private ProvBuilder addAgent (Agent agent, boolean isNew, String... attributes) throws ProvBuildException {
        store (agent, isNew, attributes);
        if (_prior != null) {
            if (_prior instanceof ActedOnBehalfOf) {
                ((ActedOnBehalfOf) _prior).setOnBehalfOf (agent);
            }
            if (_prior instanceof WasAttributedTo) {
                ((WasAttributedTo) _prior).setAttributedTo (agent);
            }
        }
        return this;
    }

    private void addAttributes (AttributeHolder addTo, boolean isNew, String... attributes) throws ProvBuildException {
        int equals;
        String keyText, valueText;
        Object key, value;

        for (String attribute : attributes) {
            equals = attribute.indexOf ('=');
            if (equals < 0) {
                if (isNew) {
                    _bookmarks.put (attribute, (Description) addTo);
                    if (_bookmarksAsLabels) {
                        addTo.addAttribute (Term.label.uri (), attribute);
                    }
                }
                continue;
            }
            valueText = attribute.substring (equals + 1).trim ();
            value = resolve (valueText);
            keyText = attribute.substring (0, equals).trim ();
            if (keyText.trim ().equals ("")) {
                id (value);
            } else {
                key = resolve (keyText);
                addTo.addAttribute (key, value);
            }
        }
    }

    private ProvBuilder addEntity (Entity entity, boolean isNew, String... attributes) throws ProvBuildException {
        store (entity, isNew, attributes);
        if (_prior != null) {
            if (_prior instanceof AlternateOf) {
                ((AlternateOf) _prior).setAlternateB (entity);
            }
            if (_prior instanceof HadMember) {
                ((HadMember) _prior).setMember (entity);
            }
            if (_prior instanceof SpecializationOf) {
                ((SpecializationOf) _prior).setGeneralEntity (entity);
            }
            if (_prior instanceof Used) {
                ((Used) _prior).setUsed (entity);
            }
            if (_prior instanceof WasAssociatedWith) {
                ((WasAssociatedWith) _prior).setPlan (entity);
            }
            if (_prior instanceof WasDerivedFrom) {
                ((WasDerivedFrom) _prior).setDerivedFrom (entity);
            }
            if (_prior instanceof WasEndedBy) {
                ((WasEndedBy) _prior).setTrigger (entity);
            }
            if (_prior instanceof WasStartedBy) {
                ((WasStartedBy) _prior).setTrigger (entity);
            }
        }
        return this;
    }

    public ProvBuilder addResolver (AbbreviationResolver resolver) {
        _resolvers.add (resolver);
        return this;
    }

    public ProvBuilder agent (String... attributes) {
        Description bookmarked;

        if (attributes.length >= 1) {
            bookmarked = _bookmarks.get (attributes[0]);
            if (bookmarked != null) {
                if (!(bookmarked instanceof Agent)) {
                    throw new ProvBuildException ("Bookmark reference " + attributes[0] + " is not an agent.");
                }
                return addAgent ((Agent) bookmarked, false);
            }
        }
        return addAgent (new Agent (idgen ("Agent", attributes)), true, attributes);
    }

    public ProvBuilder agentRef (Object identifier) {
        return addAgent (Agent.reference (identifier), false);
    }

    public ProvBuilder alternateOf () throws ProvBuildException {
        return store (new AlternateOf (getEntity ("alternateOf"), null), true);
    }

    public ProvBuilder and () {
        _prior = _current;
        _current = null;
        return this;
    }

    public Document build () {
        return _document;
    }

    public Bundle buildBundle (Object identifier) {
        return new Bundle (identifier, _document);
    }

    public ProvBuilder collection (String... attributes) {
        return addEntity (new Collection (idgen ("Collection", attributes)), true, attributes);
    }

    public ProvBuilder emptyCollection (String... attributes) {
        return addEntity (new EmptyCollection (idgen ("EmptyCollection", attributes)), true, attributes);
    }

    public ProvBuilder entity (String... attributes) {
        Description bookmarked;

        if (attributes.length >= 1) {
            bookmarked = _bookmarks.get (attributes[0]);
            if (bookmarked != null) {
                if (!(bookmarked instanceof Entity)) {
                    throw new ProvBuildException ("Bookmark reference " + attributes[0] + " is not an entity.");
                }
                return addEntity ((Entity) bookmarked, false);
            }
        }
        return addEntity (new Entity (idgen ("Entity", attributes)), true, attributes);
    }

    private Activity getActivity (String edgeType) throws ProvBuildException {
        if (_current == null || !(_current instanceof Activity)) {
            throw new ProvBuildException (edgeType + " only applies to activities");
        }
        return (Activity) _current;
    }

    private Agent getAgent (String edgeType) throws ProvBuildException {
        if (_current == null || !(_current instanceof Agent)) {
            throw new ProvBuildException (edgeType + " only applies to agents");
        }
        return (Agent) _current;
    }

    private Entity getEntity (String edgeType) throws ProvBuildException {
        if (_current == null || !(_current instanceof Entity)) {
            throw new ProvBuildException (edgeType + " only applies to entities");
        }
        return (Entity) _current;
    }

    public ProvBuilder hadMember () throws ProvBuildException {
        return store (new HadMember (getEntity ("hadMember"), null), true);
    }

    public ProvBuilder hadPrimarySource (String... attributes) throws ProvBuildException {
        return store (new HadPrimarySource (idgen ("HadPrimarySource", attributes), getEntity ("hadPrimarySource"), null), true);
    }

    private Object idgen (String descriptive, String... attributes) {
        for (String attribute : attributes) {
            if (attribute.indexOf ('=') < 0) {
                return _idgen.generateID (attribute);
            }
        }
        return _idgen.generateID (descriptive);
    }

    public ProvBuilder id (Object identifier) throws ProvBuildException {
        if (_current == null || !(_current instanceof Identified)) {
            throw new ProvBuildException ("IDs can only be applied to identifiable descriptions: " + identifier);
        }
        ((Identified) _current).setIdentifier (identifier);
        _document.addSerialisationHint (new SerialisationHint (explicitlyIdentified, _current));
        return this;
    }

    public ProvBuilder organization (String... attributes) {
        return addAgent (new Organization (idgen ("Organization", attributes)), true, attributes);
    }

    public ProvBuilder person (String... attributes) {
        return addAgent (new Person (idgen ("Person", attributes)), true, attributes);
    }

    public ProvBuilder plan (String... attributes) {
        return addEntity (new Plan (idgen ("Plan", attributes)), true, attributes);
    }

    public void recogniseURIScheme (String scheme) {
        _resolvers.add (new URISchemeResolver (scheme));
    }

    private Object resolve (String text) {
        for (AbbreviationResolver resolver : _resolvers) {
            if (resolver.appliesTo (text)) {
                return resolver.resolve (text);
            }
        }
        return text;
    }

    public ProvBuilder setBookmarksAsLabels (boolean bookmarksAsLabels) {
        _bookmarksAsLabels = bookmarksAsLabels;
        return this;
    }

    public ProvBuilder setPrefix (String prefix, String vocabularyURI) {
        addResolver (new URIPrefixResolver (prefix, vocabularyURI));
        _document.addSerialisationHint (new SerialisationHint (namespacePrefix, prefix, vocabularyURI));
        return this;
    }

    public ProvBuilder softwareAgent (String... attributes) {
        return addAgent (new SoftwareAgent (idgen ("SoftwareAgent", attributes)), true, attributes);
    }

    public ProvBuilder specializationOf () throws ProvBuildException {
        return store (new SpecializationOf (getEntity ("specializationOf"), null), true);
    }

    public ProvBuilder startNew () {
        _document = new Document ();
        _bookmarks.clear ();
        return this;
    }

    private ProvBuilder store (Description item, boolean isNew, String... attributes) throws ProvBuildException {
        if (_document == null) {
            throw new ProvBuildException ("Must start a document (or bundle) before adding to it");
        }
        if (item instanceof AttributeHolder) {
            addAttributes ((AttributeHolder) item, isNew, attributes);
        }
        if (isNew) {
            _document.add (item);
        }
        _prior = _current;
        _current = item;
        return this;
    }

    public ProvBuilder used (Date time, String... attributes) throws ProvBuildException {
        store (new Used (idgen ("Used", attributes), getActivity ("used"), null, time), true, attributes);
        if (_prior != null) {
            if (_prior instanceof WasDerivedFrom) {
                ((WasDerivedFrom) _prior).setUsage ((Used) _current);
            }
        }

        return this;
    }

    public ProvBuilder used (String... attributes) throws ProvBuildException {
        return used ((Date) null, attributes);
    }

    public ProvBuilder wasAssociatedWith (String... attributes) throws ProvBuildException {

        return store (new WasAssociatedWith (idgen ("WasAssociatedWith", attributes), getActivity ("wasAssociatedWith"), null), true, attributes);
    }

    public ProvBuilder wasAttributedTo (String... attributes) throws ProvBuildException {
        return store (new WasAttributedTo (idgen ("WasAttributedTo", attributes), getEntity ("wasAttributedTo"), null), true, attributes);
    }

    public ProvBuilder wasDerivedFrom (String... attributes) throws ProvBuildException {
        return store (new WasDerivedFrom (idgen ("WasDerivedFrom", attributes), getEntity ("wasDerivedFrom"), null), true, attributes);
    }

    public ProvBuilder wasEndedBy (Date time, String... attributes) throws ProvBuildException {
        return store (new WasEndedBy (idgen ("WasEndedBy", attributes), getActivity ("wasEndedBy"), (Entity) null, (Activity) null, time), true, attributes);
    }

    public ProvBuilder wasEndedBy (String... attributes) throws ProvBuildException {
        return wasEndedBy ((Date) null, attributes);
    }

    public ProvBuilder wasGeneratedBy (Date time, String... attributes) throws ProvBuildException {
        store (new WasGeneratedBy (idgen ("WasGeneratedBy", attributes), getEntity ("wasGeneratedBy"), null, time), true, attributes);
        if (_prior != null) {
            if (_prior instanceof WasDerivedFrom) {
                ((WasDerivedFrom) _prior).setGeneration ((WasGeneratedBy) _current);
            }
        }

        return this;
    }

    public ProvBuilder wasGeneratedBy (String... attributes) throws ProvBuildException {
        return wasGeneratedBy ((Date) null, attributes);
    }

    public ProvBuilder wasInformedBy (String... attributes) throws ProvBuildException {
        return store (new WasInformedBy (idgen ("WasInformedBy", attributes), getActivity ("wasInformedBy"), null), true, attributes);
    }

    public ProvBuilder wasInvalidatedBy (Date time, String... attributes) throws ProvBuildException {
        return store (new WasInvalidatedBy (idgen ("WasInvalidatedBy", attributes), getEntity ("wasInvalidatedBy"), (Activity) null, time), true, attributes);
    }

    public ProvBuilder wasInvalidatedBy (String... attributes) throws ProvBuildException {
        return wasInvalidatedBy ((Date) null, attributes);
    }

    public ProvBuilder wasQuotedFrom (String... attributes) throws ProvBuildException {
        return store (new WasQuotedFrom (getEntity ("wasQuotationFrom"), null), true, attributes);
    }

    public ProvBuilder wasRevisionOf (String... attributes) throws ProvBuildException {
        return store (new WasRevisionOf (idgen ("WasRevisionOf", attributes), getEntity ("wasRevisionOf"), null), true, attributes);
    }

    public ProvBuilder wasStartedBy (Date time, String... attributes) throws ProvBuildException {
        return store (new WasStartedBy (idgen ("WasStartedBy", attributes), getActivity ("wasStartedBy"), (Entity) null, (Activity) null, time), true, attributes);
    }

    public ProvBuilder wasStartedBy (String... attributes) throws ProvBuildException {
        return wasStartedBy ((Date) null, attributes);
    }

    public ProvBuilder where (String bookmark) {
        Description bookmarked;

        bookmarked = _bookmarks.get (bookmark);
        if (bookmarked == null) {
            throw new ProvBuildException ("Bookmark reference not recognised: " + bookmark);
        }
        _prior = _current;
        _current = bookmarked;

        return this;
    }

    public Date xsdDate (String xsdDateTimeString) {
        return DatatypeConverter.parseDateTime (xsdDateTimeString).getTime ();
    }
}

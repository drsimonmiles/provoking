package uk.ac.kcl.inf.provoking.builder;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
import uk.ac.kcl.inf.provoking.model.WasQuotationFrom;
import uk.ac.kcl.inf.provoking.model.WasRevisionOf;
import uk.ac.kcl.inf.provoking.model.WasStartedBy;
import uk.ac.kcl.inf.provoking.model.util.AttributeHolder;
import uk.ac.kcl.inf.provoking.model.util.IDGenerator;
import uk.ac.kcl.inf.provoking.model.util.Identified;
import uk.ac.kcl.inf.provoking.model.util.Term;
import uk.ac.kcl.inf.provoking.model.util.UniqueIDGenerator;

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
    }
    
    public ProvBuilder (IDGenerator idgen) {
        this (new Document (), idgen);
    }
    
    public ProvBuilder (Document addToExisting, String idPrefix) {
        this (addToExisting, new UniqueIDGenerator (idPrefix));
    }
    
    public ProvBuilder (String idPrefix) {
        this (new UniqueIDGenerator (idPrefix));
    }
        
    public ProvBuilder (Document addToExisting) {
        this (addToExisting, "");
    }

    public ProvBuilder () {
        this ("");
    }

    public ProvBuilder actedOnBehalfOf (String... attributes) throws ProvBuildException {
        return store (new ActedOnBehalfOf (id ("ActedOnBehalfOf"), getAgent ("actedOnBehalfOf"), null), true, attributes);
    }

    public ProvBuilder activity (Date started, Date ended, String... attributes) throws ProvBuildException {
        return addActivity (new Activity (id ("Activity"), started, ended), true, attributes);
    }

    public ProvBuilder activity (String... attributes) throws ProvBuildException {
        Description bookmarked;
        
        if (attributes.length == 1 && attributes[0].startsWith ("<")) {
            bookmarked = _bookmarks.get (attributes[0].substring (1));
            if (bookmarked == null || !(bookmarked instanceof Activity)) {
                throw new ProvBuildException ("Bookmark reference " + attributes[0] + " is undefined or not an activity.");
            }
            return addActivity ((Activity) bookmarked, false);
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

    private void addAttributes (AttributeHolder addTo, String... attributes) throws ProvBuildException {
        int equals;
        String bookmark, keyText, valueText;
        Object key, value;

        for (String attribute : attributes) {
            if (attribute.startsWith ("*")) {
                bookmark = attribute.substring (1).trim ();
                _bookmarks.put (bookmark, (Description) addTo);
                if (_bookmarksAsLabels) {
                    addTo.addAttribute (Term.label.uri (), bookmark);
                }
                continue;
            }
            equals = attribute.indexOf ('=');
            if (equals < 0) {
                throw new ProvBuildException ("Attributes must have the format X=Y, but this attribute does not: " + attribute);
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

    public ProvBuilder addResolver (AbbreviationResolver resolver) {
        _resolvers.add (resolver);
        return this;
    }

    public ProvBuilder agent (String... attributes) {
        Description bookmarked;
        
        if (attributes.length == 1 && attributes[0].startsWith ("<")) {
            bookmarked = _bookmarks.get (attributes[0].substring (1));
            if (bookmarked == null || !(bookmarked instanceof Agent)) {
                throw new ProvBuildException ("Bookmark reference " + attributes[0] + " is undefined or not an agent.");
            }
            return addAgent ((Agent) bookmarked, false);
        }
        return addAgent (new Agent (id ("Agent")), true, attributes);
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
        return addEntity (new Collection (id ("Collection")), true, attributes);
    }
    
    public ProvBuilder emptyCollection (String... attributes) {
        return addEntity (new EmptyCollection (id ("EmptyCollection")), true, attributes);
    }
    
    public ProvBuilder entity (String... attributes) {
        Description bookmarked;
        
        if (attributes.length == 1 && attributes[0].startsWith ("<")) {
            bookmarked = _bookmarks.get (attributes[0].substring (1));
            if (bookmarked == null || !(bookmarked instanceof Entity)) {
                throw new ProvBuildException ("Bookmark reference " + attributes[0] + " is undefined or not an entity.");
            }
            return addEntity ((Entity) bookmarked, false);
        }
        return addEntity (new Entity (id ("Entity")), true, attributes);
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

    public ProvBuilder hadPrimarySource () throws ProvBuildException {
        return store (new HadPrimarySource (id ("HadPrimarySource"), getEntity ("hadPrimarySource"), null), true);
    }
    
    private Object id (String descriptive) {
        return _idgen.generateID (descriptive);
    }

    public ProvBuilder id (Object identifier) throws ProvBuildException {
        if (_current == null || !(_current instanceof Identified)) {
            throw new ProvBuildException ("IDs can only be applied to identifiable descriptions: " + identifier);
        }
        ((Identified) _current).setIdentifier (identifier);
        return this;
    }

    public ProvBuilder organization (String... attributes) {
        return addAgent (new Organization (id ("Organization")), true, attributes);
    }

    public ProvBuilder person (String... attributes) {
        return addAgent (new Person (id ("Person")), true, attributes);
    }

    public ProvBuilder plan (String... attributes) {
        return addEntity (new Plan (id ("Plan")), true, attributes);
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
        return this;
    }
    
    public ProvBuilder softwareAgent (String... attributes) {
        return addAgent (new SoftwareAgent (id ("SoftwareAgent")), true, attributes);
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
        if (isNew) {
            if (item instanceof AttributeHolder) {
                addAttributes ((AttributeHolder) item, attributes);
            }
            _document.add (item);
        }
        _prior = _current;
        _current = item;
        return this;
    }

    public ProvBuilder used (Date time, String... attributes) throws ProvBuildException {
        store (new Used (id ("Used"), getActivity ("used"), null, time), true, attributes);
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
        return store (new WasAssociatedWith (id ("WasAssociatedWith"), getActivity ("wasAssociatedWith"), null), true, attributes);
    }

    public ProvBuilder wasAttributedTo (String... attributes) throws ProvBuildException {
        return store (new WasAttributedTo (id ("WasAttributedTo"), getEntity ("wasAttributedTo"), null), true, attributes);
    }

    public ProvBuilder wasDerivedFrom (String... attributes) throws ProvBuildException {
        return store (new WasDerivedFrom (id ("WasDerivedFrom"), getEntity ("wasDerivedFrom"), null), true, attributes);
    }

    public ProvBuilder wasEndedBy (Date time, String... attributes) throws ProvBuildException {
        return store (new WasEndedBy (id ("WasEndedBy"), getActivity ("wasEndedBy"), (Entity) null, (Activity) null, time), true, attributes);
    }

    public ProvBuilder wasEndedBy (String... attributes) throws ProvBuildException {
        return wasEndedBy ((Date) null, attributes);
    }

    public ProvBuilder wasGeneratedBy (Date time, String... attributes) throws ProvBuildException {
        store (new WasGeneratedBy (id ("WasGeneratedBy"), getEntity ("wasGeneratedBy"), null, time), true, attributes);
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
        return store (new WasInformedBy (id ("WasInformedBy"), getActivity ("wasInformedBy"), null), true, attributes);
    }

    public ProvBuilder wasInvalidatedBy (Date time, String... attributes) throws ProvBuildException {
        return store (new WasInvalidatedBy (id ("WasInvalidatedBy"), getEntity ("wasInvalidatedBy"), (Activity) null, time), true, attributes);
    }

    public ProvBuilder wasInvalidatedBy (String... attributes) throws ProvBuildException {
        return wasInvalidatedBy ((Date) null, attributes);
    }

    public ProvBuilder wasQuotationFrom (String... attributes) throws ProvBuildException {
        return store (new WasQuotationFrom (getEntity ("wasQuotationFrom"), null), true, attributes);
    }

    public ProvBuilder wasRevisionOf (String... attributes) throws ProvBuildException {
        return store (new WasRevisionOf (id ("WasRevisionOf"), getEntity ("wasRevisionOf"), null), true, attributes);
    }

    public ProvBuilder wasStartedBy (Date time, String... attributes) throws ProvBuildException {
        return store (new WasStartedBy (id ("WasStartedBy"), getActivity ("wasStartedBy"), (Entity) null, (Activity) null, time), true, attributes);
    }

    public ProvBuilder wasStartedBy (String... attributes) throws ProvBuildException {
        return wasStartedBy ((Date) null, attributes);
    }

    public ProvBuilder where (String bookmark) throws ProvBuildException {
        if (bookmark.startsWith ("*")) {
            bookmark = bookmark.substring (1).trim ();
        }
        _prior = _current;
        _current = _bookmarks.get (bookmark);
        if (_current == null) {
            throw new ProvBuildException ("Bookmark " + bookmark + " is not known");
        }
        return this;
    }
}

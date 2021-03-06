package uk.ac.kcl.inf.provoking.builder;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.DatatypeConverter;
import uk.ac.kcl.inf.provoking.model.*;
import uk.ac.kcl.inf.provoking.model.util.AttributeHolder;
import uk.ac.kcl.inf.provoking.model.util.IDGenerator;
import uk.ac.kcl.inf.provoking.model.util.Identified;
import uk.ac.kcl.inf.provoking.model.util.Influenceable;
import uk.ac.kcl.inf.provoking.model.util.Term;
import uk.ac.kcl.inf.provoking.model.util.UniqueIDGenerator;
import uk.ac.kcl.inf.provoking.model.util.UniqueURIGenerator;
import uk.ac.kcl.inf.provoking.serialise.ProvConstructer;
import uk.ac.kcl.inf.provoking.serialise.SerialisationHint;
import static uk.ac.kcl.inf.provoking.serialise.SerialisationHintType.*;

/**
 * A builder for iteratively and succinctly creating PROV documents.
 * 
 * By default, all builders resolve strings starting with "prov:" to a URI
 * in the PROV namespace, and also resolve strings starting "http:", "mailto:"
 * or "urn:" to URI objects. Other prefixes can be registered using setPrefix,
 * other URI schemes registered with recogniseURIScheme, and abbreviation
 * resolvers in general registered with addResolver.
 * 
 * @author Simon Miles
 */
public class ProvBuilder {
    /**
     * The document currently being built;
     */
    private Document _document;
    /**
     * The current description that more information will be added to, and the previously current description.
     */
    private Description _current, _prior;
    /**
     * The abbreviation resolvers applied to text such as attribute values during building.
     */
    private List<AbbreviationResolver> _resolvers;
    /**
     * For each bookmark string declared by the user in building, this maps to the description bookmarked.
     */
    private Map<String, Description> _bookmarks;
    /**
     * The generator used for new description identifiers.
     */
    private IDGenerator _idgen;
    /**
     * If true, descriptions' bookmarks will also be added to the document as prov:labels, so that they appear in visualisations.
     */
    private boolean _bookmarksAsLabels;
    /**
     * If true, relations (as well as activities, entities etc.) will be given newly created identifiers.
     */
    private boolean _nameRelations;
    /**
     * For extensions of the builder, this object can be used to denote (in a extension-specific way)
     * the type of description currently being built. If this object is non-null when a new description is
     * created, its toString() method will be called to get the identifier start string.
     */
    private Class _extendedDescriptionKind;
    
    /**
     * Create a builder that adds descriptions to an new document, and
     * generates new URI identifiers using a UniqueURIGenerator, with every identifier
     * starting with a given base URI. Additionally, a prefix is registered that
     * resolves to the base URI, so any text given during building that begins
     * with the prefix is resolved to a URI starting with the base URI. An example is given below.
     * 
     * ProvBuilder b = new ProvBuilder ("ex:", "http://www.inf.kcl.ac.uk/staff/simonm/provoking#");
     * 
     * @param prefix A prefix registered to resolve to the given base URI.
     * @param idURIBase The starting string for all URI identifiers.
     */
    public ProvBuilder (String prefix, String idURIBase) {
        this (new UniqueURIGenerator (idURIBase));
        setPrefix (prefix, idURIBase);
    }
    
    /**
     * Create a builder that will add new descriptions to an existing document,
     * and use the given generator to create identifiers where none are explicitly given.
     * 
     * @param addToExisting The document to add descriptions to.
     * @param idgen The generator for new identifiers.
     */
    public ProvBuilder (Document addToExisting, IDGenerator idgen) {
        _document = addToExisting;
        _current = null;
        _prior = null;
        _resolvers = new LinkedList<> ();
        _bookmarks = new HashMap<> ();
        _idgen = idgen;
        _bookmarksAsLabels = false;
        _nameRelations = false;
        _extendedDescriptionKind = null;
        setPrefix ("prov:", Term.PROV_NS);
        recogniseURIScheme ("http");
        recogniseURIScheme ("mailto");
        recogniseURIScheme ("urn");
    }

    /**
     * Create a builder that will build a new PROV document, using the identifier
     * generator given where identifiers are not stated explicitly.
     * 
     * @param idgen The generator for new identifiers.
     */
    public ProvBuilder (IDGenerator idgen) {
        this (new Document (), idgen);
    }

    /**
     * Create a builder that adds descriptions to an existing document, and
     * generates new String identifiers using a UniqueIDGenerator, with every identifier
     * starting with a given base string.
     * 
     * @param addToExisting The document to add descriptions to.
     * @param base The starting string for all identifier strings.
     */
    public ProvBuilder (Document addToExisting, String base) {
        this (addToExisting, new UniqueIDGenerator (base));
    }

    /**
     * Create a builder that adds descriptions to an new document, and
     * generates new String identifiers using a UniqueIDGenerator, with every identifier
     * starting with a given base string.
     * 
     * @param base The starting string for all identifier strings.
     */
    public ProvBuilder (String base) {
        this (new UniqueIDGenerator (base));
    }

    /**
     * Creates a builder to add descriptions to an existing document, where
     * all identifiers will be made explicit so need no generator.
     * 
     * If any
     * identifiers are generated, they will be Strings with an empty base (starting string).
     * 
     * @param addToExisting The document to add descriptions to.
     */
    public ProvBuilder (Document addToExisting) {
        this (addToExisting, "");
    }

    /**
     * Creates a builder to add descriptions to a new document, where
     * all identifiers will be made explicit so need no generator.
     * 
     * If any
     * identifiers are generated, they will be Strings with an empty base (starting string).
     */
    public ProvBuilder () {
        this ("");
    }

    public ProvBuilder actedOnBehalfOf (String... attributes) throws ProvBuildException {
        return store (new ActedOnBehalfOf (idGenRel ("ActedOnBehalfOf", attributes), getAgent ("actedOnBehalfOf"), null), true, attributes);
    }

    public ProvBuilder activity (Date started, Date ended, String... attributes) throws ProvBuildException {
        //return addActivity (new Activity (idGen ("Activity", attributes), started, ended), true, attributes);
        Object id = idGen (getIDStartString ("Activity"), attributes);
        Activity activity = (Activity) createDescription (id, Activity.class);
        
        activity.setStartedAt (started);
        activity.setEndedAt (ended);
        
        return addActivity (activity, true, attributes);
    }
           
    public ProvBuilder activity (String... attributes) throws ProvBuildException {
        Description bookmarked;

        if (attributes.length >= 1) {
            bookmarked = _bookmarks.get (attributes[0]);
            if (bookmarked != null) {
                if (!(bookmarked instanceof Activity)) {
                    throw new ProvBuildException ("Bookmark reference " + attributes[0] + " is not an activity.");
                }
                return addActivity ((Activity) bookmarked, false, attributes);
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
            ProvConstructer.setActivity (_prior, activity);
        }
        return this;
    }

    private ProvBuilder addAgent (Agent agent, boolean isNew, String... attributes) throws ProvBuildException {
        store (agent, isNew, attributes);
        if (_prior != null) {
            ProvConstructer.setAgent (_prior, agent);
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
            ProvConstructer.setEntity (_prior, entity);
        }
        return this;
    }

    private ProvBuilder addLocation (Location location, boolean isNew, String... attributes) throws ProvBuildException {
        store (location, isNew, attributes);
        if (_prior != null) {
            ProvConstructer.setLocation (_prior, location);
        }
        return this;
    }

    public ProvBuilder addResolver (AbbreviationResolver resolver) {
        _resolvers.add (resolver);
        return this;
    }

    private ProvBuilder addRole (Role role, boolean isNew, String... attributes) throws ProvBuildException {
        store (role, isNew, attributes);
        if (_prior != null) {
            ProvConstructer.setRole (_prior, role);
        }
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
                return addAgent ((Agent) bookmarked, false, attributes);
            }
        }
        return addAgent (new Agent (idGen ("Agent", attributes)), true, attributes);
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
    
    public ProvBuilder bundle (String... attributes) {
        Description bookmarked;

        if (attributes.length >= 1) {
            bookmarked = _bookmarks.get (attributes[0]);
            if (bookmarked != null) {
                if (!(bookmarked instanceof Bundle)) {
                    throw new ProvBuildException ("Bookmark reference " + attributes[0] + " is not a bundle.");
                }
                return addEntity ((Bundle) bookmarked, false, attributes);
            }
        }
        return addEntity (new Bundle (idGen ("Bundle", attributes)), true, attributes);
    }

    public ProvBuilder collection (String... attributes) {
        Description bookmarked;

        if (attributes.length >= 1) {
            bookmarked = _bookmarks.get (attributes[0]);
            if (bookmarked != null) {
                if (!(bookmarked instanceof Collection)) {
                    throw new ProvBuildException ("Bookmark reference " + attributes[0] + " is not a collection.");
                }
                return addEntity ((Collection) bookmarked, false, attributes);
            }
        }
        return addEntity (new Collection (idGen ("Collection", attributes)), true, attributes);
    }
        
    private Description createDescription (Object id, Class kind) {
        try {
            if (getExtendedDescriptionKind () != null) {
                kind = getExtendedDescriptionKind ();
            }
            setExtendedDescriptionKind (null);
            return (Description) kind.getConstructor (Object.class).newInstance (id);
        } catch (NoSuchMethodException problem) {
            throw new ProvBuildException ("Extended description type " + kind.getSimpleName () + " must have a constructor taking only an Object ID", problem);
        } catch (SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException problem) {
            throw new ProvBuildException ("Could not create extended description type " + kind.getSimpleName (), problem);
        }
    }

    public ProvBuilder emptyCollection (String... attributes) {
        Description bookmarked;

        if (attributes.length >= 1) {
            bookmarked = _bookmarks.get (attributes[0]);
            if (bookmarked != null) {
                if (!(bookmarked instanceof EmptyCollection)) {
                    throw new ProvBuildException ("Bookmark reference " + attributes[0] + " is not an empty collection.");
                }
                return addEntity ((EmptyCollection) bookmarked, false, attributes);
            }
        }
        return addEntity (new EmptyCollection (idGen ("EmptyCollection", attributes)), true, attributes);
    }

    public ProvBuilder entity (String... attributes) {
        Description bookmarked;

        if (attributes.length >= 1) {
            bookmarked = _bookmarks.get (attributes[0]);
            if (bookmarked != null) {
                if (!(bookmarked instanceof Entity)) {
                    throw new ProvBuildException ("Bookmark reference " + attributes[0] + " is not an entity.");
                }
                return addEntity ((Entity) bookmarked, false, attributes);
            }
        }
        return addEntity (new Entity (idGen ("Entity", attributes)), true, attributes);
    }

    /**
     * Returns the PROV description (entity, activity, ...) previously given a
     * bookmark in building.
     * 
     * @param bookmark The bookmark of the PROV description object to retrieve
     * @return The PROV description object referred to by the bookmark, or null if none exists
     */
    public Description getBookmarked (String bookmark) {
        return _bookmarks.get (bookmark);
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

    protected Class getExtendedDescriptionKind () {
        return _extendedDescriptionKind;
    }
    
    private String getIDStartString (String provTypeName) {
        if (getExtendedDescriptionKind () == null) {
            return provTypeName;
        } else {
            return getExtendedDescriptionKind ().getSimpleName ();
        }
    }
    
    private Influenceable getInfluenceable (String edgeType) throws ProvBuildException {
        if (_current == null || !(_current instanceof Influenceable)) {
            throw new ProvBuildException (edgeType + " only applies to activites, agents or entities");
        }
        return (Influenceable) _current;
    }
    
    public Description getLastAdded () {
        return _current;
    }

    public ProvBuilder hadMember () throws ProvBuildException {
        return store (new HadMember (getEntity ("hadMember"), null), true);
    }

    public ProvBuilder hadPrimarySource (String... attributes) throws ProvBuildException {
        return store (new HadPrimarySource (idGenRel ("HadPrimarySource", attributes), getEntity ("hadPrimarySource"), null), true);
    }

    /**
     * Set the ID of the description currently being built to be exactly the 
     * object passed as parameter. The identifier object is not changed in any
     * way, so no abbreviations are resolved if it is a String, and it is not
     * converted to a URI if it is a resolveable String.
     * 
     * For example:
     *   b.entity ("myEntity").id ("myEntity'sIDString");
     *   b.entity ("myEntity").id (new URI ("http://www.example.com/1"));
     * 
     * @param identifier The object to be set as ID for the currently built description.
     * @return This builder.
     * @throws ProvBuildException If the currently built description is not one that can take an identifier.
     */
    public ProvBuilder id (Object identifier) throws ProvBuildException {
        if (_current == null || !(_current instanceof Identified)) {
            throw new ProvBuildException ("IDs can only be applied to identifiable descriptions: " + identifier);
        }
        ((Identified) _current).setIdentifier (identifier);
        _document.addSerialisationHint (new SerialisationHint (explicitlyIdentified, _current));
        return this;
    }

    /**
     * Set the ID of the description currently being built to be the string
     * parameter or, if the string is an abbreviation, the resolved object that
     * the string represents, e.g. a URI.
     * 
     * For example:
     *   b.entity ("myEntity").id ("http://www.example.com/1");
     * will set the entity's ID to be a URI object suitable for serialisation to RDF.
     * 
     * @param identifier The object to be set as ID for the currently built description.
     * @return This builder.
     * @throws ProvBuildException If the currently built description is not one that can take an identifier.
     */
    public ProvBuilder idAbbr (String identifierAbbreviation) throws ProvBuildException {
        return id (resolve (identifierAbbreviation));
    }
    
    private Object idGen (String descriptive, String... attributes) {
        for (String attribute : attributes) {
            if (attribute.indexOf ('=') < 0 && !attribute.startsWith ("*")) {
                return _idgen.generateID (attribute);
            }
        }
        if (descriptive == null) {
            return null;
        } else {
            return _idgen.generateID (descriptive);
        }
    }
    
    private Object idGenRel (String descriptive, String... attributes) {
        if (_nameRelations) {
            return idGen (descriptive, attributes);
        } else {
            return null;
        }
    }

    /**
     * Set the location of the activity, agent, entity or event just built.
     * 
     * @param id The location string (possibly an abbreviation such as a resolveable qualified name)
     * @param attributes The attributes to annotate to the location
     * @return This builder
     */
    public ProvBuilder location (String id, String... attributes) {
        Description bookmarked;
        Location newLocation;

        bookmarked = _bookmarks.get (id);
        if (bookmarked != null) {
            if (!(bookmarked instanceof Location)) {
                throw new ProvBuildException ("Reference " + id + " is not a location.");
            }
            return addLocation ((Location) bookmarked, false, attributes);
        }
        newLocation = new Location (resolve (id));
        _bookmarks.put (id, newLocation);

        return addLocation (newLocation, true, attributes);
    }

    public ProvBuilder organization (String... attributes) {
        Description bookmarked;
        
        if (attributes.length >= 1) {
            bookmarked = _bookmarks.get (attributes[0]);
            if (bookmarked != null) {
                if (!(bookmarked instanceof Organization)) {
                    throw new ProvBuildException ("Bookmark reference " + attributes[0] + " is not an organization.");
                }
                return addAgent ((Organization) bookmarked, false, attributes);
            }
        }
        return addAgent (new Organization (idGen ("Organization", attributes)), true, attributes);
    }

    public ProvBuilder person (String... attributes) {
        Description bookmarked;
        
        if (attributes.length >= 1) {
            bookmarked = _bookmarks.get (attributes[0]);
            if (bookmarked != null) {
                if (!(bookmarked instanceof Person)) {
                    throw new ProvBuildException ("Bookmark reference " + attributes[0] + " is not a person.");
                }
                return addAgent ((Person) bookmarked, false, attributes);
            }
        }
        return addAgent (new Person (idGen ("Person", attributes)), true, attributes);
    }

    public ProvBuilder plan (String... attributes) {
        Description bookmarked;

        if (attributes.length >= 1) {
            bookmarked = _bookmarks.get (attributes[0]);
            if (bookmarked != null) {
                if (!(bookmarked instanceof Plan)) {
                    throw new ProvBuildException ("Bookmark reference " + attributes[0] + " is not a plan.");
                }
                return addEntity ((Plan) bookmarked, false, attributes);
            }
        }
        return addEntity (new Plan (idGen ("Plan", attributes)), true, attributes);
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

    public ProvBuilder role (String id, String... attributes) {
        Description bookmarked;
        Role newRole;

        bookmarked = _bookmarks.get (id);
        if (bookmarked != null) {
            if (!(bookmarked instanceof Role)) {
                throw new ProvBuildException ("Reference " + id + " is not a role.");
            }
            return addRole ((Role) bookmarked, false, attributes);
        }
        newRole = new Role (resolve (id));
        _bookmarks.put (id, newRole);

        return addRole (newRole, true, attributes);
    }

    public ProvBuilder setBookmarksAsLabels (boolean bookmarksAsLabels) {
        _bookmarksAsLabels = bookmarksAsLabels;
        return this;
    }
    
    protected void setExtendedDescriptionKind (Class kind) {
        _extendedDescriptionKind = kind;
    }

    public ProvBuilder setNameRelations (boolean nameRelations) {
        _nameRelations = nameRelations;
        return this;
    }
    
    public ProvBuilder setPrefix (String prefix, String vocabularyURI) {
        addResolver (new URIPrefixResolver (prefix, vocabularyURI));
        _document.addSerialisationHint (new SerialisationHint (namespacePrefix, prefix, vocabularyURI));
        return this;
    }

    public ProvBuilder softwareAgent (String... attributes) {
        Description bookmarked;
        
        if (attributes.length >= 1) {
            bookmarked = _bookmarks.get (attributes[0]);
            if (bookmarked != null) {
                if (!(bookmarked instanceof SoftwareAgent)) {
                    throw new ProvBuildException ("Bookmark reference " + attributes[0] + " is not a software agent.");
                }
                return addAgent ((SoftwareAgent) bookmarked, false, attributes);
            }
        }
        return addAgent (new SoftwareAgent (idGen ("SoftwareAgent", attributes)), true, attributes);
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
        store (new Used (idGenRel ("Used", attributes), getActivity ("used"), null, time), true, attributes);
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
        return store (new WasAssociatedWith (idGenRel ("WasAssociatedWith", attributes), getActivity ("wasAssociatedWith"), null), true, attributes);
    }

    public ProvBuilder wasAttributedTo (String... attributes) throws ProvBuildException {
        return store (new WasAttributedTo (idGenRel ("WasAttributedTo", attributes), getEntity ("wasAttributedTo"), null), true, attributes);
    }

    public ProvBuilder wasDerivedFrom (String... attributes) throws ProvBuildException {
        return store (new WasDerivedFrom (idGenRel ("WasDerivedFrom", attributes), getEntity ("wasDerivedFrom"), null), true, attributes);
    }

    public ProvBuilder wasEndedBy (Date time, String... attributes) throws ProvBuildException {
        return store (new WasEndedBy (idGenRel ("WasEndedBy", attributes), getActivity ("wasEndedBy"), (Entity) null, (Activity) null, time), true, attributes);
    }

    public ProvBuilder wasEndedBy (String... attributes) throws ProvBuildException {
        return wasEndedBy ((Date) null, attributes);
    }

    public ProvBuilder wasGeneratedBy (Date time, String... attributes) throws ProvBuildException {
        store (new WasGeneratedBy (idGenRel ("WasGeneratedBy", attributes), getEntity ("wasGeneratedBy"), null, time), true, attributes);
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

    public ProvBuilder wasInfluencedBy (String... attributes) throws ProvBuildException {
        return store (new WasInfluencedBy (idGenRel ("WasInfluencedBy", attributes), getInfluenceable ("wasInfluencedBy"), null), true, attributes);
    }

    public ProvBuilder wasInformedBy (String... attributes) throws ProvBuildException {
        return store (new WasInformedBy (idGenRel ("WasInformedBy", attributes), getActivity ("wasInformedBy"), null), true, attributes);
    }

    public ProvBuilder wasInvalidatedBy (Date time, String... attributes) throws ProvBuildException {
        return store (new WasInvalidatedBy (idGenRel ("WasInvalidatedBy", attributes), getEntity ("wasInvalidatedBy"), (Activity) null, time), true, attributes);
    }

    public ProvBuilder wasInvalidatedBy (String... attributes) throws ProvBuildException {
        return wasInvalidatedBy ((Date) null, attributes);
    }

    public ProvBuilder wasQuotedFrom (String... attributes) throws ProvBuildException {
        return store (new WasQuotedFrom (getEntity ("wasQuotationFrom"), null), true, attributes);
    }

    public ProvBuilder wasRevisionOf (String... attributes) throws ProvBuildException {
        return store (new WasRevisionOf (idGenRel ("WasRevisionOf", attributes), getEntity ("wasRevisionOf"), null), true, attributes);
    }

    public ProvBuilder wasStartedBy (Date time, String... attributes) throws ProvBuildException {
        return store (new WasStartedBy (idGenRel ("WasStartedBy", attributes), getActivity ("wasStartedBy"), (Entity) null, (Activity) null, time), true, attributes);
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

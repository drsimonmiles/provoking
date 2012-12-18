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
import uk.ac.kcl.inf.provoking.model.util.Identified;
import uk.ac.kcl.inf.provoking.model.util.UniqueIDGenerator;

public class ProvBuilder {
    private Document _document;
    private Description _current, _prior;
    private List<AbbreviationResolver> _resolvers;
    private Map<String, Description> _labels;
    private UniqueIDGenerator _idgen;

    public ProvBuilder (String idPrefix) {
        _document = new Document ();
        _current = null;
        _prior = null;
        _resolvers = new LinkedList<> ();
        _labels = new HashMap<> ();
        _idgen = new UniqueIDGenerator (idPrefix);
    }
    
    public ProvBuilder () {
        this ("");
    }

    public ProvBuilder actedOnBehalfOf (String... attributes) throws ProvBuildException {
        return store (new ActedOnBehalfOf (id (), getAgent ("actedOnBehalfOf"), null), attributes);
    }

    public ProvBuilder activity (Date started, Date ended, String... attributes) throws ProvBuildException {
        return addActivity (new Activity (id (), started, ended), attributes);
    }

    public ProvBuilder activity (String... attributes) throws ProvBuildException {
        return activity ((Date) null, (Date) null, attributes);
    }

    public ProvBuilder activityRef (Object identifier) throws ProvBuildException {
        return addActivity (Activity.reference (identifier));
    }

    private ProvBuilder addActivity (Activity activity, String... attributes) throws ProvBuildException {
        store (activity, attributes);
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

    private ProvBuilder addAgent (Agent agent, String... attributes) throws ProvBuildException {
        store (agent, attributes);
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

    private ProvBuilder addEntity (Entity entity, String... attributes) throws ProvBuildException {
        store (entity, attributes);
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
        String keyText, valueText;
        Object key, value;

        for (String attribute : attributes) {
            if (attribute.startsWith ("*")) {
                _labels.put (attribute, (Description) addTo);
                continue;
            }
            equals = attribute.indexOf ('=');
            if (equals < 0) {
                throw new ProvBuildException ("Attributes must have the format X=Y, but this attribute does not: " + attribute);
            }
            keyText = attribute.substring (0, equals);
            key = resolve (keyText);
            valueText = attribute.substring (equals + 1);
            value = resolve (valueText);
            addTo.addAttribute (key, value);
        }
    }

    public ProvBuilder addResolver (AbbreviationResolver resolver) {
        _resolvers.add (resolver);
        return this;
    }

    public ProvBuilder agent (String... attributes) {
        return addAgent (new Agent (id ()), attributes);
    }

    public ProvBuilder agentRef (Object identifier) {
        return addAgent (Agent.reference (identifier));
    }

    public ProvBuilder alternateOf () throws ProvBuildException {
        return store (new AlternateOf (getEntity ("alternateOf"), null));
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
        return addEntity (new Collection (id ()), attributes);
    }
    
    public ProvBuilder emptyCollection (String... attributes) {
        return addEntity (new EmptyCollection (id ()), attributes);
    }
    
    public ProvBuilder entity (String... attributes) {
        return addEntity (new Entity (id ()), attributes);
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
        return store (new HadMember (getEntity ("hadMember"), null));
    }

    public ProvBuilder hadPrimarySource () throws ProvBuildException {
        return store (new HadPrimarySource (id (), getEntity ("hadPrimarySource"), null));
    }
    
    private Object id () {
        return _idgen.generateID ();
    }

    public ProvBuilder id (Object identifier) throws ProvBuildException {
        if (_current == null || !(_current instanceof Identified)) {
            throw new ProvBuildException ("IDs can only be applied to identifiable descriptions: " + identifier);
        }
        if (identifier instanceof String) {
            identifier = resolve ((String) identifier);
        }
        ((Identified) _current).setIdentifier (identifier);
        return this;
    }

    public ProvBuilder organization (String... attributes) {
        return addAgent (new Organization (id ()), attributes);
    }

    public ProvBuilder person (String... attributes) {
        return addAgent (new Person (id ()), attributes);
    }

    public ProvBuilder plan (String... attributes) {
        return addEntity (new Plan (id ()), attributes);
    }

    private Object resolve (String text) {
        for (AbbreviationResolver resolver : _resolvers) {
            if (resolver.appliesTo (text)) {
                return resolver.resolve (text);
            }
        }
        return text;
    }

    public ProvBuilder softwareAgent (String... attributes) {
        return addAgent (new SoftwareAgent (id ()), attributes);
    }

    public ProvBuilder specializationOf () throws ProvBuildException {
        return store (new SpecializationOf (getEntity ("specializationOf"), null));
    }

    public ProvBuilder startNew () {
        _document = new Document ();
        _labels.clear ();
        return this;
    }

    private ProvBuilder store (Description newItem, String... attributes) throws ProvBuildException {
        if (_document == null) {
            throw new ProvBuildException ("Must start a document (or bundle) before adding to it");
        }
        if (!(newItem instanceof Identified) || !((Identified) newItem).isReference ()) {
            if (newItem instanceof AttributeHolder) {
                addAttributes ((AttributeHolder) newItem, attributes);
            }
            _document.add (newItem);
        }
        _prior = _current;
        _current = newItem;
        return this;
    }

    public ProvBuilder used (Date time, String... attributes) throws ProvBuildException {
        store (new Used (id (), getActivity ("used"), null, time), attributes);
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
        return store (new WasAssociatedWith (id (), getActivity ("wasAssociatedWith"), null), attributes);
    }

    public ProvBuilder wasAttributedTo (String... attributes) throws ProvBuildException {
        return store (new WasAttributedTo (id (), getEntity ("wasAttributedTo"), null), attributes);
    }

    public ProvBuilder wasDerivedFrom (String... attributes) throws ProvBuildException {
        return store (new WasDerivedFrom (id (), getEntity ("wasDerivedFrom"), null), attributes);
    }

    public ProvBuilder wasEndedBy (Date time, String... attributes) throws ProvBuildException {
        return store (new WasEndedBy (id (), getActivity ("wasEndedBy"), (Entity) null, (Activity) null, time), attributes);
    }

    public ProvBuilder wasEndedBy (String... attributes) throws ProvBuildException {
        return wasEndedBy ((Date) null, attributes);
    }

    public ProvBuilder wasGeneratedBy (Date time, String... attributes) throws ProvBuildException {
        store (new WasGeneratedBy (id (), getEntity ("wasGeneratedBy"), null, time), attributes);
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
        return store (new WasInformedBy (id (), getActivity ("wasInformedBy"), null), attributes);
    }

    public ProvBuilder wasInvalidatedBy (Date time, String... attributes) throws ProvBuildException {
        return store (new WasInvalidatedBy (id (), getEntity ("wasInvalidatedBy"), (Activity) null, time), attributes);
    }

    public ProvBuilder wasInvalidatedBy (String... attributes) throws ProvBuildException {
        return wasInvalidatedBy ((Date) null, attributes);
    }

    public ProvBuilder wasQuotationFrom () throws ProvBuildException {
        return store (new WasQuotationFrom (getEntity ("wasQuotationFrom"), null));
    }

    public ProvBuilder wasRevisionOf () throws ProvBuildException {
        return store (new WasRevisionOf (id (), getEntity ("wasRevisionOf"), null));
    }

    public ProvBuilder wasStartedBy (Date time, String... attributes) throws ProvBuildException {
        return store (new WasStartedBy (id (), getActivity ("wasStartedBy"), (Entity) null, (Activity) null, time), attributes);
    }

    public ProvBuilder wasStartedBy (String... attributes) throws ProvBuildException {
        return wasStartedBy ((Date) null, attributes);
    }

    public ProvBuilder where (String label) throws ProvBuildException {
        _prior = _current;
        _current = _labels.get (label);
        if (_current == null) {
            throw new ProvBuildException ("Label " + label + " is not known");
        }
        return this;
    }
}

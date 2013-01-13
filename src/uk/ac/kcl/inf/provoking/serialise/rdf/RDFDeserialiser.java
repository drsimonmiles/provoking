package uk.ac.kcl.inf.provoking.serialise.rdf;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import uk.ac.kcl.inf.provoking.model.*;
import uk.ac.kcl.inf.provoking.model.util.AttributeHolder;
import uk.ac.kcl.inf.provoking.model.util.Identified;
import uk.ac.kcl.inf.provoking.model.util.Term;
import uk.ac.kcl.inf.provoking.serialise.ProvConstructer;

public class RDFDeserialiser implements TriplesListener {
    private Map<Object, SingleDescriptionTriples> _buffered;

    public RDFDeserialiser () {
        _buffered = new HashMap<> ();
    }

    public Document build () {
        Document document = new Document ();
        List<Description> additional = new LinkedList<> ();
        Description description;

        for (SingleDescriptionTriples buffer : _buffered.values ()) {
            description = deserialise (buffer, additional);
            if (description != null) {
                document.add (description);
            }
        }
        document.addAll (additional);

        return document;
    }

    private Description deserialise (SingleDescriptionTriples buffer, List<Description> additional) {
        if (buffer.hasBeenDeserialised ()) {
            return buffer.getDeserialisation ();
        }

        URI type = getSpecificType (buffer);
        Description description;

        if (type == null) {
            type = getImpliedType (buffer.getSubject ());
            if (type == null) { // Not a PROV statement
                return null;
            }
        }
        description = ProvConstructer.create (type, buffer.getIdentifier ());
        buffer.setDeserialisation (description);

        for (URI predicate : buffer.getPredicates ()) {
            if (!predicate.equals (RDF.typeURI ())) {
                if (Term.isProvTerm (predicate)) {
                    deserialise (predicate, buffer, description, additional);
                } else {
                    if (description instanceof AttributeHolder) {
                        for (Literal object : buffer.getLiteralObjects (predicate)) {
                            ((AttributeHolder) description).addAttribute (predicate, object._value);
                        }
                    }
                }
            }
        }

        return description;
    }

    private void deserialise (URI predicate, SingleDescriptionTriples buffer, Description description, List<Description> additional) {
        Term relation = Term.toTerm (predicate);
        Object objectKey;
        SingleDescriptionTriples objectBuffer;
        Description objectDescription, newDescription;

        // Some relations may occur multiple times, with multiple object values
        switch (relation) {
            case label:
            case value:
            case type:
                for (Literal label : buffer.getLiteralObjects (predicate)) {
                    ((AttributeHolder) description).addAttribute (predicate, label._value);
                }
                return;
        }
        // All following should have only one object value
        objectKey = buffer.getObject (predicate);
        switch (relation) {
            case endedAtTime:
                ((Activity) description).setEndedAt ((Date) ((Literal) objectKey)._value);
                return;
            case startedAtTime:
                ((Activity) description).setEndedAt ((Date) ((Literal) objectKey)._value);
                return;
            case atTime:
                ((InstantaneousEvent) description).setTime ((Date) ((Literal) objectKey)._value);
                return;
        }
        // All following should have other descriptions as their object values
        objectBuffer = _buffered.get (objectKey);
        objectDescription = deserialise (objectBuffer, additional);
        switch (relation) {
            // Some relations set values on the subject descriptions
            case activity:
                ProvConstructer.setActivity (description, (Activity) objectDescription);
                return;
            case agent:
                ProvConstructer.setAgent (description, (Agent) objectDescription);
                return;
            case entity:
                ProvConstructer.setEntity (description, (Entity) objectDescription);
                return;
            case location:
            case atLocation:
                ProvConstructer.setLocation (description, (Location) objectDescription);
                return;
            case role:
            case hadRole:
                ProvConstructer.setRole (description, (Role) objectDescription);
                return;
            case hadGeneration:
                ((WasDerivedFrom) description).setGeneration ((WasGeneratedBy) objectDescription);
                return;
            case hadUsage:
                ((WasDerivedFrom) description).setUsage ((Used) objectDescription);
                return;
            case hadPlan:
                ((WasAssociatedWith) description).setPlan ((Entity) objectDescription);
                return;
            // Qualified relations set values on the object descriptions
            case qualifiedAssociation:
            case qualifiedAttribution:
            case qualifiedCommunication:
            case qualifiedDelegation:
            case qualifiedDerivation:
            case qualifiedPrimarySource:
            case qualifiedRevision:
            case qualifiedQuotation:
            case qualifiedEnd:
            case qualifiedGeneration:
            case qualifiedInvalidation:
            case qualifiedStart:
            case qualifiedUsage:
                ProvConstructer.setSubject (objectDescription, description);
                return;
        }
        // Binary relations require additional descriptions for which there are not necessarily a subject ID
        newDescription = ProvConstructer.createBinary (relation, description, objectDescription);
        if (newDescription != null) {
            additional.add (newDescription);
        }
    }

    private SingleDescriptionTriples getBuffer (Object key) {
        SingleDescriptionTriples buffer = _buffered.get (key);

        if (buffer == null) {
            buffer = new SingleDescriptionTriples (key);
            _buffered.put (key, buffer);
        }

        return buffer;
    }
    
    private URI getImpliedType (Object subject) {
        SingleDescriptionTriples subjectBuffer = _buffered.get (subject);
        Term type;
        
        if (subjectBuffer != null) {
            for (URI predicate : subjectBuffer.getPredicates ()) {
                if (Term.isProvTerm (predicate)) {
                    type = ProvConstructer.getDomain (Term.toTerm (predicate));
                    if (type != null) {
                        return type.uri ();
                    }
                }
            }
        }
        for (SingleDescriptionTriples buffer : _buffered.values ()) {
            for (URI predicate : buffer.getPredicatesWithObject (subject)) {
                if (Term.isProvTerm (predicate)) {
                    type = ProvConstructer.getRange (Term.toTerm (predicate));
                    if (type != null) {
                        return type.uri ();
                    }
                }
            }
        }
        return null;
    }

    private URI getSpecificType (SingleDescriptionTriples buffer) {
        List<URI> types = buffer.getURIObjects (RDF.typeURI ());
        URI provType = null;

        switch (types.size ()) {
            case 0:
                return null;
            case 1:
                return types.get (0);
            default:
                if (types.contains (Term.Entity.uri ())) {
                    if (types.contains (Term.Bundle.uri ()) || types.contains (Term.Collection.uri ())
                        || types.contains (Term.EmptyCollection.uri ()) || types.contains (Term.Plan.uri ())) {
                        types.remove (Term.Entity.uri ());
                    }
                }
                if (types.contains (Term.Collection.uri ())) {
                    if (types.contains (Term.EmptyCollection.uri ())) {
                        types.remove (Term.Collection.uri ());
                    }
                }
                if (types.contains (Term.Agent.uri ())) {
                    if (types.contains (Term.Person.uri ()) || types.contains (Term.Organization.uri ())
                        || types.contains (Term.SoftwareAgent.uri ())) {
                        types.remove (Term.Agent.uri ());
                    }
                }
                for (URI type : types) {
                    if (type.toString ().startsWith (Term.PROV_NS)) {
                        provType = type;
                    } else {
                        buffer.add (Term.type.uri (), type);
                    }
                }
                return provType;
        }
    }

    @Override
    public void triple (URI subject, URI predicate, URI object) {
        getBuffer (subject).add (predicate, object);
    }

    @Override
    public void triple (URI subject, URI predicate, String blankObject) {
        getBuffer (subject).add (predicate, blankObject);
    }

    @Override
    public void triple (URI subject, URI predicate, Literal object) {
        getBuffer (subject).add (predicate, object);
    }

    @Override
    public void triple (String blankSubject, URI predicate, URI object) {
        getBuffer (blankSubject).add (predicate, object);
    }

    @Override
    public void triple (String blankSubject, URI predicate, String blankObject) {
        getBuffer (blankSubject).add (predicate, blankObject);
    }

    @Override
    public void triple (String blankSubject, URI predicate, Literal object) {
        getBuffer (blankSubject).add (predicate, object);
    }
}

package uk.ac.kcl.inf.provoking.serialise.rdf;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import uk.ac.kcl.inf.provoking.model.*;
import uk.ac.kcl.inf.provoking.model.util.AttributeHolder;
import uk.ac.kcl.inf.provoking.model.util.Influenceable;
import uk.ac.kcl.inf.provoking.model.util.Term;
import static uk.ac.kcl.inf.provoking.model.util.Term.label;
import static uk.ac.kcl.inf.provoking.model.util.Term.type;
import static uk.ac.kcl.inf.provoking.model.util.Term.value;
import uk.ac.kcl.inf.provoking.serialise.DeserialisationException;
import uk.ac.kcl.inf.provoking.serialise.ProvConstructer;
import uk.ac.kcl.inf.provoking.serialise.SerialisationHint;
import static uk.ac.kcl.inf.provoking.serialise.SerialisationHintType.namespacePrefix;

/**
 * Implements a TriplesListener, receiving triples read in from some source and
 * storing them ready for deserialisation. Once reading is complete, a PROV
 * document can be built from the stored triples data.
 *
 * @author Simon Miles
 */
public class RDFDeserialiser implements TriplesListener {
    private Map<Object, SingleDescriptionTriples> _buffered;
    private Map<String, String> _prefixes;

    /**
     * Create a new deserialiser.
     */
    public RDFDeserialiser () {
        _buffered = new HashMap<> ();
        _prefixes = new HashMap<> ();
    }

    /**
     * Builds a PROV document from the triples that have been read.
     * 
     * @return The PROV document built
     * @throws DeserialisationException If the triples do not produce a valid PROV document
     */
    public Document build () throws DeserialisationException {
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
        for (String prefix : _prefixes.keySet ()) {
            document.addSerialisationHint (new SerialisationHint (namespacePrefix, prefix, _prefixes.get (prefix)));
        }

        return document;
    }

    private Description deserialise (SingleDescriptionTriples buffer, List<Description> additional) throws DeserialisationException {
        if (buffer.hasBeenDeserialised ()) {
            return buffer.getDeserialisation ();
        }

        if (buffer.getIdentifier () != null && buffer.getIdentifier ().toString ().contains ("chart2")) {
            System.out.println ("deserialising chart2");
        }
        URI type = getSpecificType (buffer);
        Description description;

        if (type == null) {
            type = getImpliedType (buffer.getSubject ());
            if (type == null) { // Not a PROV statement subject
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

    /**
     * Deserialise all the triples with a given subject (already deserialised)
     * and predicate. Sets the deserialised information as properties of the
     * deserialised subject. The exception to this are binary PROV relations,
     * which are added to a separate list, as they are not modelled as
     * properties of the deserialised subject.
     *
     * @param predicate The predicate of the triples to deserialise.
     * @param buffer The triples belonging to the given subject.
     * @param description The deserialised subject.
     * @param additional If the predicate is a binary PROV releation, this list
     * collects the deserialised relations.
     */
    private void deserialise (URI predicate, SingleDescriptionTriples buffer, Description description, List<Description> additional) throws DeserialisationException {
        Term relation = Term.toTerm (predicate);
        SingleDescriptionTriples objectBuffer;
        Description objectDescription, newDescription;
        String line;

        if (buffer.getIdentifier () != null && buffer.getIdentifier ().toString ().contains ("chart2")
            && predicate.toString ().contains ("wasDerivedFrom")) {
            System.out.println ("deserialising chart2 wasDerivedFrom");
        }
        for (Object objectKey : buffer.getObjects (predicate)) {
            // First, check for relations where the object is the literal value
            line = "(" + buffer.getSubject () + " " + predicate + " " + objectKey + ")";
        if (buffer.getIdentifier () != null && buffer.getIdentifier ().toString ().contains ("chart2")
            && predicate.toString ().contains ("wasDerivedFrom")) {
            System.out.println ("deserialising chart2 wasDerivedFrom " + objectKey);
        }
            switch (relation) {
                case label:
                case value:
                case type:
                    ((AttributeHolder) description).addAttribute (predicate, ((Literal) objectKey)._value);
                    continue;
                case endedAtTime:
                    ((Activity) description).setEndedAt ((Date) ((Literal) objectKey)._value);
                    continue;
                case startedAtTime:
                    ((Activity) description).setEndedAt ((Date) ((Literal) objectKey)._value);
                    continue;
                case atTime:
                    ((InstantaneousEvent) description).setTime ((Date) ((Literal) objectKey)._value);
                    continue;
            }
            // All following should have other descriptions as their object values
            objectBuffer = _buffered.get (objectKey);
            objectDescription = deserialise (objectBuffer, additional);
            switch (relation) {
                // Some relations set values on the subject descriptions
                case activity:
                    ProvConstructer.setActivity (description, (Activity) objectDescription);
                    continue;
                case agent:
                    ProvConstructer.setAgent (description, (Agent) objectDescription);
                    continue;
                case entity:
                    ProvConstructer.setEntity (description, ProvConstructer.entity (objectDescription, "object", line));
                    continue;
                case influencer:
                    ProvConstructer.setInfluenceable (description, (Influenceable) objectDescription);
                    continue;
                case location:
                case atLocation:
                    ProvConstructer.setLocation (description, (Location) objectDescription);
                    continue;
                case role:
                case hadRole:
                    ProvConstructer.setRole (description, (Role) objectDescription);
                    continue;
                case hadGeneration:
                    ((WasDerivedFrom) description).setGeneration ((WasGeneratedBy) objectDescription);
                    continue;
                case hadUsage:
                    ((WasDerivedFrom) description).setUsage ((Used) objectDescription);
                    continue;
                case hadPlan:
                    ((WasAssociatedWith) description).setPlan ((Entity) objectDescription);
                    continue;
                // Qualified relations set values on the object descriptions
                case qualifiedAssociation:
                case qualifiedAttribution:
                case qualifiedCommunication:
                case qualifiedDelegation:
                case qualifiedDerivation:
                case qualifiedInfluence:
                case qualifiedPrimarySource:
                case qualifiedRevision:
                case qualifiedQuotation:
                case qualifiedEnd:
                case qualifiedGeneration:
                case qualifiedInvalidation:
                case qualifiedStart:
                case qualifiedUsage:
                    ProvConstructer.setSubject (objectDescription, description);
                    continue;
            }
            // Binary relations require additional descriptions for which there is not necessarily a subject ID
            newDescription = ProvConstructer.createBinary (relation, description, objectDescription, line);
            if (newDescription != null) {
                additional.add (newDescription);
            }
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
    public void setPrefix (String prefix, String vocabularyURI) {
        _prefixes.put (prefix, vocabularyURI);
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

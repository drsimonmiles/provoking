package uk.ac.kcl.inf.provoking.serialise.rdf;

import java.net.URI;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import uk.ac.kcl.inf.provoking.model.Description;

/**
 * Stores data from a set of triples with the same subject. Once this subject
 * has been deserialised to a PROV model description, this description is also
 * stored.
 * 
 * @author Simon Miles
 */
class SingleDescriptionTriples implements Description {
    private final URI _subjectURI;
    private final String _subjectBlank;
    private final List<URIObject> _uriObjects;
    private final List<BlankObject> _blankObjects;
    private final List<LiteralObject> _literalObjects;
    private Description _deserialised;
    
    SingleDescriptionTriples (Object subject) {
        if (subject instanceof URI) {
            _subjectURI = (URI) subject;
            _subjectBlank = null;
        } else {
            _subjectURI = null;
            _subjectBlank = (String) subject;
        }
        _uriObjects = new LinkedList<> ();
        _blankObjects = new LinkedList<> ();
        _literalObjects = new LinkedList<> ();
        _deserialised = null;
    }
    
    void add (URI predicate, URI object) {
        _uriObjects.add (new URIObject (predicate, object));
    }
    
    void add (URI predicate, String object) {
        _blankObjects.add (new BlankObject (predicate, object));
    }
    
    void add (URI predicate, Literal object) {
        _literalObjects.add (new LiteralObject (predicate, object));
    }
    
    List<URI> getURIObjects (URI predicate) {
        List<URI> objects = new LinkedList<> ();
        
        for (URIObject relation : _uriObjects) {
            if (relation._predicate.equals (predicate)) {
                objects.add (relation._object);
            }
        }
        return objects;
    }
    
    List<String> getBlankObjects (URI predicate) {
        List<String> objects = new LinkedList<> ();
        
        for (BlankObject relation : _blankObjects) {
            if (relation._predicate.equals (predicate)) {
                objects.add (relation._object);
            }
        }
        return objects;
    }
    
    Description getDeserialisation () {
        return _deserialised;
    }

    List<Literal> getLiteralObjects (URI predicate) {
        List<Literal> objects = new LinkedList<> ();
        
        for (LiteralObject relation : _literalObjects) {
            if (relation._predicate.equals (predicate)) {
                objects.add (relation._object);
            }
        }
        return objects;
    }

    URI getIdentifier () {
        return _subjectURI;
    }

    List<Object> getObjects (URI predicate) {
        List<Object> objects = new LinkedList<> ();
        
        objects.addAll (getURIObjects (predicate));
        objects.addAll (getBlankObjects (predicate));
        objects.addAll (getLiteralObjects (predicate));

        return objects;
    }
    
    /**
     * Return the set of predicates in triples for this subject.
     * 
     * @return The set of distinct predicates in the triples recorded in this buffer.
     */
    Set<URI> getPredicates () {
        Set<URI> predicates = new HashSet<> ();
        
        for (URIObject relation : _uriObjects) {
            predicates.add (relation._predicate);
        }
        for (BlankObject relation : _blankObjects) {
            predicates.add (relation._predicate);
        }
        for (LiteralObject relation : _literalObjects) {
            predicates.add (relation._predicate);
        }
        
        return predicates;
    }
    
    List<URI> getPredicatesWithObject (Object object) {
        List<URI> predicates = new LinkedList<> ();
        
        for (URIObject relation : _uriObjects) {
            if (relation._object.equals (object)) {
                predicates.add (relation._predicate);
            }
        }
        for (BlankObject relation : _blankObjects) {
            if (relation._object.equals (object)) {
                predicates.add (relation._predicate);
            }
        }
        
        return predicates;
    }

    Object getSubject () {
        if (_subjectURI == null) {
            return _subjectBlank;
        } else {
            return _subjectURI;
        }
    }

    boolean hasBeenDeserialised () {
        return _deserialised != null;
    }
    
    void setDeserialisation (Description deserialised) {
        _deserialised = deserialised;
    }
    
    class URIObject {
        URI _predicate;
        URI _object;
        
        URIObject (URI predicate, URI object) {
            _predicate = predicate;
            _object = object;
        }
    }

    class BlankObject {
        URI _predicate;
        String _object;
        
        BlankObject (URI predicate, String object) {
            _predicate = predicate;
            _object = object;
        }
    }

    class LiteralObject {
        URI _predicate;
        Literal _object;
        
        LiteralObject (URI predicate, Literal object) {
            _predicate = predicate;
            _object = object;
        }
    }
}

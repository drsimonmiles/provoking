package uk.ac.kcl.inf.provoking.serialise.rdf;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import uk.ac.kcl.inf.provoking.model.ActedOnBehalfOf;
import uk.ac.kcl.inf.provoking.model.AlternateOf;
import uk.ac.kcl.inf.provoking.model.Attribute;
import uk.ac.kcl.inf.provoking.model.Description;
import uk.ac.kcl.inf.provoking.model.Document;
import uk.ac.kcl.inf.provoking.model.HadMember;
import uk.ac.kcl.inf.provoking.model.HadPrimarySource;
import uk.ac.kcl.inf.provoking.model.SpecializationOf;
import uk.ac.kcl.inf.provoking.model.Used;
import uk.ac.kcl.inf.provoking.model.util.AttributeHolder;
import uk.ac.kcl.inf.provoking.model.util.Identified;
import uk.ac.kcl.inf.provoking.model.util.Term;
import uk.ac.kcl.inf.provoking.model.util.TimestampedEdge;
import uk.ac.kcl.inf.provoking.serialise.SerialisationHint;

public class RDFSerialiser {
    private static URI RDF_TYPE = null;
    private static int _lastBlank = 0;
    private List<TriplesListener> _listeners;
    private Map<Description, String> _blankIDs;

    public RDFSerialiser (TriplesListener... listeners) {
        _listeners = new LinkedList<> (Arrays.asList (listeners));
        _blankIDs = new HashMap<> ();
        if (RDF_TYPE == null) {
            try {
                RDF_TYPE = new URI ("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
            } catch (URISyntaxException ex) {
            }
        }
    }

    private String blank (Description description) {
        String blank = _blankIDs.get (description);

        if (blank == null) {
            _lastBlank += 1;
            blank = "_" + _lastBlank;
            _blankIDs.put (description, blank);
        }

        return blank;
    }
    
    private void fireLiteral (URI subject, URI predicate, Object object) {
        for (TriplesListener listener : _listeners) {
            listener.triple (subject, predicate, object, object.getClass ().getSimpleName ());
        }
    }

    private void fire (URI subject, URI predicate, URI object) {
        for (TriplesListener listener : _listeners) {
            listener.triple (subject, predicate, object);
        }
    }

    private void fire (URI subject, URI predicate, String blankObject) {
        for (TriplesListener listener : _listeners) {
            listener.triple (subject, predicate, blankObject);
        }
    }

    private void fireLiteral (String blankSubject, URI predicate, Object object) {
        for (TriplesListener listener : _listeners) {
            listener.triple (blankSubject, predicate, object, object.getClass ().getSimpleName ());
        }
    }

    private void fire (String blankSubject, URI predicate, URI object) {
        for (TriplesListener listener : _listeners) {
            listener.triple (blankSubject, predicate, object);
        }
    }

    private void fire (String blankSubject, URI predicate, String blankObject) {
        for (TriplesListener listener : _listeners) {
            listener.triple (blankSubject, predicate, blankObject);
        }
    }
    
    private boolean isMinimal (Description description, Document document, Object... optionalArguments) {
        if (((AttributeHolder) description).hasAttributes ()) {
            return false;
        }
        for (Object argument : optionalArguments) {
            if (argument != null) {
                return false;
            }
        }
        if (SerialisationHint.wasExplicitlyIdentified (description, document)) {
            return false;
        }
        return true;
    }

    public void serialise (Document document) {
        for (Description description : document) {
            serialise (description, document);
        }
    }

    public void serialise (Description description, Document document) {
        // References do not have serialisations in this document
        if (description instanceof Identified && ((Identified) description).isReference ()) {
            return;
        }
        // Some relations are always binary
        if (description instanceof SpecializationOf) {
            serialise (((SpecializationOf) description).getSpecificEntity (), Term.specializationOf, ((SpecializationOf) description).getGeneralEntity ());
            return;
        }
        if (description instanceof AlternateOf) {
            serialise (((AlternateOf) description).getAlternateA (), Term.alternateOf, ((AlternateOf) description).getAlternateB ());
            return;
        }
        if (description instanceof HadMember) {
            serialise (((HadMember) description).getCollection (), Term.hadMember, ((HadMember) description).getMember ());
            return;
        }
        // Relations that are subtypes of other relations
        if (description instanceof HadPrimarySource) {
            serialise (((HadPrimarySource) description).getDerived (), Term.hadPrimarySource, ((HadPrimarySource) description).getDerivedFrom ());
        }
        // Potentially non-binary relations
        if (description instanceof ActedOnBehalfOf) {
            serialise (((ActedOnBehalfOf) description).getActer (), Term.actedOnBehalfOf, ((ActedOnBehalfOf) description).getOnBehalfOf ());
            if (isMinimal (description, document, ((ActedOnBehalfOf) description).getActivity ())) {
                return;
            }
            serialise (((ActedOnBehalfOf) description).getActer (), Term.qualifiedDelegation, description);
            serialise (description, Term.agent, ((ActedOnBehalfOf) description).getOnBehalfOf ());
            serialise (description, Term.activity, ((ActedOnBehalfOf) description).getActivity ());
        }
        if (description instanceof Used) {
            serialise (((Used) description).getUser (), Term.used, ((Used) description).getUsed ());
            if (isMinimal (description, document, ((Used) description).getTime ())) {
                return;
            }
            serialise (((Used) description).getUser (), Term.qualifiedUsage, description);
            serialise (description, Term.entity, ((Used) description).getUsed ());
        }
        // Record event timestamps
        if (description instanceof TimestampedEdge) {
            serialiseLiteral (description, Term.atTime, ((TimestampedEdge) description).getTime ());
        }
        // Record type information
        if (description instanceof Identified) {
            for (Term type : ((Identified) description).getClassTerms ()) {
                type (description, type);
            }
        }
        // Record attributes
        if (description instanceof AttributeHolder) {
            for (Attribute attribute : ((AttributeHolder) description).getAttributes ()) {
                if (attribute.getKey () instanceof URI && !attribute.getKey ().equals (Term.type.uri ())) {
                    if (attribute.getValue () instanceof URI) {
                        serialise (description, (URI) attribute.getKey (), (URI) attribute.getValue ());
                    } else {
                        serialiseLiteral (description, (URI) attribute.getKey (), attribute.getValue ());
                    }
                }
            }
        }
    }

    private void serialise (Description subject, Term predicate, Description object) {
        URI objectID;

        if (subject == null || object == null) {
            return;
        }
        objectID = uriID (object);
        if (objectID == null) {
            serialise (subject, predicate.uri (), blank (object));
        } else {
            serialise (subject, predicate.uri (), objectID);
        }
    }

    private void serialise (Description subject, URI predicate, URI objectID) {
        URI subjectID;

        if (subject == null || objectID == null) {
            return;
        }
        subjectID = uriID (subject);
        if (subjectID == null) {
            fire (blank (subject), predicate, objectID);
        } else {
            fire (subjectID, predicate, objectID);
        }
    }

    private void serialise (Description subject, URI predicate, String blankObject) {
        URI subjectID;

        if (subject == null || blankObject == null) {
            return;
        }
        subjectID = uriID (subject);
        if (subjectID == null) {
            fire (blank (subject), predicate, blankObject);
        } else {
            fire (subjectID, predicate, blankObject);
        }
    }

    private void serialiseLiteral (Description subject, Term predicate, Object objectLiteral) {
        serialiseLiteral (subject, predicate.uri (), objectLiteral);
    }
    
    private void serialiseLiteral (Description subject, URI predicate, Object objectLiteral) {
        URI subjectID;

        if (subject == null || objectLiteral == null) {
            return;
        }
        subjectID = uriID (subject);
        if (subjectID == null) {
            fireLiteral (blank (subject), predicate, objectLiteral);
        } else {
            fireLiteral (subjectID, predicate, objectLiteral);
        }
    }

    private void type (Description subject, Term term) {
        URI id = uriID (subject);

        if (id == null) {
            fire (blank (subject), RDF_TYPE, term.uri ());
        } else {
            fire (id, RDF_TYPE, term.uri ());
        }
    }

    private void type (String blank, Term term) {
        fire (blank, RDF_TYPE, term.uri ());
    }

    private URI uriID (Description identified) {
        Object id;

        if (identified instanceof Identified) {
            id = ((Identified) identified).getIdentifier ();
            if (id instanceof URI) {
                return (URI) id;
            }
        }
        return null;
    }
}

package uk.ac.kcl.inf.provoking.model.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public enum Term {
    Account, Activity, activity, Agent, agent, actedOnBehalfOf, alternateOf, 
    Association, atLocation, atTime, Attribution, Bundle, Collection, Communication, Delegation, 
    Derivation, EmptyCollection, End, endedAtTime, 
    Entity, entity, Generation, hadGeneration, hadMember, hadPlan, hadPrimarySource, hadRole, hadUsage,
    Invalidation, label, Location, location,
    Note, Organization,
    Person, Plan, PrimarySource, qualifiedAssociation, qualifiedAttribution, qualifiedCommunication,
    qualifiedDelegation, qualifiedDerivation, qualifiedEnd, qualifiedGeneration,
    qualifiedInvalidation, qualifiedPrimarySource, qualifiedRevision, qualifiedQuotation, qualifiedStart, qualifiedUsage,
    Revision, Role, role, Quotation, 
    SoftwareAgent, specializationOf, Start, startedAtTime, type, Usage, used, value,
    wasAssociatedWith,
    wasAttributedTo,
    wasDerivedFrom, wasEndedBy,
    wasGeneratedBy, wasInformedBy, wasInvalidatedBy, wasRevisionOf, wasQuotedFrom, wasStartedBy;
    public final static String PROV_NS = "http://www.w3.org/ns/prov#";

    public static boolean isProvTerm (URI uri) {
        return uri.toString ().startsWith (PROV_NS);
    }
    
    public static Term toTerm (URI fullName) {
        return valueOf (type.toString ().substring (PROV_NS.length ()));
    }
    
    public URI uri () {
        return getURISet ().uri (this);
    }

    private static class TermURIs {
        Map<Term, String> _uris = new HashMap<> ();
        
        public URI uri (Term term) {
            String uri = _uris.get (term);
            if (uri == null) {
                uri = PROV_NS + term.toString ();
                _uris.put (term, uri);
            }
            try {
                return new URI (uri);
            } catch (URISyntaxException ex) {
                return null;
            }
        }
    }
    private static TermURIs _singleton = null;

    private static TermURIs getURISet () {
        if (_singleton == null) {
            _singleton = new TermURIs ();
        }
        return _singleton;
    }
}

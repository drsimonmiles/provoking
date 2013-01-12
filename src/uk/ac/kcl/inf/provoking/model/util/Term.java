package uk.ac.kcl.inf.provoking.model.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public enum Term {
    Account, Activity, activity, Agent, agent, actedOnBehalfOf, alternateOf, 
    Association, atTime, Attribution, Bundle, Collection, Communication, Delegation, Derivation, EmptyCollection, End, endedAtTime, 
    Entity, entity, Generation, hadMember, hadPrimarySource, Invalidation, label, Location, location,
    Note, Organization,
    Person, Plan, PrimarySource, qualifiedAssociation, qualifiedDelegation, qualifiedUsage, 
    Revision, role, Quotation, 
    SoftwareAgent, specializationOf, Start, startedAtTime, type, Usage,
    wasAssociatedWith,
    wasAttributedTo,
    wasDerivedFrom,
    wasGeneratedBy, wasInformedBy, wasStartedByActivity, used, value;
    public final static String PROV_NS = "http://www.w3.org/ns/prov#";

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

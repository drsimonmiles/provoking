package uk.ac.kcl.inf.provoking.model.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public enum Term {
    Account, Activity, activity, Agent, agent, actedOnBehalfOf, alternateOf, 
    Association, Bundle, Collection, EmptyCollection, endedAtTime, 
    Entity, entity, hadOriginalSource, label, Location, location,
    Note, Organization,
    Person, Plan, PrimarySource, qualifiedAssociation, qualifiedUsage, 
    Revision, role, Quotation, 
    SoftwareAgent, startedAtTime, type, Usage,
    wasAssociatedWith,
    wasAttributedTo,
    wasDerivedFrom,
    wasGeneratedBy, wasInformedBy, wasStartedByActivity, used, value;
    public final static String PROV_NS = "http://www.w3.org/ns/prov#";

    public URI uri () {
        return getURISet ().uri (this);
    }

    private static class TermURIs {
        Map<Term, URI> _uris = new HashMap<> ();
        
        public URI uri (Term term) {
            URI uri = _uris.get (term);
            if (uri == null) {
                try {
                    uri = new URI (PROV_NS + term.toString ());
                } catch (URISyntaxException ex) {
                }
            }
            return uri;
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

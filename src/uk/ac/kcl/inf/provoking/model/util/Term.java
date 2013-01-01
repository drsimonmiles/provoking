package uk.ac.kcl.inf.provoking.model.util;

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

    public String uri () {
        return getURISet ().uri (this);
    }

    private static class TermURIs {
        Map<Term, String> _uris = new HashMap<> ();
        
        public String uri (Term term) {
            String uri = _uris.get (term);
            if (uri == null) {
                uri = PROV_NS + term.toString ();
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

package uk.ac.kcl.inf.provoking.model.rdf;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
/*import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.RepositoryResult;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFParseException;
import org.openrdf.sail.memory.MemoryStore;
import uk.ac.kcl.inf.provoking.model.Document;
import uk.ac.kcl.inf.provoking.model.Activity;
import uk.ac.kcl.inf.provoking.model.Agent;
import uk.ac.kcl.inf.provoking.model.Description;
import uk.ac.kcl.inf.provoking.model.Entity;
import uk.ac.kcl.inf.provoking.model.Term;
import uk.ac.kcl.inf.provoking.model.Used;*/

public class Loader {
/*    private Repository _store;
    private ValueFactory _values;
    private RepositoryConnection _connection;
    private Map<URI, Activity> _activities;
    private Map<URI, Agent> _agents;
    private Map<URI, Entity> _entities;
    private List<Description> _relations;

    public Loader () {
        try {
            _store = new SailRepository (new MemoryStore ());
            _store.initialize ();
            _values = _store.getValueFactory ();
        } catch (RepositoryException problem) {
        }
    }

    private static <T> T addElement (Map<URI, T> collection, URI uri, T newElement) {
        if (!collection.containsKey (uri)) {
            collection.put (uri, newElement);
            return newElement;
        } else {
            return collection.get (uri);
        }
    }

    private static <T> T getElement (Map<URI, T> collection, URI uri, T newElement) {
        return addElement (collection, uri, newElement);
    }

    public Document load (Path path, String baseURI, RDFFormat format) {
        try (InputStream in = Files.newInputStream (path)) {
            return load (in, baseURI, format);
        } catch (IOException | RDFParseException | RepositoryException problem) {
            return null;
        }
    }

    public Document load (URL path, String baseURI, RDFFormat format) {
        try (InputStream in = path.openStream ()) {
            return load (in, baseURI, format);
        } catch (IOException | RDFParseException | RepositoryException problem) {
            return null;
        }
    }    
    
    private Document load (InputStream in, String baseURI, RDFFormat format) throws IOException, RDFParseException, RepositoryException {
        try {
            _connection = _store.getConnection ();
            _connection.add (in, baseURI, format);

            _entities = new HashMap<> ();
            _activities = new HashMap<> ();
            _agents = new HashMap<> ();
            _relations = new LinkedList<> ();

            loadEntities ();
            loadActivities ();
            loadAgents ();
            loadUsed ();
            
            return new Document ().addAll (_entities.values (), _activities.values (), _agents.values (), _relations);
        } catch (IOException | RDFParseException | RepositoryException problem) {
            throw problem;
        } finally {
            try {
                _connection.close ();
            } catch (RepositoryException ex) {
            }
        }
    }

    private void loadActivities () throws RepositoryException {
        for (URI resource : getResourcesOfType (Term.Activity)) {
            addElement (_activities, resource, new Activity ());
        }
    }

    private void loadAgents () throws RepositoryException {
        for (URI resource : getResourcesOfType (Term.Agent)) {
            addElement (_agents, resource, new Agent ());
        }
    }

    private void loadEntities () throws RepositoryException {
        for (URI resource : getResourcesOfType (Term.Entity)) {
            addElement (_entities, resource, new Entity ());
        }
    }

    private void loadUsed () throws RepositoryException {
        for (Tuple<URI, URI> related : getRelated (Term.used)) {
            _relations.add (new Used (getElement (_activities, related._first, new Activity ()),
                                      getElement (_entities, related._second, new Entity ())));
        }
    }

    private BiList<URI, URI> getRelated (Term relation) throws RepositoryException {
        BiList<URI, URI> uris = new BiList<> ();
        RepositoryResult<Statement> results = _connection.getStatements (null, toOpenRDF (_values, relation), null, true);
        Statement statement;
        Resource subject;
        Value object;

        while (results.hasNext ()) {
            statement = results.next ();
            object = statement.getObject ();
            subject = statement.getSubject ();
            if (subject instanceof URI && object instanceof URI) {
                uris.add ((URI) subject, (URI) object);
            }
        }

        return uris;
    }

    private List<URI> getResourcesOfType (Term type) throws RepositoryException {
        List<URI> uris = new LinkedList<> ();
        RepositoryResult<Statement> results = _connection.getStatements (null, RDF.TYPE, toOpenRDF (_values, type), true);
        Statement statement;
        Resource subject;

        while (results.hasNext ()) {
            statement = results.next ();
            subject = statement.getSubject ();
            if (subject instanceof URI) {
                uris.add ((URI) subject);
            }
        }

        return uris;
    }

    public static URI toOpenRDF (ValueFactory values, Term term) {
        return toOpenRDF (values, term.uri ());
    }

    public static URI toOpenRDF (ValueFactory values, java.net.URI javaURI) {
        return values.createURI (javaURI.toString ());
    }*/
}

package uk.ac.kcl.inf.provoking.serialise.rdf.jena;

import com.hp.hpl.jena.datatypes.RDFDatatype;
import com.hp.hpl.jena.datatypes.xsd.XSDDateTime;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.util.FileManager;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import uk.ac.kcl.inf.provoking.model.Document;
import uk.ac.kcl.inf.provoking.serialise.DeserialisationException;
import uk.ac.kcl.inf.provoking.serialise.rdf.Language;
import uk.ac.kcl.inf.provoking.serialise.rdf.Literal;
import uk.ac.kcl.inf.provoking.serialise.rdf.RDFDeserialiser;

public class JenaDeserialiser {
    private RDFDeserialiser _deserialiser;

    public JenaDeserialiser () {
        _deserialiser = new RDFDeserialiser ();
    }
    
    /**
     * Builds a Document from previously read data and/or Jena model(s).
     * If called before any calls to read or deserialise, this will return an empty Document.
     * 
     * @return A Document containing the deserialised descriptions previously read from data files or Jena models
     * @throws DeserialisationException 
     */
    public Document build () throws DeserialisationException {
        return _deserialiser.build ();
    }

    /**
     * A convenience method for when a Document should be loaded from a single Jena model.
     * Use instead of deserialise+build.
     * 
     * @param model The model to load the PROV descriptions from
     * @return The Document deserialised
     */
    public Document load (Model model) throws DeserialisationException {
        deserialise (model);
        return build ();
    }
    
    /**
     * A convenience method for when a Document should be loaded from a single file or URI.
     * Use instead of read+build.
     * 
     * @param fileNameOrURI The file or URI to load the PROV descriptions from
     * @param baseURI The base URI to use when resolving the RDF
     * @param language The language in which the RDF is encoded
     * @return The Document deserialised
     */
    public Document load (String fileNameOrURI, String baseURI, Language language) throws DeserialisationException {
        read (fileNameOrURI, baseURI, language);
        return build ();
    }

    /**
     * A convenience method for when a Document should be loaded from a single String containing RDF data.
     * Use instead of readString+build.
     * 
     * @param rdfString The String containing an RDF document
     * @param baseURI The base URI to use when resolving the RDF
     * @param language The language in which the RDF is encoded
     * @return The Document deserialised
     */
    public Document loadString (String rdfString, String baseURI, Language language) throws DeserialisationException {
        readString (rdfString, baseURI, language);
        return build ();
    }

    /**
     * Deserialise PROV RDF data from a Jena model. Once all RDF data has been
     * read in, use the build method to create the Document.
     * 
     * @param model The model to load the PROV descriptions from
     */
    public void deserialise (Model model) throws DeserialisationException {
        for (Statement statement : model.listStatements ().toList ()) {
            deserialise (statement);
        }        
    }

    /**
     * Deserialise an individual statement from a model.
     * 
     * @param statement The RDF statement to deserialise
     * @throws DeserialisationException 
     */
    private void deserialise (Statement statement) throws DeserialisationException {
            Resource subject = statement.getSubject ();
            URI predicate = uri (statement.getPredicate ());
            RDFNode object = statement.getObject ();
            
            if (subject.isAnon ()) {
                if (object.isURIResource ()) {
                    _deserialiser.triple (blank (subject), predicate, uri (object));
                } else {
                    if (object.isAnon ()) {
                        _deserialiser.triple (blank (subject), predicate, object.toString ());
                    } else {
                        _deserialiser.triple (blank (subject), predicate, literal (object));
                    }
                }
            }
            if (subject.isURIResource ()) {
                if (object.isURIResource ()) {
                    _deserialiser.triple (uri (subject), predicate, uri (object));
                } else {
                    if (object.isAnon ()) {
                        _deserialiser.triple (uri (subject), predicate, blank (object));
                    } else {
                        _deserialiser.triple (uri (subject), predicate, literal (object));
                    }
                }
            }        
    }
    
    /**
     * Read some PROV RDF from a local or remote file (filename or URI).
     * Once all PROV data has been read, use build to create the Document.
     * 
     * @param fileNameOrURI The file or URI to load the PROV descriptions from
     * @param baseURI The base URI to use when resolving the RDF
     * @param language The language in which the RDF is encoded
     * @throws DeserialisationException 
     */
    public void read (String fileNameOrURI, String baseURI, Language language) throws DeserialisationException {
        read (FileManager.get ().open (fileNameOrURI), baseURI, language);
    }

    public void read (InputStream in, String baseURI, Language language) throws DeserialisationException {
        Model model = ModelFactory.createDefaultModel ();

        model.read (in, baseURI, getLanguageName (language));
        deserialise (model);
        model.close ();
    }
    
    /**
     * Read RDF PROV from a single String containing RDF data.
     * Once all PROV data has been read, use build to create the Document.
     * 
     * @param rdfString The String containing an RDF document
     * @param baseURI The base URI to use when resolving the RDF
     * @param language The language in which the RDF is encoded
     * @throws DeserialisationException 
     */
    public void readString (String rdfString, String baseURI, Language language) throws DeserialisationException {
        try {
            read (new ByteArrayInputStream (rdfString.getBytes ("UTF-8")), baseURI, language);
        } catch (UnsupportedEncodingException encodingProblem) {
            throw new DeserialisationException (encodingProblem);
        }
    }

    /**
     * Converts a Jena blank node into a String, as used by the store-indepenent RDF deserialiser.
     * 
     * @param blankNode The blank node to convert
     * @return The String name for the blank node
     */
    private String blank (RDFNode blankNode) {
        return blankNode.asResource ().getId ().getLabelString ();
    }

    /**
     * Return the Jena code for the given RDF encoding language.
     * 
     * @param language An RDF encoding language.
     * @return Jena's code for the given language.
     * @throws DeserialisationException 
     */
    private String getLanguageName (Language language) throws DeserialisationException {
        switch (language) {
            case rdfxml:
                return "RDF/XML";
            case ntriple:
                return "N-TRIPLE";
            case turtle:
                return "TURTLE";
            case n3:
                return "N3";
            default:
                throw new DeserialisationException ("RDF language " + language + " not supported by JenaIO");
        }        
    }

    /**
     * Convert a Jena literal node into a Literal object, as used by the store-independent RDF deserialiser.
     * 
     * @param literalNode The literal node to convert
     * @return The literal's store-independent representation
     */
    private Literal literal (RDFNode literalNode) {
        com.hp.hpl.jena.rdf.model.Literal node = literalNode.asLiteral ();
        RDFDatatype type = node.getDatatype ();
        Literal literal = new Literal (node.getValue ());
        
        if (literal._value instanceof XSDDateTime) {
            literal._value = ((XSDDateTime) literal._value).asCalendar ().getTime ();
        }
        
        return literal;
    }

    /**
     * Convert a Jena URI resource node into a URI object, as used by the store-independent RDF deserialiser.
     * 
     * @param uriResource The resource node to convert
     * @return The nodes store-independent representation as a URI
     */
    private URI uri (RDFNode uriResource) throws DeserialisationException {
        try {
            return new URI (uriResource.asResource ().getURI ());
        } catch (URISyntaxException nonURI) {
            throw new DeserialisationException (nonURI);
        }
    }
}

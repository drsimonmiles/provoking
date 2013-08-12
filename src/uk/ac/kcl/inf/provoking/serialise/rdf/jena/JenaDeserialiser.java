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
import java.io.Reader;
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
    
    public Document build () {
        return _deserialiser.build ();
    }
    
    public void deserialise (Model model) throws DeserialisationException {
        for (Statement statement : model.listStatements ().toList ()) {
            deserialise (statement);
        }        
    }

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
    
    public void read (String fileNameOrURI, String baseURI, Language language) throws DeserialisationException {
        read (FileManager.get ().open (fileNameOrURI), baseURI, language);
    }

    public void read (InputStream in, String baseURI, Language language) throws DeserialisationException {
        Model model = ModelFactory.createDefaultModel ();

        model.read (in, baseURI, getLanguageName (language));
        deserialise (model);
        model.close ();
    }
    
    public void readString (String rdfString, String baseURI, Language language) throws DeserialisationException, UnsupportedEncodingException {
        read (new ByteArrayInputStream (rdfString.getBytes ("UTF-8")), baseURI, language);
    }
    
    private String blank (RDFNode blankNode) {
        return blankNode.asResource ().getId ().getLabelString ();
    }

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
    
    private Literal literal (RDFNode literalNode) {
        com.hp.hpl.jena.rdf.model.Literal node = literalNode.asLiteral ();
        RDFDatatype type = node.getDatatype ();
        Literal literal = new Literal (node.getValue ());
        
        if (literal._value instanceof XSDDateTime) {
            literal._value = ((XSDDateTime) literal._value).asCalendar ().getTime ();
        }
        
        return literal;
    }

    private URI uri (RDFNode uriResource) throws DeserialisationException {
        try {
            return new URI (uriResource.asResource ().getURI ());
        } catch (URISyntaxException nonURI) {
            throw new DeserialisationException (nonURI);
        }
    }
}

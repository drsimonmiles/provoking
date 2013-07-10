package uk.ac.kcl.inf.provoking.builder;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import uk.ac.kcl.inf.provoking.model.Description;
import uk.ac.kcl.inf.provoking.model.Document;
import uk.ac.kcl.inf.provoking.model.Entity;
import uk.ac.kcl.inf.provoking.serialise.DeserialisationException;
import uk.ac.kcl.inf.provoking.serialise.rdf.Language;
import uk.ac.kcl.inf.provoking.serialise.rdf.jena.JenaDeserialiser;
import uk.ac.kcl.inf.provoking.serialise.rdf.turtle.TurtlePrinter;

public class OwnIDsTest {
    public OwnIDsTest () {
    }
    
    @BeforeClass
    public static void setUpClass () {
    }
    
    @AfterClass
    public static void tearDownClass () {
    }
    
    @Before
    public void setUp () {
    }
    
    @After
    public void tearDown () {
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void buildWithOwnIDs () throws IOException, DeserialisationException, URISyntaxException {
        ProvBuilder b = new ProvBuilder ("ex:", "http://www.inf.kcl.ac.uk/staff/simonm/provoking#");
        StringWriter writer = new StringWriter ();
        Document document;
        Description description;
        String serialisation;
        
        b.setPrefix ("dcterms:", "http://purl.org/dc/terms/");
        b.entity ("article", "dcterms:title=Crime rises in cities").idAbbr ("http://info.com/1");
        b.entity ("article").wasGeneratedBy ().activity ("compose");
        
        document = b.build ();
        description = b.getBookmarked ("article");
        assertNotNull (description);
        assertTrue (description instanceof Entity);
        assertEquals (((Entity) description).getIdentifier ().toString (), "http://info.com/1");

        TurtlePrinter out1 = new TurtlePrinter (writer);
        out1.serialise (document);
        out1.close ();
        serialisation = writer.getBuffer ().toString ();
        
        assertNotNull (serialisation);
        System.out.println (serialisation);
        assertTrue (serialisation.contains ("<http://info.com/1>"));
        assertTrue (serialisation.contains ("Crime rises"));
    }
}

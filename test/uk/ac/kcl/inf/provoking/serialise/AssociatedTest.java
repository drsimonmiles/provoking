package uk.ac.kcl.inf.provoking.serialise;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import org.junit.*;
import uk.ac.kcl.inf.provoking.builder.ProvBuilder;
import uk.ac.kcl.inf.provoking.model.Activity;
import uk.ac.kcl.inf.provoking.model.Agent;
import uk.ac.kcl.inf.provoking.model.Document;
import uk.ac.kcl.inf.provoking.model.WasAssociatedWith;
import uk.ac.kcl.inf.provoking.serialise.rdf.Language;
import uk.ac.kcl.inf.provoking.serialise.rdf.jena.JenaDeserialiser;
import uk.ac.kcl.inf.provoking.serialise.rdf.turtle.TurtlePrinter;

/**
 *
 * @author Simon Miles
 */
public class AssociatedTest {
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

    @Test
    public void serialiseWasAssociatedWith () throws IOException, DeserialisationException, URISyntaxException {
        Activity activity = new Activity (new URI ("http://www.example.com/activity1"));
        Agent agent = new Agent (new URI ("http://www.example.com/agent1"));
        WasAssociatedWith waw = new WasAssociatedWith (activity, agent);
        Document document = new Document ();
        JenaDeserialiser in = new JenaDeserialiser ();
        String original;
        
        document.addAll (activity, agent, waw);
        original = document.toString ();
        System.out.println (original);
        
        in.readString (original, null, Language.turtle);
        document = in.build ();
        
        System.out.println (document);
    }
}

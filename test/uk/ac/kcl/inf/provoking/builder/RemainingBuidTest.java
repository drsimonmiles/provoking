package uk.ac.kcl.inf.provoking.builder;

import java.io.File;
import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import uk.ac.kcl.inf.provoking.model.Document;
import uk.ac.kcl.inf.provoking.serialise.DeserialisationException;
import uk.ac.kcl.inf.provoking.serialise.rdf.turtle.TurtlePrinter;

public class RemainingBuidTest {
    public RemainingBuidTest () {
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
    public void buildRemaining () throws IOException, DeserialisationException {
        ProvBuilder b = new ProvBuilder ("ex:", "http://www.inf.kcl.ac.uk/staff/simonm/provoking#");
        String file1 = "remaining.ttl";
        Document document;
        
        b.setPrefix ("dcterms:", "http://purl.org/dc/terms/");
        b.setPrefix ("foaf:", "http://xmlns.com/foaf/0.1/");

        b.collection ("newStoreWithBundle").wasGeneratedBy ().activity ("addToStore");
        b.activity ("addToStore").used ().emptyCollection ("newStoreEmpty");
        b.activity ("addToStore").wasAssociatedWith ().softwareAgent ("spider");
        b.activity ("addToStore").wasStartedBy ().entity ("uploadStart").wasInfluencedBy ().entity ("ftpSpecs");
        b.activity ("addToStore").wasEndedBy ().entity ("uploadEnd");
        b.activity ("addToStore").wasInformedBy ().activity ("priorUploadCompletion");
        b.activity ("addToStore").used ().bundle ("*1", "dcterms:creator=Simon").idAbbr ("ex:aRemoteBundle");
        b.bundle ("*1").hadPrimarySource ().entity ("oldArchive").wasInvalidatedBy ().activity ("archiveDecommission").location ("ex:KCL");
        b.collection ("newStoreWithBundle").hadMember ().bundle ("*1");
       
        document = b.build ();

        TurtlePrinter out1 = new TurtlePrinter (new File (file1));
        out1.serialise (document);
        out1.close ();
    }
}

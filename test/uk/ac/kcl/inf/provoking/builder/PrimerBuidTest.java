package uk.ac.kcl.inf.provoking.builder;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import uk.ac.kcl.inf.provoking.model.Document;
import uk.ac.kcl.inf.provoking.model.rdf.RDFSerialiser;
import uk.ac.kcl.inf.provoking.model.rdf.TriplesListener;

public class PrimerBuidTest {
    public PrimerBuidTest () {
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
    public void buildPrimer () {
        ProvBuilder b = new ProvBuilder ("ex:", "http://www.inf.kcl.ac.uk/staff/simonm/provoking#");
        Document document;
        
        b.setPrefix ("dcterms:", "http://purl.org/dc/terms/");
        b.setPrefix ("foaf:", "http://xmlns.com/foaf/0.1/");
        
        b.entity ("article", "dcterms:title=Crime rises in cities");
        b.entity ("composition").wasGeneratedBy ().activity ("compose");
        b.activity ("compose").used ().entity ("dataSet1");
        b.activity ("compose").used ().entity ("regionList");
        b.entity ("chart1").wasGeneratedBy ().activity ("illustrate").used ().entity ("composition");
        
        document = b.build ();
        
        new RDFSerialiser (new TriplesListener () {
            @Override
            public void triple (String subject, String predicate, String object) {
                System.out.println (subject + " " + predicate + " " + object);
            }
        }).serialise (document);
    }
}

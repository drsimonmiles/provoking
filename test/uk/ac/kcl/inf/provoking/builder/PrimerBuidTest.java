package uk.ac.kcl.inf.provoking.builder;

import java.net.URI;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import uk.ac.kcl.inf.provoking.model.Document;
import uk.ac.kcl.inf.provoking.serialise.rdf.RDFSerialiser;
import uk.ac.kcl.inf.provoking.serialise.rdf.TriplesListener;

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
        
        b.activity ("compose").wasAssociatedWith ().person ("derek", "foaf:givenName=Derek", "foaf:mbox=mailto:derek@example.org");
        
        b.entity ("blogEntry").wasQuotedFrom ().entity ("article");
        b.entity ("articleV1").specializationOf ().entity ("article");
        b.entity ("articleV2").specializationOf ().entity ("article");
        b.entity ("articleV2").alternateOf ().entity ("articleV1");
        
        document = b.build ();
        
        new RDFSerialiser (new TriplesListener () {
            @Override
            public void triple (URI subject, URI predicate, URI object) {
                System.out.println ("<" + subject + "> <" + predicate + "> <" + object + ">");
            }

            @Override
            public void triple (URI subject, URI predicate, Object objectValue, String objectType) {
                System.out.println ("<" + subject + "> <" + predicate + "> \"" + objectValue + "\"");
            }

            @Override
            public void triple (String blankSubject, URI predicate, URI object) {
                System.out.println (blankSubject + " <" + predicate + "> <" + object + ">");
            }

            @Override
            public void triple (String blankSubject, URI predicate, Object objectValue, String objectType) {
                System.out.println (blankSubject + " <" + predicate + "> \"" + objectValue + "\"");
            }

            @Override
            public void triple (URI subject, URI predicate, String blankObject) {
                System.out.println ("<" + subject + "> <" + predicate + "> " + blankObject);
            }

            @Override
            public void triple (String blankSubject, URI predicate, String blankObject) {
                System.out.println (blankSubject + " <" + predicate + "> " + blankObject);
            }
        }).serialise (document);
    }
}

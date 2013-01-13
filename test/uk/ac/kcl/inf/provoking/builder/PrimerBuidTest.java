package uk.ac.kcl.inf.provoking.builder;

import java.io.PrintWriter;
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
import uk.ac.kcl.inf.provoking.serialise.rdf.turtle.TurtlePrinter;

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

        b.entity ("composition").wasGeneratedBy ("prov:hadRole=ex:composedData").activity ("compose");
        b.activity ("compose").used ("prov:hadRole=ex:dataToCompose").entity ("dataSet1");
        b.activity ("compose").used ("prov:hadRole=ex:regionsToAggregateBy").entity ("regionList");
        b.entity ("chart1").wasGeneratedBy ().activity ("illustrate").used ().entity ("composition");

        b.activity ("compose").wasAssociatedWith ("prov:hadRole=ex:analyst").
                person ("derek", "foaf:givenName=Derek", "foaf:mbox=mailto:derek@example.org");
        b.activity ("illustrate").wasAssociatedWith ().person ("derek");
        b.person ("derek").actedOnBehalfOf ().organization ("chartgen", "foaf:name=Chart Generators Inc");
        b.entity ("chart1").wasAttributedTo ().person ("derek");
 
        b.entity ("dataSet2").wasRevisionOf ().entity ("dataSet1");
        b.entity ("chart2").wasDerivedFrom ().entity ("dataSet2");
        b.entity ("chart2").wasRevisionOf ().entity ("chart1");
        
        b.activity (b.xsdDate ("2012-03-31T09:21:00"), b.xsdDate ("2012-04-01T15:21:00"), "correct");
        b.activity ("correct").wasAssociatedWith ("correcting").person ("edith").
                where ("correcting").plan ("instructions");
        b.entity ("dataSet2").wasGeneratedBy ().activity ("correct");

        b.entity ("chart1").wasGeneratedBy (b.xsdDate ("2012-03-02T10:30:00")).activity ("compile1");
        b.entity ("chart2").wasGeneratedBy (b.xsdDate ("2012-04-01T15:21:00")).activity ("compile2");
        
        b.entity ("blogEntry").wasQuotedFrom ().entity ("article");
        b.entity ("articleV1").specializationOf ().entity ("article");
        b.entity ("articleV2").specializationOf ().entity ("article");
        b.entity ("articleV2").alternateOf ().entity ("articleV1");
        
        document = b.build ();
        
        new TurtlePrinter (new PrintWriter (System.out, true)).serialise (document);
        /*new RDFSerialiser (new TriplesListener () {
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
        }).serialise (document);*/
    }
}

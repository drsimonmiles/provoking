package uk.ac.kcl.inf.provoking.builder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import uk.ac.kcl.inf.provoking.model.Document;
import uk.ac.kcl.inf.provoking.serialise.DeserialisationException;
import uk.ac.kcl.inf.provoking.serialise.rdf.Language;
import uk.ac.kcl.inf.provoking.serialise.rdf.jena.JenaDeserialiser;
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
    public void buildPrimer () throws IOException, DeserialisationException {
        ProvBuilder b = new ProvBuilder ("ex:", "http://www.inf.kcl.ac.uk/staff/simonm/provoking#");
        String file1 = "test.ttl", file2 = "test2.ttl";
        JenaDeserialiser in = new JenaDeserialiser ();
        Document document;
        
        b.setPrefix ("dcterms:", "http://purl.org/dc/terms/");
        b.setPrefix ("foaf:", "http://xmlns.com/foaf/0.1/");
        
        b.entity ("article", "dcterms:title=Crime rises in cities");

        b.entity ("composition").wasGeneratedBy ("*1").activity ("compose");
        b.activity ("compose").used ("*2").entity ("dataSet1");
        b.activity ("compose").used ("*3").entity ("regionList");
        b.entity ("chart1").wasGeneratedBy ().activity ("illustrate").used ().entity ("composition");

        b.activity ("compose").wasAssociatedWith ("*4").
                person ("derek", "foaf:givenName=Derek", "foaf:mbox=mailto:derek@example.org");
        b.activity ("illustrate").wasAssociatedWith ().person ("derek");
        b.person ("derek").actedOnBehalfOf ().organization ("chartgen", "foaf:name=Chart Generators Inc");
        b.entity ("chart1").wasAttributedTo ().person ("derek");

        b.where ("*1").role ("ex:composedData");
        b.where ("*2").role ("ex:dataToCompose");
        b.where ("*3").role ("ex:regionsToAggregateBy");
        b.where ("*4").role ("ex:analyst");
        
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

        TurtlePrinter out1 = new TurtlePrinter (new File (file1));
        out1.serialise (document);
        out1.close ();
        
        in.read (file1, null, Language.turtle);
        document = in.build ();

        TurtlePrinter out2 = new TurtlePrinter (new File (file2));
        out2.serialise (document);
        out2.close ();
    }
}

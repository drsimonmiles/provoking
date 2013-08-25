package uk.ac.kcl.inf.provoking.model.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;
import uk.ac.kcl.inf.provoking.builder.ProvBuilder;
import uk.ac.kcl.inf.provoking.model.Description;
import uk.ac.kcl.inf.provoking.model.Document;
import uk.ac.kcl.inf.provoking.model.Entity;
import uk.ac.kcl.inf.provoking.model.Organization;
import uk.ac.kcl.inf.provoking.model.WasAttributedTo;
import uk.ac.kcl.inf.provoking.serialise.DeserialisationException;
import uk.ac.kcl.inf.provoking.serialise.rdf.Language;
import uk.ac.kcl.inf.provoking.serialise.rdf.jena.JenaDeserialiser;

public class EquivalenceTest {
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
    public void equivalenceTest1 () throws URISyntaxException, DeserialisationException {
        Entity entity = new Entity (new URI ("http://www.example.com/entity1"));
        Organization agent1 = new Organization (new URI ("http://www.example.com/agent1"));
        WasAttributedTo wat = new WasAttributedTo (entity, agent1);
        Document document1 = new Document (), document2;
        JenaDeserialiser in = new JenaDeserialiser ();
        EquivalenceChecker check = new EquivalenceChecker ();
        String original;

        document1.addAll (entity, agent1, wat);
        original = document1.toString ();
        document2 = in.loadString (original, null, Language.turtle);
        
        assertTrue (check.equivalentDocuments (document1, document2));
    }

    @Test
    public void equivalenceTest2 () throws URISyntaxException, DeserialisationException {
        ProvBuilder b = new ProvBuilder ("ex:", "http://www.inf.kcl.ac.uk/staff/simonm/provoking#");
        JenaDeserialiser in = new JenaDeserialiser ();
        EquivalenceChecker check = new EquivalenceChecker ();
        Document document1, document2;
        String original;

        b.entity ("entity").wasAttributedTo ().organization ("agent");
        document1 = b.build ();
        original = document1.toString ();
        document2 = in.loadString (original, null, Language.turtle);
        
        assertTrue (check.equivalentDocuments (document1, document2));
    }
    
    @Test
    public void equivalenceTest3 () throws DeserialisationException {
        ProvBuilder b = new ProvBuilder ("ex:", "http://www.inf.kcl.ac.uk/staff/simonm/provoking#");
        JenaDeserialiser in = new JenaDeserialiser ();
        EquivalenceChecker check = new EquivalenceChecker ();
        List<Description> inequivalences = new LinkedList<> ();
        Document document1, document2;
        String original;
        boolean equivalent;
        
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
        
        document1 = b.build ();
        original = document1.toString ();
        document2 = in.loadString (original, null, Language.turtle);
        
        equivalent = check.equivalentDocuments (document1, document2, inequivalences);
        System.out.println (inequivalences);
        assertTrue (equivalent);
    }
}

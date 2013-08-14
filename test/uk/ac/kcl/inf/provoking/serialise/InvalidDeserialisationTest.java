package uk.ac.kcl.inf.provoking.serialise;

import java.io.IOException;
import java.net.URISyntaxException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import uk.ac.kcl.inf.provoking.model.Document;
import uk.ac.kcl.inf.provoking.serialise.rdf.Language;
import uk.ac.kcl.inf.provoking.serialise.rdf.jena.JenaDeserialiser;

public class InvalidDeserialisationTest {
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
    public void invalidClass () throws IOException, DeserialisationException, URISyntaxException {
        String original = "@prefix prov: <http://www.w3.org/ns/prov#> .\n"
                + "@prefix ex: <http://www.inf.kcl.ac.uk/staff/simonm/provoking#> .\n"
                + "@prefix dcterms: <http://purl.org/dc/terms/> .\n"
                + "@prefix foaf: <http://xmlns.com/foaf/0.1/> .\n"
                + "@prefix xsd: <http://www.w3.org/2001/XMLSchema#> .\n\n"
                + "ex:abc a prov:Entity .\n"
                + "ex:def a prov:Agent . \n"
                + "ex:abc prov:wasAssociatedWith ex:def .\n";
        JenaDeserialiser in = new JenaDeserialiser ();
        Document document;

        try {
            in.readString (original, null, Language.turtle);
            document = in.build ();
            System.out.println (document);
            throw new RuntimeException ("Failed test");
        } catch (DeserialisationException problem) {
            System.out.println (problem.getMessage ());
        }
    }
}

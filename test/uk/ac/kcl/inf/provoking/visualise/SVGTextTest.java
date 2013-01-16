/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kcl.inf.provoking.visualise;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.bridge.DocumentLoader;
import org.apache.batik.bridge.GVTBuilder;
import org.apache.batik.bridge.UserAgent;
import org.apache.batik.bridge.UserAgentAdapter;
import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.apache.batik.dom.svg.SVGOMTextElement;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGRect;

/**
 *
 * @author Simon
 */
public class SVGTextTest {
    public SVGTextTest () {
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
    
    @Test
    public void hello() throws FileNotFoundException, TranscoderException, IOException {
            DOMImplementation impl = SVGDOMImplementation.getDOMImplementation();
        String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
        Document document = impl.createDocument (svgNS, "svg", null);
        Element root = document.getDocumentElement();
        root.setAttributeNS(null, "width", "450");
        root.setAttributeNS(null, "height", "500");

        /*
        Element e;
        e = document.createElementNS(svgNS, "rect");
        e.setAttributeNS(null, "x", "10");
        e.setAttributeNS(null, "y", "10");
        e.setAttributeNS(null, "width", "200");
        e.setAttributeNS(null, "height", "300");
        e.setAttributeNS(null, "style", "fill:red;stroke:black;stroke-width:4");
        root.appendChild(e);*/

        Element text;
        // <text x="0" y="15" fill="red">Simon</text>
        text = document.createElementNS(svgNS, "text");
        text.setAttributeNS(null, "x", "0");
        text.setAttributeNS(null, "y", "7");
        text.setAttributeNS(null, "fill", "red");
        text.appendChild (document.createTextNode ("Simon"));
        root.appendChild(text);
        
        UserAgent userAgent;
        DocumentLoader loader;
        BridgeContext ctx;
        GVTBuilder builder;
        GraphicsNode rootGN;
        
        userAgent = new UserAgentAdapter();
        loader = new DocumentLoader(userAgent);
        ctx = new BridgeContext(userAgent, loader);
        ctx.setDynamicState(BridgeContext.DYNAMIC);
        builder = new GVTBuilder();
        rootGN = builder.build(ctx, document);
        
        SVGOMTextElement textElem = (SVGOMTextElement)text;
        SVGRect bbox = textElem.getBBox();
        
        System.out.println ("Height: " + bbox.getHeight ());
        System.out.println ("Width: " + bbox.getWidth ());
        
        /*JPEGTranscoder t = new JPEGTranscoder();
        t.addTranscodingHint(JPEGTranscoder.KEY_QUALITY, new Float(.8));        
        
        TranscoderInput input = new TranscoderInput(document);
        OutputStream ostream = new FileOutputStream("out.jpg");
        TranscoderOutput output = new TranscoderOutput(ostream);

        // Perform the transcoding.
        t.transcode(input, output);
        ostream.flush();
        ostream.close();*/
    }
}

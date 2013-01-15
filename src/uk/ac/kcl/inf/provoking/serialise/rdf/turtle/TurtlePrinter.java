package uk.ac.kcl.inf.provoking.serialise.rdf.turtle;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URI;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.DatatypeConverter;
import uk.ac.kcl.inf.provoking.model.Document;
import uk.ac.kcl.inf.provoking.serialise.SerialisationHint;
import uk.ac.kcl.inf.provoking.serialise.SerialisationHintType;
import uk.ac.kcl.inf.provoking.serialise.rdf.Literal;
import uk.ac.kcl.inf.provoking.serialise.rdf.RDFSerialiser;
import uk.ac.kcl.inf.provoking.serialise.rdf.TriplesListener;

public class TurtlePrinter {
    private final PrintWriter _output;
    private final TurtlePrinterTriplesListener _listener;
    private final RDFSerialiser _serialiser;
    private final Map<String, String> _prefixes;

    public TurtlePrinter (Writer output) {
        _output = new PrintWriter (output);
        _listener = new TurtlePrinterTriplesListener ();
        _serialiser = new RDFSerialiser (_listener);
        _prefixes = new HashMap<> ();
    }

    public TurtlePrinter (File output) throws IOException {
        this (new FileWriter (output));
    }

    public void close () {
        _output.close ();
    }
    
    public void serialise (Document document) {
        boolean xsd = false;

        _prefixes.clear ();
        for (SerialisationHint hint : document.getSerialisationHints ()) {
            if (hint.type == SerialisationHintType.namespacePrefix) {
                _output.print ("@prefix ");
                _output.print (hint.arguments[0].toString ());
                _output.print (" <");
                _output.print (hint.arguments[1].toString ());
                _output.println ("> .");
                _prefixes.put (hint.arguments[1].toString (), hint.arguments[0].toString ());
                if (hint.arguments[0].toString ().equals ("xsd")) {
                    xsd = true;
                }
            }
        }
        if (!xsd) {
            _output.println ("@prefix xsd: <http://www.w3.org/2001/XMLSchema#> .");
        }
        _output.println ();
        _serialiser.serialise (document);
    }

    class TurtlePrinterTriplesListener implements TriplesListener {
        public void blank (String blank) {
            _output.print (blank);
            _output.print (" ");
        }

        public void literal (Literal object) {
            Object value = object._value;
            Calendar dateTime;

            if (value instanceof String) {
                _output.print ("\"");
                _output.print (value);
                _output.print ("\"");
                _output.print ("^^xsd:string");
                return;
            }
            if (value instanceof Date) {
                dateTime = Calendar.getInstance ();
                dateTime.setTime ((Date) value);
                _output.print ("\"");
                _output.print (DatatypeConverter.printDateTime (dateTime));
                _output.print ("\"");
                _output.print ("^^xsd:dateTime");
                return;
            }
            _output.print ("\"");
            _output.print (value);
            _output.print ("\"");
        }

        public void stop () {
            _output.println (".");
        }

        @Override
        public void triple (URI subject, URI predicate, URI object) {
            uri (subject);
            uri (predicate);
            uri (object);
            stop ();
        }

        @Override
        public void triple (URI subject, URI predicate, String blankObject) {
            uri (subject);
            uri (predicate);
            blank (blankObject);
            stop ();
        }

        @Override
        public void triple (URI subject, URI predicate, Literal object) {
            uri (subject);
            uri (predicate);
            literal (object);
            stop ();
        }

        @Override
        public void triple (String blankSubject, URI predicate, URI object) {
            blank (blankSubject);
            uri (predicate);
            uri (object);
            stop ();
        }

        @Override
        public void triple (String blankSubject, URI predicate, String blankObject) {
            blank (blankSubject);
            uri (predicate);
            blank (blankObject);
            stop ();
        }

        @Override
        public void triple (String blankSubject, URI predicate, Literal object) {
            blank (blankSubject);
            uri (predicate);
            literal (object);
            stop ();
        }

        private void uri (URI uri) {
            String text = uri.toString ();

            if (text.equals ("http://www.w3.org/1999/02/22-rdf-syntax-ns#type")) {
                _output.print ("a ");
                return;
            }
            for (String vocabulary : _prefixes.keySet ()) {
                if (text.startsWith (vocabulary)) {
                    _output.print (_prefixes.get (vocabulary));
                    _output.print (text.substring (vocabulary.length ()));
                    _output.print (" ");
                    return;
                }
            }
            _output.print ("<");
            _output.print (uri.toString ());
            _output.print ("> ");
        }
    }
}

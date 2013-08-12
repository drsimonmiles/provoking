package uk.ac.kcl.inf.provoking.model;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import uk.ac.kcl.inf.provoking.serialise.SerialisationHint;
import uk.ac.kcl.inf.provoking.serialise.rdf.turtle.TurtlePrinter;

public class Document extends LinkedList<Description> {
    private Collection<SerialisationHint> _hints;
    
    public Document (Description... elements) {
        addAll (elements);
        _hints = new LinkedList<SerialisationHint> ();
    }
    
    public Document addAll (Description... elements) {
        addAll (Arrays.asList (elements));
        return this;
    }

    public Document addAll (Collection<? extends Description>... elementSets) {
        for (Collection<? extends Description> elementSet : elementSets) {
            addAll (elementSet);
        }
        return this;
    }
    
    public void addSerialisationHint (SerialisationHint hint) {
        _hints.add (hint);
    }
    
    public Collection<SerialisationHint> getSerialisationHints () {
        return _hints;
    }
    
    @Override
    public String toString () {
        StringWriter output = new StringWriter ();
        TurtlePrinter out1 = new TurtlePrinter (output);
        
        out1.serialise (this);
        out1.close ();
        
        return output.toString ();
    }
}

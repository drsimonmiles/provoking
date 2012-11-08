package uk.ac.kcl.inf.provoking.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

public class Document extends HashSet<Description> {
    public Document (Description... elements) {
        addAll (elements);
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
}

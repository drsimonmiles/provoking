package uk.ac.kcl.inf.provoking.model.rdf;

import java.util.LinkedList;

class BiList<Type1, Type2> extends LinkedList<Tuple<Type1, Type2>> {
    void add (Type1 first, Type2 second) {
        add (new Tuple<> (first, second));
    }
}

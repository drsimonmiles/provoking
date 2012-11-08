package uk.ac.kcl.inf.provoking.model.rdf;

class Tuple<Type1, Type2> {
    Type1 _first;
    Type2 _second;

    Tuple (Type1 first, Type2 second) {
        _first = first;
        _second = second;
    }
}

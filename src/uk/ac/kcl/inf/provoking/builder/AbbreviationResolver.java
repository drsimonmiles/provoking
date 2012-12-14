package uk.ac.kcl.inf.provoking.builder;

public interface AbbreviationResolver {
    boolean appliesTo (String abbreviation);
    Object resolve (String abbreviation);
}

package uk.ac.kcl.inf.provoking.builder;

/**
 * Text abbreviations can be used in building. For examples, "ex:myentity"
 * may be an abbreviation for the URL http://www.example.com#entity", while
 * "/Yesterday/" might be an abbreviation for a Date object denoting yesterday.
 * An AbbreviationResolver is used in building to convert from an abbreviation
 * to the resolved form.
 * 
 * Resolvers must be registered with the ProvBuilder
 * using the addResolver method in order to be applied. For the common case
 * of resolving a prefixed qualified name to a URI, the setPrefix method is
 * an convenient alternative that creates and registers the appropriate resolver.
 * 
 * @author Simon Miles
 */
public interface AbbreviationResolver {
    /**
     * Should return true if this resolver can resolve the given string.
     * 
     * @param abbreviation The potentially abbreviated text.
     * @return True if this resolver can resolve the abbreviated text, or false otherwise.
     */
    boolean appliesTo (String abbreviation);
    
    /**
     * For a string this resolver can resolve, return the resolved form.
     * 
     * @param abbreviation The abbreviated text.
     * @return The resolved form of the abbreviated text, if this resolver can resolve the abbreviation, or an undefined value otherwise.
     * @throws ProvBuildException If the attempt to resolve failed, usually due to the text being malformed in some way.
     */
    Object resolve (String abbreviation) throws ProvBuildException;
}

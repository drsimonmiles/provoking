package uk.ac.kcl.inf.provoking.builder;

/**
 * Indicates a failure to execute a build command in using ProvBuilder.
 * 
 * @author Simon Miles
 */
public class ProvBuildException extends RuntimeException {
    /**
     * Create an exception with a given explanation.
     * 
     * @param message Explanation of the exception.
     */
    public ProvBuildException (String message) {
        super (message);
    }
    
    /**
     * Create an exception with a given cause.
     * 
     * @param cause Cause (prior exception) leading to this exception.
     */
    public ProvBuildException (Throwable cause) {
        super (cause);
    }

    /**
     * Create an exception with a given cause and explanation.
     * 
     * @param cause Cause (prior exception) leading to this exception.
     * @param message Explanation of the exception.
     */
    public ProvBuildException (String message, Throwable cause) {
        super (message, cause);
    }
}

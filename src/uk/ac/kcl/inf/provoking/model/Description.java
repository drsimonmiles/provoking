package uk.ac.kcl.inf.provoking.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import uk.ac.kcl.inf.provoking.model.util.Identified;

public abstract class Description {
    /**
     * Creates a tabular string representation of the given description.
     * 
     * @param description The description object
     * @param attributes The description's attributes or null if none
     * @param parameters The names of the description's additional arguments, or null if none
     * @param arguments The values of the description's additional arguments, in the same order as the parameter names
     * @return 
     */
    protected static String toString (Description description, Set<Attribute> attributes, String[] parameters, Object... arguments) {
        StringBuilder text = new StringBuilder ();
        String argString;

        text.append (description.getClass ().getSimpleName ());
        if (description instanceof Identified && ((Identified) description).getIdentifier () != null) {
            text.append (" (" + ((Identified) description).getIdentifier () + ")");
        }
        text.append (" {");
        if (parameters != null) {
            for (int index = 0; index < parameters.length; index += 1) {
                if (arguments[index] != null) {
                    text.append ("\n ");
                    text.append (parameters[index]).append (": ");
                    argString = indent (arguments[index].toString (), parameters[index].length () + 3);
                    text.append (argString);
                }
            }
        }
        if (attributes != null) {
        for (Attribute attribute : attributes) {
            text.append ("\n ");
            text.append (attribute.getKey ()).append (": ");
            argString = indent (attribute.getValue ().toString (), attribute.getKey ().toString ().length () + 3);
            text.append (argString);
        }
        }
        text.append ("\n}");

        return text.toString ();
    }
    
    protected String toString (List<Description> descriptions) {
        StringBuilder text = new StringBuilder ();
        boolean first = true;
        
        for (Description description : descriptions) {
            if (!first) {
                text.append ("\n");
            }
            text.append (description);
            first = false;
        }
        
        return text.toString ();
    }

    private static String indent (String original, int amount) {
        int from = 0;
        int newline = original.indexOf ("\n", from);
        String indent = "";

        while (amount > 0) {
            indent += " ";
            amount -= 1;
        }
        while (newline >= 0) {
            original = original.substring (0, newline + 1) + indent + original.substring (newline + 1);
            from = newline + 1;
            newline = original.indexOf ("\n", from);
        }

        return original;
    }
}

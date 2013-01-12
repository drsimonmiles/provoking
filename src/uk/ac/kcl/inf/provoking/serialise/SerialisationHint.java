package uk.ac.kcl.inf.provoking.serialise;

import uk.ac.kcl.inf.provoking.model.Description;
import uk.ac.kcl.inf.provoking.model.Document;
import static uk.ac.kcl.inf.provoking.serialise.SerialisationHintType.*;
        
public class SerialisationHint {
    public SerialisationHintType type;
    public Object[] arguments;
    
    public SerialisationHint (SerialisationHintType hintType, Object... hintArguments) {
        type = hintType;
        arguments = hintArguments;
    }
    
    public static boolean wasExplicitlyIdentified (Description description, Document document) {
        for (SerialisationHint hint : document.getSerialisationHints ()) {
            if (hint.type == explicitlyIdentified && hint.arguments[0] == description) {
                return true;
            }
        }
        return false;
    }
}

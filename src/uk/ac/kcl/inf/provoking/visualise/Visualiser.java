package uk.ac.kcl.inf.provoking.visualise;

import java.util.HashMap;
import java.util.Map;
import uk.ac.kcl.inf.provoking.model.Activity;
import uk.ac.kcl.inf.provoking.model.Agent;
import uk.ac.kcl.inf.provoking.model.Description;
import uk.ac.kcl.inf.provoking.model.Document;
import uk.ac.kcl.inf.provoking.model.Entity;
import uk.ac.kcl.inf.provoking.model.WasAssociatedWith;
import uk.ac.kcl.inf.provoking.model.util.AttributeHolder;
import uk.ac.kcl.inf.provoking.model.util.Identified;
import static uk.ac.kcl.inf.provoking.visualise.PlacementType.*;

public class Visualiser {
    private Document _document;
    private Map<Description, Placement> _placements;
    private Map<String, String> _prefixes;

    public Visualiser (Document document) {
        _document = document;
        _placements = new HashMap<> ();
        _prefixes = new HashMap<> ();
        arrange ();
    }

    private void arrange () {
        initialise ();
        timeline ();
        disentangle ();
    }

    private boolean cross (Placement leftNode1, Placement rightNode1,
                           Placement leftNode2, Placement rightNode2) {
        if (leftNode1 == null || rightNode1 == null || leftNode2 == null || rightNode2 == null) {
            return false;
        }
        if (leftNode1._x >= rightNode2._x || leftNode2._x >= rightNode1._x) {
            return false;
        }
        if (leftNode1._y < leftNode2._y && rightNode1._y > rightNode2._y) {
            return true;
        }
        if (leftNode1._y > leftNode2._y && rightNode1._y < rightNode2._y) {
            return true;
        }
        return false;
    }

    private boolean cross (Placement edge1, Placement edge2) {
        Placement cause1 = getCause (edge1);
        Placement effect1 = getEffect (edge1);
        Placement cause2 = getCause (edge2);
        Placement effect2 = getEffect (edge2);

        return cross (cause1, effect1, cause2, effect2);
    }

    private void disentangle () {
        boolean changed = false;
        int tries = _placements.size ();
        int y;

        for (int count = 1; count <= tries; count += 1) {
            for (Placement placement1 : _placements.values ()) {
                for (Placement placement2 : _placements.values ()) {
                    if (cross (placement1, placement2)) {
                        y = getEffect (placement1)._y;
                        getEffect (placement1)._y = getEffect (placement2)._y;
                        getEffect (placement2)._y = y;
                        changed = true;
                    }
                }
            }
            if (!changed) {
                return;
            }
        }
    }

    private Placement getCause (Placement edge) {
        return getPlacement (Graphs.getCause (edge._description));
    }

    private Placement getEffect (Placement edge) {
        return getPlacement (Graphs.getEffect (edge._description));
    }

    private int getMaxX () {
        int max = 0;

        for (Placement placement : _placements.values ()) {
            if (placement._x > max) {
                max = placement._x;
            }
        }

        return max;
    }

    private int getMaxY () {
        int max = 0;

        for (Placement placement : _placements.values ()) {
            if (placement._y > max) {
                max = placement._y;
            }
        }

        return max;
    }

    private Placement getPlacement (Description description) {
        if (description == null) {
            return null;
        } else {
            return _placements.get (description);
        }
    }

    private void initialise () {
        int y = 0;

        for (Description description : _document) {
            if (description instanceof Agent || description instanceof Entity || description instanceof Activity) {
                _placements.put (description, new Placement (description, 0, y, node));
            } else {
                _placements.put (description, new Placement (description, 0, y, edge));
            }
            y += 1;
            /*if (description instanceof AttributeHolder && ((AttributeHolder) description).hasAttributes ()) {
             _placements.put (description, new Placement (description, 0, y, annotation));
             y += 1;
             _placements.put (description, new Placement (description, 0, y, annotationEdge));
             y += 1;
             }
             if (description instanceof WasAssociatedWith && ((WasAssociatedWith) description).getPlan () != null) {
             _placements.put (description, new Placement (description, 0, y, plan));
             y += 1;
             _placements.put (description, new Placement (description, 0, y, planEdge));
             y += 1;
             }*/
        }
    }
    
    public void setPrefix (String prefix, String uri) {
        _prefixes.put (uri, prefix);
    }

    private void sizing () {
        Description description;
        Object id;
        String text;
        
        for (Placement placement : _placements.values ()) {
            description = placement._description;
            if (description instanceof Identified) {
                id = ((Identified) description).getIdentifier ();
            } else {
                id = null;
            }
            if (id != null) {
                text = id.toString ();
                for (String uri : _prefixes.keySet ()) {
                    if (text.startsWith (uri)) {
                        text = _prefixes.get (uri) + text.substring (uri.length ());
                    }
                }
            }
            
        }
    }
    
    private void timeline () {
        boolean changed;
        Placement cause, effect;

        do {
            changed = false;
            for (Placement placement : _placements.values ()) {
                cause = getPlacement (Graphs.getCause (placement._description));
                effect = getPlacement (Graphs.getEffect (placement._description));
                if (cause != null && cause._x >= placement._x) {
                    placement._x = cause._x + 1;
                    changed = true;
                }
                if (effect != null && effect._x <= placement._x) {
                    effect._x = placement._x + 1;
                    changed = true;
                }
            }
        } while (changed);
    }

    public String toString () {
        StringBuilder text = new StringBuilder ();
        int maxx = getMaxX ();
        int maxy = getMaxY ();
        boolean found;

        for (int y = 0; y <= maxy; y += 1) {
            for (int x = 0; x <= maxx; x += 1) {
                found = false;
                for (Placement placement : _placements.values ()) {
                    if (placement._x == x && placement._y == y) {
                        if (placement._description instanceof Agent) {
                            text.append ("g");
                            found = true;
                            break;
                        }
                        if (placement._description instanceof Activity) {
                            text.append ("a");
                            found = true;
                            break;
                        }
                        if (placement._description instanceof Entity) {
                            text.append ("e");
                            found = true;
                            break;
                        }
                    }
                }
                if (!found) {
                    text.append (" ");
                }
            }
            text.append ("\n");
        }

        return text.toString ();
    }
}

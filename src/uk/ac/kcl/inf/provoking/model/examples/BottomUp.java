package uk.ac.kcl.inf.provoking.model.examples;

import uk.ac.kcl.inf.provoking.model.*;

public class BottomUp {
    public static void main (String[] arguments) {
        Entity e1 = new Entity ();
        Entity e2 = new Entity ();
        Entity e3 = new Entity ();
        Activity a1 = new Activity ();
        Agent g1 = new Agent ();
        Used us1 = new Used (a1, e1);
        Used us2 = new Used (a1, e2);
        WasGeneratedBy wg1 = new WasGeneratedBy (e3, a1);
        WasAssociatedWith as1 = new WasAssociatedWith (a1, g1);
        Document account = new Document (e1, e2, e3, a1, g1, us1, us2, wg1, as1);
    }
}

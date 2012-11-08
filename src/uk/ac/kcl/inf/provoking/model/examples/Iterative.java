package uk.ac.kcl.inf.provoking.model.examples;

import uk.ac.kcl.inf.provoking.model.*;

public class Iterative {
    public static void main (String[] arguments) {
        Document account = new Document ();
        Entity e1 = new Entity ();
        Entity e2 = new Entity ();
        Entity e3 = new Entity ();
        account.addAll (e1, e2, e3);
        Activity a1 = new Activity ();
        account.add (a1);
        Agent g1 = new Agent ();
        account.add (g1);
        Used us1 = new Used (a1, e1);
        Used us2 = new Used (a1, e2);
        account.addAll (us1, us2);
        WasGeneratedBy wg1 = new WasGeneratedBy (e3, a1);
        WasAssociatedWith as1 = new WasAssociatedWith (a1, g1);
        account.addAll (wg1, as1);
    }
}

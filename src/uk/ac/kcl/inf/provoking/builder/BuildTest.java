package uk.ac.kcl.inf.provoking.builder;

public class BuildTest {
    public static void main (String[] arguments) {
        ProvBuilder b = new ProvBuilder ();
        
        b.agent ("name=simon").actedOnBehalfOf ("*1").agent ("name=kcl").where ("*1").activity ("type=coding");
    }
}

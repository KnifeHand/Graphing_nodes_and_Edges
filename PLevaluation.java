import java.lang.Object;
import org.junit.Assert.assertTrue;
@Test
// Java's "Double Brace Initialization" is a feature that
// allows writing expression to create an intitialize collections.
// whenInitializeSetWithDoubleBraces_containsElements()
private class PLevaluation { // PLevaluation.java:5: error: class, interface, or enum expected
    public static void main(String args[]) {
        Set<String> countries = new HashSet<String>() {
            {
                countries.add("India");
                countries.add("USSR"); //PLevaluation.java:9: error: class, interface, or enum expected
                countries.add("USA"); // PLevaluation.java:10: error: class, interface, or enum expected
            }//FIXME:PLevaluation.java:11: error: class, interface, or enum expected
        };
        Assert.assertTrue(countries.contains("India")); //PLevaluation.java:13: error: class, interface, or enum expected
    }// FIXME: PLevaluation.java:14: error: class, interface, or enum expected
}
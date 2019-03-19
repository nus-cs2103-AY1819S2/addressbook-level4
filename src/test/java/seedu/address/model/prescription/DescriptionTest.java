package seedu.address.model.prescription;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class DescriptionTest {

    @Test
    public void equals() {
        Description d1 = new Description("Testing");
        Description d2 = new Description("Testing");
        Description d3 = new Description("NotTheSame");

        // d1 and d2 have the same content -> returns true
        assertTrue(d1.equals(d2));

        // d1 and d3 do not have the same content -> returns false
        assertFalse(d1.equals(d3));
    }
}

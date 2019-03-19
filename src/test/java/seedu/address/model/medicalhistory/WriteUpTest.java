package seedu.address.model.medicalhistory;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class WriteUpTest {

    @Test
    public void equals() {
        WriteUp writeUp1 = new WriteUp("test1");
        WriteUp writeUp2 = new WriteUp("test1");
        WriteUp writeUp3 = new WriteUp("test2");

        assertTrue(writeUp1.equals(writeUp1));
        assertTrue(writeUp1.equals(writeUp2));

        assertFalse(writeUp1.equals(writeUp3));
    }
}

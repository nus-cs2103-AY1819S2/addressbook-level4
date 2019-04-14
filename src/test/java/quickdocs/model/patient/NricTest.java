package quickdocs.model.patient;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import quickdocs.testutil.Assert;

public class NricTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Nric(null));
    }

    @Test
    public void constructor_invalidNric_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Nric(invalidName));

        String onlySpace = " ";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Nric(onlySpace));

        String onlySymbols = "^";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Nric(onlySymbols));

        String invalidStartingLetter = "A7693825C";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Nric(invalidStartingLetter));

        String invalidEndingLetterForSandT = "S7693825K";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Nric(invalidEndingLetterForSandT));

        String invalidEndingLetterFandG = "F8868575A";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Nric(invalidEndingLetterFandG));

        String insufficientNumbers = "S496387H";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Nric(insufficientNumbers));

        String noFirstAndLastLetter = "4963872";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Nric(noFirstAndLastLetter));

        String noFirstLetter = "4963872H";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Nric(noFirstLetter));

        String noLastLetter = "S4963872";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Nric(noLastLetter));
    }

    @Test
    public void equals() {
        Nric nric = new Nric("F2131944U");
        assertTrue(nric.equals(nric));

        Nric nric2 = new Nric("F2131944U");
        assertTrue(nric.equals(nric2));

        Nric nric3 = new Nric("S5072487E");
        assertFalse(nric.equals(nric3));
    }
}

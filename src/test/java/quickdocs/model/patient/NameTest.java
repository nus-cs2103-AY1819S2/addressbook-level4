package quickdocs.model.patient;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import quickdocs.testutil.Assert;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));

        String onlySpace = " ";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Name(onlySpace));

        String onlySymbols = "^";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Name(onlySymbols));

        String containsSymbols = "peter*";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Name(containsSymbols));
    }

    @Test
    public void equals() {
        Name name = new Name("George Gan");
        assertTrue(name.equals(name));

        Name name2 = new Name("George Gan");
        assertTrue(name.equals(name2));

        Name name3 = new Name("Georgina Gan");
        assertFalse(name.equals(name3));
    }

}

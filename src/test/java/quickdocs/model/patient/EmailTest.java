package quickdocs.model.patient;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import quickdocs.testutil.Assert;

public class EmailTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Email(null));
    }

    @Test
    public void constructor_invalidEmail_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Email(invalidName));

        String onlySpace = " ";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Email(onlySpace));

        String onlySymbols = "^";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Email(onlySymbols));

        String noDomain = "abc";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Email(noDomain));

        String noLocalPart = "@gmail.com";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Email(noLocalPart));

        String noDot = "abc@gmail";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Email(noDot));

        String unallowedlocalCharacters = "@/c@gmail.com";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Email(unallowedlocalCharacters));

        String unallowedDomainCharacters = "abc@gm@il.com";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Email(unallowedDomainCharacters));
    }

    @Test
    public void equals() {
        Email email = new Email("abc@gmail.com");
        assertTrue(email.equals(email));

        Email email2 = new Email("abc@gmail.com");
        assertTrue(email.equals(email2));

        Email email3 = new Email("bbc@gmail.com");
        assertFalse(email.equals(email3));
    }
}

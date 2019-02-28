package seedu.address.model.patient;

import org.junit.Test;

import seedu.address.testutil.Assert;

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
}

package seedu.address.model.patient;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class ContactTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Contact(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Contact(invalidPhone));

        String onlySpace = "        ";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Contact(onlySpace));

        String onlySymbol = "@#$%^*()";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Contact(onlySymbol));

        String insufficientNumbers = "1234567";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Contact(insufficientNumbers));

        String extraNumbers = "123456789";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Contact(extraNumbers));

        String alphabetsIncluded = "12E45678";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Contact(extraNumbers));
    }

}

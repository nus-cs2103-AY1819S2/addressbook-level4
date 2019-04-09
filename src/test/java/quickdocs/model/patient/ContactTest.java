package quickdocs.model.patient;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import quickdocs.testutil.Assert;

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

    @Test
    public void equals() {
        Contact contact1 = new Contact("91111111");
        assertTrue(contact1.equals(contact1));

        Contact contact2 = new Contact("91111111");
        assertTrue(contact1.equals(contact2));

        Contact contact3 = new Contact("82222222");
        assertFalse(contact1.equals(contact3));
    }

}

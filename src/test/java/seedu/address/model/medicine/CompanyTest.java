package seedu.address.model.medicine;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class CompanyTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Company(null));
    }

    @Test
    public void constructor_invalidCompany_throwsIllegalArgumentException() {
        String invalidCompany = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Company(invalidCompany));
    }

    @Test
    public void isValidCompany() {
        // null company name
        Assert.assertThrows(NullPointerException.class, () -> Company.isValidCompany(null));

        // invalid company names
        assertFalse(Company.isValidCompany("")); // empty string
        assertFalse(Company.isValidCompany(" ")); // spaces only
        assertFalse(Company.isValidCompany(("way too long company nameeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee")));

        // valid company names
        assertTrue(Company.isValidCompany("Acadia Pharmaceuticals"));
        assertTrue(Company.isValidCompany("-")); // one character
        assertTrue(Company.isValidCompany("Gulf Pharmaceutical Industries (Julphar)")); // with symbols
    }
}

package seedu.address.model.person.healthworker;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.person.Name;
import seedu.address.testutil.Assert;

public class OrganizationTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Organization(null));
    }

    @Test
    public void constructor_invalidOrgName_throwsIllegalArgumentException() {
        String invalidOrgName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Name(invalidOrgName));
    }

    @Test
    public void isValidOrgName() {
        // null orgname
        Assert.assertThrows(NullPointerException.class, () -> Organization
                .isValidOrgName(null));

        // invalid organization names
        assertFalse(Organization.isValidOrgName("")); // empty string
        assertFalse(Organization.isValidOrgName(" ")); // spaces only
        assertFalse(Organization.isValidOrgName("hello-world")); // Non space
        // symbols

        // valid name
        assertTrue(Organization.isValidOrgName("hello world"));
        //alphabets only
        assertTrue(Organization.isValidOrgName("12345")); // numbers only
        assertTrue(Organization.isValidOrgName("4tune cookie"));
        // alphanumeric characters
        assertTrue(Organization.isValidOrgName("Capital City")); //
        // with capital letters
        assertTrue(Organization.isValidOrgName("Za Warudo Over Heaven 2"));
        // long names
    }

    @Test
    public void contains() {
        Organization organization = new Organization("National University of Singapore");

        // same organization name
        assertTrue(organization.contains("National University of Singapore"));

        // substring of orgName
        assertTrue(organization.contains("National"));

        // case insensitive substring
        assertTrue(organization.contains("national"));

        // non-substring
        assertFalse(organization.contains("NTU"));
    }

}

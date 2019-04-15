package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class RelationshipTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Relationship(null));
    }

    @Test
    public void constructor_invalidRelationship_throwsIllegalArgumentException() {
        String invalidRelationship = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Relationship(invalidRelationship));
    }

    @Test
    public void isValidRelationship() {
        // null relationship
        Assert.assertThrows(NullPointerException.class, () -> Relationship.isValidRelationship(null));

        // invalid relationship
        assertFalse(Relationship.isValidRelationship("")); // empty string
        assertFalse(Relationship.isValidRelationship(" ")); // spaces only
        assertFalse(Relationship.isValidRelationship("^")); // only non-alphanumeric characters
        assertFalse(Relationship.isValidRelationship("single*")); // contains non-alphanumeric characters

        // valid relationship
        assertTrue(Relationship.isValidRelationship("single")); // alphabets only
        assertTrue(Relationship.isValidRelationship("married")); // numbers only
    }
}

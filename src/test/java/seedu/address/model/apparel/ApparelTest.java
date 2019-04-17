package seedu.address.model.apparel;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COLOR_BLUE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_B;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_BOTTOM;
import static seedu.address.testutil.TypicalApparels.BOBYIN;
import static seedu.address.testutil.TypicalApparels.NAME_INFORMAL_SHIRT;
import static seedu.address.testutil.TypicalApparels.SHIRT1;
import static seedu.address.testutil.TypicalApparels.SHIRT2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.ApparelBuilder;

public class ApparelTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /*@Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Apparel apparel = new ApparelBuilder().build();
        thrown.expect(UnsupportedOperationException.class);

        // TODO: modify the apparel content to trigger the exception here
    }*/

    @Test
    public void isSamePerson() {
        System.out.println(SHIRT1.toString());

        // same object -> returns true
        assertTrue(SHIRT1.isSameApparel(SHIRT1));

        // different shirt -> return false
        assertFalse(SHIRT1.isSameApparel(SHIRT2));

        // null -> returns false
        assertFalse(SHIRT1.isSameApparel(null));

        // different color and clothing type -> returns false
        Apparel editedAlice = new ApparelBuilder(SHIRT1).withColor(VALID_COLOR_BLUE)
                .withClothingType(VALID_TYPE_BOTTOM).build();
        assertFalse(SHIRT1.isSameApparel(editedAlice));

        // different name -> returns false
        editedAlice = new ApparelBuilder(SHIRT1).withName(VALID_NAME_B).build();
        assertFalse(SHIRT1.isSameApparel(editedAlice));

        // same name, same color, different type -> returns false
        editedAlice = new ApparelBuilder(SHIRT1).withClothingType(VALID_TYPE_BOTTOM).build();
        assertFalse(SHIRT1.isSameApparel(editedAlice));

        // same name, different color, same type -> returns false
        editedAlice = new ApparelBuilder(SHIRT1).withColor(VALID_COLOR_BLUE).build();
        assertFalse(SHIRT1.isSameApparel(editedAlice));

        // different name, same color, same type -> returns false
        editedAlice = new ApparelBuilder(SHIRT1).withName(NAME_INFORMAL_SHIRT).build();
        assertFalse(SHIRT1.isSameApparel(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Apparel aliceCopy = new ApparelBuilder(SHIRT1).build();
        assertTrue(SHIRT1.equals(aliceCopy));

        // same object -> returns true
        assertTrue(SHIRT1.equals(SHIRT1));

        // null -> returns false
        assertFalse(SHIRT1.equals(null));

        // different type -> returns false
        assertFalse(SHIRT1.equals(5));

        // different apparel -> returns false
        assertFalse(SHIRT1.equals(BOBYIN));

        // different name -> returns false
        Apparel editedAlice = new ApparelBuilder(SHIRT1).withName(VALID_NAME_B).build();
        assertFalse(SHIRT1.equals(editedAlice));

        // different color -> returns false
        editedAlice = new ApparelBuilder(SHIRT1).withColor(VALID_COLOR_BLUE).build();
        assertFalse(SHIRT1.equals(editedAlice));

        // different type -> returns false
        editedAlice = new ApparelBuilder(SHIRT1).withClothingType(VALID_TYPE_BOTTOM).build();
        assertFalse(SHIRT1.equals(editedAlice));

        // same apparel -> returns true
        editedAlice = new ApparelBuilder(SHIRT1).build();
        assertTrue(SHIRT1.equals(editedAlice));
    }
}

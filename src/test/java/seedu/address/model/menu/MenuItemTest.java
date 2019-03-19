package seedu.address.model.menu;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CODE_FRIES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_FRIES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_FRIES;
import static seedu.address.testutil.TypicalRestOrRant.FRENCH_FRIES;
import static seedu.address.testutil.TypicalRestOrRant.SALAD;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.MenuItemBuilder;

public class MenuItemTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isSameMenuItem() {
        // same object -> returns true
        assertTrue(SALAD.isSameMenuItem(SALAD));

        // null -> returns false
        assertFalse(SALAD.isSameMenuItem(null));

        // different code -> returns false
        MenuItem editedSalad = new MenuItemBuilder(SALAD).withCode(VALID_CODE_FRIES).build();
        assertFalse(SALAD.isSameMenuItem(editedSalad));

        // different name -> returns false
        editedSalad = new MenuItemBuilder(SALAD).withName(VALID_NAME_FRIES).build();
        assertFalse(SALAD.isSameMenuItem(editedSalad));

        // same name, same code, different price -> returns true
        editedSalad = new MenuItemBuilder(SALAD).withPrice(VALID_PRICE_FRIES).build();
        assertTrue(SALAD.isSameMenuItem(editedSalad));
    }

    @Test
    public void equals() {
        // same values -> returns true
        MenuItem saladCopy = new MenuItemBuilder(SALAD).build();
        assertTrue(SALAD.equals(saladCopy));

        // same object -> returns true
        assertTrue(SALAD.equals(SALAD));

        // null -> returns false
        assertFalse(SALAD.equals(null));

        // different type -> returns false
        assertFalse(SALAD.equals(5));

        // different menu item -> returns false
        assertFalse(SALAD.equals(FRENCH_FRIES));

        // different name -> returns false
        MenuItem editedSalad = new MenuItemBuilder(SALAD).withName(VALID_NAME_FRIES).build();
        assertFalse(SALAD.equals(editedSalad));

        // different code -> returns false
        editedSalad = new MenuItemBuilder(SALAD).withCode(VALID_CODE_FRIES).build();
        assertFalse(SALAD.equals(editedSalad));

        // different price -> returns false
        editedSalad = new MenuItemBuilder(SALAD).withPrice(VALID_PRICE_FRIES).build();
        assertFalse(SALAD.equals(editedSalad));
    }

}

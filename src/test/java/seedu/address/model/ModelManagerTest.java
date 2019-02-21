package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RESTAURANTS;
import static seedu.address.testutil.TypicalRestaurants.ALICE;
import static seedu.address.testutil.TypicalRestaurants.BENSON;
import static seedu.address.testutil.TypicalRestaurants.BOB;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.restaurant.NameContainsKeywordsPredicate;
import seedu.address.model.restaurant.Restaurant;
import seedu.address.model.restaurant.exceptions.RestaurantNotFoundException;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.RestaurantBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
        assertEquals(null, modelManager.getSelectedRestaurant());
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setUserPrefs(null);
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setGuiSettings(null);
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setAddressBookFilePath(null);
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void hasRestaurant_nullRestaurant_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasRestaurant(null);
    }

    @Test
    public void hasRestaurant_restaurantNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasRestaurant(ALICE));
    }

    @Test
    public void hasRestaurant_restaurantInAddressBook_returnsTrue() {
        modelManager.addRestaurant(ALICE);
        assertTrue(modelManager.hasRestaurant(ALICE));
    }

    @Test
    public void deleteRestaurant_restaurantIsSelectedAndFirstRestaurantInFilteredRestaurantList_selectionCleared() {
        modelManager.addRestaurant(ALICE);
        modelManager.setSelectedRestaurant(ALICE);
        modelManager.deleteRestaurant(ALICE);
        assertEquals(null, modelManager.getSelectedRestaurant());
    }

    @Test
    public void deleteRestaurant_restaurantIsSelectedAndSecondRestaurantInFilteredRestaurantList_firstRestaurantSelected() {
        modelManager.addRestaurant(ALICE);
        modelManager.addRestaurant(BOB);
        assertEquals(Arrays.asList(ALICE, BOB), modelManager.getFilteredRestaurantList());
        modelManager.setSelectedRestaurant(BOB);
        modelManager.deleteRestaurant(BOB);
        assertEquals(ALICE, modelManager.getSelectedRestaurant());
    }

    @Test
    public void setRestaurant_restaurantIsSelected_selectedRestaurantUpdated() {
        modelManager.addRestaurant(ALICE);
        modelManager.setSelectedRestaurant(ALICE);
        Restaurant updatedAlice = new RestaurantBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        modelManager.setRestaurant(ALICE, updatedAlice);
        assertEquals(updatedAlice, modelManager.getSelectedRestaurant());
    }

    @Test
    public void getFilteredRestaurantList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredRestaurantList().remove(0);
    }

    @Test
    public void setSelectedRestaurant_restaurantNotInFilteredRestaurantList_throwsRestaurantNotFoundException() {
        thrown.expect(RestaurantNotFoundException.class);
        modelManager.setSelectedRestaurant(ALICE);
    }

    @Test
    public void setSelectedRestaurant_restaurantInFilteredRestaurantList_setsSelectedRestaurant() {
        modelManager.addRestaurant(ALICE);
        assertEquals(Collections.singletonList(ALICE), modelManager.getFilteredRestaurantList());
        modelManager.setSelectedRestaurant(ALICE);
        assertEquals(ALICE, modelManager.getSelectedRestaurant());
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withRestaurant(ALICE).withRestaurant(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredRestaurantList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredRestaurantList(PREDICATE_SHOW_ALL_RESTAURANTS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }
}

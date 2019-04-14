package seedu.address.commons.core;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.core.InformationPanelSettings.SortDirection;
import seedu.address.commons.core.InformationPanelSettings.SortProperty;
import seedu.address.testutil.Assert;

public class InformationPanelSettingsTest {
    private final InformationPanelSettings settings = new InformationPanelSettings();

    @Test
    public void getters_defaultValues() {
        assertEquals(settings.getSortProperty(), InformationPanelSettings.DEFAULT_SORT_PROPERTY);
        assertEquals(settings.getSortDirection(), InformationPanelSettings.DEFAULT_SORT_DIRECTION);
    }

    @Test
    public void equals() {
        // same values -> returns true
        InformationPanelSettings otherSettings = new InformationPanelSettings();
        assertTrue(settings.equals(otherSettings));

        // same object -> returns true
        assertTrue(settings.equals(settings));

        // null -> returns false
        assertFalse(settings.equals(null));

        // different types -> returns false
        assertFalse(settings.equals(1));

        // different values -> returns false
        assertFalse(settings.equals(new InformationPanelSettings(SortProperty.EXPIRY, SortDirection.ASCENDING)));
    }

    @Test
    public void toString_success() {
        String expectedString = "Sort Property: 'batchnumber' Sort Direction: 'ascending'";
        assertEquals(settings.toString(), expectedString);
    }

    @Test
    public void isValidSortProperty() {
        // null
        Assert.assertThrows(NullPointerException.class, () -> SortProperty.isValidSortProperty(null));

        // invalid SortProperties
        assertFalse(SortProperty.isValidSortProperty("")); // empty string
        assertFalse(SortProperty.isValidSortProperty(" ")); // spaces only
        assertFalse(SortProperty.isValidSortProperty("random text")); // invalid text

        // valid SortProperties
        assertTrue(SortProperty.isValidSortProperty("BATCHNUMBER")); // upper case
        assertTrue(SortProperty.isValidSortProperty("QuAnTiTy")); // mixed case
        assertTrue(SortProperty.isValidSortProperty("expiry")); // lower case
    }

    @Test
    public void isValidSortDirection() {
        // null
        Assert.assertThrows(NullPointerException.class, () -> SortDirection.isValidSortDirection(null));

        // invalid SortProperties
        assertFalse(SortDirection.isValidSortDirection("")); // empty string
        assertFalse(SortDirection.isValidSortDirection(" ")); // spaces only
        assertFalse(SortDirection.isValidSortDirection("random text")); // invalid text

        // valid SortProperties
        assertTrue(SortDirection.isValidSortDirection("ASCENDING")); // upper case
        assertTrue(SortDirection.isValidSortDirection("DeScEnDing")); // mixed case
    }
}

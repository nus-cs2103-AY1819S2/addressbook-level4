package seedu.travel.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.travel.logic.commands.CommandTestUtil.DESC_AMK;
import static seedu.travel.logic.commands.CommandTestUtil.DESC_BEDOK;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_ADDRESS_BEDOK;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_COUNTRY_CODE_BEDOK;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BEDOK;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_NAME_BEDOK;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_RATING_BEDOK;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_TAG_EWL;

import org.junit.Test;

import seedu.travel.logic.commands.EditCommand.EditPlaceDescriptor;
import seedu.travel.testutil.EditPlaceDescriptorBuilder;

public class EditPlaceDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditPlaceDescriptor descriptorWithSameValues = new EditPlaceDescriptor(DESC_AMK);
        assertTrue(DESC_AMK.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMK.equals(DESC_AMK));

        // null -> returns false
        assertFalse(DESC_AMK.equals(null));

        // different types -> returns false
        assertFalse(DESC_AMK.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMK.equals(DESC_BEDOK));

        // different name -> returns false
        EditPlaceDescriptor editedAmy = new EditPlaceDescriptorBuilder(DESC_AMK).withName(VALID_NAME_BEDOK).build();
        assertFalse(DESC_AMK.equals(editedAmy));

        // different country code -> returns false
        editedAmy = new EditPlaceDescriptorBuilder(DESC_AMK).withName(VALID_COUNTRY_CODE_BEDOK).build();
        assertFalse(DESC_AMK.equals(editedAmy));

        // different rating -> returns false
        editedAmy = new EditPlaceDescriptorBuilder(DESC_AMK).withRating(VALID_RATING_BEDOK).build();
        assertFalse(DESC_AMK.equals(editedAmy));

        // different description -> returns false
        editedAmy = new EditPlaceDescriptorBuilder(DESC_AMK).withDescription(VALID_DESCRIPTION_BEDOK).build();
        assertFalse(DESC_AMK.equals(editedAmy));

        // different travel -> returns false
        editedAmy = new EditPlaceDescriptorBuilder(DESC_AMK).withAddress(VALID_ADDRESS_BEDOK).build();
        assertFalse(DESC_AMK.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditPlaceDescriptorBuilder(DESC_AMK).withTags(VALID_TAG_EWL).build();
        assertFalse(DESC_AMK.equals(editedAmy));
    }
}

package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_CS1010;
import static seedu.address.logic.commands.CommandTestUtil.DESC_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPECTED_MAX_GRADE_CS1010;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPECTED_MIN_GRADE_CS1010;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_INFO_CODE_CS1010;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEMESTER_CS1010;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import org.junit.Test;

import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.testutil.EditPersonDescriptorBuilder;

public class EditModuleTakenDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditPersonDescriptor descriptorWithSameValues = new EditPersonDescriptor(DESC_CS2103T);
        assertTrue(DESC_CS2103T.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_CS2103T.equals(DESC_CS2103T));

        // null -> returns false
        assertFalse(DESC_CS2103T.equals(null));

        // different types -> returns false
        assertFalse(DESC_CS2103T.equals(5));

        // different values -> returns false
        assertFalse(DESC_CS2103T.equals(DESC_CS1010));

        // different name -> returns false
        EditPersonDescriptor editedAmy = new EditPersonDescriptorBuilder(DESC_CS2103T)
                .withName(VALID_MODULE_INFO_CODE_CS1010).build();
        assertFalse(DESC_CS2103T.equals(editedAmy));

        // different semester -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_CS2103T)
                .withSemester(VALID_SEMESTER_CS1010).build();
        assertFalse(DESC_CS2103T.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_CS2103T)
                .withExpectedMinGrade(VALID_EXPECTED_MIN_GRADE_CS1010).build();
        assertFalse(DESC_CS2103T.equals(editedAmy));

        // different address -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_CS2103T)
                .withExpectedMaxGrade(VALID_EXPECTED_MAX_GRADE_CS1010).build();
        assertFalse(DESC_CS2103T.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_CS2103T)
                .withTags(VALID_TAG_HUSBAND).build();
        assertFalse(DESC_CS2103T.equals(editedAmy));
    }
}

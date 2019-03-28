package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.FIND_CS1010;
import static seedu.address.logic.commands.CommandTestUtil.FIND_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_CS1010;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_INFO_CODE_CS1010;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEMESTER_CS1010;
import static seedu.address.logic.parser.ParserUtil.FINISHED_STATUS_TRUE;

import org.junit.Test;

import seedu.address.logic.commands.FindCommand.FindModuleDescriptor;
import seedu.address.testutil.FindModuleDescriptorBuilder;

public class FindModuleDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        FindModuleDescriptor descriptor = new FindModuleDescriptor(FIND_CS2103T);
        assertTrue(FIND_CS2103T.equals(descriptor));

        // same object -> returns true
        assertTrue(FIND_CS2103T.equals(FIND_CS2103T));

        // null -> returns false
        assertFalse(FIND_CS2103T.equals(null));

        // different types -> returns false
        assertFalse(FIND_CS2103T.equals(5));

        // different values -> returns false
        assertFalse(FIND_CS2103T.equals(FIND_CS1010));

        // different subcode -> returns false
        descriptor = new FindModuleDescriptorBuilder(FIND_CS2103T).withCode(VALID_MODULE_INFO_CODE_CS1010).build();
        assertFalse(FIND_CS2103T.equals(descriptor));

        // different semester -> returns false
        descriptor = new FindModuleDescriptorBuilder(FIND_CS2103T).withSemester(VALID_SEMESTER_CS1010).build();
        assertFalse(FIND_CS2103T.equals(descriptor));

        // different grade -> returns false
        descriptor = new FindModuleDescriptorBuilder(FIND_CS2103T).withGrade(VALID_GRADE_CS1010).build();
        assertFalse(FIND_CS2103T.equals(descriptor));

        // different finished status -> returns false
        descriptor = new FindModuleDescriptorBuilder(FIND_CS2103T).withFinishedStatus(FINISHED_STATUS_TRUE).build();
        assertFalse(FIND_CS2103T.equals(descriptor));
    }
}

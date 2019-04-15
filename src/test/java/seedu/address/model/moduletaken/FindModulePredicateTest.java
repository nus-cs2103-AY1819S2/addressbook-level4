package seedu.address.model.moduletaken;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.FINISHED_STATUS_FALSE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPECTED_MIN_GRADE_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_CS1010;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_INFO_CODE_CS1010;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_INFO_CODE_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEMESTER_CS1010;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEMESTER_CS2103T;
import static seedu.address.logic.parser.ParserUtil.FINISHED_STATUS_TRUE;
import static seedu.address.testutil.TypicalModuleTaken.KEYWORD_MATCHING_CS;

import org.junit.Test;

import seedu.address.logic.commands.FindCommand.FindModuleDescriptor;
import seedu.address.testutil.FindModuleDescriptorBuilder;
import seedu.address.testutil.ModuleTakenBuilder;

public class FindModulePredicateTest {

    @Test
    public void equals() {
        Semester currentSemester = Semester.Y1S1;

        FindModuleDescriptor fd1 = new FindModuleDescriptorBuilder()
                .withCode(VALID_MODULE_INFO_CODE_CS2103T).build();
        FindModuleDescriptor fd2 = new FindModuleDescriptorBuilder()
                .withCode(VALID_MODULE_INFO_CODE_CS1010).build();
        FindModuleDescriptor fd3 = new FindModuleDescriptorBuilder()
                .withCode(VALID_MODULE_INFO_CODE_CS2103T)
                .withSemester(VALID_SEMESTER_CS2103T)
                .withGrade(VALID_EXPECTED_MIN_GRADE_CS2103T).build();
        FindModuleDescriptor fd4 = new FindModuleDescriptorBuilder().withCode("CS").build();
        FindModuleDescriptor fd5 = new FindModuleDescriptorBuilder().withCode("cs").build();

        FindModulePredicate fp1 = new FindModulePredicate(fd1, currentSemester);
        FindModulePredicate fp2 = new FindModulePredicate(fd2, currentSemester);
        FindModulePredicate fp3 = new FindModulePredicate(fd3, currentSemester);
        FindModulePredicate fp4 = new FindModulePredicate(fd4, currentSemester);
        FindModulePredicate fp5 = new FindModulePredicate(fd5, currentSemester);

        // same object -> returns true
        assertTrue(fp1.equals(fp1));

        // same values -> returns true
        FindModulePredicate fp1copy = new FindModulePredicate(fd1, currentSemester);
        assertTrue(fp1.equals(fp1copy));

        // same values (ignoring case) -> returns true
        assertTrue(fp4.equals(fp5));

        // different types -> returns false
        assertFalse(fp1.equals(1));

        // null -> returns false
        assertFalse(fp1.equals(null));

        // different code -> returns false
        assertFalse(fp1.equals(fp2));

        // different number of parameters -> returns false
        assertFalse(fp1.equals(fp3));
    }

    @Test
    public void test_singleParameter_returnsTrue() {
        Semester currentSemester = Semester.Y1S1;

        // code only
        FindModuleDescriptor descriptor = new FindModuleDescriptorBuilder().withCode(KEYWORD_MATCHING_CS).build();
        FindModulePredicate predicate = new FindModulePredicate(descriptor, currentSemester);
        assertTrue(predicate.test(new ModuleTakenBuilder().withModuleInfoCode(VALID_MODULE_INFO_CODE_CS2103T).build()));

        // semester only
        descriptor = new FindModuleDescriptorBuilder().withSemester(VALID_SEMESTER_CS2103T).build();
        predicate = new FindModulePredicate(descriptor, currentSemester);
        assertTrue(predicate.test(new ModuleTakenBuilder().withSemester(VALID_SEMESTER_CS2103T).build()));

        // grade only
        descriptor = new FindModuleDescriptorBuilder().withGrade(VALID_GRADE_CS2103T).build();
        predicate = new FindModulePredicate(descriptor, currentSemester);
        assertTrue(predicate.test(new ModuleTakenBuilder().withExpectedMaxGrade(VALID_GRADE_CS2103T)
                .withExpectedMinGrade(VALID_GRADE_CS2103T).build()));

        // finished status only
        currentSemester = Semester.Y2S2;
        descriptor = new FindModuleDescriptorBuilder().withFinishedStatus(FINISHED_STATUS_TRUE).build();
        predicate = new FindModulePredicate(descriptor, currentSemester);
        assertTrue(predicate.test(new ModuleTakenBuilder().withSemester(VALID_SEMESTER_CS1010).build()));
    }

    @Test
    public void test_multipleParameters_returnsTrue() {
        Semester currentSemester = Semester.Y1S1;

        FindModuleDescriptor descriptor = new FindModuleDescriptorBuilder().withCode(VALID_MODULE_INFO_CODE_CS2103T)
                .withSemester(VALID_SEMESTER_CS2103T)
                .withGrade(VALID_GRADE_CS2103T)
                .withFinishedStatus(FINISHED_STATUS_FALSE).build();
        FindModulePredicate predicate = new FindModulePredicate(descriptor, currentSemester);
        assertTrue(predicate.test(new ModuleTakenBuilder()
                .withModuleInfoCode(VALID_MODULE_INFO_CODE_CS2103T)
                .withSemester(VALID_SEMESTER_CS2103T)
                .withExpectedMaxGrade(VALID_GRADE_CS2103T)
                .withExpectedMinGrade(VALID_GRADE_CS2103T).build()));

        currentSemester = Semester.Y3S1;
        descriptor = new FindModuleDescriptorBuilder().withCode(VALID_MODULE_INFO_CODE_CS1010)
                .withSemester(VALID_SEMESTER_CS1010).withGrade(VALID_GRADE_CS1010)
                .withFinishedStatus(FINISHED_STATUS_TRUE).build();
        predicate = new FindModulePredicate(descriptor, currentSemester);
        assertTrue(predicate.test(new ModuleTakenBuilder()
                .withModuleInfoCode(VALID_MODULE_INFO_CODE_CS1010)
                .withSemester(VALID_SEMESTER_CS1010)
                .withExpectedMaxGrade(VALID_GRADE_CS1010)
                .withExpectedMinGrade(VALID_GRADE_CS1010)
                .build()));
    }

    @Test
    public void test_singleParameter_returnsFalse() {
        Semester currentSemester = Semester.Y1S1;

        // code only
        FindModuleDescriptor descriptor = new FindModuleDescriptorBuilder()
                .withCode(VALID_MODULE_INFO_CODE_CS2103T).build();
        FindModulePredicate predicate = new FindModulePredicate(descriptor, currentSemester);
        assertFalse(predicate.test(new ModuleTakenBuilder()
                .withModuleInfoCode(VALID_MODULE_INFO_CODE_CS1010).build()));

        // semester only
        descriptor = new FindModuleDescriptorBuilder().withSemester(VALID_SEMESTER_CS2103T).build();
        predicate = new FindModulePredicate(descriptor, currentSemester);
        assertFalse(predicate.test(new ModuleTakenBuilder().withSemester(VALID_SEMESTER_CS1010).build()));

        // grade only
        descriptor = new FindModuleDescriptorBuilder().withGrade(VALID_GRADE_CS2103T).build();
        predicate = new FindModulePredicate(descriptor, currentSemester);
        assertFalse(predicate.test(new ModuleTakenBuilder().withExpectedMaxGrade(VALID_GRADE_CS1010)
                .withExpectedMinGrade(VALID_GRADE_CS1010).build()));

        // finished status only
        descriptor = new FindModuleDescriptorBuilder().withFinishedStatus(FINISHED_STATUS_TRUE).build();
        predicate = new FindModulePredicate(descriptor, currentSemester);
        assertFalse(predicate.test(new ModuleTakenBuilder().build()));
    }

    @Test
    public void test_multipleParameters_returnsFalse() {
        Semester currentSemester = Semester.Y1S1;

        FindModuleDescriptor descriptor = new FindModuleDescriptorBuilder()
                .withCode(VALID_MODULE_INFO_CODE_CS2103T)
                .withSemester(VALID_SEMESTER_CS2103T)
                .withGrade(VALID_GRADE_CS2103T)
                .withFinishedStatus(FINISHED_STATUS_TRUE).build();
        FindModulePredicate predicate = new FindModulePredicate(descriptor, currentSemester);

        // all different
        assertFalse(predicate.test(new ModuleTakenBuilder()
                .withModuleInfoCode(VALID_MODULE_INFO_CODE_CS1010)
                .withSemester(VALID_SEMESTER_CS1010)
                .withExpectedMaxGrade(VALID_GRADE_CS1010)
                .withExpectedMinGrade(VALID_GRADE_CS1010).build()));

        currentSemester = Semester.GRAD;
        predicate = new FindModulePredicate(descriptor, currentSemester);

        // code different
        assertFalse(predicate.test(new ModuleTakenBuilder()
                .withModuleInfoCode(VALID_MODULE_INFO_CODE_CS1010)
                .withSemester(VALID_SEMESTER_CS2103T)
                .withExpectedMaxGrade(VALID_GRADE_CS2103T)
                .withExpectedMinGrade(VALID_GRADE_CS2103T).build()));

        // semester different
        assertFalse(predicate.test(new ModuleTakenBuilder()
                .withModuleInfoCode(VALID_MODULE_INFO_CODE_CS2103T)
                .withSemester(VALID_SEMESTER_CS1010)
                .withExpectedMaxGrade(VALID_GRADE_CS2103T)
                .withExpectedMinGrade(VALID_GRADE_CS2103T).build()));

        // grade different
        assertFalse(predicate.test(new ModuleTakenBuilder()
                .withModuleInfoCode(VALID_MODULE_INFO_CODE_CS2103T)
                .withSemester(VALID_SEMESTER_CS2103T)
                .withExpectedMaxGrade(VALID_GRADE_CS1010)
                .withExpectedMinGrade(VALID_GRADE_CS1010).build()));

        currentSemester = Semester.Y2S1;
        predicate = new FindModulePredicate(descriptor, currentSemester);

        // finished status different
        assertFalse(predicate.test(new ModuleTakenBuilder()
                .withModuleInfoCode(VALID_MODULE_INFO_CODE_CS2103T)
                .withSemester(VALID_SEMESTER_CS2103T)
                .withExpectedMaxGrade(VALID_GRADE_CS2103T)
                .withExpectedMinGrade(VALID_GRADE_CS2103T).build()));
    }
}

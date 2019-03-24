package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPECTED_MIN_GRADE_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_CS1010;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_INFO_CODE_CS1010;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_INFO_CODE_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEMESTER_CS1010;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEMESTER_CS2103T;
import static seedu.address.logic.parser.ParserUtil.FINISHED_STATUS_TRUE;

import org.junit.Test;

import seedu.address.logic.commands.FindCommand.FindModuleDescriptor;
import seedu.address.testutil.FindModuleDescriptorBuilder;
import seedu.address.testutil.ModuleTakenBuilder;

public class FindModulePredicateTest {

    @Test
    public void equals() {
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

        FindModulePredicate fp1 = new FindModulePredicate(fd1);
        FindModulePredicate fp2 = new FindModulePredicate(fd2);
        FindModulePredicate fp3 = new FindModulePredicate(fd3);
        FindModulePredicate fp4 = new FindModulePredicate(fd4);
        FindModulePredicate fp5 = new FindModulePredicate(fd5);

        // same object -> returns true
        assertTrue(fp1.equals(fp1));

        // same values -> returns true
        FindModulePredicate fp1copy = new FindModulePredicate(fd1);
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
        // code only
        FindModuleDescriptor fd = new FindModuleDescriptorBuilder().withCode(VALID_MODULE_INFO_CODE_CS2103T).build();
        FindModulePredicate fp = new FindModulePredicate(fd);
        assertTrue(fp.test(new ModuleTakenBuilder().withModuleInfoCode(VALID_MODULE_INFO_CODE_CS2103T).build()));

        // semester only
        fd = new FindModuleDescriptorBuilder().withSemester(VALID_SEMESTER_CS2103T).build();
        fp = new FindModulePredicate(fd);
        assertTrue(fp.test(new ModuleTakenBuilder().withSemester(VALID_SEMESTER_CS2103T).build()));

        // grade only
        fd = new FindModuleDescriptorBuilder().withGrade(VALID_GRADE_CS2103T).build();
        fp = new FindModulePredicate(fd);
        assertTrue(fp.test(new ModuleTakenBuilder().withExpectedMaxGrade(VALID_GRADE_CS2103T)
                .withExpectedMinGrade(VALID_GRADE_CS2103T).build()));

        // finished status only
        fd = new FindModuleDescriptorBuilder().withFinishedStatus("n").build();
        fp = new FindModulePredicate(fd);
        assertTrue(fp.test(new ModuleTakenBuilder().build()));
    }

    @Test
    public void test_multipleParameters_returnsTrue() {
        FindModuleDescriptor fd = new FindModuleDescriptorBuilder().withCode(VALID_MODULE_INFO_CODE_CS2103T)
                .withSemester(VALID_SEMESTER_CS2103T)
                .withGrade(VALID_GRADE_CS2103T)
                .withFinishedStatus("n").build();
        FindModulePredicate fp = new FindModulePredicate(fd);
        assertTrue(fp.test(new ModuleTakenBuilder()
                .withModuleInfoCode(VALID_MODULE_INFO_CODE_CS2103T)
                .withSemester(VALID_SEMESTER_CS2103T)
                .withExpectedMaxGrade(VALID_GRADE_CS2103T)
                .withExpectedMinGrade(VALID_GRADE_CS2103T).build()));

        fd = new FindModuleDescriptorBuilder().withCode(VALID_MODULE_INFO_CODE_CS1010)
                .withSemester(VALID_SEMESTER_CS1010).withGrade(VALID_GRADE_CS1010)
                .withFinishedStatus("n").build();
        fp = new FindModulePredicate(fd);
        assertTrue(fp.test(new ModuleTakenBuilder()
                .withModuleInfoCode(VALID_MODULE_INFO_CODE_CS1010)
                .withSemester(VALID_SEMESTER_CS1010)
                .withExpectedMaxGrade(VALID_GRADE_CS1010)
                .withExpectedMinGrade(VALID_GRADE_CS1010)
                .build()));
    }

    @Test
    public void test_singleParameter_returnsFalse() {
        // code only
        FindModuleDescriptor fd = new FindModuleDescriptorBuilder()
                .withCode(VALID_MODULE_INFO_CODE_CS2103T).build();
        FindModulePredicate fp = new FindModulePredicate(fd);
        assertFalse(fp.test(new ModuleTakenBuilder()
                .withModuleInfoCode(VALID_MODULE_INFO_CODE_CS1010).build()));

        // semester only
        fd = new FindModuleDescriptorBuilder().withSemester(VALID_SEMESTER_CS2103T).build();
        fp = new FindModulePredicate(fd);
        assertFalse(fp.test(new ModuleTakenBuilder().withSemester(VALID_SEMESTER_CS1010).build()));

        // grade only
        fd = new FindModuleDescriptorBuilder().withGrade(VALID_GRADE_CS2103T).build();
        fp = new FindModulePredicate(fd);
        assertFalse(fp.test(new ModuleTakenBuilder().withExpectedMaxGrade(VALID_GRADE_CS1010)
                .withExpectedMinGrade(VALID_GRADE_CS1010).build()));

        // finished status only
        fd = new FindModuleDescriptorBuilder().withFinishedStatus(FINISHED_STATUS_TRUE).build();
        fp = new FindModulePredicate(fd);
        assertFalse(fp.test(new ModuleTakenBuilder().build()));
    }

    @Test
    public void test_multipleParameters_returnsFalse() {
        // all different
        FindModuleDescriptor fd = new FindModuleDescriptorBuilder()
                .withCode(VALID_MODULE_INFO_CODE_CS2103T)
                .withSemester(VALID_SEMESTER_CS2103T)
                .withGrade(VALID_GRADE_CS2103T)
                .withFinishedStatus("n").build();
        FindModulePredicate fp = new FindModulePredicate(fd);
        assertFalse(fp.test(new ModuleTakenBuilder()
                .withModuleInfoCode(VALID_MODULE_INFO_CODE_CS1010)
                .withSemester(VALID_SEMESTER_CS1010)
                .withExpectedMaxGrade(VALID_GRADE_CS1010)
                .withExpectedMinGrade(VALID_GRADE_CS1010).build()));

        // code different
        assertFalse(fp.test(new ModuleTakenBuilder()
                .withModuleInfoCode(VALID_MODULE_INFO_CODE_CS1010)
                .withSemester(VALID_SEMESTER_CS2103T)
                .withExpectedMaxGrade(VALID_GRADE_CS2103T)
                .withExpectedMinGrade(VALID_GRADE_CS2103T).build()));

        // semester different
        assertFalse(fp.test(new ModuleTakenBuilder()
                .withModuleInfoCode(VALID_MODULE_INFO_CODE_CS2103T)
                .withSemester(VALID_SEMESTER_CS1010)
                .withExpectedMaxGrade(VALID_GRADE_CS2103T)
                .withExpectedMinGrade(VALID_GRADE_CS2103T).build()));

        // grade different
        assertFalse(fp.test(new ModuleTakenBuilder()
                .withModuleInfoCode(VALID_MODULE_INFO_CODE_CS2103T)
                .withSemester(VALID_SEMESTER_CS2103T)
                .withExpectedMaxGrade(VALID_GRADE_CS1010)
                .withExpectedMinGrade(VALID_GRADE_CS1010).build()));
    }
}

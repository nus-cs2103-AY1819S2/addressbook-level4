package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalWorkloads.CS1010Workload;
import static seedu.address.testutil.TypicalWorkloads.CS2040Workload;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.WorkloadBuilder;

class WorkloadTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isSameWorkload() {
        // same object -> returns true
        assertTrue(CS2040Workload.equals(CS2040Workload));

        // null -> returns false
        assertFalse(CS2040Workload.equals(null));

        // same values -> returns true
        Workload modifiedCS2040Workload = new WorkloadBuilder()
                .withLectureHour(CS2040Workload.getLectureHour().toString())
                .withTutorialHour(CS2040Workload.getTutorialHour().toString())
                .withLabHour(CS2040Workload.getLabHour().toString())
                .withProjectHour(CS2040Workload.getProjectHour().toString())
                .withPreparationHour(CS2040Workload.getPreparationHour().toString()).build();
        assertFalse(CS2040Workload.equals(modifiedCS2040Workload));

        //different lecture hours -> return false
        modifiedCS2040Workload = new WorkloadBuilder().withLectureHour("2")
                .withTutorialHour(CS2040Workload.getTutorialHour().toString())
                .withLabHour(CS2040Workload.getLabHour().toString())
                .withProjectHour(CS2040Workload.getProjectHour().toString())
                .withPreparationHour(CS2040Workload.getPreparationHour().toString()).build();
        assertFalse(CS2040Workload.equals(modifiedCS2040Workload));

        //different tutorial hours -> return false
        modifiedCS2040Workload = new WorkloadBuilder().withLectureHour(CS2040Workload.getLectureHour().toString())
                .withTutorialHour(CS2040Workload.getTutorialHour().toString())
                .withLabHour(CS2040Workload.getLabHour().toString())
                .withProjectHour(CS2040Workload.getProjectHour().toString())
                .withPreparationHour(CS2040Workload.getPreparationHour().toString()).build();
        assertFalse(CS2040Workload.equals(modifiedCS2040Workload));

        //different tutorial hours -> return false
        modifiedCS2040Workload = new WorkloadBuilder().withLectureHour(CS2040Workload.getLectureHour().toString())
                .withTutorialHour("2")
                .withLabHour(CS2040Workload.getLabHour().toString())
                .withProjectHour(CS2040Workload.getProjectHour().toString())
                .withPreparationHour(CS2040Workload.getPreparationHour().toString()).build();
        assertFalse(CS2040Workload.equals(modifiedCS2040Workload));

        //different lab hours -> return false
        modifiedCS2040Workload = new WorkloadBuilder().withLectureHour(CS2040Workload.getLectureHour().toString())
                .withTutorialHour(CS2040Workload.getTutorialHour().toString())
                .withLabHour("2")
                .withProjectHour(CS2040Workload.getProjectHour().toString())
                .withPreparationHour(CS2040Workload.getPreparationHour().toString()).build();
        assertFalse(CS2040Workload.equals(modifiedCS2040Workload));

        //different project hours -> return false
        modifiedCS2040Workload = new WorkloadBuilder().withLectureHour(CS2040Workload.getLectureHour().toString())
                .withTutorialHour(CS2040Workload.getTutorialHour().toString())
                .withLabHour(CS2040Workload.getLabHour().toString())
                .withProjectHour("2")
                .withPreparationHour(CS2040Workload.getPreparationHour().toString()).build();
        assertFalse(CS2040Workload.equals(modifiedCS2040Workload));

        //different preparation hours -> return false
        modifiedCS2040Workload = new WorkloadBuilder().withLectureHour(CS2040Workload.getLectureHour().toString())
                .withTutorialHour(CS2040Workload.getTutorialHour().toString())
                .withLabHour(CS2040Workload.getLabHour().toString())
                .withProjectHour(CS2040Workload.getProjectHour().toString())
                .withPreparationHour("2").build();
        assertFalse(CS2040Workload.equals(modifiedCS2040Workload));
    }

    @Test
    public void equals() {
        // same object -> returns true
        assertTrue(CS2040Workload.equals(CS2040Workload));

        // null -> returns false
        assertFalse(CS2040Workload.equals(null));

        // different type -> returns false
        assertFalse(CS2040Workload.equals(5));

        // different CS2040Workload -> returns false
        assertFalse(CS2040Workload.equals(CS1010Workload));
    }

}

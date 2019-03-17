package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalWorkloads.CS1010WORKLOAD;
import static seedu.address.testutil.TypicalWorkloads.CS2040WORKLOAD;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.WorkloadBuilder;

class WorkloadTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void equals() {
        // same object -> returns true
        assertTrue(CS2040WORKLOAD.equals(CS2040WORKLOAD));

        // different type -> returns false
        assertFalse(CS2040WORKLOAD.equals(5));

        // different CS2040WORKLOAD -> returns false
        assertFalse(CS2040WORKLOAD.equals(CS1010WORKLOAD));

        // same values -> returns true
        Workload modifiedCS2040WORKLOAD = new WorkloadBuilder()
                .withLectureHour(CS2040WORKLOAD.getLectureHour().toString())
                .withTutorialHour(CS2040WORKLOAD.getTutorialHour().toString())
                .withLabHour(CS2040WORKLOAD.getLabHour().toString())
                .withProjectHour(CS2040WORKLOAD.getProjectHour().toString())
                .withPreparationHour(CS2040WORKLOAD.getPreparationHour().toString()).build();
        assertFalse(CS2040WORKLOAD.equals(modifiedCS2040WORKLOAD));

        //different lecture hours -> return false
        modifiedCS2040WORKLOAD = new WorkloadBuilder().withLectureHour("2")
                .withTutorialHour(CS2040WORKLOAD.getTutorialHour().toString())
                .withLabHour(CS2040WORKLOAD.getLabHour().toString())
                .withProjectHour(CS2040WORKLOAD.getProjectHour().toString())
                .withPreparationHour(CS2040WORKLOAD.getPreparationHour().toString()).build();
        assertFalse(CS2040WORKLOAD.equals(modifiedCS2040WORKLOAD));

        //different tutorial hours -> return false
        modifiedCS2040WORKLOAD = new WorkloadBuilder().withLectureHour(CS2040WORKLOAD.getLectureHour().toString())
                .withTutorialHour(CS2040WORKLOAD.getTutorialHour().toString())
                .withLabHour(CS2040WORKLOAD.getLabHour().toString())
                .withProjectHour(CS2040WORKLOAD.getProjectHour().toString())
                .withPreparationHour(CS2040WORKLOAD.getPreparationHour().toString()).build();
        assertFalse(CS2040WORKLOAD.equals(modifiedCS2040WORKLOAD));

        //different tutorial hours -> return false
        modifiedCS2040WORKLOAD = new WorkloadBuilder().withLectureHour(CS2040WORKLOAD.getLectureHour().toString())
                .withTutorialHour("2")
                .withLabHour(CS2040WORKLOAD.getLabHour().toString())
                .withProjectHour(CS2040WORKLOAD.getProjectHour().toString())
                .withPreparationHour(CS2040WORKLOAD.getPreparationHour().toString()).build();
        assertFalse(CS2040WORKLOAD.equals(modifiedCS2040WORKLOAD));

        //different lab hours -> return false
        modifiedCS2040WORKLOAD = new WorkloadBuilder().withLectureHour(CS2040WORKLOAD.getLectureHour().toString())
                .withTutorialHour(CS2040WORKLOAD.getTutorialHour().toString())
                .withLabHour("2")
                .withProjectHour(CS2040WORKLOAD.getProjectHour().toString())
                .withPreparationHour(CS2040WORKLOAD.getPreparationHour().toString()).build();
        assertFalse(CS2040WORKLOAD.equals(modifiedCS2040WORKLOAD));

        //different project hours -> return false
        modifiedCS2040WORKLOAD = new WorkloadBuilder().withLectureHour(CS2040WORKLOAD.getLectureHour().toString())
                .withTutorialHour(CS2040WORKLOAD.getTutorialHour().toString())
                .withLabHour(CS2040WORKLOAD.getLabHour().toString())
                .withProjectHour("2")
                .withPreparationHour(CS2040WORKLOAD.getPreparationHour().toString()).build();
        assertFalse(CS2040WORKLOAD.equals(modifiedCS2040WORKLOAD));

        //different preparation hours -> return false
        modifiedCS2040WORKLOAD = new WorkloadBuilder().withLectureHour(CS2040WORKLOAD.getLectureHour().toString())
                .withTutorialHour(CS2040WORKLOAD.getTutorialHour().toString())
                .withLabHour(CS2040WORKLOAD.getLabHour().toString())
                .withProjectHour(CS2040WORKLOAD.getProjectHour().toString())
                .withPreparationHour("2").build();
        assertFalse(CS2040WORKLOAD.equals(modifiedCS2040WORKLOAD));
    }

}

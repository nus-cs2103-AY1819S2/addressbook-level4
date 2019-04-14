package seedu.address.model.recmodule;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static seedu.address.model.moduleinfo.ModuleInfoPrerequisites.NOREQUIREMENT_MESSAGE;
import static seedu.address.testutil.TypicalModuleTaken.CS2101;
import static seedu.address.testutil.TypicalModuleTaken.CS2103T;
import static seedu.address.testutil.TypicalModuleTaken.GER1000;

import java.util.List;

import org.junit.Test;

import seedu.address.model.GradTrak;
import seedu.address.model.course.Course;
import seedu.address.model.util.SampleCourse;
import seedu.address.testutil.GradTrakBuilder;
import seedu.address.testutil.RecModuleBuilder;
import seedu.address.testutil.TypicalModuleTaken;

public class RecModulePredicateTest {

    private Course algoCourse = SampleCourse.COMPUTER_SCIENCE_ALGORITHMS;
    private Course aiCourse = SampleCourse.COMPUTER_SCIENCE_AI;
    private Course seCourse = SampleCourse.COMPUTER_SCIENCE_SOFTWARE_ENG;
    private GradTrak emptyGradTrak = new GradTrak();
    private RecModuleBuilder rmb = new RecModuleBuilder();
    private List<RecModule> allModules = rmb.getAllModules();

    @Test
    public void equals() {
        GradTrak gt1 = new GradTrakBuilder().withPerson(CS2103T).build();
        GradTrak gt2 = new GradTrakBuilder().withPerson(CS2103T).withPerson(CS2101).build();

        /* same course and gradtrak -> returns true */
        RecModulePredicate rmp1 = new RecModulePredicate(algoCourse, gt1);
        RecModulePredicate rmp2 = new RecModulePredicate(algoCourse, gt1);
        assertTrue(rmp1.equals(rmp2));

        /* different course -> returns false */
        rmp1 = new RecModulePredicate(algoCourse, emptyGradTrak);
        rmp2 = new RecModulePredicate(aiCourse, emptyGradTrak);
        assertFalse(rmp1.equals(rmp2));

        /* different gradtrak -> returns false */
        rmp1 = new RecModulePredicate(algoCourse, emptyGradTrak);
        rmp2 = new RecModulePredicate(algoCourse, gt1);
        assertFalse(rmp1.equals(rmp2));
        rmp1 = new RecModulePredicate(algoCourse, gt2);
        assertFalse(rmp1.equals(rmp2));
    }

    @Test
    public void test_ineligibleModule_returnsFalse() {
        /* module without prerequisites but already in GradTrak -> returns false */
        GradTrak gt = new GradTrakBuilder().withPerson(GER1000).build();
        RecModulePredicate rmp = new RecModulePredicate(algoCourse, gt);
        RecModule rm = rmb.create("GER1000");
        assertFalse(rmp.test(rm));

        /* module not in GradTrak but prerequisites not met -> returns false */
        rmp = new RecModulePredicate(algoCourse, emptyGradTrak);
        rm = rmb.create("CS3230");
        assertFalse(rmp.test(rm));
    }

    @Test
    public void test_eligibleModule_returnsTrue() {
        RecModulePredicate algoRmp = new RecModulePredicate(algoCourse, emptyGradTrak);
        RecModulePredicate aiRmp = new RecModulePredicate(aiCourse, emptyGradTrak);
        RecModulePredicate seRmp = new RecModulePredicate(seCourse, emptyGradTrak);
        for (RecModule rm : allModules) {
            if (rm.getModuleInfo().getPrerequisitesString().equals(NOREQUIREMENT_MESSAGE)) {
                // all modules can contribute to UE
                assertTrue(algoRmp.test(rm));
                assertTrue(aiRmp.test(rm));
                assertTrue(seRmp.test(rm));
            }
        }
    }

    @Test
    public void test_completedRequirements_returnsFalse() {
        GradTrak algoGt = new GradTrak();
        algoGt.setModulesTaken(TypicalModuleTaken.getFullAlgoList());
        GradTrak aiGt = new GradTrak();
        aiGt.setModulesTaken(TypicalModuleTaken.getFullAiList());
        GradTrak seGt = new GradTrak();
        seGt.setModulesTaken(TypicalModuleTaken.getFullSeList());
        RecModulePredicate algoRmp = new RecModulePredicate(algoCourse, algoGt);
        RecModulePredicate aiRmp = new RecModulePredicate(aiCourse, aiGt);
        RecModulePredicate seRmp = new RecModulePredicate(seCourse, seGt);

        for (RecModule rm : allModules) {
            assertFalse(algoRmp.test(rm));
            assertFalse(aiRmp.test(rm));
            assertFalse(seRmp.test(rm));
        }
    }
}

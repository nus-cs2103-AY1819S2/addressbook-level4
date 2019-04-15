package seedu.address.model.course;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.course.CompositeRequirement.LogicalConnector.AND;
import static seedu.address.model.course.CompositeRequirement.LogicalConnector.OR;
import static seedu.address.model.course.CourseReqType.CORE;
import static seedu.address.model.course.CourseReqType.GE;
import static seedu.address.model.course.CourseReqType.UE;
import static seedu.address.model.util.SampleCourseRequirement.COMPUTER_SCIENCE_FOUNDATION;
import static seedu.address.model.util.SampleCourseRequirement.SCIENCE_REQUIREMENT;
import static seedu.address.model.util.SampleCourseRequirement.UNIVERSITY_LEVEL_REQUIREMENT;
import static seedu.address.testutil.TypicalModuleTaken.getTypicalModulesInfoCodes;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.model.moduleinfo.ModuleInfoCode;
import seedu.address.testutil.Assert;

public class CompositeRequirementTest {
    private static final CompositeRequirement sampleRequirement = new CompositeRequirement(UNIVERSITY_LEVEL_REQUIREMENT,
            COMPUTER_SCIENCE_FOUNDATION, AND, CORE);
    private static final CourseRequirement ulrReq = UNIVERSITY_LEVEL_REQUIREMENT;
    private static final CourseRequirement scienceReq = SCIENCE_REQUIREMENT;
    private static final List<ModuleInfoCode> test = getTypicalModulesInfoCodes();
    private static final List<ModuleInfoCode> geModuleCodes = test.stream()
            .filter(moduleInfoCode -> ulrReq.canFulfill(moduleInfoCode))
            .collect(Collectors.toList());
    private static final CompositeRequirement ulrAndScience = new CompositeRequirement(ulrReq, scienceReq, AND, GE);
    private static final CompositeRequirement ulrOrScience = new CompositeRequirement(ulrReq, scienceReq, OR, GE);
    private static final CompositeRequirement scienceAndUlr = new CompositeRequirement(scienceReq, ulrReq, AND, GE);
    private static final CompositeRequirement scienceOrUlr = new CompositeRequirement(scienceReq, ulrReq, OR, GE);


    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new CompositeRequirement(null ,
                UNIVERSITY_LEVEL_REQUIREMENT, AND, GE));
        Assert.assertThrows(NullPointerException.class, () -> new CompositeRequirement(UNIVERSITY_LEVEL_REQUIREMENT,
                null, AND, GE));
        Assert.assertThrows(NullPointerException.class, () -> new CompositeRequirement(UNIVERSITY_LEVEL_REQUIREMENT,
                UNIVERSITY_LEVEL_REQUIREMENT, null, GE));
        Assert.assertThrows(NullPointerException.class, () -> new CompositeRequirement(UNIVERSITY_LEVEL_REQUIREMENT,
                UNIVERSITY_LEVEL_REQUIREMENT, OR, null));
    }

    @Test
    public void equal() {
        //same object
        assertTrue(sampleRequirement.equals(sampleRequirement));
        //not equals null
        assertFalse(sampleRequirement.equals(null));
        //not equals a different object
        assertFalse(sampleRequirement.equals(5));
        //different first requirement
        assertFalse(sampleRequirement.equals(new CompositeRequirement(SCIENCE_REQUIREMENT, COMPUTER_SCIENCE_FOUNDATION,
                AND, CORE)));
        //different second requirement
        assertFalse(sampleRequirement.equals(new CompositeRequirement(UNIVERSITY_LEVEL_REQUIREMENT,
                SCIENCE_REQUIREMENT, AND, CORE)));
        //different type
        assertFalse(sampleRequirement.equals(new CompositeRequirement(UNIVERSITY_LEVEL_REQUIREMENT,
                COMPUTER_SCIENCE_FOUNDATION, AND, UE)));
        //different connector
        assertFalse(sampleRequirement.equals(new CompositeRequirement(UNIVERSITY_LEVEL_REQUIREMENT,
                COMPUTER_SCIENCE_FOUNDATION, OR, UE)));
        //everything same but different order
        assertTrue(sampleRequirement.equals(new CompositeRequirement(COMPUTER_SCIENCE_FOUNDATION,
                UNIVERSITY_LEVEL_REQUIREMENT, AND, CORE)));
    }

    @Test
    public void isFulfilled_orConnectorSatisfyAtLeastOne_returnsTrue() {
        assertTrue(ulrOrScience.isFulfilled(test));
    }

    @Test
    public void isFulfilled_orConnectorSatisfyNone_returnsFalse() {
        List<ModuleInfoCode> list = List.of();
        assertFalse(ulrOrScience.isFulfilled(list));

    }

    @Test
    public void isFulfilled_andConnectorSatisfyBoth_returnsTrue() {
        assertTrue(ulrAndScience.isFulfilled(test));
    }

    @Test
    public void isFulfilled_andConnectorAtLeastOneNotSatisfied_returnsFalse() {
        assertFalse(ulrAndScience.isFulfilled(geModuleCodes));
    }

    @Test
    public void isFulfilled_logicalConnectorCommutesWithCheckingComponent() {
        assertEquals(ulrAndScience.isFulfilled(test), ulrReq.isFulfilled(test)
                && scienceReq.isFulfilled(test));
        assertEquals(ulrOrScience.isFulfilled(test), ulrReq.isFulfilled(test)
                || scienceReq.isFulfilled(test));
    }

    @Test
    public void isFulfilled_orderDoesNotAffectResult() {
        assertEquals(ulrAndScience.isFulfilled(test), scienceAndUlr.isFulfilled(test));
        assertEquals(ulrOrScience.isFulfilled(test), scienceOrUlr.isFulfilled(test));
    }

    @Test
    public void canFulfill_satisfyEither_returnsTrue() {
        assertTrue(ulrAndScience.canFulfill(new ModuleInfoCode("GER1000")));
        assertTrue(ulrOrScience.canFulfill(new ModuleInfoCode("GER1000")));
    }

    @Test
    public void canFulfill_satisfyNone_returnsFalse() {
        assertFalse(ulrAndScience.canFulfill(new ModuleInfoCode("GE1000")));
        assertFalse(ulrOrScience.canFulfill(new ModuleInfoCode("GE1000")));
    }

    @Test
    public void getFulfilledPercentage_emptyList_equalsToZero() {
        assertTrue(0 == ulrAndScience.getFulfilledPercentage(Collections.EMPTY_LIST));
    }

    @Test
    public void getFulfilledPercentage_completedRequirement_equalsToOne() {
        assertTrue(1 == ulrOrScience.getFulfilledPercentage(test));
    }

    @Test
    public void getFulfilledPercentage_andConnector_returnsAverage() {
        assertTrue((ulrReq.getFulfilledPercentage(geModuleCodes)
                + scienceReq.getFulfilledPercentage(geModuleCodes)) / 2
                == ulrAndScience.getFulfilledPercentage(geModuleCodes));
    }

    @Test
    public void getFulfilledPercentage_orConnector_returnsMax() {
        assertTrue(Math.max(ulrReq.getFulfilledPercentage(geModuleCodes),
                scienceReq.getFulfilledPercentage(geModuleCodes))
                == ulrOrScience.getFulfilledPercentage(geModuleCodes));
    }

    @Test
    public void getFulfilledPercentage_notAffectedByOrder() {
        assertTrue(ulrOrScience.getFulfilledPercentage(geModuleCodes)
                == scienceOrUlr.getFulfilledPercentage(geModuleCodes));
        assertTrue(scienceAndUlr.getFulfilledPercentage(geModuleCodes)
                == ulrAndScience.getFulfilledPercentage(geModuleCodes));
    }

    @Test
    public void getUnfulfilled_emptyList_returnsFullList() {
        List<String> actual = ulrOrScience.getUnfulfilled(Collections.emptyList());
        List<String> expected = ulrReq.getUnfulfilled(Collections.emptyList());
        expected.addAll(scienceReq.getUnfulfilled(Collections.emptyList()));
        assertEquals(actual, expected);
    }

    @Test
    public void getUnfilfilled_someConditionsUnfulfilled_returnsScience() {
        List<String> actual = ulrOrScience.getUnfulfilled(geModuleCodes);
        List<String> expected = scienceReq.getUnfulfilled(Collections.emptyList());
        assertEquals(actual, expected);
    }

    @Test
    public void getUnfulfilled_allRequirementSatisfied_returnsEmptyList() {
        List<String> actual = ulrOrScience.getUnfulfilled(test);
        assertEquals(Collections.emptyList(), actual);
    }
}

package seedu.address.model.course;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.course.CourseReqType.GE;
import static seedu.address.model.course.CourseReqType.UE;
import static seedu.address.model.util.SampleCourseRequirement.COMPLETE_40_MODULES;
import static seedu.address.model.util.SampleCourseRequirement.COMPUTER_SCIENCE_FOUNDATION;
import static seedu.address.model.util.SampleCourseRequirement.UNIVERSITY_LEVEL_REQUIREMENT;

import static seedu.address.testutil.TypicalCondition.GEH;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.model.moduleinfo.ModuleInfoCode;
import seedu.address.testutil.Assert;

public class PrimitiveRequirementTest {
    public static final PrimitiveRequirement SAMPLE_REQUIREMENT = (PrimitiveRequirement) UNIVERSITY_LEVEL_REQUIREMENT;
    public static final Condition[] SAMPLE_CONDITION = SAMPLE_REQUIREMENT.getConditions().toArray(new Condition[0]);
    public static final PrimitiveRequirement SAMPLE_REQUIREMENT_2 = (PrimitiveRequirement) COMPUTER_SCIENCE_FOUNDATION;
    public static final List<ModuleInfoCode> SAMPLE_LIST = List.of(new ModuleInfoCode("GER1000"),
            new ModuleInfoCode("GES1000"), new ModuleInfoCode("GET1000"),
            new ModuleInfoCode("GEQ1000"), new ModuleInfoCode("GEH1000"));

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new PrimitiveRequirement(null , "some description",
                GE, GEH));
        Assert.assertThrows(NullPointerException.class, () -> new PrimitiveRequirement("ReqName",
               null, GE, SAMPLE_CONDITION));
        Assert.assertThrows(NullPointerException.class, () -> new PrimitiveRequirement("Reqname",
               "nonNullDescription", GE, null, GEH));
        Assert.assertThrows(NullPointerException.class, () -> new PrimitiveRequirement("Reqname",
                "nonNullDescription", null, GEH));
    }

    @Test
    public void equal() {
        //same object
        assertTrue(SAMPLE_REQUIREMENT.equals(SAMPLE_REQUIREMENT));
        //same attributes
        assertTrue(SAMPLE_REQUIREMENT.equals(new PrimitiveRequirement(SAMPLE_REQUIREMENT.getCourseReqName(),
                SAMPLE_REQUIREMENT.getCourseReqDesc(), SAMPLE_REQUIREMENT.getType(), SAMPLE_CONDITION)));
        //not equals null
        assertFalse(SAMPLE_REQUIREMENT.equals(null));
        //not equals a different object
        assertFalse(SAMPLE_REQUIREMENT.equals(5));
        //different name
        assertFalse(SAMPLE_REQUIREMENT.equals(new PrimitiveRequirement("Other name",
            SAMPLE_REQUIREMENT.getCourseReqDesc(), SAMPLE_REQUIREMENT.getType(),
                SAMPLE_CONDITION)));
        //different description
        assertFalse(SAMPLE_REQUIREMENT.equals(new PrimitiveRequirement(SAMPLE_REQUIREMENT.getCourseReqName(),
            "other description", SAMPLE_REQUIREMENT.getType(), SAMPLE_CONDITION)));
        //different condition
        assertFalse(SAMPLE_REQUIREMENT.equals(new PrimitiveRequirement(SAMPLE_REQUIREMENT.getCourseReqName(),
            SAMPLE_REQUIREMENT.getCourseReqDesc(), SAMPLE_REQUIREMENT.getType(), COMPLETE_40_MODULES)));
        //different type
        assertFalse(SAMPLE_REQUIREMENT.equals(new PrimitiveRequirement(SAMPLE_REQUIREMENT.getCourseReqName(),
            SAMPLE_REQUIREMENT.getCourseReqDesc(), UE, SAMPLE_CONDITION)));
    }

    @Test
    public void or_sameObjects_areEqual() {
        assertEquals(SAMPLE_REQUIREMENT.or(SAMPLE_REQUIREMENT_2), SAMPLE_REQUIREMENT.or(SAMPLE_REQUIREMENT_2));
    }

    @Test
    public void or_objectSameAttributes_areEqual() {
        assertEquals(SAMPLE_REQUIREMENT.or(SAMPLE_REQUIREMENT_2), SAMPLE_REQUIREMENT.or(new PrimitiveRequirement(
                SAMPLE_REQUIREMENT_2.getCourseReqName(), SAMPLE_REQUIREMENT_2.getCourseReqDesc(),
                SAMPLE_REQUIREMENT_2.getType(), SAMPLE_REQUIREMENT_2.getConditions().toArray(new Condition[0]))));
    }

    @Test
    public void or_orderOfObject_doesMatter() {
        assertNotEquals(SAMPLE_REQUIREMENT_2.or(SAMPLE_REQUIREMENT), SAMPLE_REQUIREMENT.or(SAMPLE_REQUIREMENT_2));
    }

    @Test
    public void and_sameObjects_areEqual() {
        assertEquals(SAMPLE_REQUIREMENT.equals(SAMPLE_REQUIREMENT_2), SAMPLE_REQUIREMENT.equals(SAMPLE_REQUIREMENT_2));
    }

    @Test
    public void and_sameAttributes_areEqual() {
        assertEquals(SAMPLE_REQUIREMENT.and(SAMPLE_REQUIREMENT_2), SAMPLE_REQUIREMENT.and(new PrimitiveRequirement(
                SAMPLE_REQUIREMENT_2.getCourseReqName(), SAMPLE_REQUIREMENT_2.getCourseReqDesc(),
                SAMPLE_REQUIREMENT_2.getType(), SAMPLE_REQUIREMENT_2.getConditions().toArray(new Condition[0]))));
    }

    @Test
    public void and_differentOrder_areNotEqual() {
        assertNotEquals(SAMPLE_REQUIREMENT_2.and(SAMPLE_REQUIREMENT), SAMPLE_REQUIREMENT.and(SAMPLE_REQUIREMENT_2));
    }

    @Test
    public void isFulfilled_fulfilsAllCondition_returnsTrue() {
        assertTrue(SAMPLE_REQUIREMENT.isFulfilled(SAMPLE_LIST));
    }

    @Test
    public void isFulfilled_atLeastOneConditionNotFulfilled_returnsFalse() {
        List<ModuleInfoCode> testList = new ArrayList<>();
        testList.addAll(SAMPLE_LIST);
        testList.remove(0);
        assertFalse(SAMPLE_REQUIREMENT.isFulfilled(testList));
    }

    @Test
    public void isFulfilled_emptyList_returnsFalse() {
        assertFalse(SAMPLE_REQUIREMENT.isFulfilled(new ArrayList<>()));
    }

    @Test
    public void canFulfill_noneMatch_returnsFalse() {
        assertFalse(SAMPLE_REQUIREMENT.canFulfill(new ModuleInfoCode("GE1000K")));
    }

    @Test
    public void canFulfill_atLeastOneMatch_returnsTrue() {
        assertTrue(SAMPLE_REQUIREMENT.canFulfill(new ModuleInfoCode("GER1000S")));
    }

    @Test
    public void getFulfilledPercentage_fulfilledRequirement_returnsOne() {
        assertTrue(SAMPLE_REQUIREMENT.getFulfilledPercentage(SAMPLE_LIST) == 1);
    }

    @Test
    public void getFulfilledPercentage_moreModulesThanFulfilled_returnsOne() {
        List<ModuleInfoCode> testList = new ArrayList<>();
        testList.addAll(SAMPLE_LIST);
        testList.add(new ModuleInfoCode("GEH1036"));
        assertTrue(SAMPLE_REQUIREMENT.getFulfilledPercentage(testList) == 1);
    }

    @Test
    public void getFulfilledPercentage_atLeastOneModuleUnsatisfied_returnsLessThanOne() {
        List<ModuleInfoCode> testList = new ArrayList<>();
        testList.addAll(SAMPLE_LIST);
        testList.remove(0);
        assertTrue(SAMPLE_REQUIREMENT.getFulfilledPercentage(testList) == 0.8);
    }

    @Test
    public void getFulfilledPercentage_emptyList_returnsZero() {
        assertTrue(SAMPLE_REQUIREMENT.getFulfilledPercentage(new ArrayList<>()) == 0);
    }

    @Test
    public void getUnfulfilled_emptyList_returnsAllPatterns() {
        assertEquals(SAMPLE_REQUIREMENT.getUnfulfilled(new ArrayList<>()),
                SAMPLE_REQUIREMENT.getConditions()
                        .stream().map(Condition::getPattern).map(Pattern::toString).collect(Collectors.toList()));
    }

    @Test
    public void getUnfufilled_completedList_returnsEmptyList() {
        assertEquals(SAMPLE_REQUIREMENT.getUnfulfilled(SAMPLE_LIST), new ArrayList<>());
    }

    @Test
    public void getUnfulfilled_oneConditionUnsatisfied_returnsUnsatisfiedPattern() {
        List<ModuleInfoCode> testList;
        testList = SAMPLE_LIST.stream()
                .filter(moduleInfoCode -> !GEH.canSatisfy(moduleInfoCode))
                .collect(Collectors.toList());
        assertEquals(SAMPLE_REQUIREMENT.getUnfulfilled(testList), List.of(GEH.getPattern().toString()));
    }
}

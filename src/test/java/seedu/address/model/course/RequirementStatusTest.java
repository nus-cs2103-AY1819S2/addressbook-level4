package seedu.address.model.course;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.model.util.SampleCourseRequirement.TOTAL_MODULE_COUNT;
import static seedu.address.model.util.SampleCourseRequirement.UNIVERSITY_LEVEL_REQUIREMENT;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.model.moduleinfo.ModuleInfoCode;
import seedu.address.testutil.Assert;

public class RequirementStatusTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new RequirementStatus(null, new ArrayList<>()));
        Assert.assertThrows(NullPointerException.class, () -> new RequirementStatus(UNIVERSITY_LEVEL_REQUIREMENT,
                null));
    }

    @Test
    public void equals() {
        List<ModuleInfoCode> moduleInfoCodeList = List.of(new ModuleInfoCode("GER1000"), new ModuleInfoCode("GEH1000"));
        List<ModuleInfoCode> modifiedList = new ArrayList<>();
        modifiedList.addAll(moduleInfoCodeList);
        modifiedList.add(new ModuleInfoCode("CS2013T"));
        RequirementStatus test = new RequirementStatus(UNIVERSITY_LEVEL_REQUIREMENT, moduleInfoCodeList);
        assertEquals(test, test);
        assertEquals(test, new RequirementStatus(UNIVERSITY_LEVEL_REQUIREMENT, moduleInfoCodeList));
        modifiedList.remove(0);
        modifiedList.add(new ModuleInfoCode("GES1000"));
        assertEquals(test, new RequirementStatus(UNIVERSITY_LEVEL_REQUIREMENT, moduleInfoCodeList));

        modifiedList.remove(0);
        assertNotEquals(test, null);
        assertNotEquals(test, 0);
        assertNotEquals(test, new RequirementStatus(UNIVERSITY_LEVEL_REQUIREMENT, modifiedList));
        assertNotEquals(test, new RequirementStatus(TOTAL_MODULE_COUNT, modifiedList));
    }

    @Test
    public void updateRequirementStatus_sameList_sameRequirementStatus() {
        List<ModuleInfoCode> modifiedList = new ArrayList<>();
        RequirementStatus actual = new RequirementStatus(UNIVERSITY_LEVEL_REQUIREMENT, modifiedList);
        List<ModuleInfoCode> moduleInfoCodeList = List.of(new ModuleInfoCode("GER1000"), new ModuleInfoCode("GEH1000"));
        RequirementStatus expected = new RequirementStatus(UNIVERSITY_LEVEL_REQUIREMENT, moduleInfoCodeList);
        assertNotEquals(actual, expected);
        actual.updateRequirementStatus(moduleInfoCodeList);
        assertEquals(actual, expected);
    }

    @Test
    public void updateRequirementStatus_differentListSamePercentageFulfilled_sameRequirementStatus() {
        List<ModuleInfoCode> moduleInfoCodeList = List.of(new ModuleInfoCode("GER1000"), new ModuleInfoCode("GEH1000"));
        RequirementStatus actual = new RequirementStatus(UNIVERSITY_LEVEL_REQUIREMENT, moduleInfoCodeList);
        RequirementStatus expected = new RequirementStatus(UNIVERSITY_LEVEL_REQUIREMENT, moduleInfoCodeList);
        assertEquals(actual, expected);
        List<ModuleInfoCode> anotherList = List.of(new ModuleInfoCode("GET1000"), new ModuleInfoCode("GES1000"));
        expected.updateRequirementStatus(anotherList);
        assertEquals(actual, expected);
    }


    @Test
    public void updateRequirementStatus_differentListdifferentPercentageFulfilled_differentRequirementStatus() {
        List<ModuleInfoCode> moduleInfoCodeList = List.of(new ModuleInfoCode("GER1000"), new ModuleInfoCode("GEH1000"));
        RequirementStatus actual = new RequirementStatus(UNIVERSITY_LEVEL_REQUIREMENT, moduleInfoCodeList);
        RequirementStatus expected = new RequirementStatus(UNIVERSITY_LEVEL_REQUIREMENT, moduleInfoCodeList);
        assertEquals(actual, expected);
        List<ModuleInfoCode> anotherList = List.of(new ModuleInfoCode("GET1000"), new ModuleInfoCode("GE1000"));
        expected.updateRequirementStatus(anotherList);
        assertNotEquals(actual, expected);
    }

}

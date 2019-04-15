package seedu.address.model.course;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.model.util.SampleCourse.COMPUTER_SCIENCE_ALGORITHMS;
import static seedu.address.model.util.SampleCourse.COMPUTER_SCIENCE_SOFTWARE_ENG;
import static seedu.address.model.util.SampleCourseRequirement.UNIVERSITY_LEVEL_REQUIREMENT;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.moduleinfo.ModuleInfoCode;

public class RequirementStatusListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor() {
        Assert.assertEquals(Collections.EMPTY_LIST, new RequirementStatusList().getRequirementStatusList());
    }

    @Test
    public void updateRequirementStatus_null_throwsNullPointerException() {
        RequirementStatusList requirementStatusList = new RequirementStatusList();
        assertThrows(NullPointerException.class, () -> requirementStatusList.updateRequirementStatus(null,
                List.of(new ModuleInfoCode("CS2101"))));
        assertThrows(NullPointerException.class, () -> requirementStatusList.updateRequirementStatus(
                COMPUTER_SCIENCE_ALGORITHMS, null));
    }

    @Test
    public void updateRequirementStatus_differentCourseSameList_differentRequirementStatusList() {
        List<ModuleInfoCode> moduleInfoCodeList = List.of();
        RequirementStatusList algorithmsCourse = new RequirementStatusList();
        RequirementStatusList softwareEngCourse = new RequirementStatusList();
        algorithmsCourse.updateRequirementStatus(COMPUTER_SCIENCE_ALGORITHMS, moduleInfoCodeList);
        softwareEngCourse.updateRequirementStatus(COMPUTER_SCIENCE_SOFTWARE_ENG, moduleInfoCodeList);
        assertNotEquals(softwareEngCourse, algorithmsCourse);

        algorithmsCourse.updateRequirementStatus(moduleInfoCodeList);
        softwareEngCourse.updateRequirementStatus(moduleInfoCodeList);
        assertNotEquals(softwareEngCourse, algorithmsCourse);
    }

    @Test
    public void updateRequirementStatus_sameCourseDifferentListDifferentCompletion_differentRequirementStatus() {
        List<ModuleInfoCode> moduleInfoCodeList = new ArrayList<>();
        RequirementStatusList noModulesCompleted = new RequirementStatusList();
        RequirementStatusList oneModuleCompleted = new RequirementStatusList();
        noModulesCompleted.updateRequirementStatus(COMPUTER_SCIENCE_ALGORITHMS, moduleInfoCodeList);
        moduleInfoCodeList.add(new ModuleInfoCode("GER1000"));
        oneModuleCompleted.updateRequirementStatus(COMPUTER_SCIENCE_ALGORITHMS, moduleInfoCodeList);
        assertNotEquals(noModulesCompleted, oneModuleCompleted);
    }

    @Test
    public void updateRequirementStatus_sameCourseDifferentListSameCompletion_sameRequirementStatusList() {
        List<ModuleInfoCode> gerGetCodes = List.of(new ModuleInfoCode("GER1000"), new ModuleInfoCode("GET1000"));
        List<ModuleInfoCode> gehGesCodes = List.of(new ModuleInfoCode("GEH1000"), new ModuleInfoCode("GES1000"));
        RequirementStatusList completedGerGet = new RequirementStatusList();
        RequirementStatusList completedGehGes = new RequirementStatusList();
        completedGerGet.updateRequirementStatus(COMPUTER_SCIENCE_ALGORITHMS, gerGetCodes);
        completedGehGes.updateRequirementStatus(COMPUTER_SCIENCE_ALGORITHMS, gehGesCodes);
        assertEquals(completedGehGes, completedGerGet);

        //to check overloaded method
        completedGehGes.updateRequirementStatus(gehGesCodes);
        completedGerGet.updateRequirementStatus(gerGetCodes);
        assertEquals(completedGehGes, completedGerGet);
    }

    @Test
    public void updateRequirementStatus_sameModuleTakenList_sameRequirementStatusList() {
        List<ModuleInfoCode> gerGetCodes = List.of(new ModuleInfoCode("GER1000"), new ModuleInfoCode("GET1000"));
        RequirementStatusList actual = new RequirementStatusList();
        RequirementStatusList expected = new RequirementStatusList();
        actual.updateRequirementStatus(COMPUTER_SCIENCE_ALGORITHMS, List.of());
        actual.updateRequirementStatus(gerGetCodes);
        expected.updateRequirementStatus(COMPUTER_SCIENCE_ALGORITHMS, gerGetCodes);
        assertEquals(expected, actual);
    }

    @Test
    public void getRequirementStatusList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        new RequirementStatusList()
                .getRequirementStatusList()
                .add(new RequirementStatus(UNIVERSITY_LEVEL_REQUIREMENT, List.of()));
    }
}

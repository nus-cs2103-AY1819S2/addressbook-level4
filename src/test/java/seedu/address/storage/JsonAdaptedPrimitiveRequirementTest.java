package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.model.util.SampleCourseRequirement.UNIVERSITY_LEVEL_REQUIREMENT;
import static seedu.address.storage.coursestorage.JsonAdaptedPrimitiveRequirement.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.course.Condition;
import seedu.address.model.course.PrimitiveRequirement;
import seedu.address.storage.coursestorage.JsonAdaptedCondition;
import seedu.address.storage.coursestorage.JsonAdaptedPrimitiveRequirement;

public class JsonAdaptedPrimitiveRequirementTest {
    private final PrimitiveRequirement ulr = (PrimitiveRequirement) UNIVERSITY_LEVEL_REQUIREMENT;

    private final String requirementName = ulr.getCourseReqName();
    private final String requirementDesc = ulr.getCourseReqDesc();
    private final List<JsonAdaptedCondition> jsonAdaptedConditions =
            ulr.getConditions().stream().map(JsonAdaptedCondition::new).collect(Collectors.toList());
    private final String requirementType = ulr.getType().toString();
    private final String invalidType = "invalid type!";

    @Test
    public void toModelType_validPrimitiveRequirement_returnsPrimitiveRequirement() throws Exception {
        JsonAdaptedPrimitiveRequirement test = new JsonAdaptedPrimitiveRequirement(ulr);
        assertEquals(test.toModelType(), ulr);
    }

    @Test
    public void toModelType_missingName_throwsIllegalValueException() {
        JsonAdaptedPrimitiveRequirement test = new JsonAdaptedPrimitiveRequirement(null, requirementDesc,
                requirementType, jsonAdaptedConditions);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "requirementName");
        assertThrows(IllegalValueException.class, expectedMessage, test::toModelType);
    }


    @Test
    public void toModelType_missingDescription_throwsIllegalValueException() {
        JsonAdaptedPrimitiveRequirement test = new JsonAdaptedPrimitiveRequirement(requirementName, null,
                requirementType, jsonAdaptedConditions);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "requirementDescription");
        assertThrows(IllegalValueException.class, expectedMessage, test::toModelType);
    }

    @Test
    public void toModelType_invalidType_throwsIllegalValueException() {
        JsonAdaptedPrimitiveRequirement test = new JsonAdaptedPrimitiveRequirement(requirementName, requirementDesc,
                invalidType, jsonAdaptedConditions);
        //TODO: get rid of magic number
        String expectedMessage = "No enum constant seedu.address.model.course.CourseReqType.invalid type!";
        assertThrows(IllegalValueException.class, expectedMessage, test::toModelType);
    }

    @Test
    public void toModelType_missingType_throwsIllegalValueException() {
        JsonAdaptedPrimitiveRequirement test = new JsonAdaptedPrimitiveRequirement(requirementName, requirementDesc,
                null, jsonAdaptedConditions);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "requirementType");
        assertThrows(IllegalValueException.class, expectedMessage, test::toModelType);
    }

    @Test
    public void toModelType_missingConditions_throwsIllegalValueException() {
        JsonAdaptedPrimitiveRequirement test = new JsonAdaptedPrimitiveRequirement(requirementName, requirementDesc,
                requirementType, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Condition.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, test::toModelType);
    }
}


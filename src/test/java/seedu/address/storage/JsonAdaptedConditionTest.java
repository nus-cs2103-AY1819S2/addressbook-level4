package seedu.address.storage;

import static seedu.address.storage.coursestorage.JsonAdaptedCondition.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCondition.GEH;

import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.course.Condition;
import seedu.address.storage.coursestorage.JsonAdaptedCondition;
import seedu.address.testutil.ConditionBuilder;

public class JsonAdaptedConditionTest {

    private static final String VALID_PATTERN = GEH.getPattern().toString();
    private static final String VALID_MINTOSATISFY = String.format("%d", GEH.getMinToSatisfy());

    private static final String INVALID_PATTERN = "{))([bc}";
    private static final String INVALID_MINTOSATISFY = "0";

    @Test
    public void toModelType_validCondition_returnsCondition() throws Exception {
        JsonAdaptedCondition test = new JsonAdaptedCondition(new ConditionBuilder().build());
        Assert.assertEquals(test.toModelType(), GEH);
    }

    @Test
    public void toModelType_invalidPattern_throwsIllegalValueException() {
        JsonAdaptedCondition test = new JsonAdaptedCondition(VALID_MINTOSATISFY, INVALID_PATTERN);
        String expectedMessage = Condition.INVALID_REGEXES;
        assertThrows(IllegalValueException.class, expectedMessage, test::toModelType);
    }
    @Test
    public void toModelType_nullPattern_throwsIllegalValueException() {
        JsonAdaptedCondition test = new JsonAdaptedCondition(VALID_MINTOSATISFY, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Pattern.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, test::toModelType);
    }

    @Test
    public void toModelType_invalidMinToSatisfy_throwIllegalValueException() {
        JsonAdaptedCondition test = new JsonAdaptedCondition(INVALID_MINTOSATISFY, VALID_PATTERN);
        String expectedMessage = Condition.INVALID_MIN_TO_SATISFY;
        assertThrows(IllegalValueException.class, expectedMessage, test::toModelType);
    }

    @Test
    public void toModelType_nullMinToSatisfy_throwsIllegalValueException() {
        JsonAdaptedCondition test = new JsonAdaptedCondition(null, VALID_PATTERN);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "minToSatisfy");
        assertThrows(IllegalValueException.class, expectedMessage, test::toModelType);
    }

    @Test
    public void toModelType_unparsableAsIntMinToSatisfy_throwsIllegalValueException() {
        JsonAdaptedCondition test = new JsonAdaptedCondition(INVALID_PATTERN, VALID_PATTERN);
        String expectedMessage = JsonAdaptedCondition.NOT_PARSABLE_AS_INT;
        assertThrows(IllegalValueException.class, expectedMessage, test::toModelType);
    }

}

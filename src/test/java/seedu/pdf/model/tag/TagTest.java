package seedu.pdf.model.tag;

import static org.junit.Assert.fail;
import static seedu.pdf.logic.commands.CommandTestUtil.MESSAGE_UNEXPECTEDEXCEPTION_VALIDINPUT;
import static seedu.pdf.logic.commands.CommandTestUtil.TAG_VALID_LECTURE;

import org.junit.Test;

import seedu.pdf.testutil.Assert;



public class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        Assert.assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));

        // valid tag -> expect no exceptions
        try {
            new Tag(TAG_VALID_LECTURE);
        } catch (Exception e) {
            fail(MESSAGE_UNEXPECTEDEXCEPTION_VALIDINPUT);
        }
    }

}

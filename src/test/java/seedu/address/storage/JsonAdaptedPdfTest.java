package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_JSON_DONE;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_JSON_INVALID_INVALIDDATE;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_JSON_NOTDONE;
import static seedu.address.logic.commands.CommandTestUtil.DIR_1_VALID;
import static seedu.address.logic.commands.CommandTestUtil.DIR_INVALID_NONEXISTENT;
import static seedu.address.logic.commands.CommandTestUtil.NAME_1_VALID;
import static seedu.address.logic.commands.CommandTestUtil.NAME_INVALID_EXTENSION;
import static seedu.address.logic.commands.CommandTestUtil.SIZE_1_VALID;
import static seedu.address.logic.commands.CommandTestUtil.SIZE_INVALID_ALPHABET;
import static seedu.address.logic.commands.CommandTestUtil.TAG_INVALID_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_VALID_LECTURE;
import static seedu.address.storage.JsonAdaptedPdf.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalPdfs.SAMPLE_PDF_1;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.pdf.Deadline;
import seedu.address.model.pdf.Directory;
import seedu.address.model.pdf.Name;
import seedu.address.model.pdf.Size;
import seedu.address.testutil.Assert;

public class JsonAdaptedPdfTest {

    @Test
    public void toModelType_validPdfDetails_returnsPdf() throws Exception {
        JsonAdaptedPdf pdf = new JsonAdaptedPdf(SAMPLE_PDF_1);
        assertEquals(SAMPLE_PDF_1, pdf.toModelType());
    }

    @Test
    public void toModelType_invalidDirectorty_throwsIllegalValueException() {
        JsonAdaptedPdf pdf =
                new JsonAdaptedPdf(SAMPLE_PDF_1.getName().getFullName(), DIR_INVALID_NONEXISTENT, SIZE_1_VALID,
                null, DEADLINE_JSON_NOTDONE);
        String expectedMessage = Directory.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, pdf::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>();
        invalidTags.add(new JsonAdaptedTag(TAG_VALID_LECTURE));
        invalidTags.add(new JsonAdaptedTag(TAG_INVALID_FRIEND));
        JsonAdaptedPdf person =
                new JsonAdaptedPdf(NAME_1_VALID, DIR_1_VALID, SIZE_1_VALID, invalidTags, DEADLINE_JSON_DONE);
        Assert.assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        List<JsonAdaptedTag> tags = new ArrayList<>();
        JsonAdaptedPdf person =
                new JsonAdaptedPdf(NAME_INVALID_EXTENSION, DIR_1_VALID, SIZE_1_VALID, tags, DEADLINE_JSON_DONE);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        List<JsonAdaptedTag> tags = new ArrayList<>();
        JsonAdaptedPdf person = new JsonAdaptedPdf(null, DIR_1_VALID, SIZE_1_VALID, tags, DEADLINE_JSON_DONE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidDirectory_throwsIllegalValueException() {
        List<JsonAdaptedTag> tags = new ArrayList<>();
        JsonAdaptedPdf person =
                new JsonAdaptedPdf(NAME_1_VALID, DIR_INVALID_NONEXISTENT, SIZE_1_VALID, tags, DEADLINE_JSON_DONE);
        String expectedMessage = Directory.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullDirectory_throwsIllegalValueException() {
        List<JsonAdaptedTag> tags = new ArrayList<>();
        JsonAdaptedPdf person = new JsonAdaptedPdf(NAME_1_VALID, null, SIZE_1_VALID, tags, DEADLINE_JSON_DONE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Directory.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidSize_throwsIllegalValueException() {
        List<JsonAdaptedTag> tags = new ArrayList<>();
        JsonAdaptedPdf person =
                new JsonAdaptedPdf(NAME_1_VALID, DIR_1_VALID, SIZE_INVALID_ALPHABET, tags, DEADLINE_JSON_DONE);
        String expectedMessage = Size.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullSize_throwsIllegalValueException() {
        List<JsonAdaptedTag> tags = new ArrayList<>();
        JsonAdaptedPdf person =
                new JsonAdaptedPdf(NAME_1_VALID, DIR_1_VALID, null, tags, DEADLINE_JSON_DONE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Size.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidDeadline_throwsIllegalValueException() {
        List<JsonAdaptedTag> tags = new ArrayList<>();
        JsonAdaptedPdf person =
                new JsonAdaptedPdf(NAME_1_VALID, DIR_1_VALID, SIZE_1_VALID, tags, DEADLINE_JSON_INVALID_INVALIDDATE);
        String expectedMessage = Deadline.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullDeadline_throwsIllegalValueException() {
        List<JsonAdaptedTag> tags = new ArrayList<>();
        JsonAdaptedPdf person =
                new JsonAdaptedPdf(NAME_1_VALID, DIR_1_VALID, SIZE_1_VALID, tags, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Deadline.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }



}

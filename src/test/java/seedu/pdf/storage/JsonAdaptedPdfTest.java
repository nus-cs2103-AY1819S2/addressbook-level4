package seedu.pdf.storage;

import static org.junit.Assert.assertEquals;
import static seedu.pdf.logic.commands.CommandTestUtil.DEADLINE_JSON_DONE;
import static seedu.pdf.logic.commands.CommandTestUtil.DEADLINE_JSON_INVALID_INVALID_DATE;
import static seedu.pdf.logic.commands.CommandTestUtil.DEADLINE_JSON_NOT_DONE;
import static seedu.pdf.logic.commands.CommandTestUtil.DIR_1_VALID;
import static seedu.pdf.logic.commands.CommandTestUtil.DIR_INVALID_NONEXISTENT;
import static seedu.pdf.logic.commands.CommandTestUtil.NAME_1_VALID;
import static seedu.pdf.logic.commands.CommandTestUtil.NAME_INVALID_EXTENSION;
import static seedu.pdf.logic.commands.CommandTestUtil.SIZE_1_VALID;
import static seedu.pdf.logic.commands.CommandTestUtil.SIZE_INVALID_ALPHABET;
import static seedu.pdf.logic.commands.CommandTestUtil.TAG_INVALID_FRIEND;
import static seedu.pdf.logic.commands.CommandTestUtil.TAG_VALID_LECTURE;
import static seedu.pdf.storage.JsonAdaptedPdf.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.pdf.testutil.TypicalPdfs.SAMPLE_PDF_1;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.pdf.commons.exceptions.IllegalValueException;
import seedu.pdf.model.pdf.Deadline;
import seedu.pdf.model.pdf.Directory;
import seedu.pdf.model.pdf.Name;
import seedu.pdf.model.pdf.Size;
import seedu.pdf.testutil.Assert;

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
                null, DEADLINE_JSON_NOT_DONE);
        String expectedMessage = Directory.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, pdf::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>();
        invalidTags.add(new JsonAdaptedTag(TAG_VALID_LECTURE));
        invalidTags.add(new JsonAdaptedTag(TAG_INVALID_FRIEND));
        JsonAdaptedPdf pdf =
                new JsonAdaptedPdf(NAME_1_VALID, DIR_1_VALID, SIZE_1_VALID, invalidTags, DEADLINE_JSON_DONE);
        Assert.assertThrows(IllegalValueException.class, pdf::toModelType);
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        List<JsonAdaptedTag> tags = new ArrayList<>();
        JsonAdaptedPdf pdf =
                new JsonAdaptedPdf(NAME_INVALID_EXTENSION, DIR_1_VALID, SIZE_1_VALID, tags, DEADLINE_JSON_DONE);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, pdf::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        List<JsonAdaptedTag> tags = new ArrayList<>();
        JsonAdaptedPdf pdf = new JsonAdaptedPdf(null, DIR_1_VALID, SIZE_1_VALID, tags, DEADLINE_JSON_DONE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, pdf::toModelType);
    }

    @Test
    public void toModelType_invalidDirectory_throwsIllegalValueException() {
        List<JsonAdaptedTag> tags = new ArrayList<>();
        JsonAdaptedPdf pdf =
                new JsonAdaptedPdf(NAME_1_VALID, DIR_INVALID_NONEXISTENT, SIZE_1_VALID, tags, DEADLINE_JSON_DONE);
        String expectedMessage = Directory.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, pdf::toModelType);
    }

    @Test
    public void toModelType_nullDirectory_throwsIllegalValueException() {
        List<JsonAdaptedTag> tags = new ArrayList<>();
        JsonAdaptedPdf pdf = new JsonAdaptedPdf(NAME_1_VALID, null, SIZE_1_VALID, tags, DEADLINE_JSON_DONE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Directory.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, pdf::toModelType);
    }

    @Test
    public void toModelType_invalidSize_throwsIllegalValueException() {
        List<JsonAdaptedTag> tags = new ArrayList<>();
        JsonAdaptedPdf pdf =
                new JsonAdaptedPdf(NAME_1_VALID, DIR_1_VALID, SIZE_INVALID_ALPHABET, tags, DEADLINE_JSON_DONE);
        String expectedMessage = Size.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, pdf::toModelType);
    }

    @Test
    public void toModelType_nullSize_throwsIllegalValueException() {
        List<JsonAdaptedTag> tags = new ArrayList<>();
        JsonAdaptedPdf pdf =
                new JsonAdaptedPdf(NAME_1_VALID, DIR_1_VALID, null, tags, DEADLINE_JSON_DONE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Size.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, pdf::toModelType);
    }

    @Test
    public void toModelType_invalidDeadline_throwsIllegalValueException() {
        List<JsonAdaptedTag> tags = new ArrayList<>();
        JsonAdaptedPdf pdf =
                new JsonAdaptedPdf(NAME_1_VALID, DIR_1_VALID, SIZE_1_VALID, tags, DEADLINE_JSON_INVALID_INVALID_DATE);
        String expectedMessage = Deadline.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, pdf::toModelType);
    }

    @Test
    public void toModelType_nullDeadline_throwsIllegalValueException() {
        List<JsonAdaptedTag> tags = new ArrayList<>();
        JsonAdaptedPdf pdf =
                new JsonAdaptedPdf(NAME_1_VALID, DIR_1_VALID, SIZE_1_VALID, tags, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Deadline.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, pdf::toModelType);
    }



}

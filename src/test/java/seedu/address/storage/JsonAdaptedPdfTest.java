package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPdfs.SAMPLE_PDF_1;

import java.util.List;
//import java.util.ArrayList;
import java.util.stream.Collectors;
//import seedu.address.commons.exceptions.IllegalValueException;
//import seedu.address.model.pdf.Directory;
//import seedu.address.testutil.Assert;

import org.junit.Test;

public class JsonAdaptedPdfTest {
    private static final String INVALID_LOCATION = "DefinitelyWrongLocation";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = SAMPLE_PDF_1.getName().getFullName();
    private static final String VALID_LOCATION = SAMPLE_PDF_1.getName().getFullName();
    private static final String VALID_SIZE = SAMPLE_PDF_1.getSize().getValue();
    private static final List<JsonAdaptedTag> VALID_TAGS = SAMPLE_PDF_1.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPdfDetails_returnsPdf() throws Exception {
        JsonAdaptedPdf pdf = new JsonAdaptedPdf(SAMPLE_PDF_1);
        assertEquals(SAMPLE_PDF_1, pdf.toModelType());
    }

    /*@Test
    public void toModelType_invalidLocation_throwsIllegalValueException() {
        JsonAdaptedPdf pdf =
                new JsonAdaptedPdf(SAMPLE_PDF_1.getName().getFullName(), INVALID_LOCATION,
                SAMPLE_PDF_1.getSize().getValue(),
                        VALID_TAGS);
        String expectedMessage = Directory.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, pdf::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPdf person =
                new JsonAdaptedPdf(VALID_NAME, VALID_LOCATION, VALID_SIZE, invalidTags);
        Assert.assertThrows(IllegalValueException.class, person::toModelType);
    }*/

    /*@Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPdf person =
                new JsonAdaptedPdf(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPdf person = new JsonAdaptedPdf(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPdf person =
                new JsonAdaptedPdf(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPdf person = new JsonAdaptedPdf(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPdf person =
                new JsonAdaptedPdf(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPdf person = new JsonAdaptedPdf(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPdf person =
                new JsonAdaptedPdf(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS, VALID_TAGS);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedPdf person = new JsonAdaptedPdf(VALID_NAME, VALID_PHONE, VALID_EMAIL, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }*/



}

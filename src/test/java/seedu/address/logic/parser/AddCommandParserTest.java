package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.FILE_DESC_A_PDF;
import static seedu.address.logic.commands.CommandTestUtil.FILE_DESC_B_PDF;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FILE_PATH_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_LECTURE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_A;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_LECTURE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_TUTORIAL;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPdfs.A_PDF;
import static seedu.address.testutil.TypicalPdfs.B_PDF;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.pdf.Name;
import seedu.address.model.pdf.Pdf;
import seedu.address.testutil.PdfBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Pdf expectedPdf = new PdfBuilder(A_PDF).withTags(VALID_TAG_LECTURE).build();

        System.out.println(FILE_DESC_A_PDF);

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + FILE_DESC_A_PDF + TAG_DESC_LECTURE,
                new AddCommand(expectedPdf));

        // multiple names - last file accepted
        assertParseSuccess(parser, FILE_DESC_B_PDF + FILE_DESC_A_PDF + TAG_DESC_LECTURE,
                new AddCommand(expectedPdf));

        /*// multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPdf));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPdf));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_AMY
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPdf));*/

        // multiple tags - all accepted
        Pdf expectedPdfMultipleTags = new PdfBuilder(A_PDF).withTags(VALID_TAG_LECTURE, VALID_TAG_TUTORIAL)
                .build();
        assertParseSuccess(parser, FILE_DESC_A_PDF + TAG_DESC_LECTURE + TAG_DESC_TUTORIAL,
                new AddCommand(expectedPdfMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero labels
        Pdf expectedPdf = new PdfBuilder(B_PDF).withTags().build();
        assertParseSuccess(parser, FILE_DESC_B_PDF , new AddCommand(expectedPdf));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing file prefix
        assertParseFailure(parser, VALID_NAME_A, expectedMessage);

        /*// missing name prefix
        assertParseFailure(parser, VALID_NAME_A + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB,
                expectedMessage);*/
    }

    @Test
    public void parse_invalidValue_failure() {
        /*// invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Address.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));*/
        // invalid file path
        assertParseFailure(parser, INVALID_FILE_PATH_DESC + TAG_DESC_TUTORIAL + TAG_DESC_LECTURE,
                Name.MESSAGE_CONSTRAINTS);
    }
}

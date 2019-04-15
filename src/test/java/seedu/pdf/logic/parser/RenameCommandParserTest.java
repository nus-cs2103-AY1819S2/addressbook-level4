package seedu.pdf.logic.parser;

import static seedu.pdf.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.pdf.logic.commands.CommandTestUtil.DEADLINE_JSON_NOT_DONE;
import static seedu.pdf.logic.commands.CommandTestUtil.FILE_DESC_1_PDF;
import static seedu.pdf.logic.commands.CommandTestUtil.NAME_1_VALID;
import static seedu.pdf.logic.commands.CommandTestUtil.NAME_2_VALID;
import static seedu.pdf.logic.commands.CommandTestUtil.NAME_DESC_1_INVALID;
import static seedu.pdf.logic.commands.CommandTestUtil.NAME_DESC_1_VALID;
import static seedu.pdf.logic.commands.CommandTestUtil.NAME_DESC_2_VALID;
import static seedu.pdf.logic.commands.CommandTestUtil.TAG_DESC_CS2103T;
import static seedu.pdf.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.pdf.logic.parser.CliSyntax.PREFIX_TAG_ADD;

import static seedu.pdf.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.pdf.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.pdf.testutil.TypicalIndexes.INDEX_FIRST_PDF;
import static seedu.pdf.testutil.TypicalIndexes.INDEX_SECOND_PDF;

import org.junit.Test;

import seedu.pdf.commons.core.index.Index;
import seedu.pdf.logic.commands.RenameCommand;
import seedu.pdf.model.pdf.Name;
import seedu.pdf.testutil.EditPdfDescriptorBuilder;


public class RenameCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG_ADD;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, RenameCommand.MESSAGE_USAGE);

    private RenameCommandParser parser = new RenameCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, NAME_1_VALID, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", RenameCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);


        assertParseFailure(parser, "1 " + PREFIX_NAME, Name.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, "-1 " + NAME_1_VALID, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_containsIrrelevantParts_failure() {
        assertParseFailure(parser, "1 " + NAME_DESC_1_VALID + DEADLINE_JSON_NOT_DONE, Name.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1 " + NAME_DESC_1_VALID + TAG_DESC_CS2103T, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "1 " + NAME_DESC_1_VALID + TAG_EMPTY, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "1 " + NAME_DESC_1_VALID + FILE_DESC_1_PDF, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_2_VALID, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_2_VALID, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 se/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + NAME_DESC_1_INVALID, Name.MESSAGE_CONSTRAINTS); // invalid name
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PDF;

        String userInput = targetIndex.getOneBased() + " " + PREFIX_NAME + NAME_1_VALID;

        RenameCommand.EditPdfDescriptor descriptor = new EditPdfDescriptorBuilder()
                .withName(NAME_1_VALID).build();

        RenameCommand expectedCommand = new RenameCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_PDF;
        String userInput = targetIndex.getOneBased() + NAME_DESC_1_VALID + NAME_DESC_2_VALID;

        RenameCommand.EditPdfDescriptor descriptor = new EditPdfDescriptorBuilder().withName(NAME_2_VALID).build();
        RenameCommand expectedCommand = new RenameCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        Index targetIndex = INDEX_FIRST_PDF;
        String userInput = targetIndex.getOneBased() + NAME_DESC_1_INVALID + NAME_DESC_2_VALID;
        RenameCommand.EditPdfDescriptor descriptor = new EditPdfDescriptorBuilder().withName(NAME_2_VALID).build();
        RenameCommand expectedCommand = new RenameCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}

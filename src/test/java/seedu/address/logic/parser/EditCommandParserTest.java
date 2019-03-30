package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_JSON_READY;
import static seedu.address.logic.commands.CommandTestUtil.FILE_DESC_1_PDF;
import static seedu.address.logic.commands.CommandTestUtil.NAME_1_VALID;
import static seedu.address.logic.commands.CommandTestUtil.NAME_2_VALID;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_1_INVALID;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_1_VALID;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_2_VALID;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_CS2103T;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_ADD;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PDF;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PDF;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.model.pdf.Name;
import seedu.address.testutil.EditPdfDescriptorBuilder;


public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG_ADD;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, NAME_1_VALID, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

        assertParseFailure(parser, "1 " + NAME_DESC_1_VALID + DEADLINE_JSON_READY, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_containsIrrelevantParts_failure() {
        assertParseFailure(parser, "1 " + NAME_DESC_1_VALID + DEADLINE_JSON_READY, MESSAGE_INVALID_FORMAT);
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

        EditCommand.EditPdfDescriptor descriptor = new EditPdfDescriptorBuilder()
                .withName(NAME_1_VALID).build();

        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_PDF;
        String userInput = targetIndex.getOneBased() + NAME_DESC_1_VALID + NAME_DESC_2_VALID;

        EditCommand.EditPdfDescriptor descriptor = new EditPdfDescriptorBuilder().withName(NAME_2_VALID).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        Index targetIndex = INDEX_FIRST_PDF;
        String userInput = targetIndex.getOneBased() + NAME_DESC_1_INVALID + NAME_DESC_2_VALID;
        EditCommand.EditPdfDescriptor descriptor = new EditPdfDescriptorBuilder().withName(NAME_2_VALID).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}

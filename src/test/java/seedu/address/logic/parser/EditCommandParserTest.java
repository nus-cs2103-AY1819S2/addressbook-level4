package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.BACKFACE_DESC_GOOD;
import static seedu.address.logic.commands.CommandTestUtil.FRONTFACE_DESC_GOOD;
import static seedu.address.logic.commands.CommandTestUtil.FRONTFACE_DESC_HITBAG;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_BACKFACE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FRONTFACE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_INDONESIAN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BACKFACE_GOOD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FRONTFACE_GOOD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_INDONESIAN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_FLASHCARD;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_FLASHCARD;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_FLASHCARD;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.model.flashcard.Face;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditFlashcardDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_FRONTFACE_GOOD, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + FRONTFACE_DESC_GOOD, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + FRONTFACE_DESC_GOOD, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_FRONTFACE_DESC, Face.MESSAGE_CONSTRAINTS); // invalid face
        assertParseFailure(parser, "1" + INVALID_BACKFACE_DESC, Face.MESSAGE_CONSTRAINTS); // invalid face
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_FLASHCARD;
        String userInput = targetIndex.getOneBased() + FRONTFACE_DESC_GOOD + BACKFACE_DESC_GOOD + TAG_DESC_INDONESIAN;

        EditCommand.EditFlashcardDescriptor descriptor = new EditFlashcardDescriptorBuilder()
            .withFrontFace(VALID_FRONTFACE_GOOD).withBackFace(VALID_BACKFACE_GOOD)
            .withTags(VALID_TAG_INDONESIAN).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_FLASHCARD;
        String userInput = targetIndex.getOneBased() + FRONTFACE_DESC_GOOD;

        EditCommand.EditFlashcardDescriptor descriptor = new EditFlashcardDescriptorBuilder()
            .withFrontFace(VALID_FRONTFACE_GOOD).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        Index targetIndex = INDEX_FIRST_FLASHCARD;
        String userInput = targetIndex.getOneBased() + FRONTFACE_DESC_GOOD;

        EditCommand.EditFlashcardDescriptor descriptor = new EditFlashcardDescriptorBuilder()
            .withFrontFace(VALID_FRONTFACE_GOOD).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_FLASHCARD;
        String userInput = targetIndex.getOneBased() + FRONTFACE_DESC_HITBAG + FRONTFACE_DESC_GOOD;

        EditCommand.EditFlashcardDescriptor descriptor = new EditFlashcardDescriptorBuilder()
            .withFrontFace(VALID_FRONTFACE_GOOD).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_FLASHCARD;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditCommand.EditFlashcardDescriptor descriptor = new EditFlashcardDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}

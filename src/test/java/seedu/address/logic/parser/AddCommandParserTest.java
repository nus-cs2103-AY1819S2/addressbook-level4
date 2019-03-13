package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.BACKFACE_DESC_GOOD;
import static seedu.address.logic.commands.CommandTestUtil.FRONTFACE_DESC_GOOD;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_BACKFACE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FRONTFACE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_INDONESIAN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BACKFACE_GOOD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FRONTFACE_GOOD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_INDONESIAN;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalFlashcards.GOOD;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.flashcard.Face;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.FlashcardBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Flashcard expectedFlashcard = new FlashcardBuilder(GOOD).withTags(VALID_TAG_CHINESE).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + FRONTFACE_DESC_GOOD + BACKFACE_DESC_GOOD
            + TAG_DESC_CHINESE, new AddCommand(expectedFlashcard));

        // multiple tags - all accepted
        Flashcard expectedFlashcardMultipleTags = new FlashcardBuilder(GOOD).withTags(VALID_TAG_INDONESIAN,
            VALID_TAG_CHINESE).build();
        assertParseSuccess(parser, FRONTFACE_DESC_GOOD + BACKFACE_DESC_GOOD + TAG_DESC_CHINESE
            + TAG_DESC_INDONESIAN, new AddCommand(expectedFlashcardMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Flashcard expectedFlashcard = new FlashcardBuilder(GOOD).withTags().build();
        assertParseSuccess(parser, FRONTFACE_DESC_GOOD + BACKFACE_DESC_GOOD,
            new AddCommand(expectedFlashcard));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing front prefix
        assertParseFailure(parser, VALID_FRONTFACE_GOOD + BACKFACE_DESC_GOOD, expectedMessage);

        // missing back prefix
        assertParseFailure(parser, FRONTFACE_DESC_GOOD + VALID_BACKFACE_GOOD, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_FRONTFACE_GOOD + VALID_BACKFACE_GOOD, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid frontface
        assertParseFailure(parser,
            INVALID_FRONTFACE_DESC + BACKFACE_DESC_GOOD + TAG_DESC_INDONESIAN, Face.MESSAGE_CONSTRAINTS);

        // invalid backface
        assertParseFailure(parser,
            FRONTFACE_DESC_GOOD + INVALID_BACKFACE_DESC + TAG_DESC_INDONESIAN, Face.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser,
            FRONTFACE_DESC_GOOD + BACKFACE_DESC_GOOD + INVALID_TAG_DESC + VALID_TAG_CHINESE, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser,
            INVALID_FRONTFACE_DESC + BACKFACE_DESC_GOOD + INVALID_TAG_DESC, Face.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + FRONTFACE_DESC_GOOD + BACKFACE_DESC_GOOD
            + TAG_DESC_CHINESE, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}

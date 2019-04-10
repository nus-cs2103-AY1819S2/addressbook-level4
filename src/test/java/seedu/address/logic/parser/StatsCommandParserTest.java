package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.BACKFACE_DESC_GOOD;
import static seedu.address.logic.commands.CommandTestUtil.FRONTFACE_DESC_DUCK;
import static seedu.address.logic.commands.CommandTestUtil.FRONTFACE_DESC_GOOD;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_BACKFACE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FRONTFACE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PREFIX_UNDEFINED;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_INDONESIAN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BACKFACE_GOOD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FRONTFACE_DUCK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FRONTFACE_GOOD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_INDONESIAN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.StatsCommand;
import seedu.address.model.flashcard.Face;
import seedu.address.model.flashcard.FlashcardContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;

public class StatsCommandParserTest {

    private StatsCommandParser parser = new StatsCommandParser();


    @Test
    public void parse_missingPrefix_throwsParseException() {
        assertParseFailure(parser, VALID_FRONTFACE_GOOD, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                StatsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_wrongPrefix_throwsParseException() {
        assertParseFailure(parser, INVALID_PREFIX_UNDEFINED, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                StatsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleTagsUnderSameTagPrefix_throwsParseException() {
        assertParseFailure(parser, PREFIX_TAG + VALID_TAG_INDONESIAN + " " + VALID_TAG_CHINESE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_allFieldsPresent_success() {
        ArrayList<String> frontFaceKeywords = new ArrayList<>(Arrays.asList(VALID_FRONTFACE_GOOD));
        ArrayList<String> backFaceKeywords = new ArrayList<>(Arrays.asList(VALID_BACKFACE_GOOD));
        ArrayList<String> tagKeywords = new ArrayList<>(Arrays.asList(VALID_TAG_INDONESIAN));

        FlashcardContainsKeywordsPredicate expectedPredicate = new FlashcardContainsKeywordsPredicate(
                frontFaceKeywords, backFaceKeywords, tagKeywords);

        // whitespace only
        assertParseSuccess(parser, PREAMBLE_WHITESPACE , new StatsCommand());

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + FRONTFACE_DESC_GOOD + BACKFACE_DESC_GOOD
                + TAG_DESC_INDONESIAN, new StatsCommand(expectedPredicate));

        // different ordering of args
        assertParseSuccess(parser, TAG_DESC_INDONESIAN + BACKFACE_DESC_GOOD
                + FRONTFACE_DESC_GOOD, new StatsCommand(expectedPredicate));

        // multiple tags - all accepted
        tagKeywords.add(VALID_TAG_CHINESE);
        FlashcardContainsKeywordsPredicate expectedPredicateMultipleTags = new FlashcardContainsKeywordsPredicate(
                frontFaceKeywords, backFaceKeywords, tagKeywords);
        assertParseSuccess(parser, FRONTFACE_DESC_GOOD + BACKFACE_DESC_GOOD
                + TAG_DESC_INDONESIAN + TAG_DESC_CHINESE, new StatsCommand(expectedPredicateMultipleTags));
        tagKeywords.remove(1);

        // multiple frontFace keywords under same frontFace prefix
        frontFaceKeywords.add(VALID_FRONTFACE_DUCK);
        FlashcardContainsKeywordsPredicate expectedPredicateMultipleFrontfaceKeywords =
                new FlashcardContainsKeywordsPredicate(frontFaceKeywords, backFaceKeywords, tagKeywords);
        assertParseSuccess(parser, FRONTFACE_DESC_GOOD + " " + VALID_FRONTFACE_DUCK + BACKFACE_DESC_GOOD
                + TAG_DESC_INDONESIAN, new StatsCommand(expectedPredicateMultipleFrontfaceKeywords));
    }

    @Test
    public void parse_optionalFieldMissing_success() {
        ArrayList<String> frontFaceKeywords = new ArrayList<>();
        ArrayList<String> backFaceKeywords = new ArrayList<>();
        ArrayList<String> tagKeywords = new ArrayList<>();

        // only frontFace keyword
        frontFaceKeywords.add(VALID_FRONTFACE_GOOD);
        FlashcardContainsKeywordsPredicate expectedPredicateFrontFaceOnly = new FlashcardContainsKeywordsPredicate(
                frontFaceKeywords, backFaceKeywords, tagKeywords);
        assertParseSuccess(parser, FRONTFACE_DESC_GOOD, new StatsCommand(expectedPredicateFrontFaceOnly));
        frontFaceKeywords.remove(0);

        // only backFace keyword with whitespace preamble
        backFaceKeywords.add(VALID_BACKFACE_GOOD);
        FlashcardContainsKeywordsPredicate expectedPredicateBackFaceOnly = new FlashcardContainsKeywordsPredicate(
                frontFaceKeywords, backFaceKeywords, tagKeywords);
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + BACKFACE_DESC_GOOD,
                new StatsCommand(expectedPredicateBackFaceOnly));
        backFaceKeywords.remove(0);

        // only tag keyword
        tagKeywords.add(VALID_TAG_CHINESE);
        FlashcardContainsKeywordsPredicate expectedPredicateTagOnly = new FlashcardContainsKeywordsPredicate(
                frontFaceKeywords, backFaceKeywords, tagKeywords);
        assertParseSuccess(parser, TAG_DESC_CHINESE, new StatsCommand(expectedPredicateTagOnly));
        tagKeywords.remove(0);

        // only tag and frontFace
        tagKeywords.add(VALID_TAG_CHINESE);
        frontFaceKeywords.add(VALID_FRONTFACE_DUCK);
        FlashcardContainsKeywordsPredicate expectedPredicateTagAndFrontFaceOnly =
                new FlashcardContainsKeywordsPredicate(frontFaceKeywords, backFaceKeywords, tagKeywords);
        assertParseSuccess(parser, TAG_DESC_CHINESE + FRONTFACE_DESC_DUCK,
                new StatsCommand(expectedPredicateTagAndFrontFaceOnly));
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid frontFace
        assertParseFailure(parser,
                INVALID_FRONTFACE_DESC + BACKFACE_DESC_GOOD + TAG_DESC_INDONESIAN, Face.MESSAGE_CONSTRAINTS);

        // invalid backFace
        assertParseFailure(parser,
                FRONTFACE_DESC_GOOD + INVALID_BACKFACE_DESC + TAG_DESC_INDONESIAN, Face.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, FRONTFACE_DESC_GOOD + BACKFACE_DESC_GOOD + INVALID_TAG_DESC
                + VALID_TAG_CHINESE, Tag.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + FRONTFACE_DESC_GOOD + BACKFACE_DESC_GOOD
                + TAG_DESC_CHINESE, String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatsCommand.MESSAGE_USAGE));
    }
}

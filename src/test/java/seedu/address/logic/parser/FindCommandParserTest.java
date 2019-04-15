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
import static seedu.address.logic.commands.CommandTestUtil.VALID_BOUND_75;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FRONTFACE_DUCK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FRONTFACE_GOOD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MAX_BOUND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MAX_BOUND_STRING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MIN_BOUND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MIN_BOUND_STRING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_INDONESIAN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUCCESS_RATE_RANGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.flashcard.Face;
import seedu.address.model.flashcard.FlashcardPredicate;
import seedu.address.model.tag.Tag;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingPrefix_throwsParseException() {
        assertParseFailure(parser, VALID_FRONTFACE_GOOD, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_wrongPrefix_throwsParseException() {
        assertParseFailure(parser, INVALID_PREFIX_UNDEFINED, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleTagsUnderSameTagPrefix_throwsParseException() {
        assertParseFailure(parser, PREFIX_TAG + VALID_TAG_INDONESIAN + " " + VALID_TAG_CHINESE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_allFieldsPresent_success() {
        ArrayList<String> frontFaceKeywords = new ArrayList<>(Arrays.asList(VALID_FRONTFACE_GOOD));
        ArrayList<String> backFaceKeywords = new ArrayList<>(Arrays.asList(VALID_BACKFACE_GOOD));
        ArrayList<String> tagKeywords = new ArrayList<>(Arrays.asList(VALID_TAG_INDONESIAN));
        double[] successRateRange = {VALID_MIN_BOUND, VALID_BOUND_75};

        FlashcardPredicate expectedPredicate = new FlashcardPredicate(
                frontFaceKeywords, backFaceKeywords, tagKeywords, successRateRange);

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + FRONTFACE_DESC_GOOD + BACKFACE_DESC_GOOD
                + TAG_DESC_INDONESIAN + " " + PREFIX_SUCCESS_RATE_RANGE + VALID_MIN_BOUND + " "
                + VALID_BOUND_75, new FindCommand(expectedPredicate));

        // different ordering of args
        assertParseSuccess(parser, TAG_DESC_INDONESIAN + " " + PREFIX_SUCCESS_RATE_RANGE + VALID_MIN_BOUND
                + " " + VALID_BOUND_75 + BACKFACE_DESC_GOOD + FRONTFACE_DESC_GOOD , new FindCommand(expectedPredicate));

        // multiple tags - all accepted
        tagKeywords.add(VALID_TAG_CHINESE);
        FlashcardPredicate expectedPredicateMultipleTags = new FlashcardPredicate(
                frontFaceKeywords, backFaceKeywords, tagKeywords, successRateRange);
        assertParseSuccess(parser, FRONTFACE_DESC_GOOD + BACKFACE_DESC_GOOD
                + TAG_DESC_INDONESIAN + TAG_DESC_CHINESE + " " + PREFIX_SUCCESS_RATE_RANGE + VALID_MIN_BOUND + " "
                + VALID_BOUND_75, new FindCommand(expectedPredicateMultipleTags));
        tagKeywords.remove(1);

        // multiple frontFace keywords under same frontFace prefix
        frontFaceKeywords.add(VALID_FRONTFACE_DUCK);
        FlashcardPredicate expectedPredicateMultipleFrontfaceKeywords =
                new FlashcardPredicate(frontFaceKeywords, backFaceKeywords, tagKeywords, successRateRange);
        assertParseSuccess(parser, FRONTFACE_DESC_GOOD + " " + VALID_FRONTFACE_DUCK + BACKFACE_DESC_GOOD
                + TAG_DESC_INDONESIAN + " " + PREFIX_SUCCESS_RATE_RANGE + VALID_MIN_BOUND + " "
                + VALID_BOUND_75, new FindCommand(expectedPredicateMultipleFrontfaceKeywords));
    }

    @Test
    public void parse_optionalFieldMissing_success() {
        ArrayList<String> frontFaceKeywords = new ArrayList<>();
        ArrayList<String> backFaceKeywords = new ArrayList<>();
        ArrayList<String> tagKeywords = new ArrayList<>();
        double[] defaultSuccessRateRange = {VALID_MIN_BOUND, VALID_MAX_BOUND};

        // only frontFace keyword
        frontFaceKeywords.add(VALID_FRONTFACE_GOOD);
        FlashcardPredicate expectedPredicateFrontFaceOnly = new FlashcardPredicate(
                frontFaceKeywords, backFaceKeywords, tagKeywords, defaultSuccessRateRange);
        assertParseSuccess(parser, FRONTFACE_DESC_GOOD, new FindCommand(expectedPredicateFrontFaceOnly));
        frontFaceKeywords.remove(0);

        // only backFace keyword with whitespace preamble
        backFaceKeywords.add(VALID_BACKFACE_GOOD);
        FlashcardPredicate expectedPredicateBackFaceOnly = new FlashcardPredicate(
                frontFaceKeywords, backFaceKeywords, tagKeywords, defaultSuccessRateRange);
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + BACKFACE_DESC_GOOD,
                new FindCommand(expectedPredicateBackFaceOnly));
        backFaceKeywords.remove(0);

        // only tag keyword
        tagKeywords.add(VALID_TAG_CHINESE);
        FlashcardPredicate expectedPredicateTagOnly = new FlashcardPredicate(
                frontFaceKeywords, backFaceKeywords, tagKeywords, defaultSuccessRateRange);
        assertParseSuccess(parser, TAG_DESC_CHINESE, new FindCommand(expectedPredicateTagOnly));
        tagKeywords.remove(0);

        // only tag and frontFace
        tagKeywords.add(VALID_TAG_CHINESE);
        frontFaceKeywords.add(VALID_FRONTFACE_DUCK);
        FlashcardPredicate expectedPredicateTagAndFrontFaceOnly =
                new FlashcardPredicate(frontFaceKeywords, backFaceKeywords, tagKeywords, defaultSuccessRateRange);
        assertParseSuccess(parser, TAG_DESC_CHINESE + FRONTFACE_DESC_DUCK,
                new FindCommand(expectedPredicateTagAndFrontFaceOnly));
        tagKeywords.remove(0);
        frontFaceKeywords.remove(0);

        // only success rate range as integer
        FlashcardPredicate expectedPredicateIntegerRangeOnly =
                new FlashcardPredicate(frontFaceKeywords, backFaceKeywords, tagKeywords, defaultSuccessRateRange);
        assertParseSuccess(parser, " " + PREFIX_SUCCESS_RATE_RANGE + VALID_MIN_BOUND_STRING + " "
                + VALID_MAX_BOUND_STRING, new FindCommand(expectedPredicateIntegerRangeOnly));

        // only success rate range as double
        double[] decimalSuccessRateRange = {20.5, 70.5};
        FlashcardPredicate expectedPredicateDoubleRangeOnly =
                new FlashcardPredicate(frontFaceKeywords, backFaceKeywords, tagKeywords, decimalSuccessRateRange);
        assertParseSuccess(parser, " " + PREFIX_SUCCESS_RATE_RANGE + "20.5 70.5",
                new FindCommand(expectedPredicateDoubleRangeOnly));

        // multiple success rate ranges specified (picks second one)
        FlashcardPredicate expectedPredicateMultipleSuccessRate =
                new FlashcardPredicate(frontFaceKeywords, backFaceKeywords, tagKeywords, decimalSuccessRateRange);
        assertParseSuccess(parser, " " + PREFIX_SUCCESS_RATE_RANGE + "0 100"
                + " " + PREFIX_SUCCESS_RATE_RANGE + "20.5 70.5",
                new FindCommand(expectedPredicateMultipleSuccessRate));
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

        // invalid success rate range (lower bound > upper bound)
        assertParseFailure(parser, " " + PREFIX_SUCCESS_RATE_RANGE + "50.5 20",
                ParserUtil.MESSAGE_INVALID_RANGE);

        // invalid success rate range (lower bound < 0)
        assertParseFailure(parser, " " + PREFIX_SUCCESS_RATE_RANGE + "-0.1 50",
                ParserUtil.MESSAGE_INVALID_RANGE);

        // invalid success rate range (upper bound > 1000)
        assertParseFailure(parser, " " + PREFIX_SUCCESS_RATE_RANGE + "0 100.1",
                ParserUtil.MESSAGE_INVALID_RANGE);

        // invalid success rate format (text as value)
        assertParseFailure(parser, " " + PREFIX_SUCCESS_RATE_RANGE + "hello 100.1",
                ParserUtil.MESSAGE_INVALID_RANGE_FORMAT);

        // invalid success rate format (3 values in range)
        assertParseFailure(parser, " " + PREFIX_SUCCESS_RATE_RANGE + "0 50 100",
                ParserUtil.MESSAGE_INVALID_RANGE_FORMAT);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + FRONTFACE_DESC_GOOD + BACKFACE_DESC_GOOD
                + TAG_DESC_CHINESE, String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

}

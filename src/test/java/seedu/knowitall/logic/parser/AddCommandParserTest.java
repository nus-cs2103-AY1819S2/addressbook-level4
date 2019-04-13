package seedu.knowitall.logic.parser;

import static seedu.knowitall.commons.core.Messages.MESSAGE_ILLEGAL_OPTION_CANNOT_BE_SAME_AS_ANSWER;
import static seedu.knowitall.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.knowitall.commons.core.Messages.MESSAGE_INVALID_NUMBER_OF_CARD_ARGUMENTS;
import static seedu.knowitall.logic.commands.CommandTestUtil.ANSWER_DESC_SAMPLE_1;
import static seedu.knowitall.logic.commands.CommandTestUtil.ANSWER_DESC_SAMPLE_2;
import static seedu.knowitall.logic.commands.CommandTestUtil.HINT_DESC_FRIEND;
import static seedu.knowitall.logic.commands.CommandTestUtil.HINT_DESC_HUSBAND;
import static seedu.knowitall.logic.commands.CommandTestUtil.INVALID_ANSWER_DESC;
import static seedu.knowitall.logic.commands.CommandTestUtil.INVALID_HINT_DESC;
import static seedu.knowitall.logic.commands.CommandTestUtil.INVALID_OPTION_DESC;
import static seedu.knowitall.logic.commands.CommandTestUtil.INVALID_OPTION_SAME_AS_ANSWER_1;
import static seedu.knowitall.logic.commands.CommandTestUtil.INVALID_QUESTION_DESC;
import static seedu.knowitall.logic.commands.CommandTestUtil.OPTION_DESC_SAMPLE_1;
import static seedu.knowitall.logic.commands.CommandTestUtil.OPTION_DESC_SAMPLE_2;
import static seedu.knowitall.logic.commands.CommandTestUtil.OPTION_DESC_SAMPLE_3;
import static seedu.knowitall.logic.commands.CommandTestUtil.OPTION_DESC_SAMPLE_4;
import static seedu.knowitall.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.knowitall.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.knowitall.logic.commands.CommandTestUtil.QUESTION_DESC_SAMPLE_1;
import static seedu.knowitall.logic.commands.CommandTestUtil.QUESTION_DESC_SAMPLE_2;
import static seedu.knowitall.logic.commands.CommandTestUtil.VALID_ANSWER_2;
import static seedu.knowitall.logic.commands.CommandTestUtil.VALID_HINT_FRIEND;
import static seedu.knowitall.logic.commands.CommandTestUtil.VALID_HINT_HUSBAND;
import static seedu.knowitall.logic.commands.CommandTestUtil.VALID_OPTION_1;
import static seedu.knowitall.logic.commands.CommandTestUtil.VALID_QUESTION_2;
import static seedu.knowitall.logic.parser.CardOperationUtil.ANSWER_STRING;
import static seedu.knowitall.logic.parser.CardOperationUtil.HINT_STRING;
import static seedu.knowitall.logic.parser.CardOperationUtil.OPTION_STRING;
import static seedu.knowitall.logic.parser.CardOperationUtil.QUESTION_STRING;
import static seedu.knowitall.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.knowitall.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.knowitall.model.card.Card.MAX_ANSWERS;
import static seedu.knowitall.model.card.Card.MAX_HINTS;
import static seedu.knowitall.model.card.Card.MAX_OPTIONS;
import static seedu.knowitall.model.card.Card.MAX_QUESTIONS;
import static seedu.knowitall.testutil.TypicalCards.CARD_1;
import static seedu.knowitall.testutil.TypicalCards.CARD_2;

import org.junit.Test;

import seedu.knowitall.logic.commands.AddCommand;
import seedu.knowitall.model.card.Answer;
import seedu.knowitall.model.card.Card;
import seedu.knowitall.model.card.Option;
import seedu.knowitall.model.card.Question;
import seedu.knowitall.model.hint.Hint;
import seedu.knowitall.testutil.CardBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Card expectedCard = new CardBuilder(CARD_2).withHint(VALID_HINT_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + QUESTION_DESC_SAMPLE_2 + ANSWER_DESC_SAMPLE_2
                + HINT_DESC_FRIEND, new AddCommand(expectedCard));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero hints or options
        Card expectedCard = new CardBuilder(CARD_1).withHint().build();
        assertParseSuccess(parser, QUESTION_DESC_SAMPLE_1 + ANSWER_DESC_SAMPLE_1, new AddCommand(expectedCard));

        // one hint and no options
        expectedCard = new CardBuilder(CARD_1).withHint(VALID_HINT_HUSBAND).build();
        assertParseSuccess(parser, QUESTION_DESC_SAMPLE_1 + ANSWER_DESC_SAMPLE_1 + HINT_DESC_HUSBAND,
                new AddCommand(expectedCard));

        // no hint and one option
        expectedCard = new CardBuilder(CARD_1).withHint().withOptions(VALID_OPTION_1).build();
        assertParseSuccess(parser, QUESTION_DESC_SAMPLE_1 + ANSWER_DESC_SAMPLE_1 + OPTION_DESC_SAMPLE_1,
                new AddCommand(expectedCard));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing question prefix
        assertParseFailure(parser, VALID_QUESTION_2 + ANSWER_DESC_SAMPLE_2, expectedMessage);

        // missing answer prefix
        assertParseFailure(parser, QUESTION_DESC_SAMPLE_2 + VALID_ANSWER_2, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_QUESTION_2 + VALID_ANSWER_2, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid question
        assertParseFailure(parser, INVALID_QUESTION_DESC + ANSWER_DESC_SAMPLE_2 + HINT_DESC_HUSBAND,
                Question.MESSAGE_CONSTRAINTS);

        // invalid answer
        assertParseFailure(parser, QUESTION_DESC_SAMPLE_2 + INVALID_ANSWER_DESC + HINT_DESC_HUSBAND,
                Answer.MESSAGE_CONSTRAINTS);

        // invalid hint
        assertParseFailure(parser, QUESTION_DESC_SAMPLE_2 + ANSWER_DESC_SAMPLE_2 + INVALID_HINT_DESC,
                Hint.MESSAGE_CONSTRAINTS);

        // invalid option
        assertParseFailure(parser, QUESTION_DESC_SAMPLE_2 + ANSWER_DESC_SAMPLE_2 + INVALID_OPTION_DESC
                + OPTION_DESC_SAMPLE_2, Option.MESSAGE_CONSTRAINTS);

        // invalid number of questions
        assertParseFailure(parser, QUESTION_DESC_SAMPLE_1 + QUESTION_DESC_SAMPLE_1 + ANSWER_DESC_SAMPLE_2,
                String.format(MESSAGE_INVALID_NUMBER_OF_CARD_ARGUMENTS, MAX_QUESTIONS, QUESTION_STRING));

        // invalid number of answers
        assertParseFailure(parser, QUESTION_DESC_SAMPLE_1 + ANSWER_DESC_SAMPLE_1 + ANSWER_DESC_SAMPLE_2,
                String.format(MESSAGE_INVALID_NUMBER_OF_CARD_ARGUMENTS, MAX_ANSWERS, ANSWER_STRING));

        // invalid number of hints
        assertParseFailure(parser, QUESTION_DESC_SAMPLE_1 + ANSWER_DESC_SAMPLE_2 + HINT_DESC_FRIEND
                + HINT_DESC_HUSBAND, String.format(MESSAGE_INVALID_NUMBER_OF_CARD_ARGUMENTS, MAX_HINTS,
                HINT_STRING));

        // invalid number of options
        assertParseFailure(parser, QUESTION_DESC_SAMPLE_1 + ANSWER_DESC_SAMPLE_2 + HINT_DESC_FRIEND
                + OPTION_DESC_SAMPLE_1 + OPTION_DESC_SAMPLE_2 + OPTION_DESC_SAMPLE_3 + OPTION_DESC_SAMPLE_4,
                String.format(MESSAGE_INVALID_NUMBER_OF_CARD_ARGUMENTS, MAX_OPTIONS, OPTION_STRING));

        // same answer and option
        assertParseFailure(parser, QUESTION_DESC_SAMPLE_2 + ANSWER_DESC_SAMPLE_1
                + INVALID_OPTION_SAME_AS_ANSWER_1, MESSAGE_ILLEGAL_OPTION_CANNOT_BE_SAME_AS_ANSWER);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + QUESTION_DESC_SAMPLE_2 + ANSWER_DESC_SAMPLE_2
                + HINT_DESC_HUSBAND + HINT_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}

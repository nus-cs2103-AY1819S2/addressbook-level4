package seedu.knowitall.logic.parser;

import static seedu.knowitall.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.knowitall.logic.commands.CommandTestUtil.ANSWER_DESC_SAMPLE_1;
import static seedu.knowitall.logic.commands.CommandTestUtil.ANSWER_DESC_SAMPLE_2;
import static seedu.knowitall.logic.commands.CommandTestUtil.HINT_DESC_FRIEND;
import static seedu.knowitall.logic.commands.CommandTestUtil.HINT_DESC_HUSBAND;
import static seedu.knowitall.logic.commands.CommandTestUtil.INVALID_ANSWER_DESC;
import static seedu.knowitall.logic.commands.CommandTestUtil.INVALID_HINT_DESC;
import static seedu.knowitall.logic.commands.CommandTestUtil.INVALID_QUESTION_DESC;
import static seedu.knowitall.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.knowitall.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.knowitall.logic.commands.CommandTestUtil.QUESTION_DESC_SAMPLE_1;
import static seedu.knowitall.logic.commands.CommandTestUtil.QUESTION_DESC_SAMPLE_2;
import static seedu.knowitall.logic.commands.CommandTestUtil.VALID_ANSWER_2;
import static seedu.knowitall.logic.commands.CommandTestUtil.VALID_HINT_FRIEND;
import static seedu.knowitall.logic.commands.CommandTestUtil.VALID_QUESTION_2;
import static seedu.knowitall.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.knowitall.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.knowitall.testutil.TypicalCards.CARD_1;
import static seedu.knowitall.testutil.TypicalCards.CARD_2;

import org.junit.Test;

import seedu.knowitall.logic.commands.AddCommand;
import seedu.knowitall.model.card.Answer;
import seedu.knowitall.model.card.Card;
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

        // multiple names - last name accepted
        assertParseSuccess(parser, QUESTION_DESC_SAMPLE_1 + QUESTION_DESC_SAMPLE_2 + ANSWER_DESC_SAMPLE_2
                + HINT_DESC_FRIEND, new AddCommand(expectedCard));

        // multiple answers - last answer accepted
        assertParseSuccess(parser, QUESTION_DESC_SAMPLE_2 + ANSWER_DESC_SAMPLE_1 + ANSWER_DESC_SAMPLE_2
                + HINT_DESC_FRIEND, new AddCommand(expectedCard));

        // multiple hints - last hint accepted
        assertParseSuccess(parser, QUESTION_DESC_SAMPLE_2 + ANSWER_DESC_SAMPLE_2 + HINT_DESC_HUSBAND
                + HINT_DESC_FRIEND, new AddCommand(expectedCard));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero hints
        Card expectedCard = new CardBuilder(CARD_1).withHint().build();
        assertParseSuccess(parser, QUESTION_DESC_SAMPLE_1 + ANSWER_DESC_SAMPLE_1, new AddCommand(expectedCard));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_QUESTION_2 + ANSWER_DESC_SAMPLE_2, expectedMessage);

        // missing answer prefix
        assertParseFailure(parser, QUESTION_DESC_SAMPLE_2 + VALID_ANSWER_2, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_QUESTION_2 + VALID_ANSWER_2, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_QUESTION_DESC + ANSWER_DESC_SAMPLE_2 + HINT_DESC_HUSBAND
                + HINT_DESC_FRIEND, Question.MESSAGE_CONSTRAINTS);

        // invalid answer
        assertParseFailure(parser, QUESTION_DESC_SAMPLE_2 + INVALID_ANSWER_DESC + HINT_DESC_HUSBAND
                + HINT_DESC_FRIEND, Answer.MESSAGE_CONSTRAINTS);

        // invalid hint
        assertParseFailure(parser, QUESTION_DESC_SAMPLE_2 + ANSWER_DESC_SAMPLE_2 + INVALID_HINT_DESC
                + VALID_HINT_FRIEND, Hint.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_QUESTION_DESC + ANSWER_DESC_SAMPLE_2, Question.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + QUESTION_DESC_SAMPLE_2 + ANSWER_DESC_SAMPLE_2
                + HINT_DESC_HUSBAND + HINT_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}

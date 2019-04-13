package seedu.knowitall.logic.parser;

import static seedu.knowitall.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.knowitall.commons.core.Messages.MESSAGE_INVALID_NUMBER_OF_CARD_ARGUMENTS;
import static seedu.knowitall.logic.commands.CommandTestUtil.ANSWER_DESC_SAMPLE_1;
import static seedu.knowitall.logic.commands.CommandTestUtil.ANSWER_DESC_SAMPLE_2;
import static seedu.knowitall.logic.commands.CommandTestUtil.HINT_DESC_FRIEND;
import static seedu.knowitall.logic.commands.CommandTestUtil.HINT_DESC_HUSBAND;
import static seedu.knowitall.logic.commands.CommandTestUtil.INVALID_ANSWER_DESC;
import static seedu.knowitall.logic.commands.CommandTestUtil.INVALID_HINT_DESC;
import static seedu.knowitall.logic.commands.CommandTestUtil.INVALID_QUESTION_DESC;
import static seedu.knowitall.logic.commands.CommandTestUtil.OPTION_DESC_SAMPLE_1;
import static seedu.knowitall.logic.commands.CommandTestUtil.OPTION_DESC_SAMPLE_2;
import static seedu.knowitall.logic.commands.CommandTestUtil.OPTION_DESC_SAMPLE_3;
import static seedu.knowitall.logic.commands.CommandTestUtil.OPTION_DESC_SAMPLE_4;
import static seedu.knowitall.logic.commands.CommandTestUtil.QUESTION_DESC_SAMPLE_1;
import static seedu.knowitall.logic.commands.CommandTestUtil.VALID_ANSWER_1;
import static seedu.knowitall.logic.commands.CommandTestUtil.VALID_ANSWER_2;
import static seedu.knowitall.logic.commands.CommandTestUtil.VALID_HINT_FRIEND;
import static seedu.knowitall.logic.commands.CommandTestUtil.VALID_HINT_HUSBAND;
import static seedu.knowitall.logic.commands.CommandTestUtil.VALID_QUESTION_1;
import static seedu.knowitall.logic.parser.CardOperationUtil.ANSWER_STRING;
import static seedu.knowitall.logic.parser.CardOperationUtil.HINT_STRING;
import static seedu.knowitall.logic.parser.CardOperationUtil.OPTION_STRING;
import static seedu.knowitall.logic.parser.CardOperationUtil.QUESTION_STRING;
import static seedu.knowitall.logic.parser.CliSyntax.PREFIX_HINT;
import static seedu.knowitall.logic.parser.CliSyntax.PREFIX_OPTION;
import static seedu.knowitall.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.knowitall.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.knowitall.model.card.Card.MAX_ANSWERS;
import static seedu.knowitall.model.card.Card.MAX_HINTS;
import static seedu.knowitall.model.card.Card.MAX_OPTIONS;
import static seedu.knowitall.model.card.Card.MAX_QUESTIONS;
import static seedu.knowitall.testutil.TypicalIndexes.INDEX_FIRST_CARD;
import static seedu.knowitall.testutil.TypicalIndexes.INDEX_SECOND_CARD;
import static seedu.knowitall.testutil.TypicalIndexes.INDEX_THIRD_CARD;

import org.junit.Test;

import seedu.knowitall.commons.core.index.Index;
import seedu.knowitall.logic.commands.EditCommand;
import seedu.knowitall.model.card.Answer;
import seedu.knowitall.model.card.Question;
import seedu.knowitall.testutil.EditCardDescriptorBuilder;

public class EditCommandParserTest {

    private static final String HINT_EMPTY = " " + PREFIX_HINT;

    private static final String OPTION_EMPTY = " " + PREFIX_OPTION;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_QUESTION_1, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + QUESTION_DESC_SAMPLE_1, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + QUESTION_DESC_SAMPLE_1, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 j/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_QUESTION_DESC, Question.MESSAGE_CONSTRAINTS); // invalid question
        assertParseFailure(parser, "1" + INVALID_ANSWER_DESC, Answer.MESSAGE_CONSTRAINTS); // invalid answer

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser,
                "1" + INVALID_QUESTION_DESC + INVALID_HINT_DESC + VALID_ANSWER_1,
                Question.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // invalid number of questions
        assertParseFailure(parser, "1" + QUESTION_DESC_SAMPLE_1 + QUESTION_DESC_SAMPLE_1,
                String.format(MESSAGE_INVALID_NUMBER_OF_CARD_ARGUMENTS, MAX_QUESTIONS, QUESTION_STRING));

        // invalid number of answers
        assertParseFailure(parser, "1" + ANSWER_DESC_SAMPLE_1 + ANSWER_DESC_SAMPLE_2,
                String.format(MESSAGE_INVALID_NUMBER_OF_CARD_ARGUMENTS, MAX_ANSWERS, ANSWER_STRING));

        // invalid number of hints
        assertParseFailure(parser, "1" + HINT_DESC_FRIEND + HINT_DESC_HUSBAND,
                String.format(MESSAGE_INVALID_NUMBER_OF_CARD_ARGUMENTS, MAX_HINTS, HINT_STRING));

        // invalid number of questions
        assertParseFailure(parser, "1" + OPTION_DESC_SAMPLE_1 + OPTION_DESC_SAMPLE_2 + OPTION_DESC_SAMPLE_3
                + OPTION_DESC_SAMPLE_4, String.format(MESSAGE_INVALID_NUMBER_OF_CARD_ARGUMENTS, MAX_OPTIONS,
                OPTION_STRING));
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_CARD;
        String userInput = targetIndex.getOneBased() + ANSWER_DESC_SAMPLE_2 + HINT_DESC_HUSBAND
                + QUESTION_DESC_SAMPLE_1;

        EditCommand.EditCardDescriptor descriptor = new EditCardDescriptorBuilder().withQuestion(VALID_QUESTION_1)
                .withAnswer(VALID_ANSWER_2).withHint(VALID_HINT_HUSBAND).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_CARD;
        String userInput = targetIndex.getOneBased() + ANSWER_DESC_SAMPLE_2;

        EditCommand.EditCardDescriptor descriptor = new EditCardDescriptorBuilder().withAnswer(VALID_ANSWER_2)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_CARD;
        String userInput = targetIndex.getOneBased() + QUESTION_DESC_SAMPLE_1;
        EditCommand.EditCardDescriptor descriptor =
                new EditCardDescriptorBuilder().withQuestion(VALID_QUESTION_1).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // answer
        userInput = targetIndex.getOneBased() + ANSWER_DESC_SAMPLE_1;
        descriptor = new EditCardDescriptorBuilder().withAnswer(VALID_ANSWER_1).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // hints
        userInput = targetIndex.getOneBased() + HINT_DESC_FRIEND;
        descriptor = new EditCardDescriptorBuilder().withHint(VALID_HINT_FRIEND).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetHints_success() {
        Index targetIndex = INDEX_THIRD_CARD;
        String userInput = targetIndex.getOneBased() + HINT_EMPTY;

        EditCommand.EditCardDescriptor descriptor = new EditCardDescriptorBuilder().withHint().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetOptions_success() {
        Index targetIndex = INDEX_THIRD_CARD;
        String userInput = targetIndex.getOneBased() + OPTION_EMPTY;

        EditCommand.EditCardDescriptor descriptor = new EditCardDescriptorBuilder().withOptions().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}

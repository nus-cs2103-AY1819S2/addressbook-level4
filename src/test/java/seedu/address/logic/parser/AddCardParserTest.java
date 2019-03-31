package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.management.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.management.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.Syntax.PREFIX_CORE;
import static seedu.address.logic.parser.Syntax.PREFIX_OPTIONAL;
import static seedu.address.testutil.TypicalCards.CARD_JAPAN_CORE1;
import static seedu.address.testutil.TypicalCards.CARD_JAPAN_CORE2;
import static seedu.address.testutil.TypicalCards.CARD_JAPAN_OPT1;

import org.junit.Test;

import seedu.address.logic.commands.management.AddCardCommand;
import seedu.address.model.card.Card;
import seedu.address.testutil.CardBuilder;
import seedu.address.testutil.TypicalCards;

public class AddCardParserTest {
    private static final String CARD_CORE_1 = " " + PREFIX_CORE + CARD_JAPAN_CORE1;
    private static final String CARD_CORE_2 = " " + PREFIX_CORE + CARD_JAPAN_CORE2;
    private static final String CARD_OPT_1 = " " + PREFIX_OPTIONAL + CARD_JAPAN_OPT1;
    private String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCardCommand.MESSAGE_USAGE);
    private AddCardParser addCardParser = new AddCardParser();
    private Card card = TypicalCards.CARD_JAPAN;

    @Test
    public void parse_allFieldsPresent_success() {
        // whitespace only preamble
        assertParseSuccess(addCardParser, PREAMBLE_WHITESPACE + CARD_CORE_1 + CARD_CORE_2
                + CARD_OPT_1,
                new AddCardCommand(card));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero optionals
        Card expectedCard = new CardBuilder(card).withNoOptionals().build();
        assertParseSuccess(addCardParser, CARD_CORE_1 + CARD_CORE_2,
                new AddCardCommand(expectedCard));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {

        // missing 1 core prefix
        assertParseFailure(addCardParser, CARD_JAPAN_CORE1 + CARD_CORE_2, expectedMessage);

        // all prefixes missing
        assertParseFailure(addCardParser, CARD_JAPAN_CORE1 + CARD_JAPAN_CORE2, expectedMessage);

        // missing 1 core
        assertParseFailure(addCardParser, CARD_JAPAN_CORE1, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // non-empty preamble
        assertParseFailure(addCardParser, PREAMBLE_NON_EMPTY + CARD_JAPAN_CORE1 + CARD_CORE_2, expectedMessage);
    }
}

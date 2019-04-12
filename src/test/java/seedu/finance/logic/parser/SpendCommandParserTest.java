package seedu.finance.logic.parser;

import static seedu.finance.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.finance.logic.commands.CommandTestUtil.AMOUNT_DESC_AMY;
import static seedu.finance.logic.commands.CommandTestUtil.AMOUNT_DESC_BOB;
import static seedu.finance.logic.commands.CommandTestUtil.CATEGORY_DESC_FRIEND;
import static seedu.finance.logic.commands.CommandTestUtil.CATEGORY_DESC_HUSBAND;
import static seedu.finance.logic.commands.CommandTestUtil.DATE_DESC_AMY;
import static seedu.finance.logic.commands.CommandTestUtil.DATE_DESC_BOB;
import static seedu.finance.logic.commands.CommandTestUtil.DESCRIPTION_DESC_AMY;
import static seedu.finance.logic.commands.CommandTestUtil.DESCRIPTION_DESC_BOB;
import static seedu.finance.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC;
import static seedu.finance.logic.commands.CommandTestUtil.INVALID_CATEGORY_DESC;
import static seedu.finance.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.finance.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC;
import static seedu.finance.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.finance.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.finance.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.finance.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.finance.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_AMOUNT_BOB;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_CATEGORY_FRIEND;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.finance.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.finance.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.finance.testutil.TypicalRecords.AMY;
import static seedu.finance.testutil.TypicalRecords.BOB;

import java.time.LocalDate;

import org.junit.Test;

import seedu.finance.logic.commands.SpendCommand;
import seedu.finance.model.category.Category;
import seedu.finance.model.record.Amount;
import seedu.finance.model.record.Date;
import seedu.finance.model.record.Description;
import seedu.finance.model.record.Name;
import seedu.finance.model.record.Record;
import seedu.finance.testutil.RecordBuilder;

public class SpendCommandParserTest {
    private SpendCommandParser parser = new SpendCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Record expectedRecord = new RecordBuilder(BOB).withCategory(VALID_CATEGORY_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + AMOUNT_DESC_BOB + DATE_DESC_BOB
                + CATEGORY_DESC_FRIEND + DESCRIPTION_DESC_BOB, new SpendCommand(expectedRecord));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + AMOUNT_DESC_BOB + DATE_DESC_BOB
                + CATEGORY_DESC_FRIEND + DESCRIPTION_DESC_BOB, new SpendCommand(expectedRecord));

        // multiple amounts - last amount accepted
        assertParseSuccess(parser, NAME_DESC_BOB + AMOUNT_DESC_AMY + AMOUNT_DESC_BOB + DATE_DESC_BOB
                + CATEGORY_DESC_FRIEND + DESCRIPTION_DESC_BOB, new SpendCommand(expectedRecord));

        // multiple dates - last date accepted
        assertParseSuccess(parser, NAME_DESC_BOB + AMOUNT_DESC_BOB + DATE_DESC_AMY + DATE_DESC_BOB
                + CATEGORY_DESC_FRIEND + DESCRIPTION_DESC_BOB, new SpendCommand(expectedRecord));

        // multiple categories - last category accepted
        assertParseSuccess(parser, NAME_DESC_BOB + AMOUNT_DESC_BOB + DATE_DESC_BOB
                        + CATEGORY_DESC_HUSBAND + DESCRIPTION_DESC_BOB + CATEGORY_DESC_FRIEND,
                new SpendCommand(expectedRecord));
    }

    @Test
    public void parse_optionalDateFieldMissing_success() {
        Date date = new Date(LocalDate.now());
        Record expectedRecord = new RecordBuilder(AMY).withDate(date.toString()).build();
        assertParseSuccess(parser, NAME_DESC_AMY + AMOUNT_DESC_AMY + CATEGORY_DESC_FRIEND
                + DESCRIPTION_DESC_AMY, new SpendCommand(expectedRecord));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SpendCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + AMOUNT_DESC_BOB + DATE_DESC_BOB
                + CATEGORY_DESC_FRIEND, expectedMessage);

        // missing amount prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_AMOUNT_BOB + DATE_DESC_BOB
                + CATEGORY_DESC_FRIEND, expectedMessage);

        // missing category prefix
        assertParseFailure(parser, NAME_DESC_BOB + AMOUNT_DESC_BOB + DATE_DESC_BOB
                + VALID_CATEGORY_FRIEND, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_AMOUNT_BOB + VALID_DATE_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + AMOUNT_DESC_BOB + DATE_DESC_BOB
                + CATEGORY_DESC_HUSBAND + CATEGORY_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid amount
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_AMOUNT_DESC + DATE_DESC_BOB
                + CATEGORY_DESC_HUSBAND + CATEGORY_DESC_FRIEND, Amount.MESSAGE_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser, NAME_DESC_BOB + AMOUNT_DESC_BOB + INVALID_DATE_DESC
                + CATEGORY_DESC_HUSBAND + CATEGORY_DESC_FRIEND, Date.MESSAGE_CONSTRAINTS);

        // invalid category
        assertParseFailure(parser, NAME_DESC_BOB + AMOUNT_DESC_BOB + DATE_DESC_BOB
                + INVALID_CATEGORY_DESC, Category.MESSAGE_CONSTRAINTS);

        //invalid description
        assertParseFailure(parser, NAME_DESC_BOB + AMOUNT_DESC_BOB + DATE_DESC_BOB
                + CATEGORY_DESC_FRIEND + INVALID_DESCRIPTION_DESC, Description.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + AMOUNT_DESC_BOB + INVALID_AMOUNT_DESC
                + DATE_DESC_BOB + CATEGORY_DESC_HUSBAND, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + AMOUNT_DESC_BOB + DATE_DESC_BOB
                        + CATEGORY_DESC_HUSBAND + CATEGORY_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SpendCommand.MESSAGE_USAGE));
    }
}

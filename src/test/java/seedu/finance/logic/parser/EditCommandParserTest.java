package seedu.finance.logic.parser;

import static seedu.finance.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.finance.logic.commands.CommandTestUtil.AMOUNT_DESC_AMY;
import static seedu.finance.logic.commands.CommandTestUtil.AMOUNT_DESC_BOB;
import static seedu.finance.logic.commands.CommandTestUtil.CATEGORY_DESC_FRIEND;
import static seedu.finance.logic.commands.CommandTestUtil.CATEGORY_DESC_HUSBAND;
import static seedu.finance.logic.commands.CommandTestUtil.DATE_DESC_AMY;
import static seedu.finance.logic.commands.CommandTestUtil.DATE_DESC_BOB;
import static seedu.finance.logic.commands.CommandTestUtil.DESCRIPTION_DESC_AMY;
import static seedu.finance.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC;
import static seedu.finance.logic.commands.CommandTestUtil.INVALID_CATEGORY_DESC;
import static seedu.finance.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.finance.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC;
import static seedu.finance.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.finance.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_AMOUNT_AMY;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_AMOUNT_BOB;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_CATEGORY_FRIEND;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_CATEGORY_HUSBAND;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_DATE_AMY;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_DESCRIPTION_AMY;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.finance.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.finance.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.finance.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.finance.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.finance.testutil.TypicalIndexes.INDEX_FIRST_RECORD;
import static seedu.finance.testutil.TypicalIndexes.INDEX_SECOND_RECORD;
import static seedu.finance.testutil.TypicalIndexes.INDEX_THIRD_RECORD;

import org.junit.Test;

import seedu.finance.commons.core.index.Index;
import seedu.finance.logic.commands.EditCommand;
import seedu.finance.logic.commands.EditCommand.EditRecordDescriptor;
import seedu.finance.model.category.Category;
import seedu.finance.model.record.Amount;
import seedu.finance.model.record.Date;
import seedu.finance.model.record.Description;
import seedu.finance.model.record.Name;
import seedu.finance.testutil.EditRecordDescriptorBuilder;

public class EditCommandParserTest {

    private static final String CATEGORY_EMPTY = " " + PREFIX_CATEGORY;

    private static final String DESCRIPTION_EMPTY = PREFIX_DESCRIPTION + " ";

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    // Category is a compulsory field.
    @Test
    public void parse_resetCategories_failure() {
        Index targetIndex = INDEX_THIRD_RECORD;
        String userInput = targetIndex.getOneBased() + CATEGORY_EMPTY;
        assertParseFailure(parser, userInput, Category.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_AMOUNT_DESC, Amount.MESSAGE_CONSTRAINTS); // invalid amount
        assertParseFailure(parser, "1" + INVALID_DATE_DESC, Date.MESSAGE_CONSTRAINTS); // invalid date
        assertParseFailure(parser, "1" + INVALID_CATEGORY_DESC, Category.MESSAGE_CONSTRAINTS); // invalid category
        assertParseFailure(parser, "1"
                + INVALID_DESCRIPTION_DESC, Description.MESSAGE_CONSTRAINTS); // invalid description

        // valid amount followed by invalid amount. The test case for invalid amount followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + AMOUNT_DESC_BOB + INVALID_AMOUNT_DESC, Amount.MESSAGE_CONSTRAINTS);

        // parsing {@code PREFIX_CATEGORY} as the last argument will result in an error
        assertParseFailure(parser, "1" + CATEGORY_DESC_FRIEND + CATEGORY_DESC_HUSBAND
                            + CATEGORY_EMPTY, Category.MESSAGE_CONSTRAINTS);

        // parsing {@code PREFIX_CATEGORY} will result in an error
        assertParseFailure(parser, "1" + CATEGORY_EMPTY, Category.MESSAGE_CONSTRAINTS);


        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_AMOUNT_DESC + VALID_AMOUNT_AMY
                        + VALID_DATE_AMY, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_RECORD;
        String userInput = targetIndex.getOneBased() + AMOUNT_DESC_BOB + CATEGORY_DESC_HUSBAND
                + DATE_DESC_AMY + NAME_DESC_AMY + CATEGORY_DESC_FRIEND;

        EditRecordDescriptor descriptor = new EditRecordDescriptorBuilder().withName(VALID_NAME_AMY)
                .withAmount(VALID_AMOUNT_BOB).withDate(VALID_DATE_AMY)
                .withCategory(VALID_CATEGORY_FRIEND)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_RECORD;
        String userInput = targetIndex.getOneBased() + AMOUNT_DESC_BOB + DATE_DESC_AMY;

        EditRecordDescriptor descriptor = new EditRecordDescriptorBuilder().withAmount(VALID_AMOUNT_BOB)
                .withDate(VALID_DATE_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_RECORD;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditRecordDescriptor descriptor = new EditRecordDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // amount
        userInput = targetIndex.getOneBased() + AMOUNT_DESC_AMY;
        descriptor = new EditRecordDescriptorBuilder().withAmount(VALID_AMOUNT_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // date
        userInput = targetIndex.getOneBased() + DATE_DESC_AMY;
        descriptor = new EditRecordDescriptorBuilder().withDate(VALID_DATE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // categories
        userInput = targetIndex.getOneBased() + CATEGORY_DESC_FRIEND;
        descriptor = new EditRecordDescriptorBuilder().withCategory(VALID_CATEGORY_FRIEND).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        //description
        userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_AMY;
        descriptor = new EditRecordDescriptorBuilder().withDescription(VALID_DESCRIPTION_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_RECORD;
        String userInput = targetIndex.getOneBased() + AMOUNT_DESC_AMY + DATE_DESC_AMY
                + CATEGORY_DESC_FRIEND + AMOUNT_DESC_AMY + DATE_DESC_AMY + CATEGORY_DESC_FRIEND
                + AMOUNT_DESC_BOB + DATE_DESC_BOB + CATEGORY_DESC_HUSBAND;

        EditRecordDescriptor descriptor = new EditRecordDescriptorBuilder().withAmount(VALID_AMOUNT_BOB)
                .withDate(VALID_DATE_BOB).withCategory(VALID_CATEGORY_HUSBAND)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_RECORD;
        String userInput = targetIndex.getOneBased() + INVALID_AMOUNT_DESC + AMOUNT_DESC_BOB;
        EditRecordDescriptor descriptor = new EditRecordDescriptorBuilder().withAmount(VALID_AMOUNT_BOB).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + DATE_DESC_BOB + INVALID_AMOUNT_DESC + AMOUNT_DESC_BOB;
        descriptor = new EditRecordDescriptorBuilder().withAmount(VALID_AMOUNT_BOB).withDate(VALID_DATE_BOB).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}

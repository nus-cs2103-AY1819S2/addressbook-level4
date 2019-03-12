package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.AMOUNT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.AMOUNT_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.CATEGORY_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.CATEGORY_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CATEGORY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_RECORD;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_RECORD;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditRecordDescriptor;
import seedu.address.model.category.Category;
import seedu.address.model.record.Amount;
import seedu.address.model.record.Date;
import seedu.address.model.record.Name;
import seedu.address.testutil.EditRecordDescriptorBuilder;

public class EditCommandParserTest {

    private static final String CATEGORY_EMPTY = " " + PREFIX_CATEGORY;

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
        assertParseFailure(parser, "1" + INVALID_CATEGORY_DESC, Category.MESSAGE_CONSTRAINTS); // invalid
                                                                                                        // category

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + AMOUNT_DESC_BOB + INVALID_AMOUNT_DESC, Amount.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_CATEGORY} alone will reset the tags of the {@code Record} being edited,
        // parsing it together with a valid category results in error
        assertParseFailure(parser, "1" + CATEGORY_DESC_FRIEND + CATEGORY_DESC_HUSBAND
                            + CATEGORY_EMPTY, Category.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + CATEGORY_DESC_FRIEND + CATEGORY_EMPTY
                            + CATEGORY_DESC_HUSBAND, Category.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + CATEGORY_EMPTY + CATEGORY_DESC_FRIEND
                            + CATEGORY_DESC_HUSBAND, Category.MESSAGE_CONSTRAINTS);

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
                .withCategories(VALID_CATEGORY_HUSBAND, VALID_CATEGORY_FRIEND)
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
        Index targetIndex = INDEX_THIRD_PERSON;
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

        // tags
        userInput = targetIndex.getOneBased() + CATEGORY_DESC_FRIEND;
        descriptor = new EditRecordDescriptorBuilder().withCategories(VALID_CATEGORY_FRIEND).build();
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
                .withDate(VALID_DATE_BOB).withCategories(VALID_CATEGORY_FRIEND, VALID_CATEGORY_HUSBAND)
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

    @Test
    public void parse_resetCategories_success() {
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + CATEGORY_EMPTY;

        EditCommand.EditRecordDescriptor descriptor = new EditRecordDescriptorBuilder().withCategories().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INPUT_COLOR_BLUE;
import static seedu.address.logic.commands.CommandTestUtil.INPUT_COLOR_GREEN;
import static seedu.address.logic.commands.CommandTestUtil.INPUT_NAME_A;
import static seedu.address.logic.commands.CommandTestUtil.INPUT_TYPE_BOTTOM;
import static seedu.address.logic.commands.CommandTestUtil.INPUT_TYPE_TOP;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INPUT_COLOR;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INPUT_NAME;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INPUT_TYPE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COLOR_BLUE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COLOR_GREEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_A;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_BOTTOM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_TOP;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_APPAREL;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_APPAREL;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_APPAREL;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.apparel.ClothingType;
import seedu.address.model.apparel.Color;
import seedu.address.model.apparel.Name;
import seedu.address.testutil.EditApparelDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " ";

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_A, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + INPUT_NAME_A, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + INPUT_NAME_A, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_INPUT_NAME, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_INPUT_COLOR, Color.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + INVALID_INPUT_TYPE, ClothingType.MESSAGE_CONSTRAINTS); //invalid email

        // invalid phone followed by valid email
        assertParseFailure(parser, "1" + INVALID_INPUT_COLOR + INPUT_TYPE_TOP, Color.MESSAGE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + INPUT_COLOR_BLUE + INVALID_INPUT_COLOR, Color.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_INPUT_NAME + INVALID_INPUT_TYPE + VALID_COLOR_GREEN,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_APPAREL;
        String userInput = targetIndex.getOneBased() + INPUT_COLOR_BLUE + INPUT_TYPE_TOP + INPUT_NAME_A;

        EditPersonDescriptor descriptor = new EditApparelDescriptorBuilder().withName(VALID_NAME_A)
                .withColor(VALID_COLOR_BLUE).withClothingType(VALID_TYPE_TOP).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_APPAREL;
        String userInput = targetIndex.getOneBased() + INPUT_COLOR_BLUE + INPUT_TYPE_TOP;

        EditPersonDescriptor descriptor = new EditApparelDescriptorBuilder().withColor(VALID_COLOR_BLUE)
                .withClothingType(VALID_TYPE_TOP).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_APPAREL;
        String userInput = targetIndex.getOneBased() + INPUT_NAME_A;
        EditPersonDescriptor descriptor = new EditApparelDescriptorBuilder().withName(VALID_NAME_A).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + INPUT_COLOR_GREEN;
        descriptor = new EditApparelDescriptorBuilder().withColor(VALID_COLOR_GREEN).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + INPUT_TYPE_TOP;
        descriptor = new EditApparelDescriptorBuilder().withClothingType(VALID_TYPE_TOP).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_APPAREL;
        String userInput = targetIndex.getOneBased() + INPUT_COLOR_GREEN + INPUT_TYPE_TOP
                + INPUT_COLOR_GREEN + INPUT_TYPE_TOP + INPUT_COLOR_BLUE + INPUT_TYPE_BOTTOM;

        EditPersonDescriptor descriptor = new EditApparelDescriptorBuilder().withColor(VALID_COLOR_BLUE)
                .withClothingType(VALID_TYPE_BOTTOM).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_APPAREL;
        String userInput = targetIndex.getOneBased() + INVALID_INPUT_COLOR + INPUT_COLOR_BLUE;
        EditPersonDescriptor descriptor = new EditApparelDescriptorBuilder().withColor(VALID_COLOR_BLUE).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + INPUT_TYPE_BOTTOM + INVALID_INPUT_COLOR + INPUT_COLOR_BLUE;
        descriptor = new EditApparelDescriptorBuilder().withColor(VALID_COLOR_BLUE).withClothingType(VALID_TYPE_BOTTOM)
                .build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    /*@Test not dealing with tags anymore.
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_APPAREL;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditPersonDescriptor descriptor = new EditApparelDescriptorBuilder().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }*/
}

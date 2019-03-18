package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUISINE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_RESTAURANT;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SetCategoryCommand;
import seedu.address.model.restaurant.categories.Category;
import seedu.address.model.restaurant.categories.Cuisine;

public class CategoryCommandParserTest {

    private static final String VALID_INDEX = "1";
    private static final String VALID_CUISINE = "Fast Food";
    private static final String INVALID_CUISINE = "Fast$Food";
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetCategoryCommand.MESSAGE_USAGE);

    private CategoryCommandParser parser = new CategoryCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // missing index
        assertParseFailure(parser, PREFIX_CUISINE + VALID_CUISINE, MESSAGE_INVALID_FORMAT);

        // missing cuisine
        assertParseFailure(parser, VALID_INDEX, MESSAGE_INVALID_FORMAT);

        // nothing specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5 " + PREFIX_CUISINE + VALID_CUISINE, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0 " + PREFIX_CUISINE + VALID_CUISINE, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidCuisine_failure() {
        String invalidCuisine = VALID_INDEX + PREFIX_CUISINE + INVALID_CUISINE;
        assertParseFailure(parser, invalidCuisine, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_RESTAURANT;
        String validFormat = targetIndex.getOneBased() + " " + PREFIX_CUISINE + VALID_CUISINE;
        Category validCategory = new Category(new Cuisine(VALID_CUISINE), null);
        SetCategoryCommand expectedCommand = new SetCategoryCommand(targetIndex, validCategory);
        assertParseSuccess(parser, validFormat, expectedCommand);
    }
}

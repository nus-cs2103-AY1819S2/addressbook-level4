package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.EditReviewCommand.EditReviewDescriptor;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_RESTAURANT;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditReviewCommand;
import seedu.address.testutil.EditReviewDescriptorBuilder;

public class EditReviewCommandParserTest {
    private EditReviewCommandParser parser = new EditReviewCommandParser();

    @Test
    public void parse_allArgumentsPresent_success() {
        Index expectedIndex = INDEX_FIRST_RESTAURANT;
        EditReviewDescriptorBuilder editReviewDescriptorBuilder = new EditReviewDescriptorBuilder();
        EditReviewDescriptor descriptor = editReviewDescriptorBuilder.withRating("3").withEntry("Standard restaurant")
                .build();

        //correct command to edit a review
        assertParseSuccess(parser, "1 re/Standard restaurant rr/3", new EditReviewCommand(expectedIndex,
                descriptor));
    }

    @Test
    public void parse_optionalArgumentsPresent_success() {
        Index expectedIndex = INDEX_FIRST_RESTAURANT;
        EditReviewDescriptorBuilder editReviewDescriptorBuilder = new EditReviewDescriptorBuilder();
        EditReviewDescriptor descriptor = editReviewDescriptorBuilder.withRating("3").build();

        //correct command to edit a review
        assertParseSuccess(parser, "1 rr/3", new EditReviewCommand(expectedIndex,
                descriptor));

        EditReviewDescriptorBuilder editReviewDescriptorBuilder2 = new EditReviewDescriptorBuilder();
        EditReviewDescriptor descriptor2 = editReviewDescriptorBuilder2.withEntry("Standard restaurant").build();

        //correct command to edit a review
        assertParseSuccess(parser, "1 re/Standard restaurant", new EditReviewCommand(expectedIndex,
                descriptor2));
    }

    @Test
    public void parse_argumentMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditReviewCommand.MESSAGE_USAGE);

        String expectedMessageOnlyIndex = String.format(EditReviewCommand.MESSAGE_NOT_EDITED);

        //If no index given
        assertParseFailure(parser, "re/Nice rr/4", expectedMessage);

        //If only index given
        assertParseFailure(parser, "1", expectedMessageOnlyIndex);

        //If no arguments given
        assertParseFailure(parser, "", expectedMessage);
    }
}

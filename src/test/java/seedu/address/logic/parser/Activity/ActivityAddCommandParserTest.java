package seedu.address.logic.parser.activity;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ACTIVITY_DATETIME_DESC_HTML;
import static seedu.address.logic.commands.CommandTestUtil.ACTIVITY_DATETIME_DESC_OUTING;
import static seedu.address.logic.commands.CommandTestUtil.ACTIVITY_DESCRIPTION_DESC_HTML;
import static seedu.address.logic.commands.CommandTestUtil.ACTIVITY_DESCRIPTION_DESC_OUTING;
import static seedu.address.logic.commands.CommandTestUtil.ACTIVITY_LOCATION_DESC_HTML;
import static seedu.address.logic.commands.CommandTestUtil.ACTIVITY_LOCATION_DESC_OUTING;
import static seedu.address.logic.commands.CommandTestUtil.ACTIVITY_NAME_DESC_HTML;
import static seedu.address.logic.commands.CommandTestUtil.ACTIVITY_NAME_DESC_OUTING;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ACTIVITY_DATETIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ACTIVITY_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ACTIVITY_DATETIME_HTML;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ACTIVITY_DATETIME_OUTING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ACTIVITY_DESCRIPTION_OUTING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ACTIVITY_LOCATION_HTML;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ACTIVITY_LOCATION_OUTING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ACTIVITY_NAME_HTML;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ACTIVITY_NAME_OUTING;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalActivities.HTML;

import org.junit.Test;

import seedu.address.logic.commands.ActivityAddCommand;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.ActivityDateTime;
import seedu.address.model.activity.ActivityName;
import seedu.address.testutil.ActivityBuilder;

public class ActivityAddCommandParserTest {
    private ActivityAddCommandParser parser = new ActivityAddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Activity expectedActivity = new ActivityBuilder(HTML).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + ACTIVITY_NAME_DESC_HTML
                + ACTIVITY_DATETIME_DESC_HTML + ACTIVITY_LOCATION_DESC_HTML + ACTIVITY_DESCRIPTION_DESC_HTML,
                new ActivityAddCommand(expectedActivity));

    }

    @Test
    public void parse_optionalFieldMissing_success() {
        Activity expectedActivity = new ActivityBuilder(HTML).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + ACTIVITY_NAME_DESC_HTML
                        + ACTIVITY_DATETIME_DESC_HTML + ACTIVITY_LOCATION_DESC_HTML,
                new ActivityAddCommand(expectedActivity));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ActivityAddCommand.MESSAGE_USAGE);

        //missing activity_name prefix
        assertParseFailure(parser, VALID_ACTIVITY_NAME_HTML + ACTIVITY_DATETIME_DESC_HTML
                + ACTIVITY_LOCATION_DESC_HTML, expectedMessage);

        //missing activity_date_time prefix
        assertParseFailure(parser, ACTIVITY_NAME_DESC_OUTING + VALID_ACTIVITY_DATETIME_OUTING
                + VALID_ACTIVITY_LOCATION_OUTING + VALID_ACTIVITY_DESCRIPTION_OUTING, expectedMessage);

        //missing activity_location prefix
        assertParseFailure(parser, ACTIVITY_NAME_DESC_OUTING + ACTIVITY_DATETIME_DESC_OUTING
                + VALID_ACTIVITY_LOCATION_OUTING + VALID_ACTIVITY_DESCRIPTION_OUTING, expectedMessage);

        //all prefixes missing
        assertParseFailure(parser, VALID_ACTIVITY_NAME_OUTING + VALID_ACTIVITY_DATETIME_HTML
                + VALID_ACTIVITY_LOCATION_HTML + VALID_ACTIVITY_DESCRIPTION_OUTING, expectedMessage);
    }

    @Test
    public void parse_redundantFields_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ActivityAddCommand.MESSAGE_DUPLICATED_PREFIXES);

        //duplicated name
        assertParseFailure(parser, ACTIVITY_NAME_DESC_OUTING + ACTIVITY_NAME_DESC_HTML
                + ACTIVITY_DATETIME_DESC_OUTING + ACTIVITY_LOCATION_DESC_OUTING + ACTIVITY_DESCRIPTION_DESC_HTML,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        //invalid activityName
        assertParseFailure(parser, INVALID_ACTIVITY_NAME_DESC + ACTIVITY_DATETIME_DESC_OUTING
                + ACTIVITY_LOCATION_DESC_OUTING + ACTIVITY_DESCRIPTION_DESC_OUTING, ActivityName.MESSAGE_CONSTRAINTS);

        //invalid activity Datetime
        assertParseFailure(parser, ACTIVITY_NAME_DESC_HTML + INVALID_ACTIVITY_DATETIME_DESC
                + ACTIVITY_LOCATION_DESC_HTML, ActivityDateTime.MESSAGE_CONSTRAINTS);
    }

}

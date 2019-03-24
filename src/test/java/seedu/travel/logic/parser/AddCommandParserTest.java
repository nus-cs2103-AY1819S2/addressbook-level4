package seedu.travel.logic.parser;

import static seedu.travel.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.travel.logic.commands.CommandTestUtil.ADDRESS_DESC_AMK;
import static seedu.travel.logic.commands.CommandTestUtil.ADDRESS_DESC_BEDOK;
import static seedu.travel.logic.commands.CommandTestUtil.COUNTRY_CODE_DESC_AMK;
import static seedu.travel.logic.commands.CommandTestUtil.COUNTRY_CODE_DESC_BEDOK;
import static seedu.travel.logic.commands.CommandTestUtil.DATE_VISITED_DESC_AMK;
import static seedu.travel.logic.commands.CommandTestUtil.DATE_VISITED_DESC_BEDOK;
import static seedu.travel.logic.commands.CommandTestUtil.DESCRIPTION_AMK;
import static seedu.travel.logic.commands.CommandTestUtil.DESCRIPTION_BEDOK;
import static seedu.travel.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.travel.logic.commands.CommandTestUtil.INVALID_COUNTRY_CODE_DESC;
import static seedu.travel.logic.commands.CommandTestUtil.INVALID_DESCRIPTION;
import static seedu.travel.logic.commands.CommandTestUtil.INVALID_FORMAT_DATE_VISITED_DESC;
import static seedu.travel.logic.commands.CommandTestUtil.INVALID_FUTURE_DATE_VISITED_DESC;
import static seedu.travel.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.travel.logic.commands.CommandTestUtil.INVALID_RATING_DESC;
import static seedu.travel.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.travel.logic.commands.CommandTestUtil.NAME_DESC_AMK;
import static seedu.travel.logic.commands.CommandTestUtil.NAME_DESC_BEDOK;
import static seedu.travel.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.travel.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.travel.logic.commands.CommandTestUtil.RATING_DESC_AMK;
import static seedu.travel.logic.commands.CommandTestUtil.RATING_DESC_BEDOK;
import static seedu.travel.logic.commands.CommandTestUtil.TAG_DESC_EWL;
import static seedu.travel.logic.commands.CommandTestUtil.TAG_DESC_MRT;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_ADDRESS_BEDOK;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_COUNTRY_CODE_BEDOK;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_DATE_VISITED_BEDOK;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BEDOK;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_NAME_BEDOK;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_RATING_BEDOK;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_TAG_EWL;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_TAG_MRT;
import static seedu.travel.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.travel.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.travel.testutil.TypicalPlaces.AMK;
import static seedu.travel.testutil.TypicalPlaces.BEDOK;

import org.junit.Test;

import seedu.travel.logic.commands.AddCommand;
import seedu.travel.model.place.Address;
import seedu.travel.model.place.CountryCode;
import seedu.travel.model.place.DateVisited;
import seedu.travel.model.place.Description;
import seedu.travel.model.place.Name;
import seedu.travel.model.place.Place;
import seedu.travel.model.place.Rating;
import seedu.travel.model.tag.Tag;
import seedu.travel.testutil.PlaceBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Place expectedPlace = new PlaceBuilder(BEDOK).withTags(VALID_TAG_MRT).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BEDOK + COUNTRY_CODE_DESC_BEDOK
                + DATE_VISITED_DESC_BEDOK + RATING_DESC_BEDOK + DESCRIPTION_BEDOK + ADDRESS_DESC_BEDOK + TAG_DESC_MRT,
                new AddCommand(expectedPlace));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMK + NAME_DESC_BEDOK + COUNTRY_CODE_DESC_BEDOK
                + DATE_VISITED_DESC_BEDOK + RATING_DESC_BEDOK + DESCRIPTION_BEDOK + ADDRESS_DESC_BEDOK + TAG_DESC_MRT,
                new AddCommand(expectedPlace));

        // multiple country codes - last country code accepted
        assertParseSuccess(parser, NAME_DESC_BEDOK + COUNTRY_CODE_DESC_AMK + COUNTRY_CODE_DESC_BEDOK
                + DATE_VISITED_DESC_BEDOK + RATING_DESC_BEDOK + DESCRIPTION_BEDOK
                + ADDRESS_DESC_BEDOK + TAG_DESC_MRT, new AddCommand(expectedPlace));

        // multiple date visited - last date visited accepted
        assertParseSuccess(parser, NAME_DESC_BEDOK + COUNTRY_CODE_DESC_BEDOK + DATE_VISITED_DESC_AMK
                + DATE_VISITED_DESC_BEDOK + RATING_DESC_BEDOK + DESCRIPTION_BEDOK
                + ADDRESS_DESC_BEDOK + TAG_DESC_MRT, new AddCommand(expectedPlace));

        // multiple ratings - last rating accepted
        assertParseSuccess(parser, NAME_DESC_BEDOK + COUNTRY_CODE_DESC_BEDOK + DATE_VISITED_DESC_BEDOK
                + RATING_DESC_AMK + RATING_DESC_BEDOK + DESCRIPTION_BEDOK
                + ADDRESS_DESC_BEDOK + TAG_DESC_MRT, new AddCommand(expectedPlace));

        // multiple descriptions - last description accepted
        assertParseSuccess(parser, NAME_DESC_BEDOK + COUNTRY_CODE_DESC_BEDOK + DATE_VISITED_DESC_BEDOK
                + RATING_DESC_BEDOK + DESCRIPTION_AMK + DESCRIPTION_BEDOK
                + ADDRESS_DESC_BEDOK + TAG_DESC_MRT, new AddCommand(expectedPlace));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, NAME_DESC_BEDOK + COUNTRY_CODE_DESC_BEDOK + DATE_VISITED_DESC_BEDOK
                + RATING_DESC_BEDOK + DESCRIPTION_BEDOK + ADDRESS_DESC_AMK
                + ADDRESS_DESC_BEDOK + TAG_DESC_MRT, new AddCommand(expectedPlace));

        // multiple tags - all accepted
        Place expectedPlaceMultipleTags = new PlaceBuilder(BEDOK).withTags(VALID_TAG_MRT, VALID_TAG_EWL)
                .build();
        assertParseSuccess(parser, NAME_DESC_BEDOK + COUNTRY_CODE_DESC_BEDOK + DATE_VISITED_DESC_BEDOK
                + RATING_DESC_BEDOK + DESCRIPTION_BEDOK + ADDRESS_DESC_BEDOK
                + TAG_DESC_EWL + TAG_DESC_MRT, new AddCommand(expectedPlaceMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Place expectedPlace = new PlaceBuilder(AMK).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMK + COUNTRY_CODE_DESC_AMK + DATE_VISITED_DESC_AMK
                + RATING_DESC_AMK + DESCRIPTION_AMK + ADDRESS_DESC_AMK, new AddCommand(expectedPlace));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BEDOK + COUNTRY_CODE_DESC_BEDOK + DATE_VISITED_DESC_BEDOK
                + RATING_DESC_BEDOK + DESCRIPTION_BEDOK + ADDRESS_DESC_BEDOK, expectedMessage);

        // missing country code prefix
        assertParseFailure(parser, NAME_DESC_BEDOK + VALID_COUNTRY_CODE_BEDOK + DATE_VISITED_DESC_BEDOK
                + RATING_DESC_BEDOK + DESCRIPTION_BEDOK + ADDRESS_DESC_BEDOK, expectedMessage);

        // missing date visited prefix
        assertParseFailure(parser,
            NAME_DESC_BEDOK + COUNTRY_CODE_DESC_BEDOK + VALID_DATE_VISITED_BEDOK + RATING_DESC_BEDOK
                + DESCRIPTION_BEDOK + ADDRESS_DESC_BEDOK, expectedMessage);

        // missing rating prefix
        assertParseFailure(parser, NAME_DESC_BEDOK + COUNTRY_CODE_DESC_BEDOK + DATE_VISITED_DESC_BEDOK
                + VALID_RATING_BEDOK + DESCRIPTION_BEDOK + ADDRESS_DESC_BEDOK, expectedMessage);

        // missing description prefix
        assertParseFailure(parser,
            NAME_DESC_BEDOK + COUNTRY_CODE_DESC_BEDOK + DATE_VISITED_DESC_BEDOK + RATING_DESC_BEDOK
                + VALID_DESCRIPTION_BEDOK + ADDRESS_DESC_BEDOK, expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_BEDOK + COUNTRY_CODE_DESC_BEDOK + DATE_VISITED_DESC_BEDOK
                + RATING_DESC_BEDOK + DESCRIPTION_BEDOK + VALID_ADDRESS_BEDOK, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BEDOK + VALID_COUNTRY_CODE_BEDOK + VALID_DATE_VISITED_BEDOK
            + VALID_RATING_BEDOK + VALID_DESCRIPTION_BEDOK + VALID_ADDRESS_BEDOK, expectedMessage);

    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + COUNTRY_CODE_DESC_BEDOK + DATE_VISITED_DESC_BEDOK
                + RATING_DESC_BEDOK + DESCRIPTION_BEDOK
                + ADDRESS_DESC_BEDOK + TAG_DESC_EWL + TAG_DESC_MRT, Name.MESSAGE_CONSTRAINTS);

        // invalid country code
        assertParseFailure(parser, NAME_DESC_BEDOK + INVALID_COUNTRY_CODE_DESC + DATE_VISITED_DESC_BEDOK
                + RATING_DESC_BEDOK + DESCRIPTION_BEDOK
                + ADDRESS_DESC_BEDOK + TAG_DESC_EWL + TAG_DESC_MRT, CountryCode.MESSAGE_CONSTRAINTS);

        // incorrect format of date visited
        assertParseFailure(parser, NAME_DESC_BEDOK + COUNTRY_CODE_DESC_BEDOK + INVALID_FORMAT_DATE_VISITED_DESC
                + RATING_DESC_BEDOK + DESCRIPTION_BEDOK
                + ADDRESS_DESC_BEDOK + TAG_DESC_EWL + TAG_DESC_MRT, DateVisited.MESSAGE_INCORRECT_FORMAT);

        // future date visited added
        assertParseFailure(parser, NAME_DESC_BEDOK + COUNTRY_CODE_DESC_BEDOK + INVALID_FUTURE_DATE_VISITED_DESC
                + RATING_DESC_BEDOK + DESCRIPTION_BEDOK
                + ADDRESS_DESC_BEDOK + TAG_DESC_EWL + TAG_DESC_MRT, DateVisited.MESSAGE_FUTURE_DATE_ADDED);

        // invalid rating
        assertParseFailure(parser, NAME_DESC_BEDOK + COUNTRY_CODE_DESC_BEDOK + DATE_VISITED_DESC_BEDOK
                + INVALID_RATING_DESC + DESCRIPTION_BEDOK
                + ADDRESS_DESC_BEDOK + TAG_DESC_EWL + TAG_DESC_MRT, Rating.MESSAGE_CONSTRAINTS);

        // invalid description
        assertParseFailure(parser, NAME_DESC_BEDOK + COUNTRY_CODE_DESC_BEDOK + DATE_VISITED_DESC_BEDOK
                + RATING_DESC_BEDOK + INVALID_DESCRIPTION
                + ADDRESS_DESC_BEDOK + TAG_DESC_EWL + TAG_DESC_MRT, Description.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BEDOK + COUNTRY_CODE_DESC_BEDOK + DATE_VISITED_DESC_BEDOK
                + RATING_DESC_BEDOK + DESCRIPTION_BEDOK
                + INVALID_ADDRESS_DESC + TAG_DESC_EWL + TAG_DESC_MRT, Address.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BEDOK + COUNTRY_CODE_DESC_BEDOK + DATE_VISITED_DESC_BEDOK
                + RATING_DESC_BEDOK + DESCRIPTION_BEDOK
                + ADDRESS_DESC_BEDOK + INVALID_TAG_DESC + VALID_TAG_MRT, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + COUNTRY_CODE_DESC_BEDOK + DATE_VISITED_DESC_BEDOK
                + RATING_DESC_BEDOK + DESCRIPTION_BEDOK
                + INVALID_ADDRESS_DESC, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BEDOK + RATING_DESC_BEDOK
                + COUNTRY_CODE_DESC_BEDOK + DATE_VISITED_DESC_BEDOK + DESCRIPTION_BEDOK + ADDRESS_DESC_BEDOK
                + TAG_DESC_EWL + TAG_DESC_MRT,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}

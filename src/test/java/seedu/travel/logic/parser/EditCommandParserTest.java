package seedu.travel.logic.parser;

import static seedu.travel.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.travel.logic.commands.CommandTestUtil.ADDRESS_DESC_AMK;
import static seedu.travel.logic.commands.CommandTestUtil.ADDRESS_DESC_BEDOK;
import static seedu.travel.logic.commands.CommandTestUtil.COUNTRY_CODE_DESC_AMK;
import static seedu.travel.logic.commands.CommandTestUtil.COUNTRY_CODE_DESC_BEDOK;
import static seedu.travel.logic.commands.CommandTestUtil.DESCRIPTION_AMK;
import static seedu.travel.logic.commands.CommandTestUtil.DESCRIPTION_BEDOK;
import static seedu.travel.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.travel.logic.commands.CommandTestUtil.INVALID_COUNTRY_CODE_DESC;
import static seedu.travel.logic.commands.CommandTestUtil.INVALID_DESCRIPTION;
import static seedu.travel.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.travel.logic.commands.CommandTestUtil.INVALID_RATING_DESC;
import static seedu.travel.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.travel.logic.commands.CommandTestUtil.NAME_DESC_AMK;
import static seedu.travel.logic.commands.CommandTestUtil.RATING_DESC_AMK;
import static seedu.travel.logic.commands.CommandTestUtil.RATING_DESC_BEDOK;
import static seedu.travel.logic.commands.CommandTestUtil.TAG_DESC_EWL;
import static seedu.travel.logic.commands.CommandTestUtil.TAG_DESC_MRT;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_ADDRESS_AMK;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_ADDRESS_BEDOK;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_COUNTRY_CODE_AMK;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_COUNTRY_CODE_BEDOK;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_DESCRIPTION_AMK;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BEDOK;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_NAME_AMK;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_RATING_AMK;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_RATING_BEDOK;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_TAG_EWL;
import static seedu.travel.logic.commands.CommandTestUtil.VALID_TAG_MRT;
import static seedu.travel.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.travel.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.travel.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.travel.testutil.TypicalIndexes.INDEX_FIRST_PLACE;
import static seedu.travel.testutil.TypicalIndexes.INDEX_SECOND_PLACE;
import static seedu.travel.testutil.TypicalIndexes.INDEX_THIRD_PLACE;

import org.junit.Test;

import seedu.travel.commons.core.index.Index;
import seedu.travel.logic.commands.EditCommand;
import seedu.travel.logic.commands.EditCommand.EditPlaceDescriptor;
import seedu.travel.model.place.Address;
import seedu.travel.model.place.CountryCode;
import seedu.travel.model.place.Description;
import seedu.travel.model.place.Name;
import seedu.travel.model.place.Rating;
import seedu.travel.model.tag.Tag;
import seedu.travel.testutil.EditPlaceDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMK, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMK, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMK, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1"
            + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1"
            + INVALID_COUNTRY_CODE_DESC, CountryCode.MESSAGE_CONSTRAINTS); // invalid country code
        assertParseFailure(parser, "1" + INVALID_RATING_DESC, Rating.MESSAGE_CONSTRAINTS); // invalid rating
        assertParseFailure(parser, "1" + INVALID_DESCRIPTION,
                Description.MESSAGE_CONSTRAINTS); // invalid description
        assertParseFailure(parser, "1" + INVALID_ADDRESS_DESC,
                Address.MESSAGE_CONSTRAINTS); // invalid travel
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid country code followed by valid description
        assertParseFailure(parser, "1" + INVALID_COUNTRY_CODE_DESC
            + DESCRIPTION_AMK, CountryCode.MESSAGE_CONSTRAINTS);

        // invalid rating followed by valid description
        assertParseFailure(parser, "1" + INVALID_RATING_DESC + DESCRIPTION_AMK, Rating.MESSAGE_CONSTRAINTS);

        // valid country code followed by invalid country code.
        assertParseFailure(parser, "1" + COUNTRY_CODE_DESC_BEDOK
            + INVALID_COUNTRY_CODE_DESC, CountryCode.MESSAGE_CONSTRAINTS);

        // valid rating followed by invalid rating. The test case for invalid rating followed by valid rating
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + RATING_DESC_BEDOK + INVALID_RATING_DESC, Rating.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Place} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_MRT
                + TAG_DESC_EWL + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_MRT
                + TAG_EMPTY + TAG_DESC_EWL, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_MRT
                + TAG_DESC_EWL, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_DESCRIPTION
                + VALID_ADDRESS_AMK + VALID_RATING_AMK, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PLACE;
        String userInput = targetIndex.getOneBased() + RATING_DESC_BEDOK + COUNTRY_CODE_DESC_AMK + TAG_DESC_EWL
                + DESCRIPTION_AMK + ADDRESS_DESC_AMK + NAME_DESC_AMK + TAG_DESC_MRT;

        EditPlaceDescriptor descriptor = new EditPlaceDescriptorBuilder().withName(VALID_NAME_AMK)
                .withCountryCode(VALID_COUNTRY_CODE_AMK)
                .withRating(VALID_RATING_BEDOK).withDescription(VALID_DESCRIPTION_AMK).withAddress(VALID_ADDRESS_AMK)
                .withTags(VALID_TAG_EWL, VALID_TAG_MRT).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PLACE;
        String userInput = targetIndex.getOneBased() + COUNTRY_CODE_DESC_AMK + RATING_DESC_BEDOK + DESCRIPTION_AMK;

        EditPlaceDescriptor descriptor = new EditPlaceDescriptorBuilder().withCountryCode(VALID_COUNTRY_CODE_AMK)
                .withRating(VALID_RATING_BEDOK)
                .withDescription(VALID_DESCRIPTION_AMK).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_PLACE;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMK;
        EditPlaceDescriptor descriptor = new EditPlaceDescriptorBuilder().withName(VALID_NAME_AMK).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        /*
        userInput = targetIndex.getOneBased() + COUNTRY_CODE_DESC_AMK;
        descriptor = new EditPlaceDescriptorBuilder().withCountryCode(VALID_COUNTRY_CODE_AMK).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
        */

        // rating
        userInput = targetIndex.getOneBased() + RATING_DESC_AMK;
        descriptor = new EditPlaceDescriptorBuilder().withRating(VALID_RATING_AMK).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // description
        userInput = targetIndex.getOneBased() + DESCRIPTION_AMK;
        descriptor = new EditPlaceDescriptorBuilder().withDescription(VALID_DESCRIPTION_AMK).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // travel
        userInput = targetIndex.getOneBased() + ADDRESS_DESC_AMK;
        descriptor = new EditPlaceDescriptorBuilder().withAddress(VALID_ADDRESS_AMK).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_MRT;
        descriptor = new EditPlaceDescriptorBuilder().withTags(VALID_TAG_MRT).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_PLACE;
        String userInput =
            targetIndex.getOneBased() + COUNTRY_CODE_DESC_AMK + RATING_DESC_AMK + ADDRESS_DESC_AMK + DESCRIPTION_AMK
                + TAG_DESC_MRT + RATING_DESC_AMK + ADDRESS_DESC_AMK + COUNTRY_CODE_DESC_AMK
                + DESCRIPTION_AMK + TAG_DESC_MRT + RATING_DESC_BEDOK + ADDRESS_DESC_BEDOK + DESCRIPTION_BEDOK
                + TAG_DESC_EWL;

        EditPlaceDescriptor descriptor = new EditPlaceDescriptorBuilder().withCountryCode(VALID_COUNTRY_CODE_BEDOK)
                .withRating(VALID_RATING_BEDOK)
                .withDescription(VALID_DESCRIPTION_BEDOK).withAddress(VALID_ADDRESS_BEDOK)
                .withTags(VALID_TAG_MRT, VALID_TAG_EWL).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_PLACE;
        String userInput = targetIndex.getOneBased() + INVALID_RATING_DESC + RATING_DESC_BEDOK;
        EditPlaceDescriptor descriptor = new EditPlaceDescriptorBuilder().withRating(VALID_RATING_BEDOK).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + DESCRIPTION_BEDOK + INVALID_RATING_DESC + ADDRESS_DESC_BEDOK
                + RATING_DESC_BEDOK;
        descriptor = new EditPlaceDescriptorBuilder().withRating(VALID_RATING_BEDOK)
                .withDescription(VALID_DESCRIPTION_BEDOK).withAddress(VALID_ADDRESS_BEDOK).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_PLACE;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditPlaceDescriptor descriptor = new EditPlaceDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}

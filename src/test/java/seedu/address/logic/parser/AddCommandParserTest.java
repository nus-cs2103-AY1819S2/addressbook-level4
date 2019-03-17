package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EXPECTED_MAX_GRADE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EXPECTED_MAX_GRADE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EXPECTED_MIN_GRADE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EXPECTED_MIN_GRADE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EXPECTED_MAX_GRADE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EXPECTED_MIN_GRADE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SEMESTER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.SEMESTER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.SEMESTER_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPECTED_MAX_GRADE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPECTED_MIN_GRADE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEMESTER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.Grade;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Semester;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB
                + SEMESTER_DESC_BOB + EXPECTED_MIN_GRADE_DESC_BOB
                + EXPECTED_MAX_GRADE_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB
                + SEMESTER_DESC_BOB + EXPECTED_MIN_GRADE_DESC_BOB
                + EXPECTED_MAX_GRADE_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple semesters - last semester accepted
        assertParseSuccess(parser, NAME_DESC_BOB + SEMESTER_DESC_AMY
                + SEMESTER_DESC_BOB + EXPECTED_MIN_GRADE_DESC_BOB
                + EXPECTED_MAX_GRADE_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_BOB + SEMESTER_DESC_BOB
                + EXPECTED_MIN_GRADE_DESC_AMY + EXPECTED_MIN_GRADE_DESC_BOB
                + EXPECTED_MAX_GRADE_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, NAME_DESC_BOB + SEMESTER_DESC_BOB
                + EXPECTED_MIN_GRADE_DESC_BOB + EXPECTED_MAX_GRADE_DESC_AMY
                + EXPECTED_MAX_GRADE_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser, NAME_DESC_BOB + SEMESTER_DESC_BOB
                + EXPECTED_MIN_GRADE_DESC_BOB + EXPECTED_MAX_GRADE_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Person expectedPerson = new PersonBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + SEMESTER_DESC_AMY
                        + EXPECTED_MIN_GRADE_DESC_AMY + EXPECTED_MAX_GRADE_DESC_AMY,
                new AddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + SEMESTER_DESC_BOB
                        + EXPECTED_MIN_GRADE_DESC_BOB + EXPECTED_MAX_GRADE_DESC_BOB,
                expectedMessage);

        // missing semester prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_SEMESTER_BOB
                        + EXPECTED_MIN_GRADE_DESC_BOB + EXPECTED_MAX_GRADE_DESC_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_SEMESTER_BOB
                        + VALID_EXPECTED_MIN_GRADE_BOB + VALID_EXPECTED_MAX_GRADE_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + SEMESTER_DESC_BOB
                + EXPECTED_MIN_GRADE_DESC_BOB + EXPECTED_MAX_GRADE_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid semester
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_SEMESTER_DESC
                + EXPECTED_MIN_GRADE_DESC_BOB + EXPECTED_MAX_GRADE_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Semester.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + SEMESTER_DESC_BOB
                + INVALID_EXPECTED_MIN_GRADE_DESC + EXPECTED_MAX_GRADE_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Grade.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + SEMESTER_DESC_BOB
                + EXPECTED_MIN_GRADE_DESC_BOB + INVALID_EXPECTED_MAX_GRADE_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Grade.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + SEMESTER_DESC_BOB
                + EXPECTED_MIN_GRADE_DESC_BOB + EXPECTED_MAX_GRADE_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + SEMESTER_DESC_BOB
                        + EXPECTED_MIN_GRADE_DESC_BOB + INVALID_EXPECTED_MAX_GRADE_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB
                        + SEMESTER_DESC_BOB + EXPECTED_MIN_GRADE_DESC_BOB
                + EXPECTED_MAX_GRADE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}

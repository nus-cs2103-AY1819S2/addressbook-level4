package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EXPECTED_MAX_GRADE_DESC_CS1010;
import static seedu.address.logic.commands.CommandTestUtil.EXPECTED_MAX_GRADE_DESC_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.EXPECTED_MIN_GRADE_DESC_CS1010;
import static seedu.address.logic.commands.CommandTestUtil.EXPECTED_MIN_GRADE_DESC_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CS2103T_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EXPECTED_MAX_GRADE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EXPECTED_MIN_GRADE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SEMESTER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_CS1010;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.SEMESTER_DESC_CS1010;
import static seedu.address.logic.commands.CommandTestUtil.SEMESTER_DESC_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPECTED_MAX_GRADE_CS1010;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPECTED_MIN_GRADE_CS1010;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_INFO_CODE_CS1010;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEMESTER_CS1010;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalModuleTaken.DEFAULT_MODULE_CS1010;
import static seedu.address.testutil.TypicalModuleTaken.DEFAULT_MODULE_CS2103T;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.moduleinfo.ModuleInfoCode;
import seedu.address.model.person.Grade;
import seedu.address.model.person.ModuleTaken;
import seedu.address.model.person.Semester;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.ModuleTakenBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        ModuleTaken expectedModuleTaken = new ModuleTakenBuilder(DEFAULT_MODULE_CS1010)
                .withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_CS1010
                + SEMESTER_DESC_CS1010 + EXPECTED_MIN_GRADE_DESC_CS1010
                + EXPECTED_MAX_GRADE_DESC_CS1010 + TAG_DESC_FRIEND, new AddCommand(expectedModuleTaken));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_CS2103T + NAME_DESC_CS1010
                + SEMESTER_DESC_CS1010 + EXPECTED_MIN_GRADE_DESC_CS1010
                + EXPECTED_MAX_GRADE_DESC_CS1010 + TAG_DESC_FRIEND, new AddCommand(expectedModuleTaken));

        // multiple semesters - last semester accepted
        assertParseSuccess(parser, NAME_DESC_CS1010 + SEMESTER_DESC_CS2103T
                + SEMESTER_DESC_CS1010 + EXPECTED_MIN_GRADE_DESC_CS1010
                + EXPECTED_MAX_GRADE_DESC_CS1010 + TAG_DESC_FRIEND, new AddCommand(expectedModuleTaken));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_CS1010 + SEMESTER_DESC_CS1010
                + EXPECTED_MIN_GRADE_DESC_CS2103T + EXPECTED_MIN_GRADE_DESC_CS1010
                + EXPECTED_MAX_GRADE_DESC_CS1010 + TAG_DESC_FRIEND, new AddCommand(expectedModuleTaken));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, NAME_DESC_CS1010 + SEMESTER_DESC_CS1010
                + EXPECTED_MIN_GRADE_DESC_CS1010 + EXPECTED_MAX_GRADE_DESC_CS2103T
                + EXPECTED_MAX_GRADE_DESC_CS1010 + TAG_DESC_FRIEND, new AddCommand(expectedModuleTaken));

        // multiple tags - all accepted
        ModuleTaken expectedModuleTakenMultipleTags = new ModuleTakenBuilder(DEFAULT_MODULE_CS1010)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();
        assertParseSuccess(parser, NAME_DESC_CS1010 + SEMESTER_DESC_CS1010
                + EXPECTED_MIN_GRADE_DESC_CS1010 + EXPECTED_MAX_GRADE_DESC_CS1010
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, new AddCommand(expectedModuleTakenMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        ModuleTaken expectedModuleTaken = new ModuleTakenBuilder(DEFAULT_MODULE_CS2103T).withTags().build();
        assertParseSuccess(parser, NAME_DESC_CS2103T + SEMESTER_DESC_CS2103T
                        + EXPECTED_MIN_GRADE_DESC_CS2103T + EXPECTED_MAX_GRADE_DESC_CS2103T,
                new AddCommand(expectedModuleTaken));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_MODULE_INFO_CODE_CS1010 + SEMESTER_DESC_CS1010
                        + EXPECTED_MIN_GRADE_DESC_CS1010 + EXPECTED_MAX_GRADE_DESC_CS1010,
                expectedMessage);

        // missing semester prefix
        assertParseFailure(parser, NAME_DESC_CS1010 + VALID_SEMESTER_CS1010
                        + EXPECTED_MIN_GRADE_DESC_CS1010 + EXPECTED_MAX_GRADE_DESC_CS1010,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_MODULE_INFO_CODE_CS1010 + VALID_SEMESTER_CS1010
                        + VALID_EXPECTED_MIN_GRADE_CS1010 + VALID_EXPECTED_MAX_GRADE_CS1010,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid module info code
        assertParseFailure(parser, INVALID_CS2103T_DESC + SEMESTER_DESC_CS1010
                + EXPECTED_MIN_GRADE_DESC_CS1010 + EXPECTED_MAX_GRADE_DESC_CS1010
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, ModuleInfoCode.MESSAGE_CONSTRAINTS);

        // invalid semester
        assertParseFailure(parser, NAME_DESC_CS1010 + INVALID_SEMESTER_DESC
                + EXPECTED_MIN_GRADE_DESC_CS1010 + EXPECTED_MAX_GRADE_DESC_CS1010
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Semester.MESSAGE_CONSTRAINTS);

        // invalid min grade
        assertParseFailure(parser, NAME_DESC_CS1010 + SEMESTER_DESC_CS1010
                + INVALID_EXPECTED_MIN_GRADE_DESC + EXPECTED_MAX_GRADE_DESC_CS1010
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Grade.MESSAGE_CONSTRAINTS);

        // invalid max grade
        assertParseFailure(parser, NAME_DESC_CS1010 + SEMESTER_DESC_CS1010
                + EXPECTED_MIN_GRADE_DESC_CS1010 + INVALID_EXPECTED_MAX_GRADE_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Grade.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_CS1010 + SEMESTER_DESC_CS1010
                + EXPECTED_MIN_GRADE_DESC_CS1010 + EXPECTED_MAX_GRADE_DESC_CS1010
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_CS2103T_DESC + SEMESTER_DESC_CS1010
                        + EXPECTED_MIN_GRADE_DESC_CS1010 + INVALID_EXPECTED_MAX_GRADE_DESC,
                ModuleInfoCode.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_CS1010
                        + SEMESTER_DESC_CS1010 + EXPECTED_MIN_GRADE_DESC_CS1010
                + EXPECTED_MAX_GRADE_DESC_CS1010 + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}

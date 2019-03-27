package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.FINISHED_STATUS_DESC_TRUE;
import static seedu.address.logic.commands.CommandTestUtil.GRADE_DESC_CS1010;
import static seedu.address.logic.commands.CommandTestUtil.GRADE_DESC_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GRADE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SEMESTER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.SEMESTER_DESC_CS1010;
import static seedu.address.logic.commands.CommandTestUtil.SEMESTER_DESC_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_CS1010;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_INFO_CODE_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEMESTER_CS1010;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEMESTER_CS2103T;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.FINISHED_STATUS_TRUE;

import org.junit.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindCommand.FindModuleDescriptor;
import seedu.address.model.moduletaken.Grade;
import seedu.address.model.moduletaken.Semester;
import seedu.address.testutil.FindModuleDescriptorBuilder;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_nonEmptyPreamble_failure() {
        assertParseFailure(parser, "1", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "cs", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, INVALID_SEMESTER_DESC, Semester.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, INVALID_GRADE_DESC, Grade.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = NAME_DESC_CS2103T + SEMESTER_DESC_CS2103T + GRADE_DESC_CS2103T + FINISHED_STATUS_DESC_TRUE;

        FindModuleDescriptor fd = new FindModuleDescriptorBuilder().withCode(VALID_MODULE_INFO_CODE_CS2103T)
                .withSemester(VALID_SEMESTER_CS2103T).withGrade(VALID_GRADE_CS2103T)
                .withFinishedStatus(FINISHED_STATUS_TRUE).build();
        FindCommand expectedCommand = new FindCommand(fd);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        String userInput = NAME_DESC_CS2103T + GRADE_DESC_CS2103T;

        FindModuleDescriptor fd = new FindModuleDescriptorBuilder().withCode(VALID_MODULE_INFO_CODE_CS2103T)
                .withGrade(VALID_GRADE_CS2103T).build();
        FindCommand expectedCommand = new FindCommand(fd);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // subcode
        FindModuleDescriptor fd = new FindModuleDescriptorBuilder().withCode(VALID_MODULE_INFO_CODE_CS2103T).build();
        FindCommand expectedCommand = new FindCommand(fd);
        assertParseSuccess(parser, NAME_DESC_CS2103T, expectedCommand);

        // semester
        fd = new FindModuleDescriptorBuilder().withSemester(VALID_SEMESTER_CS2103T).build();
        expectedCommand = new FindCommand(fd);
        assertParseSuccess(parser, SEMESTER_DESC_CS2103T, expectedCommand);

        // grade
        fd = new FindModuleDescriptorBuilder().withGrade(VALID_GRADE_CS2103T).build();
        expectedCommand = new FindCommand(fd);
        assertParseSuccess(parser, GRADE_DESC_CS2103T, expectedCommand);

        // finished status
        fd = new FindModuleDescriptorBuilder().withFinishedStatus(FINISHED_STATUS_TRUE).build();
        expectedCommand = new FindCommand(fd);
        assertParseSuccess(parser, FINISHED_STATUS_DESC_TRUE, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        String userInput = SEMESTER_DESC_CS2103T + GRADE_DESC_CS2103T + SEMESTER_DESC_CS1010 + GRADE_DESC_CS1010;

        FindModuleDescriptor fd = new FindModuleDescriptorBuilder().withSemester(VALID_SEMESTER_CS1010)
                .withGrade(VALID_GRADE_CS1010).build();
        FindCommand expectedCommand = new FindCommand(fd);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        String userInput = INVALID_SEMESTER_DESC + SEMESTER_DESC_CS2103T;
        FindModuleDescriptor fd = new FindModuleDescriptorBuilder().withSemester(VALID_SEMESTER_CS2103T).build();
        FindCommand expectedCommand = new FindCommand(fd);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = INVALID_SEMESTER_DESC + GRADE_DESC_CS2103T + SEMESTER_DESC_CS2103T;
        fd = new FindModuleDescriptorBuilder().withSemester(VALID_SEMESTER_CS2103T)
                .withGrade(VALID_GRADE_CS2103T).build();
        expectedCommand = new FindCommand(fd);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}

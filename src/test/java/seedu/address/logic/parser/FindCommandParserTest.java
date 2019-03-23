package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.FINISHED_STATUS_DESC_TRUE;
import static seedu.address.logic.commands.CommandTestUtil.GRADE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.GRADE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GRADE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SEMESTER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.SEMESTER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.SEMESTER_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEMESTER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEMESTER_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.FINISHED_STATUS_TRUE;

import org.junit.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindCommand.FindModuleDescriptor;
import seedu.address.model.person.FindModulePredicate;
import seedu.address.model.person.Grade;
import seedu.address.model.person.Semester;
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
        String userInput = NAME_DESC_AMY + SEMESTER_DESC_AMY + GRADE_DESC_AMY + FINISHED_STATUS_DESC_TRUE;

        FindModuleDescriptor fd = new FindModuleDescriptorBuilder().withCode(VALID_NAME_AMY)
                .withSemester(VALID_SEMESTER_AMY).withGrade(VALID_GRADE_AMY)
                .withFinishedStatus(FINISHED_STATUS_TRUE).build();
        FindCommand expectedCommand = new FindCommand(new FindModulePredicate(fd));

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        String userInput = NAME_DESC_AMY + GRADE_DESC_AMY;

        FindModuleDescriptor fd = new FindModuleDescriptorBuilder().withCode(VALID_NAME_AMY)
                .withGrade(VALID_GRADE_AMY).build();
        FindCommand expectedCommand = new FindCommand(new FindModulePredicate(fd));

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // subcode
        FindModuleDescriptor fd = new FindModuleDescriptorBuilder().withCode(VALID_NAME_AMY).build();
        FindCommand expectedCommand = new FindCommand(new FindModulePredicate(fd));
        assertParseSuccess(parser, NAME_DESC_AMY, expectedCommand);

        // semester
        fd = new FindModuleDescriptorBuilder().withSemester(VALID_SEMESTER_AMY).build();
        expectedCommand = new FindCommand(new FindModulePredicate(fd));
        assertParseSuccess(parser, SEMESTER_DESC_AMY, expectedCommand);

        // grade
        fd = new FindModuleDescriptorBuilder().withGrade(VALID_GRADE_AMY).build();
        expectedCommand = new FindCommand(new FindModulePredicate(fd));
        assertParseSuccess(parser, GRADE_DESC_AMY, expectedCommand);

        // finished status
        fd = new FindModuleDescriptorBuilder().withFinishedStatus(FINISHED_STATUS_TRUE).build();
        expectedCommand = new FindCommand(new FindModulePredicate(fd));
        assertParseSuccess(parser, FINISHED_STATUS_DESC_TRUE, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        String userInput = SEMESTER_DESC_AMY + GRADE_DESC_AMY + SEMESTER_DESC_BOB + GRADE_DESC_BOB;

        FindModuleDescriptor fd = new FindModuleDescriptorBuilder().withSemester(VALID_SEMESTER_BOB)
                .withGrade(VALID_GRADE_BOB).build();
        FindCommand expectedCommand = new FindCommand(new FindModulePredicate(fd));

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        String userInput = INVALID_SEMESTER_DESC + SEMESTER_DESC_AMY;
        FindModuleDescriptor fd = new FindModuleDescriptorBuilder().withSemester(VALID_SEMESTER_AMY).build();
        FindCommand expectedCommand = new FindCommand(new FindModulePredicate(fd));
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = INVALID_SEMESTER_DESC + GRADE_DESC_AMY + SEMESTER_DESC_AMY;
        fd = new FindModuleDescriptorBuilder().withSemester(VALID_SEMESTER_AMY).withGrade(VALID_GRADE_AMY).build();
        expectedCommand = new FindCommand(new FindModulePredicate(fd));
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}

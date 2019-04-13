package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_JOB_NAME_TEACHER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MovePersonCommand;
import seedu.address.model.job.JobListName;
import seedu.address.model.job.JobName;


/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class MovePersonCommandParserTest {

    private MovePersonCommandParser parser = new MovePersonCommandParser();

    @Test
    public void parse_allArgs1Index_returnsMovePersonCommand() {
        ArrayList<Index> indexes = new ArrayList<>();
        indexes.add(INDEX_FIRST_PERSON);
        assertParseSuccess(parser, " k a 1 jn/Teacher",
                new MovePersonCommand(JobListName.KIV, JobListName.APPLICANT,
                        indexes, new JobName(VALID_JOB_NAME_TEACHER)));
    }

    @Test
    public void parse_allArgs2Indexes_returnsMovePersonCommand() {
        ArrayList<Index> indexes = new ArrayList<>();
        indexes.add(INDEX_FIRST_PERSON);
        indexes.add(INDEX_SECOND_PERSON);
        assertParseSuccess(parser, " k a 1, 2 jn/Teacher",
                new MovePersonCommand(JobListName.KIV, JobListName.APPLICANT,
                        indexes, new JobName(VALID_JOB_NAME_TEACHER)));
    }

    @Test
    public void parse_argsNoJob_returnsMovePersonCommand() {
        ArrayList<Index> indexes = new ArrayList<>();
        indexes.add(INDEX_FIRST_PERSON);
        indexes.add(INDEX_SECOND_PERSON);
        assertParseSuccess(parser, " k a 1, 2",
                new MovePersonCommand(JobListName.KIV, JobListName.APPLICANT,
                        indexes, null));
    }

    @Test
    public void parse_noArgs_throwsParseException() {
        assertParseFailure(parser, " ",
                MovePersonCommand.MESSAGE_NO_DESTINATION
                        + String.format(MESSAGE_INVALID_COMMAND_FORMAT, MovePersonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingArgs_throwsParseException() {
        assertParseFailure(parser, " a",
                MovePersonCommand.MESSAGE_NO_SOURCE
                        + String.format(MESSAGE_INVALID_COMMAND_FORMAT, MovePersonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingArgsIndex_throwsParseException() {
        assertParseFailure(parser, " k a",
                MovePersonCommand.MESSAGE_NO_INDEX
                        + String.format(MESSAGE_INVALID_COMMAND_FORMAT, MovePersonCommand.MESSAGE_USAGE));
    }
}

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.AddAppCommandParser.PREFIX_COMMENT;
import static seedu.address.logic.parser.AddAppCommandParser.PREFIX_DATE;
import static seedu.address.logic.parser.AddAppCommandParser.PREFIX_END;
import static seedu.address.logic.parser.AddAppCommandParser.PREFIX_INDEX;
import static seedu.address.logic.parser.AddAppCommandParser.PREFIX_START;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.AddAppCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class AddAppCommandParserTest {
    private AddAppCommandParser parser = new AddAppCommandParser();
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    private String dateString = "2019-10-23";
    private String startString = "16:00";
    private String endString = "17:00";

    private LocalDate date = LocalDate.parse(dateString);
    private LocalTime start = LocalTime.parse(startString);
    private LocalTime end = LocalTime.parse(endString);

    private String comment = "This is a comment";

    @Test
    public void parse_allFieldsPresent_success() {
        // whitespace only preamble
        System.out.println(PREFIX_INDEX + "" + INDEX_FIRST_PERSON.getOneBased() + " "
                + PREFIX_DATE + dateString + " "
                + PREFIX_START + startString + " "
                + PREFIX_END + endString + " "
                + PREFIX_COMMENT + comment);
        assertParseSuccess(parser,
                PREFIX_INDEX + "" + INDEX_FIRST_PERSON.getOneBased() + " "
                        + PREFIX_DATE + dateString + " "
                        + PREFIX_START + startString + " "
                        + PREFIX_END + endString + " "
                        + PREFIX_COMMENT + comment + "\n",
                new AddAppCommand(INDEX_SECOND_PERSON, date, start, end, comment));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAppCommand.MESSAGE_USAGE);

        // missing index prefix
        assertParseFailure(parser,
                INDEX_FIRST_PERSON + " "
                        + PREFIX_DATE + dateString + " "
                        + PREFIX_START + startString + " "
                        + PREFIX_END + endString + " "
                        + PREFIX_COMMENT + comment,
                expectedMessage);

        // missing date prefix
        assertParseFailure(parser,
                PREFIX_INDEX + "" + INDEX_FIRST_PERSON + " "
                        + dateString + " "
                        + PREFIX_START + startString + " "
                        + PREFIX_END + endString + " "
                        + PREFIX_COMMENT + comment,
                expectedMessage);

        // missing start prefix
        assertParseFailure(parser,
                PREFIX_INDEX + "" + INDEX_FIRST_PERSON + " "
                        + PREFIX_DATE + dateString + " "
                        + startString + " "
                        + PREFIX_END + endString + " "
                        + PREFIX_COMMENT + comment,
                expectedMessage);

        // missing end prefix
        assertParseFailure(parser,
                PREFIX_INDEX + "" + INDEX_FIRST_PERSON + " "
                        + PREFIX_DATE + dateString + " "
                        + PREFIX_START + startString + " "
                        + endString + " "
                        + PREFIX_COMMENT + comment,
                expectedMessage);

        // missing comment prefix
        assertParseFailure(parser,
                PREFIX_INDEX + "" + INDEX_FIRST_PERSON + " "
                        + PREFIX_DATE + dateString + " "
                        + PREFIX_START + startString + " "
                        + PREFIX_END + endString + " "
                        + comment,
                expectedMessage);

        // all prefix missing
        assertParseFailure(parser,
                INDEX_FIRST_PERSON + " "
                        + dateString + " "
                        + startString + " "
                        + endString + " "
                        + comment,
                expectedMessage);
    }
}

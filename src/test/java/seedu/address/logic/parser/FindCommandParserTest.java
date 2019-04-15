package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BATCHNUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.medicine.predicates.BatchContainsKeywordsPredicate;
import seedu.address.model.medicine.predicates.CompanyContainsKeywordsPredicate;
import seedu.address.model.medicine.predicates.NameContainsKeywordsPredicate;
import seedu.address.model.medicine.predicates.TagContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_hasPreamble_throwsParseException() {
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + PREFIX_NAME + "keyword" ,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multiplePrefixes_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_NAME + "Paracetamol " + PREFIX_COMPANY + " 3M",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_noKeywords_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_NAME, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Paracetamol", "Gabapentin")));
        assertParseSuccess(parser, " " + PREFIX_NAME + " Paracetamol Gabapentin", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " " + PREFIX_NAME + " \n Paracetamol \n \t Gabapentin  \t", expectedFindCommand);

        // find company
        expectedFindCommand = new FindCommand(new CompanyContainsKeywordsPredicate(Arrays.asList("3M", "Johnson")));
        assertParseSuccess(parser, " " + PREFIX_COMPANY + " 3M Johnson", expectedFindCommand);

        // find tag
        expectedFindCommand = new FindCommand(new TagContainsKeywordsPredicate(Arrays.asList("fever", "painkiller")));
        assertParseSuccess(parser, " " + PREFIX_TAG + " fever painkiller", expectedFindCommand);

        // find batch
        expectedFindCommand = new FindCommand(new BatchContainsKeywordsPredicate(Arrays.asList("CD485", "CD486")));
        assertParseSuccess(parser, " " + PREFIX_BATCHNUMBER + " CD485 CD486", expectedFindCommand);
    }
}

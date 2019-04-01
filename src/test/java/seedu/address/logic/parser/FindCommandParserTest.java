package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.util.predicate.AddressContainsKeywordsPredicate;
import seedu.address.model.util.predicate.ContainsKeywordsPredicate;
import seedu.address.model.util.predicate.DateOfBirthContainsKeywordsPredicate;
import seedu.address.model.util.predicate.EmailContainsKeywordsPredicate;
import seedu.address.model.util.predicate.MultipleContainsKeywordsPredicate;
import seedu.address.model.util.predicate.NameContainsKeywordsPredicate;
import seedu.address.model.util.predicate.NricContainsKeywordsPredicate;
import seedu.address.model.util.predicate.PhoneContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validNameArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        MultipleContainsKeywordsPredicate tempPred = new MultipleContainsKeywordsPredicate(Collections.emptyList(),
            true, false);
        List<ContainsKeywordsPredicate> predicateList = new ArrayList<>();
        predicateList.add(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        tempPred.setPredicateList(predicateList);

        FindCommand expectedFindCommand = new FindCommand(tempPred);
        assertParseSuccess(parser, " n/Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " n/ \n Alice \n \t Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_validPhoneArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        MultipleContainsKeywordsPredicate tempPred = new MultipleContainsKeywordsPredicate(Collections.emptyList(),
            true, false);
        List<ContainsKeywordsPredicate> predicateList = new ArrayList<>();
        predicateList.add(new PhoneContainsKeywordsPredicate(Arrays.asList("98765432", "88884444")));
        tempPred.setPredicateList(predicateList);

        FindCommand expectedFindCommand = new FindCommand(tempPred);
        assertParseSuccess(parser, " p/98765432 88884444", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " p/ \n 98765432 \n \t 88884444  \t", expectedFindCommand);
    }

    @Test
    public void parse_validAddressArgs_returnsFindCommand() {
        MultipleContainsKeywordsPredicate tempPred = new MultipleContainsKeywordsPredicate(Collections.emptyList(),
            true, false);
        List<ContainsKeywordsPredicate> predicateList = new ArrayList<>();
        predicateList.add(new AddressContainsKeywordsPredicate(Arrays.asList("street", "ave")));
        tempPred.setPredicateList(predicateList);

        // no leading and trailing whitespaces
        FindCommand expectedFindCommand = new FindCommand(tempPred);
        assertParseSuccess(parser, " a/street ave", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " a/ \n street \n \t ave  \t", expectedFindCommand);
    }

    @Test
    public void parse_validEmailArgs_returnsFindCommand() {
        MultipleContainsKeywordsPredicate tempPred = new MultipleContainsKeywordsPredicate(Collections.emptyList(),
            true, false);
        List<ContainsKeywordsPredicate> predicateList = new ArrayList<>();
        predicateList.add(new EmailContainsKeywordsPredicate(Arrays.asList("test@sample.com", "sample@test.com")));
        tempPred.setPredicateList(predicateList);

        // no leading and trailing whitespaces
        FindCommand expectedFindCommand = new FindCommand(tempPred);
        assertParseSuccess(parser, " e/test@sample.com sample@test.com", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " e/ \n test@sample.com \n \t sample@test.com  \t", expectedFindCommand);
    }

    @Test
    public void parse_validDateOfBirthArgs_returnsFindCommand() {
        MultipleContainsKeywordsPredicate tempPred = new MultipleContainsKeywordsPredicate(Collections.emptyList(),
            true, false);
        List<ContainsKeywordsPredicate> predicateList = new ArrayList<>();
        predicateList.add(new DateOfBirthContainsKeywordsPredicate(Arrays.asList("1990", "May")));
        tempPred.setPredicateList(predicateList);

        // no leading and trailing whitespaces
        FindCommand expectedFindCommand = new FindCommand(tempPred);
        assertParseSuccess(parser, " dob/1990 May", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " dob/ \n 1990 \n \t May \t", expectedFindCommand);
    }

    @Test
    public void parse_validNricArgs_returnsFindCommand() {
        MultipleContainsKeywordsPredicate tempPred = new MultipleContainsKeywordsPredicate(Collections.emptyList(),
            true, false);
        List<ContainsKeywordsPredicate> predicateList = new ArrayList<>();
        predicateList.add(new NricContainsKeywordsPredicate(Arrays.asList("S1234567H", "S7654321D")));
        tempPred.setPredicateList(predicateList);

        // no leading and trailing whitespaces
        FindCommand expectedFindCommand = new FindCommand(tempPred);
        assertParseSuccess(parser, " ic/S1234567H S7654321D", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " ic/ \n S1234567H \n \t S7654321D \t", expectedFindCommand);
    }

    @Test
    public void parse_multipleArgs_returnsFindCommand() {
        MultipleContainsKeywordsPredicate tempPred = new MultipleContainsKeywordsPredicate(Collections.emptyList(),
            true, false);
        List<ContainsKeywordsPredicate> predicateList = new ArrayList<>();
        predicateList.add(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "George")));
        predicateList.add(new DateOfBirthContainsKeywordsPredicate(Arrays.asList("December", "11")));
        tempPred.setPredicateList(predicateList);

        // no leading and trailing whitespaces
        FindCommand expectedFindCommand = new FindCommand(tempPred);
        assertParseSuccess(parser, " n/Alice George dob/December 11", expectedFindCommand);
        assertParseSuccess(parser, " n/ \n Alice \n \t George \n \t dob/December 11 \t", expectedFindCommand);
    }
    //TODO: Add in tests for the other Attribute predicates
}

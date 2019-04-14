package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_EMPTY_KEYWORD;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.commands.PatientFindCommand;
import seedu.address.model.util.predicate.AddressContainsKeywordsPredicate;
import seedu.address.model.util.predicate.ContainsKeywordsPredicate;
import seedu.address.model.util.predicate.DateOfBirthContainsKeywordsPredicate;
import seedu.address.model.util.predicate.DrugAllergyContainsKeywordsPredicate;
import seedu.address.model.util.predicate.EmailContainsKeywordsPredicate;
import seedu.address.model.util.predicate.KinAddressContainsKeywordsPredicate;
import seedu.address.model.util.predicate.KinNameContainsKeywordsPredicate;
import seedu.address.model.util.predicate.KinPhoneContainsKeywordsPredicate;
import seedu.address.model.util.predicate.KinRelationContainsKeywordsPredicate;
import seedu.address.model.util.predicate.MultipleContainsKeywordsPredicate;
import seedu.address.model.util.predicate.NameContainsKeywordsPredicate;
import seedu.address.model.util.predicate.NricContainsKeywordsPredicate;
import seedu.address.model.util.predicate.PhoneContainsKeywordsPredicate;
import seedu.address.model.util.predicate.SexContainsKeywordsPredicate;
import seedu.address.model.util.predicate.TagsContainsKeywordsPredicate;

public class PatientFindCommandParserTest {

    private PatientFindCommandParser parser = new PatientFindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            PatientFindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validNameArgs_returnsPatientFindCommand() {
        // no leading and trailing whitespaces
        MultipleContainsKeywordsPredicate tempPred = new MultipleContainsKeywordsPredicate(Collections.emptyList(),
            true, false);
        List<ContainsKeywordsPredicate> predicateList = new ArrayList<>();
        predicateList.add(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        tempPred.setPredicateList(predicateList);

        PatientFindCommand expectedPatientFindCommand = new PatientFindCommand(tempPred);
        assertParseSuccess(parser, " n/Alice Bob", expectedPatientFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " n/ \n Alice \n \t Bob  \t", expectedPatientFindCommand);
    }

    @Test
    public void parseCaseSensitive_validNameArgs_returnsPatientFindCommand() {
        // no leading and trailing whitespaces
        MultipleContainsKeywordsPredicate tempPred = new MultipleContainsKeywordsPredicate(Collections.emptyList(),
            false, false);
        List<ContainsKeywordsPredicate> predicateList = new ArrayList<>();
        predicateList.add(new NameContainsKeywordsPredicate(Arrays.asList("alice", "Bob")));
        tempPred.setPredicateList(predicateList);

        PatientFindCommand expectedPatientFindCommand = new PatientFindCommand(tempPred);
        assertParseSuccess(parser, "CS n/alice Bob", expectedPatientFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "CS n/ \n alice \n \t Bob  \t", expectedPatientFindCommand);
    }

    @Test
    public void parseAndOperator_validNameArgs_returnsPatientFindCommand() {
        // no leading and trailing whitespaces
        MultipleContainsKeywordsPredicate tempPred = new MultipleContainsKeywordsPredicate(Collections.emptyList(),
            true, true);
        List<ContainsKeywordsPredicate> predicateList = new ArrayList<>();
        predicateList.add(new NameContainsKeywordsPredicate(Arrays.asList("alice", "Bob")));
        tempPred.setPredicateList(predicateList);

        PatientFindCommand expectedPatientFindCommand = new PatientFindCommand(tempPred);
        assertParseSuccess(parser, "AND n/alice Bob", expectedPatientFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "AND n/ \n alice \n \t Bob  \t", expectedPatientFindCommand);
    }

    @Test
    public void parseAndCaseSensitive_validNameArgs_returnsPatientFindCommand() {
        // no leading and trailing whitespaces
        MultipleContainsKeywordsPredicate tempPred = new MultipleContainsKeywordsPredicate(Collections.emptyList(),
            false, true);
        List<ContainsKeywordsPredicate> predicateList = new ArrayList<>();
        predicateList.add(new NameContainsKeywordsPredicate(Arrays.asList("alice", "Bob")));
        tempPred.setPredicateList(predicateList);

        PatientFindCommand expectedPatientFindCommand = new PatientFindCommand(tempPred);
        assertParseSuccess(parser, "CS AND n/alice Bob", expectedPatientFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "AND CS n/ \n alice \n \t Bob  \t", expectedPatientFindCommand);
    }

    @Test
    public void parseFailure_validNameArgs_throwsParseException() {
        assertParseFailure(parser, "CS ASDASDSAFD n/alice Bob", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            PatientFindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseFailure_emptyKeyword_throwsParseException() {
        assertParseFailure(parser, "CS n/", MESSAGE_EMPTY_KEYWORD);
    }

    @Test
    public void parse_validPhoneArgs_returnsPatientFindCommand() {
        // no leading and trailing whitespaces
        MultipleContainsKeywordsPredicate tempPred = new MultipleContainsKeywordsPredicate(Collections.emptyList(),
            true, false);
        List<ContainsKeywordsPredicate> predicateList = new ArrayList<>();
        predicateList.add(new PhoneContainsKeywordsPredicate(Arrays.asList("98765432", "88884444")));
        tempPred.setPredicateList(predicateList);

        PatientFindCommand expectedPatientFindCommand = new PatientFindCommand(tempPred);
        assertParseSuccess(parser, " p/98765432 88884444", expectedPatientFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " p/ \n 98765432 \n \t 88884444  \t", expectedPatientFindCommand);
    }

    @Test
    public void parse_validAddressArgs_returnsPatientFindCommand() {
        MultipleContainsKeywordsPredicate tempPred = new MultipleContainsKeywordsPredicate(Collections.emptyList(),
            true, false);
        List<ContainsKeywordsPredicate> predicateList = new ArrayList<>();
        predicateList.add(new AddressContainsKeywordsPredicate(Arrays.asList("street", "ave")));
        tempPred.setPredicateList(predicateList);

        // no leading and trailing whitespaces
        PatientFindCommand expectedPatientFindCommand = new PatientFindCommand(tempPred);
        assertParseSuccess(parser, " a/street ave", expectedPatientFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " a/ \n street \n \t ave  \t", expectedPatientFindCommand);
    }

    @Test
    public void parse_validEmailArgs_returnsPatientFindCommand() {
        MultipleContainsKeywordsPredicate tempPred = new MultipleContainsKeywordsPredicate(Collections.emptyList(),
            true, false);
        List<ContainsKeywordsPredicate> predicateList = new ArrayList<>();
        predicateList.add(new EmailContainsKeywordsPredicate(Arrays.asList("test@sample.com", "sample@test.com")));
        tempPred.setPredicateList(predicateList);

        // no leading and trailing whitespaces
        PatientFindCommand expectedPatientFindCommand = new PatientFindCommand(tempPred);
        assertParseSuccess(parser, " e/test@sample.com sample@test.com", expectedPatientFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " e/ \n test@sample.com \n \t sample@test.com  \t", expectedPatientFindCommand);
    }

    @Test
    public void parse_validDateOfBirthArgs_returnsPatientFindCommand() {
        MultipleContainsKeywordsPredicate tempPred = new MultipleContainsKeywordsPredicate(Collections.emptyList(),
            true, false);
        List<ContainsKeywordsPredicate> predicateList = new ArrayList<>();
        predicateList.add(new DateOfBirthContainsKeywordsPredicate(Arrays.asList("1990", "May")));
        tempPred.setPredicateList(predicateList);

        // no leading and trailing whitespaces
        PatientFindCommand expectedPatientFindCommand = new PatientFindCommand(tempPred);
        assertParseSuccess(parser, " dob/1990 May", expectedPatientFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " dob/ \n 1990 \n \t May \t", expectedPatientFindCommand);
    }

    @Test
    public void parse_validNricArgs_returnsPatientFindCommand() {
        MultipleContainsKeywordsPredicate tempPred = new MultipleContainsKeywordsPredicate(Collections.emptyList(),
            true, false);
        List<ContainsKeywordsPredicate> predicateList = new ArrayList<>();
        predicateList.add(new NricContainsKeywordsPredicate(Arrays.asList("S1234567H", "S7654321D")));
        tempPred.setPredicateList(predicateList);

        // no leading and trailing whitespaces
        PatientFindCommand expectedPatientFindCommand = new PatientFindCommand(tempPred);
        assertParseSuccess(parser, " ic/S1234567H S7654321D", expectedPatientFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " ic/ \n S1234567H \n \t S7654321D \t", expectedPatientFindCommand);
    }

    @Test
    public void parse_multipleArgs_returnsPatientFindCommand() {
        MultipleContainsKeywordsPredicate tempPred = new MultipleContainsKeywordsPredicate(Collections.emptyList(),
            true, false);
        List<ContainsKeywordsPredicate> predicateList = new ArrayList<>();
        predicateList.add(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "George")));
        predicateList.add(new PhoneContainsKeywordsPredicate(Arrays.asList("987", "999")));
        predicateList.add(new DateOfBirthContainsKeywordsPredicate(Arrays.asList("December", "11")));
        tempPred.setPredicateList(predicateList);

        // no leading and trailing whitespaces
        PatientFindCommand expectedPatientFindCommand = new PatientFindCommand(tempPred);
        assertParseSuccess(parser, " n/Alice George dob/December 11 p/987 999", expectedPatientFindCommand);
        assertParseSuccess(parser, " n/ \n Alice \n \t George \n \t dob/December 11 \t p/987 999",
                            expectedPatientFindCommand);
    }

    @Test
    public void parse_multipleArgsTwo_returnsPatientFindCommand() {
        MultipleContainsKeywordsPredicate tempPred = new MultipleContainsKeywordsPredicate(Collections.emptyList(),
            true, false);
        List<ContainsKeywordsPredicate> predicateList = new ArrayList<>();
        predicateList.add(new SexContainsKeywordsPredicate(Arrays.asList("M")));
        predicateList.add(new DrugAllergyContainsKeywordsPredicate(Arrays.asList("NIL")));
        predicateList.add(new KinNameContainsKeywordsPredicate(Arrays.asList("Alex")));
        predicateList.add(new KinRelationContainsKeywordsPredicate(Arrays.asList("Mother", "Father")));
        predicateList.add(new KinPhoneContainsKeywordsPredicate(Arrays.asList("984", "999")));
        predicateList.add(new KinAddressContainsKeywordsPredicate(Arrays.asList("Street", "4")));
        predicateList.add(new TagsContainsKeywordsPredicate(Arrays.asList("Healthy", "Teeth")));
        tempPred.setPredicateList(predicateList);

        // no leading and trailing whitespaces
        PatientFindCommand expectedPatientFindCommand = new PatientFindCommand(tempPred);

        assertParseSuccess(parser, " sex/M da/NIL nokr/Mother Father nokn/Alex nokp/984 999 noka/Street 4"
            + " tag/Healthy Teeth ", expectedPatientFindCommand);
        assertParseSuccess(parser, " sex/ \n M da/NIL nokr/Mother Father \t nokn/Alex nokp/984 999 \t "
            + "noka/Street 4 tag/Healthy Teeth", expectedPatientFindCommand);
    }
}

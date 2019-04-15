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

import seedu.address.logic.commands.RecordFindCommand;
import seedu.address.model.record.Record;
import seedu.address.model.util.predicate.ContainsKeywordsPredicate;
import seedu.address.model.util.predicate.DescriptionRecordContainsKeywordsPredicate;
import seedu.address.model.util.predicate.MultipleContainsKeywordsPredicate;
import seedu.address.model.util.predicate.ProcedureContainsKeywordsPredicate;


public class RecordFindCommandParserTest {

    private RecordFindCommandParser parser = new RecordFindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            RecordFindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validProcArgs_returnsPatientFindCommand() {
        MultipleContainsKeywordsPredicate<Record> tempPred =
            new MultipleContainsKeywordsPredicate<>(Collections.emptyList(), true, false);
        List<ContainsKeywordsPredicate> predicateList = new ArrayList<>();
        predicateList.add(new ProcedureContainsKeywordsPredicate(Arrays.asList("Other", "filling")));
        tempPred.setPredicateList(predicateList);

        RecordFindCommand expectedRecordFindCommand = new RecordFindCommand(tempPred);
        assertParseSuccess(parser, " pro/Other filling", expectedRecordFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " pro/ \n Other \t filling  \t", expectedRecordFindCommand);
    }

    @Test
    public void parseFailure_validNameArgs_throwsParseException() {
        assertParseFailure(parser, "CS ASDASDSAFD pro/Other filling", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            RecordFindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseFailure_emptyKeyword_throwsParseException() {
        assertParseFailure(parser, "CS pro/", MESSAGE_EMPTY_KEYWORD);
    }

    @Test
    public void parse_validDescArgs_returnsPatientFindCommand() {
        MultipleContainsKeywordsPredicate<Record> tempPred =
            new MultipleContainsKeywordsPredicate<>(Collections.emptyList(), true, false);
        List<ContainsKeywordsPredicate> predicateList = new ArrayList<>();
        predicateList.add(new DescriptionRecordContainsKeywordsPredicate(Arrays.asList("today", "patient")));
        tempPred.setPredicateList(predicateList);

        RecordFindCommand expectedRecordFindCommand = new RecordFindCommand(tempPred);
        assertParseSuccess(parser, " desc/today patient", expectedRecordFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " desc/ \n today \t patient  \t", expectedRecordFindCommand);
    }

    @Test
    public void parseAndOperator_validDescArgs_returnsPatientFindCommand() {
        MultipleContainsKeywordsPredicate<Record> tempPred =
            new MultipleContainsKeywordsPredicate<>(Collections.emptyList(), true, true);
        List<ContainsKeywordsPredicate> predicateList = new ArrayList<>();
        predicateList.add(new DescriptionRecordContainsKeywordsPredicate(Arrays.asList("today", "patient")));
        tempPred.setPredicateList(predicateList);

        RecordFindCommand expectedRecordFindCommand = new RecordFindCommand(tempPred);
        assertParseSuccess(parser, " AND desc/today patient", expectedRecordFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " AND desc/ \n today \t patient  \t", expectedRecordFindCommand);
    }

    @Test
    public void parseCaseSensitiveAndOperator_validDescArgs_returnsPatientFindCommand() {
        MultipleContainsKeywordsPredicate<Record> tempPred =
            new MultipleContainsKeywordsPredicate<>(Collections.emptyList(), false, true);
        List<ContainsKeywordsPredicate> predicateList = new ArrayList<>();
        predicateList.add(new DescriptionRecordContainsKeywordsPredicate(Arrays.asList("today", "patient")));
        tempPred.setPredicateList(predicateList);

        RecordFindCommand expectedRecordFindCommand = new RecordFindCommand(tempPred);
        assertParseSuccess(parser, " cs AND desc/today patient", expectedRecordFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " cs AND desc/ \n today \t patient  \t", expectedRecordFindCommand);
    }
}

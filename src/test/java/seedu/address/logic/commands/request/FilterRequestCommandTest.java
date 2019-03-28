package seedu.address.logic.commands.request;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_REQUEST_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_REQUESTS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONDITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.testutil.TypicalHealthWorkers.getTypicalHealthWorkerBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalRequests.ALICE_REQUEST;
import static seedu.address.testutil.TypicalRequests.BENSON_REQUEST;
import static seedu.address.testutil.TypicalRequests.CARL_REQUEST;
import static seedu.address.testutil.TypicalRequests.DANIEL_REQUEST;
import static seedu.address.testutil.TypicalRequests.getTypicalRequestBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.RequestPredicateUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.request.Request;
import seedu.address.model.request.RequestNameContainsKeywordPredicate;

/**
 * Contains integration tests (interaction with Model) for {@code FilterRequestCommand}
 */
public class FilterRequestCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalHealthWorkerBook(),
        getTypicalRequestBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(),
        getTypicalHealthWorkerBook(), getTypicalRequestBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    /**
     * Returns true if at least one of the prefixes contain non-empty {@code Optional} values in
     * the given {@code ArgumentMultiMap}
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap,
                                              Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    @Test
    public void equals() {
        Predicate<Request> firstPredicate = new RequestNameContainsKeywordPredicate("first");
        Predicate<Request> secondPredicate = new RequestNameContainsKeywordPredicate("second");

        FilterRequestCommand findFirstRequestCommand = new FilterRequestCommand(firstPredicate);
        FilterRequestCommand findSecondRequestCommand = new FilterRequestCommand(secondPredicate);

        assertEquals(findFirstRequestCommand, findFirstRequestCommand);

        FilterRequestCommand findFirstRequestCommandCopy = new FilterRequestCommand(firstPredicate);
        assertEquals(findFirstRequestCommand, findFirstRequestCommandCopy);

        assertNotEquals(findFirstRequestCommand, 1);

        assertNotEquals(findFirstRequestCommand, null);

        assertNotEquals(findFirstRequestCommand, findSecondRequestCommand);
    }

    @Test
    public void execute_singlePrefixSingleKeyword_oneOrderFound() throws ParseException {
        String expectedMessage = String.format(MESSAGE_REQUESTS_LISTED_OVERVIEW, 1);
        Predicate<Request> predicate = preparePredicate(" n/alice");
        FilterRequestCommand command = new FilterRequestCommand(predicate);
        expectedModel.updateFilteredRequestList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE_REQUEST), model.getFilteredRequestList());
    }

    @Test
    public void execute_singlePrefixSingleKeyword_singleRequestFound() throws ParseException {
        String expectedMessage = String.format(MESSAGE_REQUESTS_LISTED_OVERVIEW, 1);
        Predicate<Request> predicate = preparePredicate(" dt/02-01-2919 14:00:00");
        FilterRequestCommand command = new FilterRequestCommand(predicate);
        expectedModel.updateFilteredRequestList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL_REQUEST), model.getFilteredRequestList());
    }

    @Test
    public void execute_singlePrefixMultipleKeywords_singleRequestFound() throws ParseException {
        String expectedMessage = String.format(MESSAGE_REQUESTS_LISTED_OVERVIEW, 1);
        Predicate<Request> predicate = preparePredicate(" p/94351253");
        FilterRequestCommand command = new FilterRequestCommand(predicate);
        expectedModel.updateFilteredRequestList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE_REQUEST), model.getFilteredRequestList());
    }

    @Test
    public void execute_multiplePrefixSingleKeywords_singleRequestFound() throws ParseException {
        String expectedMessage = String.format(MESSAGE_REQUESTS_LISTED_OVERVIEW, 1);
        Predicate<Request> predicate = preparePredicate(" p/82015737 c/Palliative");
        FilterRequestCommand command = new FilterRequestCommand(predicate);
        expectedModel.updateFilteredRequestList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(DANIEL_REQUEST), model.getFilteredRequestList());
    }

    @Test
    public void execute_multiplePrefixSingleKeywords_zeroRequestsFound() throws ParseException {
        String expectedMessage = String.format(MESSAGE_REQUESTS_LISTED_OVERVIEW, 0);
        Predicate<Request> predicate = preparePredicate(" dt/01-01-2019 10:00:00 c/Palliative");
        FilterRequestCommand command = new FilterRequestCommand(predicate);
        expectedModel.updateFilteredRequestList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredRequestList());
    }

    @Test
    public void execute_multiplePrefixMultipleKeywords_zeroRequestsFound() throws ParseException {
        String expectedMessage = String.format(MESSAGE_REQUESTS_LISTED_OVERVIEW, 0);
        Predicate<Request> predicate =
            preparePredicate(" c/Cancer a/Jurong");
        FilterRequestCommand command = new FilterRequestCommand(predicate);
        expectedModel.updateFilteredRequestList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredRequestList());
    }

    @Test
    public void execute_singleRepeatedPrefixSingleKeywordTakeLast_multipleRequestsFound() throws ParseException {
        String expectedMessage = String.format(MESSAGE_REQUESTS_LISTED_OVERVIEW, 1);
        Predicate<Request> predicate = preparePredicate(" c/Dialysis c/Physiotherapy");
        FilterRequestCommand command = new FilterRequestCommand(predicate);
        expectedModel.updateFilteredRequestList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE_REQUEST), model.getFilteredRequestList());
    }

    @Test
    public void execute_singlePrefixSingleKeyword_multipleOrdersFound() throws ParseException {
        String expectedMessage = String.format(MESSAGE_REQUESTS_LISTED_OVERVIEW, 3);
        Predicate<Request> predicate = preparePredicate(" c/Palliative");
        FilterRequestCommand command = new FilterRequestCommand(predicate);
        expectedModel.updateFilteredRequestList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON_REQUEST, CARL_REQUEST, DANIEL_REQUEST),
            model.getFilteredRequestList());
    }

    @Test
    public void execute_allSupportedPrefixesSingleKeyword_oneOrderFound() throws ParseException {
        String expectedMessage = String.format(MESSAGE_REQUESTS_LISTED_OVERVIEW, 1);
        Predicate<Request> predicate =
            preparePredicate(" n/alice p/94351253 a/Jurong West Ave 6 dt/01-01-2019 10:00:00 "
                + "c/Physiotherapy");
        FilterRequestCommand command = new FilterRequestCommand(predicate);
        expectedModel.updateFilteredRequestList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE_REQUEST), model.getFilteredRequestList());
    }

    @Test
    public void execute_allSupportedPrefixesSingleKeyword_zeroRequestFound() throws ParseException {
        String expectedMessage = String.format(MESSAGE_REQUESTS_LISTED_OVERVIEW, 0);
        Predicate<Request> predicate =
            preparePredicate(" n/alice dt/01-10-2018 10:00:00 c/Chicken Pox p/1223214 a/Block 38");
        FilterRequestCommand command = new FilterRequestCommand(predicate);
        expectedModel.updateFilteredRequestList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredRequestList());
    }

    /**
     * Parses {@code userInput} into a {@code FilterRequestCommand}.
     */
    private Predicate<Request> preparePredicate(String userInput) throws ParseException {
        ArgumentMultimap argMultimap = parseStringInput(userInput);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE, PREFIX_NRIC, PREFIX_ADDRESS,
            PREFIX_DATE, PREFIX_CONDITION)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_REQUEST_COMMAND_FORMAT,
                FilterRequestCommand.MESSAGE_USAGE));
        }

        return new RequestPredicateUtil().parsePredicate(argMultimap);
    }

    private ArgumentMultimap parseStringInput(String userInput) {
        return ArgumentTokenizer.tokenize(userInput, PREFIX_NAME, PREFIX_PHONE, PREFIX_NRIC,
            PREFIX_ADDRESS, PREFIX_DATE, PREFIX_CONDITION);
    }
}

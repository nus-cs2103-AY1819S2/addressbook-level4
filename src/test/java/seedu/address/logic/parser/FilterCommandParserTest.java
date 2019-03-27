package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_HEALTHWORKER_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.MODE_HEALTHWORKER;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_ANDY;
import static seedu.address.logic.commands.CommandTestUtil.ORGANIZATION_DESC_ANDY;
import static seedu.address.logic.commands.CommandTestUtil.SKILLS_DESC_ANDY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ANDY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORGANIZATION_ANDY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalHealthWorkers.ANDY;
import static seedu.address.testutil.TypicalHealthWorkers.getTypicalHealthStaff;
import static seedu.address.testutil.TypicalHealthWorkers.getTypicalHealthWorkerBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalRequests.getTypicalRequestBook;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.FilterHealthWorkerCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.healthworker.HealthWorker;
import seedu.address.model.tag.Specialisation;

public class FilterCommandParserTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalHealthWorkerBook(),
            getTypicalRequestBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalHealthWorkerBook(),
            getTypicalRequestBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        // empty args
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FilterCommandParser.MESSAGE_USAGE));

        // command mode present, empty fields
        assertParseFailure(parser, "h", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FilterCommandParser.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyCommandMode() {
        assertParseFailure(parser, NAME_DESC_ANDY, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FilterCommandParser.MESSAGE_USAGE));
    }

    @Test
    public void parseHealthWorkerPredicates_invalidInput() {
        // empty string
        assertThrows(ParseException.class, () -> FilterCommandParser.parseHealthWorkerPredicates(""));

        // invalid prefixes
        assertThrows(ParseException.class, () -> FilterCommandParser.parseHealthWorkerPredicates("r/John"));
    }

    @Test
    public void parseHealthWorkerPredicates_validInput() throws ParseException {
        Predicate<HealthWorker> firstPredicate = x -> x.getName().contains(VALID_NAME_ANDY);
        Predicate<HealthWorker> secondPredicate = x -> x.getOrganization().contains(VALID_ORGANIZATION_ANDY);
        Predicate<HealthWorker> thirdPredicate = x -> x.getSkills().containsAll(Arrays.asList(
                Specialisation.GENERAL_PRACTICE, Specialisation.PHYSIOTHERAPY));

        // filter by name
        List<Predicate> predicateList = Arrays.asList(firstPredicate);
        List<Predicate> newPredicateList = FilterCommandParser.parseHealthWorkerPredicates(NAME_DESC_ANDY);

        assertEquals(getTypicalHealthStaff().stream().filter(FilterHealthWorkerCommand
                .reducePredicates(predicateList)).collect(Collectors.toList()), getTypicalHealthStaff()
                .stream().filter(FilterHealthWorkerCommand.reducePredicates(newPredicateList))
                .collect(Collectors.toList()));

        // filter by organization
        predicateList = Arrays.asList(secondPredicate);
        newPredicateList = FilterCommandParser.parseHealthWorkerPredicates(ORGANIZATION_DESC_ANDY);

        assertEquals(getTypicalHealthStaff().stream().filter(FilterHealthWorkerCommand
                .reducePredicates(predicateList)).collect(Collectors.toList()), getTypicalHealthStaff()
                .stream().filter(FilterHealthWorkerCommand.reducePredicates(newPredicateList))
                .collect(Collectors.toList()));

        // filter by skills
        predicateList = Arrays.asList(thirdPredicate);
        newPredicateList = FilterCommandParser.parseHealthWorkerPredicates(SKILLS_DESC_ANDY);

        assertEquals(getTypicalHealthStaff().stream().filter(FilterHealthWorkerCommand
                .reducePredicates(predicateList)).collect(Collectors.toList()), getTypicalHealthStaff()
                .stream().filter(FilterHealthWorkerCommand.reducePredicates(newPredicateList))
                .collect(Collectors.toList()));

        // filter by multiple
        predicateList = Arrays.asList(firstPredicate, secondPredicate);
        newPredicateList = FilterCommandParser.parseHealthWorkerPredicates(NAME_DESC_ANDY
                + ORGANIZATION_DESC_ANDY);

        assertEquals(getTypicalHealthStaff().stream().filter(FilterHealthWorkerCommand
                .reducePredicates(predicateList)).collect(Collectors.toList()), getTypicalHealthStaff()
                .stream().filter(FilterHealthWorkerCommand.reducePredicates(newPredicateList))
                .collect(Collectors.toList()));
    }

    @Test
    public void parse_healthWorker_invalidInput() {
        // missing prefixes
        assertParseFailure(parser, MODE_HEALTHWORKER + " " + VALID_NAME_ANDY, String.format
                (MESSAGE_INVALID_COMMAND_FORMAT, FilterHealthWorkerCommand.MESSAGE_USAGE));

        // invalid prefixes
        assertParseFailure(parser, MODE_HEALTHWORKER + " " + ADDRESS_DESC_AMY, String.format
                (MESSAGE_INVALID_COMMAND_FORMAT, FilterHealthWorkerCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_healthWorker_validInput() throws ParseException {
        String expectedMessage = String.format(MESSAGE_HEALTHWORKER_LISTED_OVERVIEW, 1);
        Predicate<HealthWorker> firstPredicate = x -> x.getName().contains(VALID_NAME_ANDY);
        Predicate<HealthWorker> secondPredicate = x -> x.getOrganization().contains(VALID_ORGANIZATION_ANDY);
        Predicate<HealthWorker> thirdPredicate = x -> x.getSkills().containsAll(Arrays.asList(
                Specialisation.GENERAL_PRACTICE, Specialisation.PHYSIOTHERAPY));

        FilterHealthWorkerCommand command = (FilterHealthWorkerCommand) parser.parse(MODE_HEALTHWORKER
                + NAME_DESC_ANDY + ORGANIZATION_DESC_ANDY + SKILLS_DESC_ANDY);
        expectedModel.updateFilteredHealthWorkerList(FilterHealthWorkerCommand
                .reducePredicates(Arrays.asList(firstPredicate, secondPredicate, thirdPredicate)));
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ANDY), model.getFilteredHealthWorkerList());
    }
}

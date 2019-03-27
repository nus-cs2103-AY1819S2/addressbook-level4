package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.sortmethods.SortAlphabetical;
import seedu.address.logic.commands.sortmethods.SortSkills;
import seedu.address.logic.commands.sortmethods.SortGpa;
import seedu.address.logic.commands.sortmethods.SortEducation;
import seedu.address.logic.commands.sortmethods.SortUtil;
import seedu.address.logic.parser.SortWord;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Sorts all persons in the address book and lists to the user.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_NOT_SORTED = "Sorting is not successful!";
    public static final String MESSAGE_SUCCESS = "Sorted all persons";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts all persons in address book "
            + "according to the specified keyword and displays them as a list with index numbers.\n"
            + "Parameters: [reverse] KEYWORD \n"
            + "Valid KEYWORD: alphabetical, skills, education, gpa \n"
            + "Example: " + COMMAND_WORD + " alphabetical \n"
            + "Example: " + COMMAND_WORD + " reverse skills ";

    private final SortWord method;

    private List<Person> sortedPersons;

    public SortCommand(SortWord method) {
        this.method = method;
    }

    /**
     * Checks if the sort should be reversed.
     * Maybe this should be done at a lower level
     */
    private Boolean hasReverse() {
        String input = this.method.getSortWord();
        Boolean reverse;
        if (input.contains("reverse")) {
            reverse = true;
        }
        else {
            reverse = false;
        }
        return reverse;
    }

    /**
     * Processes the sort command
     *
     * @param model
     */
    private void processCommand(Model model) {
        List<Person> lastShownList = model.getAddressBook().getPersonList();
        //Maybe use switch statement here?
        //Do not delete from model, possible to delete from versioned addressbook?
        String input = this.method.getSortWord();
        if (input.contains("alphabetical")) {
            SortAlphabetical sorted = new SortAlphabetical(lastShownList);
            sortedPersons = sorted.getList();
            model.deleteAllPerson();
        } else if (input.contains("skills")) {
            SortSkills sorted = new SortSkills(lastShownList);
            sortedPersons = sorted.getList();
            model.deleteAllPerson();
        } else if (input.contains("gpa")) {
            SortGpa sorted = new SortGpa(lastShownList);
            sortedPersons = sorted.getList();
            //TODO: remove this print statement
            //Temporarily add print statement here since the gpa is not being printed to the GUI
            //Note: this is performed before any reversal
            System.out.println(sortedPersons);
            model.deleteAllPerson();
        } else if (input.contains("education")) {
            SortEducation sorted = new SortEducation(lastShownList);
            sortedPersons = sorted.getList();
            //TODO: remove this print statement
            //Temporarily add print statement here since the education is not being printed to the GUI
            System.out.println(sortedPersons);
            model.deleteAllPerson();
        }
        if (hasReverse()) {
            sortedPersons = SortUtil.reversePersonList(sortedPersons);
        }
        for (Person newPerson : sortedPersons) {
            model.addPersonWithFilter(newPerson);
        }
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        try {
            model.setSortInfo(true);
            processCommand(model);
            String messageSuccess = "Sorted all persons by " + method.toString();
            model.commitAddressBook();
            model.setSortInfo(false);
            return new CommandResult(messageSuccess);
        } catch (Exception e) {
            return new CommandResult(MESSAGE_NOT_SORTED);
        }
    }
}

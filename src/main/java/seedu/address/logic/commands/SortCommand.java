package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.sortmethods.SortEducation;
import seedu.address.logic.commands.sortmethods.SortGpa;
import seedu.address.logic.commands.sortmethods.SortName;
import seedu.address.logic.commands.sortmethods.SortSkills;
import seedu.address.logic.commands.sortmethods.SortSurname;
import seedu.address.logic.commands.sortmethods.SortTagNumber;
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
            + "Valid KEYWORD: education, endorsements, endorsement number, gpa, name, positions, position number,\n"
            + "               skills, skill number, surname \n"
            + "Example: " + COMMAND_WORD + " name \n"
            + "Example: " + COMMAND_WORD + " reverse skills ";

    private final SortWord method;

    private List<Person> sortedPersons;

    private Boolean isReverseList;

    public SortCommand(SortWord method) {
        this.method = method;
    }

    /**
     * Checks if the sort should be reversed.
     * If it should be reversed the class wide isReverseList boolean is updated and the input is shortened
     *   to leave just the sorting method
     */
    private String checkReverse() {
        String input = this.method.getSortWord();
        isReverseList = false;
        if (input.contains("reverse")) {
            isReverseList = true;
            int firstSpace = input.indexOf(" ");
            return input.substring(firstSpace + 1);
        }
        return input;
    }

    /**
     * Processes the sort command
     *
     * @param model
     */
    private void processCommand(Model model) {
        List<Person> lastShownList = model.getAddressBook().getPersonList();
        //Maybe use switch statement here?
        String commandInput = checkReverse();

        if (commandInput.equals("name")) {
            SortName sorted = new SortName(lastShownList);
            sortedPersons = sorted.getList();
            model.deleteAllPerson();
        } else if (commandInput.equals("surname")) {
            SortSurname sorted = new SortSurname(lastShownList);
            sortedPersons = sorted.getList();
            model.deleteAllPerson();
        } else if (commandInput.equals("skills")) {
            SortSkills sorted = new SortSkills(lastShownList, commandInput);
            sortedPersons = sorted.getList();
            model.deleteAllPerson();
        } else if (commandInput.equals("endorsements")) {
            SortSkills sorted = new SortSkills(lastShownList, commandInput);
            sortedPersons = sorted.getList();
            model.deleteAllPerson();
        } else if (commandInput.equals("positions")) {
            SortSkills sorted = new SortSkills(lastShownList, commandInput);
            sortedPersons = sorted.getList();
            model.deleteAllPerson();
        } else if (commandInput.equals("gpa")) {
            SortGpa sorted = new SortGpa(lastShownList);
            sortedPersons = sorted.getList();
            //TODO: remove this print statement
            //Temporarily add print statement here since the gpa is not being printed to the GUI
            //Note: this is performed before any reversal
            System.out.println(sortedPersons);
            model.deleteAllPerson();
        } else if (commandInput.equals("education")) {
            SortEducation sorted = new SortEducation(lastShownList);
            sortedPersons = sorted.getList();
            //TODO: remove this print statement
            //Temporarily add print statement here since the education is not being printed to the GUI
            System.out.println(sortedPersons);
            model.deleteAllPerson();
        } else if (commandInput.substring(commandInput.lastIndexOf(" ") + 1).equals("number")) {
            SortTagNumber sorted = new SortTagNumber(lastShownList, commandInput);
            sortedPersons = sorted.getList();
            model.deleteAllPerson();
        }
        if (isReverseList) {
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

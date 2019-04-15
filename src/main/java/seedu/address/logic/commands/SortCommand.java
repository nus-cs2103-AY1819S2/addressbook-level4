package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.sortmethods.SortDegree;
import seedu.address.logic.commands.sortmethods.SortEducation;
import seedu.address.logic.commands.sortmethods.SortGpa;
import seedu.address.logic.commands.sortmethods.SortMethod;
import seedu.address.logic.commands.sortmethods.SortName;
import seedu.address.logic.commands.sortmethods.SortSurname;
import seedu.address.logic.commands.sortmethods.SortTagNumber;
import seedu.address.logic.commands.sortmethods.SortTags;
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
            + "Valid KEYWORD: degree, education, endorsements, endorsement number, gpa, name, positions,\n"
            + "               position number, skills, skill number, surname \n"
            + "Example: " + COMMAND_WORD + " name \n"
            + "Example: " + COMMAND_WORD + " reverse skills ";

    private final Logger logger = LogsCenter.getLogger(SortUtil.class);
    private final SortWord method;

    private List<Person> sortedPersons;

    private Boolean isReverseList;
    private Boolean isNewListPresent;

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

    private void processSortMethod(SortMethod command, List<Person> lastShownList, String... type) {
        command.execute(lastShownList, type);
        this.sortedPersons = command.getList();
        this.isNewListPresent = true;
    }

    /**
     * Processes the sort command and calls processSortMethod based on the sort command
     *
     * @param model
     */
    private void processCommand(Model model) {
        List<Person> lastShownList = model.getAddressBook().getPersonList();
        String commandInput = checkReverse();

        if (commandInput.equals("name")) {
            processSortMethod(new SortName(), lastShownList);
        } else if ("surname".equals(commandInput)) {
            processSortMethod(new SortSurname(), lastShownList);
        } else if ("gpa".equals(commandInput)) {
            processSortMethod(new SortGpa(), lastShownList);
        } else if ("education".equals(commandInput)) {
            processSortMethod(new SortEducation(), lastShownList);
        } else if ("number".equals(commandInput.substring(commandInput.lastIndexOf(" ") + 1))) {
            processSortMethod(new SortTagNumber(), lastShownList, commandInput);
        } else if ("degree".equals(commandInput)) {
            processSortMethod(new SortDegree(), lastShownList);
        } else if ("skills".equals(commandInput) || "endorsements".equals(commandInput)
                || "positions".equals(commandInput)) {
            processSortMethod(new SortTags(), lastShownList, commandInput);
        } else {
            logger.info("Invalid sort input and cannot be processed.");
        }
        if (isReverseList) {
            sortedPersons = SortUtil.reversePersonList(sortedPersons);
        }
        if (isNewListPresent) {
            model.deleteAllPerson();
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

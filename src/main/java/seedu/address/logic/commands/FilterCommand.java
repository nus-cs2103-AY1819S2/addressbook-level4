package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL_REVERSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME_REVERSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE_REVERSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILL_REVERSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILL;
import static java.util.Objects.requireNonNull;

/**
 * Filters the address book.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";
    public static final String MESSAGE_CLEAR_FILTER_PERSON_SUCCESS = "The Address Book is cleared from "
            + "all the filters.";
    public static final String MESSAGE_FILTER_PERSON_SUCCESS = "The Address Book is filtered.";
    public static final String MESSAGE_NOT_FILTERED = "Filtering is not successful!";
    public static final String MESSAGE_NO_FILTER_TO_CLEAR = "There is no filter to clear.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " clear/or/and " + "[prefix/text/prefix] \n"
            + "Examples: \n"
            + COMMAND_WORD + " or  " + PREFIX_PHONE + "91234567" + PREFIX_PHONE_REVERSE + " " + PREFIX_SKILL
            + "C++, MS-Excel" + PREFIX_SKILL_REVERSE
            + " --> SAVES THE FILTER. IF ONE OF THE FILTER TYPES MATCH, IT PRINTS IT! \n"
            + COMMAND_WORD + " and " + PREFIX_NAME + "Alex" + PREFIX_NAME_REVERSE + " " + PREFIX_EMAIL
            + "johndoe@example.com" + PREFIX_EMAIL_REVERSE
            + " --> SAVES THE FILTER. IF ALL OF THE FILTER TYPES MATCH, IT PRINTS IT! \n"
            + COMMAND_WORD + " clear " + " --> CLEARS ALL THE PREVIOUSLY MADE FILTERING!";

    /**
     * Explanation of process numbers:
     * 0 -> clear
     * 1 -> or
     * 2 -> and
     */

    private final int processNum;
    private String name;
    private String phone;
    private String email;
    private String[] skillList;
    private String[] posList;
    private String gpa;
    private String education;
    private String address;
    private boolean isFilterCleared;

    public FilterCommand(String filteringConditions, String[] criterion, int processNumber) {

        requireNonNull(filteringConditions);
        processNum = processNumber;
        name = criterion[0];
        phone = criterion[1];
        email = criterion[2];
        address = criterion[3];
        gpa = criterion[6];
        education = criterion[7];
        isFilterCleared = false;

        if (criterion[4] != null) {
            skillList = criterion[4].trim().split(",");

            for (int i = 0; i < skillList.length; i++) {
                skillList[i] = skillList[i].trim().toLowerCase();
            }
        } else {
            skillList = null;
        }

        if (criterion[5] != null) {
            posList = criterion[5].trim().split(",");

            for (int i = 0; i < posList.length; i++) {
                posList[i] = posList[i].trim().toLowerCase();
            }
        } else {
            posList = null;
        }
    }

    /**
     * Decides which filter method will be called (and/or/clear) and calls the method
     */
    private void processCommand(Model model) {

        // or statement will be processed
        if (processNum == 1) {
            isFilterCleared = false;
            model.filterOr(name, phone, email, address, skillList, posList, gpa, education);
        }

        // and statement will be processed
        else if (processNum == 2) {
            isFilterCleared = false;
            model.filterAnd(name, phone, email, address, skillList, posList, gpa, education);
        }

        // clear statement will be processed
        else {
            if (model.getFilterInfo()) {
                model.clearFilter();
                isFilterCleared = true;
            }
        }
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {

        try {
            processCommand(model);

            if (isFilterCleared) {
                isFilterCleared = false;
                model.commitAddressBook();
                return new CommandResult(MESSAGE_CLEAR_FILTER_PERSON_SUCCESS);
            } else if (processNum == 0 && !isFilterCleared) {
                return new CommandResult(MESSAGE_NO_FILTER_TO_CLEAR);
            } else {
                model.commitAddressBook();
                return new CommandResult(MESSAGE_FILTER_PERSON_SUCCESS);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            return new CommandResult(MESSAGE_NOT_FILTERED);
        }
    }
}

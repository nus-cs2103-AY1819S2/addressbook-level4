package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_FILTER_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILTER_EMAIL_REVERSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILTER_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILTER_NAME_REVERSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILTER_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILTER_PHONE_REVERSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILTER_SKILL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILTER_SKILL_REVERSE;

import java.util.Arrays;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Filters the address book.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";
    public static final String MESSAGE_CLEAR_FILTER_PERSON_SUCCESS = "The Address Book is cleared from "
            + "all the filters.";
    public static final String MESSAGE_FILTER_PERSON_SUCCESS = "The Address Book is filtered.";
    public static final String MESSAGE_FILTER_REVERSE_SUCCESS = "The filtering is reversed.";
    public static final String MESSAGE_NO_FILTER_TO_CLEAR = "There is no filter to clear.";
    public static final String MESSAGE_NO_FILTER_TO_REVERSE = "There is no filter to reverse.";
    public static final String MESSAGE_USAGE =
            COMMAND_WORD + " or/and " + "prefix1<text>prefix1 [prefix2<text>prefix2] "
            + "--- OR --- " + COMMAND_WORD + " clear/reverse \n"
            + "Examples: \n"
            + COMMAND_WORD + " or  " + PREFIX_FILTER_PHONE + "91234567" + PREFIX_FILTER_PHONE_REVERSE + " "
            + PREFIX_FILTER_SKILL + "C++, MS-Excel" + PREFIX_FILTER_SKILL_REVERSE
            + " --> SAVES THE FILTER. IF ONE OF THE FILTER TYPES MATCH, IT PRINTS IT! \n"
            + COMMAND_WORD + " and " + PREFIX_FILTER_NAME + "Alex" + PREFIX_FILTER_NAME_REVERSE + " "
            + PREFIX_FILTER_EMAIL + "johndoe@example.com" + PREFIX_FILTER_EMAIL_REVERSE
            + " --> SAVES THE FILTER. IF ALL OF THE FILTER TYPES MATCH, IT PRINTS IT! \n"
            + COMMAND_WORD + " clear " + " --> CLEARS ALL THE PREVIOUSLY MADE FILTERING! \n"
            + COMMAND_WORD + " reverse " + " --> REVERSES THE FILTERING!";

    /**
     * Explanation of process numbers:
     * 0 -> clear
     * 1 -> or
     * 2 -> and
     * 3 -> reverse
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
    private String endorseCount;
    private String degreeLevel;
    private boolean isFilterCleared;

    public FilterCommand(String[] criterion, int processNumber) {

        processNum = processNumber;
        name = criterion[0];
        phone = criterion[1];
        email = criterion[2];
        address = criterion[3];
        gpa = criterion[6];
        education = criterion[7];
        endorseCount = criterion[8];
        degreeLevel = criterion[9];
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

        if (processNum == 1) {
            // or statement will be processed
            isFilterCleared = false;
            model.filterOr(name, phone, email, address, skillList, posList,
                    endorseCount, gpa, education, degreeLevel);
        } else if (processNum == 2) {
            // and statement will be processed
            isFilterCleared = false;
            model.filterAnd(name, phone, email, address, skillList, posList,
                    endorseCount, gpa, education, degreeLevel);
        } else if (processNum == 3) {
            // reverse statement will be processed
            isFilterCleared = false;
            model.reverseFilter();
        } else {
            // clear statement will be processed
            if (model.getFilterInfo()) {
                model.clearFilter();
                isFilterCleared = true;
            }
        }
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        try {
            processCommand(model);

            if (isFilterCleared) {
                isFilterCleared = false;
                model.commitAddressBook();
                return new CommandResult(MESSAGE_CLEAR_FILTER_PERSON_SUCCESS);
            } else if (processNum == 0 && !isFilterCleared) {
                throw new CommandException(MESSAGE_NO_FILTER_TO_CLEAR);
            } else if (processNum == 3) {
                if (model.getFilterInfo()) {
                    model.commitAddressBook();
                    return new CommandResult(MESSAGE_FILTER_REVERSE_SUCCESS);
                } else {
                    throw new CommandException(MESSAGE_NO_FILTER_TO_REVERSE);
                }
            } else {
                model.commitAddressBook();
                return new CommandResult(MESSAGE_FILTER_PERSON_SUCCESS);
            }
        } catch (Exception e) {
            throw new CommandException(e.getMessage());
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this || (other instanceof FilterCommand
                && ((name == null && ((FilterCommand) other).name == null)
                || name.equals(((FilterCommand) other).name))
                && ((phone == null && ((FilterCommand) other).phone == null)
                || phone.equals(((FilterCommand) other).phone))
                && ((email == null && ((FilterCommand) other).email == null)
                || email.equals(((FilterCommand) other).email))
                && ((address == null && ((FilterCommand) other).address == null)
                || address.equals(((FilterCommand) other).address))
                && ((skillList == null && ((FilterCommand) other).skillList == null)
                || Arrays.equals(skillList, ((FilterCommand) other).skillList))
                && ((posList == null && ((FilterCommand) other).posList == null)
                || Arrays.equals(posList, ((FilterCommand) other).posList))
                && ((education == null && ((FilterCommand) other).education == null)
                || education.equals(((FilterCommand) other).education))
                && ((gpa == null && ((FilterCommand) other).gpa == null)
                || gpa.equals(((FilterCommand) other).gpa))
                && ((endorseCount == null && ((FilterCommand) other).endorseCount == null)
                || endorseCount.equals(((FilterCommand) other).endorseCount))
                && ((degreeLevel == null && ((FilterCommand) other).degreeLevel == null)
                || degreeLevel.equals(((FilterCommand) other).degreeLevel)))) {
            return true;
        }

        return false;
    }
}

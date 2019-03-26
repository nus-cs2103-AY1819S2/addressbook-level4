package seedu.address.logic.parser;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.concurrent.atomic.AtomicInteger;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS_REVERSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EDUCATION_REVERSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EDUCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL_REVERSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GPA_REVERSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GPA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME_REVERSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE_REVERSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POS_REVERSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILL_REVERSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILL;

/**
 * Parses the information in filter command
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    private final int totalNumberOfInfo = 8;

    /**
     * Since there are multiple options in filtering: and, or , clear
     * Decides which type of the filtering process will be executed from above
     */
    private String filterTypeDivider(String args, AtomicInteger typeOfProcess) {

        typeOfProcess.set(-1);

        if (args.length() > 3 && args.substring(0, 3).equals("or ")) {
            typeOfProcess.set(1);
            args = args.substring(3);
        } else if (args.length() > 4 && args.substring(0, 4).equals("and ")) {
            typeOfProcess.set(2);
            args = args.substring(4);
        } else if (args.length() > 4 && args.substring(0, 5).equals("clear")) {
            typeOfProcess.set(0);
        }

        return args;
    }

    /** Divides every filter criteria into Strings. The information order in the
     *  returned array is as follows:
     *  1- Name
     *  2- Phone
     *  3- Email
     *  4- Address
     *  5- Skills
     *  6- Positions
     *  7- Gpa
     *  8- Education
     *  !!! If some of the above ones are not given, then their value will be null
     */

    /**
     * Since there can be multiple filtering criterion at once, this function detects which criteron exist and
     * Places evey criteria text into a String array
     */
    private String[] divideFilterCriterion(String args, AtomicInteger typeOfProcess) {
        String[] criterion = new String[totalNumberOfInfo];
        int totalNumOfCriterion = 0;

        if (args.contains(PREFIX_NAME.toString()) && args.contains(PREFIX_NAME_REVERSE.toString())
                && args.indexOf(PREFIX_NAME.toString()) < args.indexOf(PREFIX_NAME_REVERSE.toString())) {
            criterion[0] = "available";
            totalNumOfCriterion++;
        } else {
            criterion[0] = null;
        }

        if (args.contains(PREFIX_PHONE.toString()) && args.contains(PREFIX_PHONE_REVERSE.toString())
                && args.indexOf(PREFIX_PHONE.toString()) < args.indexOf(PREFIX_PHONE_REVERSE.toString())) {
            criterion[1] = "available";
            totalNumOfCriterion++;
        } else {
            criterion[1] = null;
        }

        if (args.contains(PREFIX_EMAIL.toString()) && args.contains(PREFIX_EMAIL_REVERSE.toString())
                && args.indexOf(PREFIX_EMAIL.toString()) < args.indexOf(PREFIX_EMAIL_REVERSE.toString())) {
            criterion[2] = "available";
            totalNumOfCriterion++;
        } else {
            criterion[2] = null;
        }

        if (args.contains(PREFIX_ADDRESS.toString()) && args.contains(PREFIX_ADDRESS_REVERSE.toString())
                && args.indexOf(PREFIX_ADDRESS.toString()) < args.indexOf(PREFIX_ADDRESS_REVERSE.toString())) {
            criterion[3] = "available";
            totalNumOfCriterion++;
        } else {
            criterion[3] = null;
        }

        if (args.contains(PREFIX_SKILL.toString()) && args.contains(PREFIX_SKILL_REVERSE.toString())
                && args.indexOf(PREFIX_SKILL.toString()) < args.indexOf(PREFIX_SKILL_REVERSE.toString())) {
            criterion[4] = "available";
            totalNumOfCriterion++;
        } else {
            criterion[4] = null;
        }

        if (args.contains(PREFIX_POS.toString()) && args.contains(PREFIX_POS_REVERSE.toString())
                && args.indexOf(PREFIX_POS.toString()) < args.indexOf(PREFIX_POS_REVERSE.toString())) {
            criterion[5] = "available";
            totalNumOfCriterion++;
        } else {
            criterion[5] = null;
        }

        if (args.contains(PREFIX_GPA.toString()) && args.contains(PREFIX_GPA_REVERSE.toString())
                && args.indexOf(PREFIX_GPA.toString()) < args.indexOf(PREFIX_GPA_REVERSE.toString())) {
            criterion[6] = "available";
            totalNumOfCriterion++;
        } else {
            criterion[6] = null;
        }

        if (args.contains(PREFIX_EDUCATION.toString()) && args.contains(PREFIX_EDUCATION_REVERSE.toString())
                && args.indexOf(PREFIX_EDUCATION.toString()) < args.indexOf(PREFIX_EDUCATION_REVERSE.toString())) {
            criterion[7] = "available";
            totalNumOfCriterion++;
        } else {
            criterion[7] = null;
        }

        if (totalNumOfCriterion == 0) {
            typeOfProcess.set(-1);
        } else {

            if (criterion[0] != null) {
                criterion[0] = infoBetweenPrefixes(args, PREFIX_NAME.toString(), PREFIX_NAME_REVERSE.toString(),
                        typeOfProcess, false);
            }

            if (criterion[1] != null) {
                criterion[1] = infoBetweenPrefixes(args, PREFIX_PHONE.toString(), PREFIX_PHONE_REVERSE.toString(),
                        typeOfProcess, false);
            }

            if (criterion[2] != null) {
                criterion[2] = infoBetweenPrefixes(args, PREFIX_EMAIL.toString(), PREFIX_EMAIL_REVERSE.toString(),
                        typeOfProcess, false);
            }

            if (criterion[3] != null) {
                criterion[3] = infoBetweenPrefixes(args, PREFIX_ADDRESS.toString(), PREFIX_ADDRESS_REVERSE.toString(),
                        typeOfProcess, false);
            }

            if (criterion[4] != null) {
                criterion[4] = infoBetweenPrefixes(args, PREFIX_SKILL.toString(), PREFIX_SKILL_REVERSE.toString(),
                        typeOfProcess, false);
            }

            if (criterion[5] != null) {
                criterion[5] = infoBetweenPrefixes(args, PREFIX_POS.toString(), PREFIX_POS_REVERSE.toString(),
                        typeOfProcess, false);
            }
            if (criterion[6] != null) {
                criterion[6] = infoBetweenPrefixes(args, PREFIX_GPA.toString(), PREFIX_GPA_REVERSE.toString(),
                        typeOfProcess, true);
            }
            if (criterion[7] != null) {
                criterion[7] = infoBetweenPrefixes(args, PREFIX_EDUCATION.toString(),
                        PREFIX_EDUCATION_REVERSE.toString(), typeOfProcess, false);
            }
        }

        return criterion;
    }

    /**
     * Since filter form is like prefix/text/prefix, this function returns the text between given prefixes.
     */
    private String infoBetweenPrefixes(String args, String prefixBegin, String prefixEnd,
                                       AtomicInteger typeOfProcess, boolean isInputGpa) {

        int beginLoc = args.indexOf(prefixBegin) + prefixBegin.length();
        int endLoc = args.indexOf(prefixEnd);

        if (beginLoc >= endLoc) {
            typeOfProcess.set(-1);
            return null;
        }

        for (int i = beginLoc; i < endLoc; i++) {
            if (args.charAt(i) == ' ') {
                beginLoc++;
            } else {
                break;
            }
        }

        for (int j = endLoc - 1; j > beginLoc; j--) {
            if (args.charAt(j) == ' ') {
                endLoc--;
            } else {
                break;
            }
        }

        // checks if the Gpa value can be parsed into a float
        if (isInputGpa) {
            try {
                Float.parseFloat(args.substring(beginLoc, endLoc));
            } catch (NumberFormatException e) {
                typeOfProcess.set(-1);
                return args.substring(beginLoc, endLoc);
            }
        }
        return args.substring(beginLoc, endLoc).toLowerCase();
    }

    @Override
    public FilterCommand parse(String args) throws ParseException {

        args = args.trim().replaceAll(" +", " ");
        AtomicInteger typeOfProcess = new AtomicInteger(-1);
        args = filterTypeDivider(args, typeOfProcess);

        String[] criterion = {" ", " ", " ", " ", " ", " ", " ", " "};

        if (typeOfProcess.get() != 0) {
            criterion = divideFilterCriterion(args, typeOfProcess);
        }

        if (typeOfProcess.get() == -1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        } else if (typeOfProcess.get() == 1) {
            return new FilterCommand(args, criterion, 1);
        } else if (typeOfProcess.get() == 2) {
            return new FilterCommand(args, criterion, 2);
        } else {
            return new FilterCommand(args, criterion, 0);
        }
    }
}

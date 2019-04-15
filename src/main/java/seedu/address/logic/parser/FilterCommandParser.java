package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.address.logic.parser.CliSyntax.PREFIX_FILTER_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILTER_ADDRESS_REVERSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILTER_DEGREE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILTER_DEGREE_REVERSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILTER_EDUCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILTER_EDUCATION_REVERSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILTER_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILTER_EMAIL_REVERSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILTER_ENDORSEMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILTER_ENDORSEMENT_REVERSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILTER_GPA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILTER_GPA_REVERSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILTER_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILTER_NAME_REVERSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILTER_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILTER_PHONE_REVERSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILTER_POS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILTER_POS_REVERSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILTER_SKILL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILTER_SKILL_REVERSE;

import java.util.concurrent.atomic.AtomicInteger;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Education;
import seedu.address.model.person.Gpa;
import seedu.address.model.person.Name;
import seedu.address.model.tag.SkillsTag;

/**
 * Parses the information in filter command
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    public static final String MESSAGE_INPUT_NOT_IN_TRUE_FORM = "The filtering parameters entered "
            + "is not correct accepted form!";

    private static final String EMAIL_VALIDATION_REGEX = "[\\p{ASCII}][\\p{ASCII} ]*";
    private final int totalNumberOfInfo = 10;
    private boolean inputParameterInCorrectForm = true;

    /**
     * Since there are multiple options in filtering: and, or , clear, reverse
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
        } else if (args.length() > 6 && args.substring(0, 7).equals("reverse")) {
            typeOfProcess.set(3);
        }

        return args;
    }

    /**
     * Checks if the input for phone is correct or not
     */
    private boolean numberInputControl(String arg) {

        arg = arg.trim();
        if (arg.length() < 1) {
            return false;
        }

        for (int i = 0; i < arg.length(); i++) {
            if (arg.charAt(i) < '0' || arg.charAt(i) > '9') {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if the input for degree is correct or not
     */
    private String degreeInputControl(String arg) {

        String text = arg.trim().replace(" ", "").toLowerCase();

        if ("0".equals(text) || "highschool".equals(text)) {
            return "0";
        } else if ("1".equals(text) || "associates".equals(text)) {
            return "1";
        } else if ("2".equals(text) || "bachelors".equals(text)) {
            return "2";
        } else if ("3".equals(text) || "masters".equals(text)) {
            return "3";
        } else if ("4".equals(text) || "phd".equals(text)) {
            return "4";
        }

        return "false";
    }


    /**
     * Checks if the input for any input that needs at least 1 ASCII character
     */
    public static boolean isValidAsciiInput(String test) {
        return test.matches(EMAIL_VALIDATION_REGEX);
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
     *  9- Endorsement
     *  !!! If some of the above ones are not given, then their value will be null
     *
     * Since there can be multiple filtering criterion at once, this function detects which criteron exist and
     * Places evey criteria text into a String array
     */
    private String[] divideFilterCriterion(String args, AtomicInteger typeOfProcess) {
        String[] criterion = new String[totalNumberOfInfo];
        int totalNumOfCriterion = 0;

        if ((args.contains(PREFIX_FILTER_NAME.toString())
                && !args.contains(PREFIX_FILTER_NAME_REVERSE.toString()))
                || (!args.contains(PREFIX_FILTER_NAME.toString())
                && args.contains(PREFIX_FILTER_NAME_REVERSE.toString()))) {
            inputParameterInCorrectForm = false;
        } else if (args.contains(PREFIX_FILTER_NAME.toString()) && args.contains(PREFIX_FILTER_NAME_REVERSE.toString())
                && args.indexOf(PREFIX_FILTER_NAME.toString())
                < args.indexOf(PREFIX_FILTER_NAME_REVERSE.toString())) {
            criterion[0] = "available";
            totalNumOfCriterion++;
        } else {
            criterion[0] = null;
        }

        if ((args.contains(PREFIX_FILTER_PHONE.toString())
                && !args.contains(PREFIX_FILTER_PHONE_REVERSE.toString()))
                || (!args.contains(PREFIX_FILTER_PHONE.toString())
                && args.contains(PREFIX_FILTER_PHONE_REVERSE.toString()))) {
            inputParameterInCorrectForm = false;
        } else if (args.contains(PREFIX_FILTER_PHONE.toString())
                && args.contains(PREFIX_FILTER_PHONE_REVERSE.toString())
                && args.indexOf(PREFIX_FILTER_PHONE.toString())
                < args.indexOf(PREFIX_FILTER_PHONE_REVERSE.toString())) {
            criterion[1] = "available";
            totalNumOfCriterion++;
        } else {
            criterion[1] = null;
        }

        if ((args.contains(PREFIX_FILTER_EMAIL.toString())
                && !args.contains(PREFIX_FILTER_EMAIL_REVERSE.toString()))
                || (!args.contains(PREFIX_FILTER_EMAIL.toString())
                && args.contains(PREFIX_FILTER_EMAIL_REVERSE.toString()))) {
            inputParameterInCorrectForm = false;
        } else if (args.contains(PREFIX_FILTER_EMAIL.toString())
                && args.contains(PREFIX_FILTER_EMAIL_REVERSE.toString())
                && args.indexOf(PREFIX_FILTER_EMAIL.toString())
                < args.indexOf(PREFIX_FILTER_EMAIL_REVERSE.toString())) {
            criterion[2] = "available";
            totalNumOfCriterion++;
        } else {
            criterion[2] = null;
        }

        if ((args.contains(PREFIX_FILTER_ADDRESS.toString())
                && !args.contains(PREFIX_FILTER_ADDRESS_REVERSE.toString()))
                || (!args.contains(PREFIX_FILTER_ADDRESS.toString())
                && args.contains(PREFIX_FILTER_ADDRESS_REVERSE.toString()))) {
            inputParameterInCorrectForm = false;
        } else if (args.contains(PREFIX_FILTER_ADDRESS.toString())
                && args.contains(PREFIX_FILTER_ADDRESS_REVERSE.toString())
                && args.indexOf(PREFIX_FILTER_ADDRESS.toString())
                < args.indexOf(PREFIX_FILTER_ADDRESS_REVERSE.toString())) {
            criterion[3] = "available";
            totalNumOfCriterion++;
        } else {
            criterion[3] = null;
        }

        if ((args.contains(PREFIX_FILTER_SKILL.toString())
                && !args.contains(PREFIX_FILTER_SKILL_REVERSE.toString()))
                || (!args.contains(PREFIX_FILTER_SKILL.toString())
                && args.contains(PREFIX_FILTER_SKILL_REVERSE.toString()))) {
            inputParameterInCorrectForm = false;
        } else if (args.contains(PREFIX_FILTER_SKILL.toString())
                && args.contains(PREFIX_FILTER_SKILL_REVERSE.toString())
                && args.indexOf(PREFIX_FILTER_SKILL.toString())
                < args.indexOf(PREFIX_FILTER_SKILL_REVERSE.toString())) {
            criterion[4] = "available";
            totalNumOfCriterion++;
        } else {
            criterion[4] = null;
        }

        if ((args.contains(PREFIX_FILTER_POS.toString())
                && !args.contains(PREFIX_FILTER_POS_REVERSE.toString()))
                || (!args.contains(PREFIX_FILTER_POS.toString())
                && args.contains(PREFIX_FILTER_POS_REVERSE.toString()))) {
            inputParameterInCorrectForm = false;
        } else if (args.contains(PREFIX_FILTER_POS.toString())
                && args.contains(PREFIX_FILTER_POS_REVERSE.toString())
                && args.indexOf(PREFIX_FILTER_POS.toString()) < args.indexOf(PREFIX_FILTER_POS_REVERSE.toString())) {
            criterion[5] = "available";
            totalNumOfCriterion++;
        } else {
            criterion[5] = null;
        }

        if ((args.contains(PREFIX_FILTER_GPA.toString())
                && !args.contains(PREFIX_FILTER_GPA_REVERSE.toString()))
                || (!args.contains(PREFIX_FILTER_GPA.toString())
                && args.contains(PREFIX_FILTER_GPA_REVERSE.toString()))) {
            inputParameterInCorrectForm = false;
        } else if (args.contains(PREFIX_FILTER_GPA.toString())
                && args.contains(PREFIX_FILTER_GPA_REVERSE.toString())
                && args.indexOf(PREFIX_FILTER_GPA.toString()) < args.indexOf(PREFIX_FILTER_GPA_REVERSE.toString())) {
            criterion[6] = "available";
            totalNumOfCriterion++;
        } else {
            criterion[6] = null;
        }

        if ((args.contains(PREFIX_FILTER_EDUCATION.toString())
                && !args.contains(PREFIX_FILTER_EDUCATION_REVERSE.toString()))
                || (!args.contains(PREFIX_FILTER_EDUCATION.toString())
                && args.contains(PREFIX_FILTER_EDUCATION_REVERSE.toString()))) {
            inputParameterInCorrectForm = false;
        } else if (args.contains(PREFIX_FILTER_EDUCATION.toString())
                && args.contains(PREFIX_FILTER_EDUCATION_REVERSE.toString())
                && args.indexOf(PREFIX_FILTER_EDUCATION.toString())
                < args.indexOf(PREFIX_FILTER_EDUCATION_REVERSE.toString())) {
            criterion[7] = "available";
            totalNumOfCriterion++;
        } else {
            criterion[7] = null;
        }

        if ((args.contains(PREFIX_FILTER_ENDORSEMENT.toString())
                && !args.contains(PREFIX_FILTER_ENDORSEMENT_REVERSE.toString()))
                || (!args.contains(PREFIX_FILTER_ENDORSEMENT.toString())
                && args.contains(PREFIX_FILTER_ENDORSEMENT_REVERSE.toString()))) {
            inputParameterInCorrectForm = false;
        } else if (args.contains(PREFIX_FILTER_ENDORSEMENT.toString())
                && args.contains(PREFIX_FILTER_ENDORSEMENT_REVERSE.toString())
                && args.indexOf(PREFIX_FILTER_ENDORSEMENT.toString())
                < args.indexOf(PREFIX_FILTER_ENDORSEMENT_REVERSE.toString())) {
            criterion[8] = "available";
            totalNumOfCriterion++;
        } else {
            criterion[8] = null;
        }

        if ((args.contains(PREFIX_FILTER_DEGREE.toString())
                && !args.contains(PREFIX_FILTER_DEGREE_REVERSE.toString()))
                || (!args.contains(PREFIX_FILTER_DEGREE.toString())
                && args.contains(PREFIX_FILTER_DEGREE_REVERSE.toString()))) {
            inputParameterInCorrectForm = false;
        } else if (args.contains(PREFIX_FILTER_DEGREE.toString())
                && args.contains(PREFIX_FILTER_DEGREE_REVERSE.toString())
                && args.indexOf(PREFIX_FILTER_DEGREE.toString())
                < args.indexOf(PREFIX_FILTER_DEGREE_REVERSE.toString())) {
            criterion[9] = "available";
            totalNumOfCriterion++;
        } else {
            criterion[9] = null;
        }

        if (totalNumOfCriterion == 0 || !inputParameterInCorrectForm) {
            typeOfProcess.set(-1);
        } else {

            if (criterion[0] != null) {
                criterion[0] = infoBetweenPrefixes(args, PREFIX_FILTER_NAME.toString(),
                        PREFIX_FILTER_NAME_REVERSE.toString(), typeOfProcess, false);
                if (criterion[0] == null || !Name.isValidName(criterion[0])) {
                    inputParameterInCorrectForm = false;
                    typeOfProcess.set(-1);
                }
            }

            if (criterion[1] != null) {
                criterion[1] = infoBetweenPrefixes(args, PREFIX_FILTER_PHONE.toString(),
                        PREFIX_FILTER_PHONE_REVERSE.toString(), typeOfProcess, false);
                if (criterion[1] == null || !numberInputControl(criterion[1])) {
                    inputParameterInCorrectForm = false;
                    typeOfProcess.set(-1);
                }
            }

            if (criterion[2] != null) {
                criterion[2] = infoBetweenPrefixes(args, PREFIX_FILTER_EMAIL.toString(),
                        PREFIX_FILTER_EMAIL_REVERSE.toString(), typeOfProcess, false);
                if (criterion[2] == null || !isValidAsciiInput(criterion[2])) {
                    inputParameterInCorrectForm = false;
                    typeOfProcess.set(-1);
                }
            }

            if (criterion[3] != null) {
                criterion[3] = infoBetweenPrefixes(args, PREFIX_FILTER_ADDRESS.toString(),
                        PREFIX_FILTER_ADDRESS_REVERSE.toString(), typeOfProcess, false);
                if (criterion[3] == null || !Address.isValidAddress(criterion[3])) {
                    inputParameterInCorrectForm = false;
                    typeOfProcess.set(-1);
                }
            }

            if (criterion[4] != null) {
                criterion[4] = infoBetweenPrefixes(args, PREFIX_FILTER_SKILL.toString(),
                        PREFIX_FILTER_SKILL_REVERSE.toString(), typeOfProcess, false);
                if (criterion[4] != null) {
                    for (String tag : criterion[4].split(",")) {
                        tag.trim();
                        if (!SkillsTag.isValidTagName(tag)) {
                            inputParameterInCorrectForm = false;
                            typeOfProcess.set(-1);
                        }
                    }
                } else {
                    inputParameterInCorrectForm = false;
                    typeOfProcess.set(-1);
                }
            }

            if (criterion[5] != null) {
                criterion[5] = infoBetweenPrefixes(args, PREFIX_FILTER_POS.toString(),
                        PREFIX_FILTER_POS_REVERSE.toString(), typeOfProcess, false);
                if (criterion[5] != null) {
                    for (String tag : criterion[5].split(",")) {
                        tag = tag.trim();
                        if (!SkillsTag.isValidTagName(tag)) {
                            inputParameterInCorrectForm = false;
                            typeOfProcess.set(-1);
                        }
                    }
                } else {
                    inputParameterInCorrectForm = false;
                    typeOfProcess.set(-1);
                }
            }

            if (criterion[6] != null) {
                criterion[6] = infoBetweenPrefixes(args, PREFIX_FILTER_GPA.toString(),
                        PREFIX_FILTER_GPA_REVERSE.toString(), typeOfProcess, true);
                if (criterion[6] == null || !Gpa.isValidGpa(criterion[6])) {
                    inputParameterInCorrectForm = false;
                    typeOfProcess.set(-1);
                }
            }
            if (criterion[7] != null) {
                criterion[7] = infoBetweenPrefixes(args, PREFIX_FILTER_EDUCATION.toString(),
                        PREFIX_FILTER_EDUCATION_REVERSE.toString(), typeOfProcess, false);
                if (criterion[7] == null || !Education.isValidEducation(criterion[7])) {
                    inputParameterInCorrectForm = false;
                    typeOfProcess.set(-1);
                }
            }

            if (criterion[8] != null) {
                criterion[8] = infoBetweenPrefixes(args, PREFIX_FILTER_ENDORSEMENT.toString(),
                        PREFIX_FILTER_ENDORSEMENT_REVERSE.toString(), typeOfProcess, false);
                if (criterion[8] == null || !numberInputControl(criterion[8])) {
                    inputParameterInCorrectForm = false;
                    typeOfProcess.set(-1);
                }
            }

            if (criterion[9] != null) {
                criterion[9] = infoBetweenPrefixes(args, PREFIX_FILTER_DEGREE.toString(),
                        PREFIX_FILTER_DEGREE_REVERSE.toString(), typeOfProcess, false);
                criterion[9] = degreeInputControl(criterion[9]);
                if (criterion[9] == null || criterion[9].equals("false")) {
                    inputParameterInCorrectForm = false;
                    typeOfProcess.set(-1);
                }
            }
        }

        return criterion;
    }

    /**
     * Since filter form is like prefix + text + prefix, this function returns the text between given prefixes.
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

        String[] criterion = {" ", " ", " ", " ", " ", " ", " ", " ", " ", " "};

        if (typeOfProcess.get() != 0 && typeOfProcess.get() != 3) {
            criterion = divideFilterCriterion(args, typeOfProcess);
        }

        if (typeOfProcess.get() == -1) {
            if (!inputParameterInCorrectForm) {
                throw new ParseException(MESSAGE_INPUT_NOT_IN_TRUE_FORM);
            }
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        } else if (typeOfProcess.get() == 1) {
            return new FilterCommand(criterion, 1);
        } else if (typeOfProcess.get() == 2) {
            return new FilterCommand(criterion, 2);
        } else if (typeOfProcess.get() == 3) {
            return new FilterCommand(criterion, 3);
        } else {
            return new FilterCommand(criterion, 0);
        }
    }
}

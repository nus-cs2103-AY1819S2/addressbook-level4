package seedu.address.logic.parser;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.*;
import java.util.concurrent.atomic.AtomicInteger;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

public class FilterCommandParser implements Parser<FilterCommand> {

    private final int TOTAL_NUMBER_OF_INFO = 5;

    /**
     * Since there are multiple options in filtering: and, or , clear
     * Decides which type of the filtering process will be executed from above
     */
    private String FilterTypeDivider(String args, AtomicInteger typeOfProcess) {

        typeOfProcess.set(-1);

        if (args.length() > 3 && args.substring(0, 3).equals("or ")) {
             typeOfProcess.set(1);
             args = args.substring(3);
        }

        else if (args.length() > 4 && args.substring(0, 4).equals("and ")) {
             typeOfProcess.set(2);
             args = args.substring(4);
        }

        else if(args.length() > 4 && args.substring(0, 5).equals("clear")) {
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
     *  5- All tags
     *
     *  !!! If some of the above ones are not given, then their value will be null
     */

    /**
     * Since there can be multiple filtering criterion at once, this function detects which criteron exist and
     * Places evey criteria text into a String array
     */
    private String[] DivideFilterCriterion(String args, AtomicInteger typeOfProcess)
    {
        String[] criterion = new String[TOTAL_NUMBER_OF_INFO];
        int totalNumOfCriterion = 0;

        if(args.contains(PREFIX_NAME.toString()) && args.contains(PREFIX_NAME_REVERSE.toString())
                && args.indexOf(PREFIX_NAME.toString()) < args.indexOf(PREFIX_NAME_REVERSE.toString())) {
            criterion[0] = "available";
            totalNumOfCriterion++;
        }
        else {
            criterion[0] = null;
        }

        if(args.contains(PREFIX_PHONE.toString()) && args.contains(PREFIX_PHONE_REVERSE.toString())
                && args.indexOf(PREFIX_PHONE.toString()) < args.indexOf(PREFIX_PHONE_REVERSE.toString())) {
            criterion[1] = "available";
            totalNumOfCriterion++;
        }
        else {
            criterion[1] = null;
        }

        if(args.contains(PREFIX_EMAIL.toString()) && args.contains(PREFIX_EMAIL_REVERSE.toString())
                && args.indexOf(PREFIX_EMAIL.toString()) < args.indexOf(PREFIX_EMAIL_REVERSE.toString())) {
            criterion[2] = "available";
            totalNumOfCriterion++;
        }
        else {
            criterion[2] = null;
        }

        if(args.contains(PREFIX_ADDRESS.toString()) && args.contains(PREFIX_ADDRESS_REVERSE.toString())
                && args.indexOf(PREFIX_ADDRESS.toString()) < args.indexOf(PREFIX_ADDRESS_REVERSE.toString())) {
            criterion[3] = "available";
            totalNumOfCriterion++;
        }
        else {
            criterion[3] = null;
        }

        if(args.contains(PREFIX_SKILL.toString()) && args.contains(PREFIX_SKILL_REVERSE.toString())
                && args.indexOf(PREFIX_SKILL.toString()) < args.indexOf(PREFIX_SKILL_REVERSE.toString())) {
            criterion[4] = "available";
            totalNumOfCriterion++;
        }
        else {
            criterion[4] = null;
        }

//        if(args.contains(PREFIX_POS.toString()) && args.contains(PREFIX_POS_REVERSE.toString())
//                && args.indexOf(PREFIX_POS.toString()) < args.indexOf(PREFIX_POS_REVERSE.toString())) {
//            criterion[4] = "available";
//            totalNumOfCriterion++;
//        }
//        else {
//            criterion[4] = null;
//        }

        if(totalNumOfCriterion == 0) {
            typeOfProcess.set(-1);
        }

        else {

            if(criterion[0] != null) {
                criterion[0] = InfoBetweenPrefixes(args, PREFIX_NAME.toString(), PREFIX_NAME_REVERSE.toString(), typeOfProcess);
            }

            if(criterion[1] != null) {
                criterion[1] = InfoBetweenPrefixes(args, PREFIX_PHONE.toString(), PREFIX_PHONE_REVERSE.toString(), typeOfProcess);
            }

            if(criterion[2] != null) {
                criterion[2] = InfoBetweenPrefixes(args, PREFIX_EMAIL.toString(), PREFIX_EMAIL_REVERSE.toString(), typeOfProcess);
            }

            if(criterion[3] != null) {
                criterion[3] = InfoBetweenPrefixes(args, PREFIX_ADDRESS.toString(), PREFIX_ADDRESS_REVERSE.toString(), typeOfProcess);
            }

            if(criterion[4] != null) {
                criterion[4] = InfoBetweenPrefixes(args, PREFIX_SKILL.toString(), PREFIX_SKILL_REVERSE.toString(), typeOfProcess);
            }
        }

        return criterion;
    }

    /**
     * Since filter form is like prefix/text/prefix, this function returns the text between given prefixes.
     */
    private String InfoBetweenPrefixes(String args, String prefixBegin, String prefixEnd, AtomicInteger typeOfProcess) {

        int beginLoc = args.indexOf(prefixBegin) + prefixBegin.length();
        int endLoc = args.indexOf(prefixEnd);

        if(beginLoc >= endLoc) {
            typeOfProcess.set(-1);
            return null;
        }

        for(int i = beginLoc; i < endLoc; i++) {

            if(args.charAt(i) == ' ')
                beginLoc++;

            else break;
        }

        for(int j = endLoc - 1; j > beginLoc; j--) {

            if(args.charAt(j) == ' ')
                endLoc--;

            else break;
        }

        return args.substring(beginLoc, endLoc).toLowerCase();
    }

    @Override
    public FilterCommand parse(String args) throws ParseException {

        args = args.trim().replaceAll(" +", " ");
        AtomicInteger typeOfProcess = new AtomicInteger(-1);
        args = FilterTypeDivider(args, typeOfProcess);

        String[] criterion = {" ", " ", " ", " ", " "};

        if(typeOfProcess.get() != 0)
            criterion = DivideFilterCriterion(args, typeOfProcess);

        if (typeOfProcess.get() == -1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }
        else if (typeOfProcess.get() == 1) {
            return new FilterCommand(args, criterion, 1);
        }
        else if (typeOfProcess.get() == 2) {
            return new FilterCommand(args, criterion, 2);
        }
        else {
            return new FilterCommand(args, criterion, 0);
        }
    }
}

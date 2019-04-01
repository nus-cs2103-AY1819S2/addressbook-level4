package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.JOBSAPPLY_DESC_ENGINEER;
import static seedu.address.logic.commands.CommandTestUtil.MAJOR_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PASTJOB_DESC_PROFESSOR;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.RACE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.SCHOOL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_JOBSAPPLY_ENGINEER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MAJOR_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASTJOB_PROFESSSOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RACE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHOOL_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

import seedu.address.logic.commands.SearchCommand;

public class SearchCommandParserTest {

    private SearchCommandParser parser = new SearchCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsSearchCommand() {
        // no leading and trailing whitespaces
        SearchCommand.PredicatePersonDescriptor descriptorAmy = new SearchCommand.PredicatePersonDescriptor();
        descriptorAmy.setName(new HashSet<>(Arrays.asList(VALID_NAME_AMY.split("\\s+"))));
        descriptorAmy.setPhone(new HashSet<>(Arrays.asList(VALID_PHONE_AMY.split("\\s+"))));
        descriptorAmy.setEmail(new HashSet<>(Arrays.asList(VALID_EMAIL_AMY.split("\\s+"))));
        descriptorAmy.setRace(new HashSet<>(Arrays.asList(VALID_RACE_AMY.split("\\s+"))));
        descriptorAmy.setAddress(new HashSet<>(Arrays.asList(VALID_ADDRESS_AMY.split("\\s+"))));
        descriptorAmy.setSchool(new HashSet<>(Arrays.asList(VALID_SCHOOL_AMY.split("\\s+"))));
        descriptorAmy.setMajor(new HashSet<>(Arrays.asList(VALID_MAJOR_AMY.split("\\s+"))));
        descriptorAmy.setGender(new HashSet<>(Arrays.asList(VALID_GENDER_AMY.split("\\s+"))));
        descriptorAmy.setNric(new HashSet<>(Arrays.asList(VALID_NRIC_AMY.split("\\s+"))));
        SearchCommand expectedSearchCommand = new SearchCommand(descriptorAmy);

        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + NRIC_DESC_AMY
            + GENDER_DESC_AMY + RACE_DESC_AMY + ADDRESS_DESC_AMY + MAJOR_DESC_AMY
            + SCHOOL_DESC_AMY, expectedSearchCommand);

        // test other fields
        SearchCommand.PredicatePersonDescriptor descriptorOtherFields = new SearchCommand.PredicatePersonDescriptor();
        descriptorOtherFields.setPastJobs(new HashSet<>(Arrays.asList(VALID_PASTJOB_PROFESSSOR.split("\\s+"))));
        descriptorOtherFields.setJobsApply(new HashSet<>(
            Arrays.asList(VALID_JOBSAPPLY_ENGINEER.split("\\s+"))));
        expectedSearchCommand = new SearchCommand(descriptorOtherFields);
        assertParseSuccess(parser, PASTJOB_DESC_PROFESSOR
            + JOBSAPPLY_DESC_ENGINEER, expectedSearchCommand);


    }

}

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GPA_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;

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
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.FilterCommand;

public class FilterCommandParserTest {

    private static final String AND_TYPE = "and ";
    private static final String OR_TYPE = "or ";

    private static final String NON_EXISTING_PREFIX = "h<";
    private static final String NON_EXISTING_PREFIX_FILTER_REVERSE = ">h";

    private static final String EMPTY_FIELD_ADDRESS = PREFIX_FILTER_ADDRESS + "" + PREFIX_FILTER_ADDRESS_REVERSE;
    private static final String EMPTY_FIELD_EMAIL = PREFIX_FILTER_EMAIL + "" + PREFIX_FILTER_EMAIL_REVERSE;
    private static final String EMPTY_FIELD_EDUCATION = PREFIX_FILTER_EDUCATION + "" + PREFIX_FILTER_EDUCATION_REVERSE;
    private static final String EMPTY_FIELD_ENDORSEMENT = PREFIX_FILTER_ENDORSEMENT + ""
            + PREFIX_FILTER_ENDORSEMENT_REVERSE;
    private static final String EMPTY_FIELD_GPA = PREFIX_FILTER_GPA + "" + PREFIX_FILTER_GPA_REVERSE;
    private static final String EMPTY_FIELD_NAME = PREFIX_FILTER_NAME + "" + PREFIX_FILTER_NAME_REVERSE;
    private static final String EMPTY_FIELD_PHONE = PREFIX_FILTER_PHONE + "" + PREFIX_FILTER_PHONE_REVERSE;
    private static final String EMPTY_FIELD_POS = PREFIX_FILTER_POS + "" + PREFIX_FILTER_POS_REVERSE;
    private static final String EMPTY_FIELD_SKILL = PREFIX_FILTER_SKILL + "" + PREFIX_FILTER_SKILL_REVERSE;
    private static final String EMPTY_FIELD_DEGREE = PREFIX_FILTER_DEGREE + "" + PREFIX_FILTER_DEGREE_REVERSE;

    private static final String INVALID_ENDORSEMENT1 =
            PREFIX_FILTER_ENDORSEMENT + "2.5" + PREFIX_FILTER_ENDORSEMENT_REVERSE;
    private static final String INVALID_ENDORSEMENT2 =
            PREFIX_FILTER_ENDORSEMENT + "text" + PREFIX_FILTER_ENDORSEMENT_REVERSE;
    private static final String INVALID_GPA1 = PREFIX_FILTER_GPA + "2..5" + PREFIX_FILTER_GPA_REVERSE;
    private static final String INVALID_GPA2 = PREFIX_FILTER_GPA + "text" + PREFIX_FILTER_GPA_REVERSE;
    private static final String INVALID_GPA3 = PREFIX_FILTER_GPA + "4.1" + PREFIX_FILTER_GPA_REVERSE;
    private static final String INVALID_GPA4 = PREFIX_FILTER_GPA + "-0.2" + PREFIX_FILTER_GPA_REVERSE;
    private static final String INVALID_PHONE1 = PREFIX_FILTER_PHONE + "2..5" + PREFIX_FILTER_PHONE_REVERSE;
    private static final String INVALID_PHONE2 = PREFIX_FILTER_PHONE + "text" + PREFIX_FILTER_PHONE_REVERSE;
    private static final String INVALID_PHONE3 = PREFIX_FILTER_PHONE + "-2" + PREFIX_FILTER_PHONE_REVERSE;
    private static final String INVALID_NAME1 = PREFIX_FILTER_NAME + "m8" + PREFIX_FILTER_NAME_REVERSE;
    private static final String INVALID_NAME2 = PREFIX_FILTER_NAME + "Alex*" + PREFIX_FILTER_NAME_REVERSE;

    private static final String VALID_ADDRESS = PREFIX_FILTER_ADDRESS + "street" + PREFIX_FILTER_ADDRESS_REVERSE;
    private static final String VALID_ADDRESS2 = PREFIX_FILTER_ADDRESS + "road" + PREFIX_FILTER_ADDRESS_REVERSE;
    private static final String VALID_EDUCATION = PREFIX_FILTER_EDUCATION + "NUS" + PREFIX_FILTER_EDUCATION_REVERSE;
    private static final String VALID_EDUCATION2 = PREFIX_FILTER_EDUCATION + "NTU" + PREFIX_FILTER_EDUCATION_REVERSE;
    private static final String VALID_EMAIL = PREFIX_FILTER_EMAIL + "e@ex" + PREFIX_FILTER_EMAIL_REVERSE;
    private static final String VALID_EMAIL2 = PREFIX_FILTER_EMAIL + "b@ex" + PREFIX_FILTER_EMAIL_REVERSE;
    private static final String VALID_ENDORSEMENT = PREFIX_FILTER_ENDORSEMENT + "2"
            + PREFIX_FILTER_ENDORSEMENT_REVERSE;
    private static final String VALID_ENDORSEMENT2 = PREFIX_FILTER_ENDORSEMENT + "3"
            + PREFIX_FILTER_ENDORSEMENT_REVERSE;
    private static final String VALID_GPA = PREFIX_FILTER_GPA + "3.1" + PREFIX_FILTER_GPA_REVERSE;
    private static final String VALID_GPA2 = PREFIX_FILTER_GPA + "2.7" + PREFIX_FILTER_GPA_REVERSE;
    private static final String VALID_NAME1 = PREFIX_FILTER_NAME + VALID_NAME_AMY + PREFIX_FILTER_NAME_REVERSE;
    private static final String VALID_NAME2 = PREFIX_FILTER_NAME + "Neill" + PREFIX_FILTER_NAME_REVERSE;
    private static final String VALID_PHONE = PREFIX_FILTER_PHONE + "92" + PREFIX_FILTER_PHONE_REVERSE;
    private static final String VALID_PHONE2 = PREFIX_FILTER_PHONE + "65" + PREFIX_FILTER_PHONE_REVERSE;
    private static final String VALID_POS = PREFIX_FILTER_POS + "Manager, Engineer" + PREFIX_FILTER_POS_REVERSE;
    private static final String VALID_POS2 = PREFIX_FILTER_POS + "Engineer, UI" + PREFIX_FILTER_POS_REVERSE;
    private static final String VALID_SKILL = PREFIX_FILTER_SKILL + "Java, C++" + PREFIX_FILTER_SKILL_REVERSE;
    private static final String VALID_SKILL2 = PREFIX_FILTER_SKILL + "Java, Python" + PREFIX_FILTER_SKILL_REVERSE;

    private static final String INVALID_DEGREE_INT1 = PREFIX_FILTER_DEGREE + "-1" + PREFIX_FILTER_DEGREE_REVERSE;
    private static final String INVALID_DEGREE_INT2 = PREFIX_FILTER_DEGREE + "5" + PREFIX_FILTER_DEGREE_REVERSE;
    private static final String INVALID_DEGREE_STRING1 = PREFIX_FILTER_DEGREE + "AHigHsChool"
            + PREFIX_FILTER_DEGREE_REVERSE;
    private static final String INVALID_DEGREE_STRING2 = PREFIX_FILTER_DEGREE + "AssoCiatees"
            + PREFIX_FILTER_DEGREE_REVERSE;
    private static final String INVALID_DEGREE_STRING3 = PREFIX_FILTER_DEGREE + "bachelor"
            + PREFIX_FILTER_DEGREE_REVERSE;

    private static final String VALID_DEGREE_INT1 = PREFIX_FILTER_DEGREE + "0" + PREFIX_FILTER_DEGREE_REVERSE;
    private static final String VALID_DEGREE_INT2 = PREFIX_FILTER_DEGREE + "1" + PREFIX_FILTER_DEGREE_REVERSE;
    private static final String VALID_DEGREE_INT3 = PREFIX_FILTER_DEGREE + "2" + PREFIX_FILTER_DEGREE_REVERSE;
    private static final String VALID_DEGREE_INT4 = PREFIX_FILTER_DEGREE + "3" + PREFIX_FILTER_DEGREE_REVERSE;
    private static final String VALID_DEGREE_INT5 = PREFIX_FILTER_DEGREE + "4" + PREFIX_FILTER_DEGREE_REVERSE;
    private static final String VALID_DEGREE_STRING1 = PREFIX_FILTER_DEGREE + "HigH sChool"
            + PREFIX_FILTER_DEGREE_REVERSE;
    private static final String VALID_DEGREE_STRING2 = PREFIX_FILTER_DEGREE + "AssoCiates"
            + PREFIX_FILTER_DEGREE_REVERSE;
    private static final String VALID_DEGREE_STRING3 = PREFIX_FILTER_DEGREE + "BachELOrs"
            + PREFIX_FILTER_DEGREE_REVERSE;
    private static final String VALID_DEGREE_STRING4 = PREFIX_FILTER_DEGREE + "MasTerS"
            + PREFIX_FILTER_DEGREE_REVERSE;
    private static final String VALID_DEGREE_STRING5 = PREFIX_FILTER_DEGREE + "pHD" + PREFIX_FILTER_DEGREE_REVERSE;


    private static final String MISSING_BEGIN_PREFIX_FILTER_EMAIL = VALID_EMAIL_AMY + PREFIX_FILTER_EMAIL_REVERSE;
    private static final String MISSING_END_PREFIX_FILTER_ADDRESS = PREFIX_FILTER_ADDRESS + VALID_ADDRESS_AMY;
    private static final String MISSING_END_PREFIX_FILTER_EDUCATION = PREFIX_FILTER_EDUCATION + VALID_GPA_AMY;
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE);

    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_missingParts_failure() {

        // no process type specified
        assertParseFailure(parser, VALID_NAME1, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, AND_TYPE, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, OR_TYPE, MESSAGE_INVALID_FORMAT);

        // no type and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPrefixPair_failure() {

        // only beginning prefix is used
        assertParseFailure(parser, AND_TYPE + MISSING_END_PREFIX_FILTER_ADDRESS,
                FilterCommandParser.MESSAGE_INPUT_NOT_IN_TRUE_FORM);

        // only ending prefix is used
        assertParseFailure(parser, OR_TYPE + MISSING_BEGIN_PREFIX_FILTER_EMAIL,
                FilterCommandParser.MESSAGE_INPUT_NOT_IN_TRUE_FORM);

        // false beginning and ending prefix couples are used
        assertParseFailure(parser, AND_TYPE + MISSING_END_PREFIX_FILTER_EDUCATION + PREFIX_FILTER_GPA_REVERSE,
                FilterCommandParser.MESSAGE_INPUT_NOT_IN_TRUE_FORM);

        // non-existing prefix is used all by itself
        assertParseFailure(parser, OR_TYPE + NON_EXISTING_PREFIX + "someText"
                + NON_EXISTING_PREFIX_FILTER_REVERSE, FilterCommandParser.MESSAGE_INPUT_NOT_IN_TRUE_FORM);
    }

    @Test
    public void parse_emptyFields_failure() {

        assertParseFailure(parser, AND_TYPE + EMPTY_FIELD_ADDRESS,
                FilterCommandParser.MESSAGE_INPUT_NOT_IN_TRUE_FORM);

        assertParseFailure(parser, OR_TYPE + EMPTY_FIELD_EDUCATION,
                FilterCommandParser.MESSAGE_INPUT_NOT_IN_TRUE_FORM);

        assertParseFailure(parser, AND_TYPE + EMPTY_FIELD_EMAIL,
                FilterCommandParser.MESSAGE_INPUT_NOT_IN_TRUE_FORM);

        assertParseFailure(parser, OR_TYPE + EMPTY_FIELD_ENDORSEMENT,
                FilterCommandParser.MESSAGE_INPUT_NOT_IN_TRUE_FORM);

        assertParseFailure(parser, AND_TYPE + EMPTY_FIELD_GPA,
                FilterCommandParser.MESSAGE_INPUT_NOT_IN_TRUE_FORM);

        assertParseFailure(parser, OR_TYPE + EMPTY_FIELD_NAME,
                FilterCommandParser.MESSAGE_INPUT_NOT_IN_TRUE_FORM);

        assertParseFailure(parser, AND_TYPE + EMPTY_FIELD_PHONE,
                FilterCommandParser.MESSAGE_INPUT_NOT_IN_TRUE_FORM);

        assertParseFailure(parser, OR_TYPE + EMPTY_FIELD_POS,
                FilterCommandParser.MESSAGE_INPUT_NOT_IN_TRUE_FORM);

        assertParseFailure(parser, AND_TYPE + EMPTY_FIELD_SKILL,
                FilterCommandParser.MESSAGE_INPUT_NOT_IN_TRUE_FORM);

        assertParseFailure(parser, OR_TYPE + EMPTY_FIELD_DEGREE,
                FilterCommandParser.MESSAGE_INPUT_NOT_IN_TRUE_FORM);
    }

    @Test
    public void parse_invalidFieldValues_failure() {

        // float value for endorsement
        assertParseFailure(parser, AND_TYPE + INVALID_ENDORSEMENT1,
                FilterCommandParser.MESSAGE_INPUT_NOT_IN_TRUE_FORM);

        // string value for endorsement
        assertParseFailure(parser, OR_TYPE + INVALID_ENDORSEMENT2,
                FilterCommandParser.MESSAGE_INPUT_NOT_IN_TRUE_FORM);

        // float value for phone
        assertParseFailure(parser, AND_TYPE + INVALID_PHONE1,
                FilterCommandParser.MESSAGE_INPUT_NOT_IN_TRUE_FORM);

        // string value for phone
        assertParseFailure(parser, OR_TYPE + INVALID_PHONE2,
                FilterCommandParser.MESSAGE_INPUT_NOT_IN_TRUE_FORM);

        // negative value for phone
        assertParseFailure(parser, OR_TYPE + INVALID_PHONE3,
                FilterCommandParser.MESSAGE_INPUT_NOT_IN_TRUE_FORM);

        // float value for gpa with 2 dots
        assertParseFailure(parser, AND_TYPE + INVALID_GPA1,
                FilterCommandParser.MESSAGE_INPUT_NOT_IN_TRUE_FORM);

        // string value for gpa
        assertParseFailure(parser, OR_TYPE + INVALID_GPA2,
                FilterCommandParser.MESSAGE_INPUT_NOT_IN_TRUE_FORM);

        // float value for gpa bigger than 4.0
        assertParseFailure(parser, OR_TYPE + INVALID_GPA3,
                FilterCommandParser.MESSAGE_INPUT_NOT_IN_TRUE_FORM);

        // negative float value for gpa
        assertParseFailure(parser, OR_TYPE + INVALID_GPA4,
                FilterCommandParser.MESSAGE_INPUT_NOT_IN_TRUE_FORM);

        // Name has a numerical character
        assertParseFailure(parser, AND_TYPE + INVALID_NAME1,
                FilterCommandParser.MESSAGE_INPUT_NOT_IN_TRUE_FORM);

        // Name has a non-alphanumerical character, in this case '*'
        assertParseFailure(parser, OR_TYPE + INVALID_NAME2,
                FilterCommandParser.MESSAGE_INPUT_NOT_IN_TRUE_FORM);
    }

    @Test
    public void parse_validFields_success() {

        String userInputAddress = AND_TYPE + VALID_ADDRESS;
        String[] criterionAddress = {null, null, null, "street".trim().toLowerCase(), null, null, null, null, null,
            null};
        FilterCommand expectedAddressCommand = new FilterCommand(criterionAddress, 2);
        assertParseSuccess(parser, userInputAddress, expectedAddressCommand);

        String userInputEducation = AND_TYPE + VALID_EDUCATION;
        String[] criterionEducation = {null, null, null, null, null, null, null, "NUS".trim().toLowerCase(),
            null, null};
        FilterCommand expectedEducationCommand = new FilterCommand(criterionEducation, 2);
        assertParseSuccess(parser, userInputEducation, expectedEducationCommand);

        String userInputEmail = AND_TYPE + VALID_EMAIL;
        String[] criterionEmail = {null, null, "e@ex".trim().toLowerCase(), null, null, null, null, null, null, null};
        FilterCommand expectedEmailCommand = new FilterCommand(criterionEmail, 2);
        assertParseSuccess(parser, userInputEmail, expectedEmailCommand);

        String userInputEndorsement = AND_TYPE + VALID_ENDORSEMENT;
        String[] criterionEndorsement = {null, null, null, null, null, null, null, null, "2".trim().toLowerCase(),
            null};
        FilterCommand expectedEndorsementCommand = new FilterCommand(criterionEndorsement, 2);
        assertParseSuccess(parser, userInputEndorsement, expectedEndorsementCommand);

        String userInputGpa = AND_TYPE + VALID_GPA;
        String[] criterionGpa = {null, null, null, null, null, null, "3.1".trim().toLowerCase(), null, null, null};
        FilterCommand expectedGpaCommand = new FilterCommand(criterionGpa, 2);
        assertParseSuccess(parser, userInputGpa, expectedGpaCommand);

        String userInputName = OR_TYPE + VALID_NAME2;
        String[] criterionName = {"Neill".trim().toLowerCase(), null, null, null, null, null, null, null, null, null};
        FilterCommand expectedNameCommand = new FilterCommand(criterionName, 1);
        assertParseSuccess(parser, userInputName, expectedNameCommand);

        String userInputPhone = OR_TYPE + VALID_PHONE;
        String[] criterionPhone = {null, "92".trim().toLowerCase(), null, null, null, null, null, null, null, null};
        FilterCommand expectedPhoneCommand = new FilterCommand(criterionPhone, 1);
        assertParseSuccess(parser, userInputPhone, expectedPhoneCommand);

        String userInputPos = OR_TYPE + VALID_POS;
        String[] criterionPos = {null, null, null, null, null, "Manager, Engineer".trim().toLowerCase(),
            null, null, null, null};
        FilterCommand expectedPosCommand = new FilterCommand(criterionPos, 1);
        assertParseSuccess(parser, userInputPos, expectedPosCommand);

        String userInputSkill = OR_TYPE + VALID_SKILL;
        String[] criterionSkill = {null, null, null, null, "Java, C++".trim().toLowerCase(), null, null, null, null,
            null};
        FilterCommand expectedSkillCommand = new FilterCommand(criterionSkill, 1);
        assertParseSuccess(parser, userInputSkill, expectedSkillCommand);
    }

    /**
     * Degree is added later, so its input validity tests are added later to the testing class
     */
    @Test
    public void parse_validDegreeInteger_success() {
        String userInput1 = AND_TYPE + VALID_DEGREE_INT1;
        String[] criterion1 = {null, null, null, null, null, null, null, null, null, "0"};
        FilterCommand expectedAddressCommand = new FilterCommand(criterion1, 2);
        assertParseSuccess(parser, userInput1, expectedAddressCommand);

        String userInput2 = OR_TYPE + VALID_DEGREE_INT2;
        String[] criterion2 = {null, null, null, null, null, null, null, null, null, "1"};
        expectedAddressCommand = new FilterCommand(criterion2, 1);
        assertParseSuccess(parser, userInput2, expectedAddressCommand);

        String userInput3 = AND_TYPE + VALID_DEGREE_INT3;
        String[] criterion3 = {null, null, null, null, null, null, null, null, null, "2"};
        expectedAddressCommand = new FilterCommand(criterion3, 2);
        assertParseSuccess(parser, userInput3, expectedAddressCommand);

        String userInput4 = OR_TYPE + VALID_DEGREE_INT4;
        String[] criterion4 = {null, null, null, null, null, null, null, null, null, "3"};
        expectedAddressCommand = new FilterCommand(criterion4, 1);
        assertParseSuccess(parser, userInput4, expectedAddressCommand);

        String userInput5 = AND_TYPE + VALID_DEGREE_INT5;
        String[] criterion5 = {null, null, null, null, null, null, null, null, null, "4"};
        expectedAddressCommand = new FilterCommand(criterion5, 2);
        assertParseSuccess(parser, userInput5, expectedAddressCommand);
    }

    @Test
    public void parse_validDegreeString_success() {
        String userInput1 = AND_TYPE + VALID_DEGREE_STRING1;
        String[] criterion1 = {null, null, null, null, null, null, null, null, null, "0"};
        FilterCommand expectedAddressCommand = new FilterCommand(criterion1, 2);
        assertParseSuccess(parser, userInput1, expectedAddressCommand);

        String userInput2 = OR_TYPE + VALID_DEGREE_STRING2;
        String[] criterion2 = {null, null, null, null, null, null, null, null, null, "1"};
        expectedAddressCommand = new FilterCommand(criterion2, 1);
        assertParseSuccess(parser, userInput2, expectedAddressCommand);

        String userInput3 = AND_TYPE + VALID_DEGREE_STRING3;
        String[] criterion3 = {null, null, null, null, null, null, null, null, null, "2"};
        expectedAddressCommand = new FilterCommand(criterion3, 2);
        assertParseSuccess(parser, userInput3, expectedAddressCommand);

        String userInput4 = OR_TYPE + VALID_DEGREE_STRING4;
        String[] criterion4 = {null, null, null, null, null, null, null, null, null, "3"};
        expectedAddressCommand = new FilterCommand(criterion4, 1);
        assertParseSuccess(parser, userInput4, expectedAddressCommand);

        String userInput5 = AND_TYPE + VALID_DEGREE_STRING5;
        String[] criterion5 = {null, null, null, null, null, null, null, null, null, "4"};
        expectedAddressCommand = new FilterCommand(criterion5, 2);
        assertParseSuccess(parser, userInput5, expectedAddressCommand);
    }

    @Test
    public void parse_repeatedDegreeIntegers_success() {
        String userInput1 = AND_TYPE + VALID_DEGREE_INT2 + " " + VALID_DEGREE_INT1;
        String[] criterion1 = {null, null, null, null, null, null, null, null, null, "1"};
        FilterCommand expectedAddressCommand = new FilterCommand(criterion1, 2);
        assertParseSuccess(parser, userInput1, expectedAddressCommand);
    }

    @Test
    public void parse_repeatedDegreeIntegerString_success() {
        String userInput1 = AND_TYPE + VALID_DEGREE_INT3 + " " + VALID_DEGREE_STRING1;
        String[] criterion1 = {null, null, null, null, null, null, null, null, null, "2"};
        FilterCommand expectedAddressCommand = new FilterCommand(criterion1, 2);
        assertParseSuccess(parser, userInput1, expectedAddressCommand);
    }

    @Test
    public void parse_repeatedDegreeStringInteger_success() {
        String userInput1 = AND_TYPE + VALID_DEGREE_STRING2 + " " + VALID_DEGREE_INT4;
        String[] criterion1 = {null, null, null, null, null, null, null, null, null, "1"};
        FilterCommand expectedAddressCommand = new FilterCommand(criterion1, 2);
        assertParseSuccess(parser, userInput1, expectedAddressCommand);
    }

    @Test
    public void parse_repeatedDegreeStringString_success() {
        String userInput1 = AND_TYPE + VALID_DEGREE_STRING5 + " " + VALID_DEGREE_STRING4;
        String[] criterion1 = {null, null, null, null, null, null, null, null, null, "4"};
        FilterCommand expectedAddressCommand = new FilterCommand(criterion1, 2);
        assertParseSuccess(parser, userInput1, expectedAddressCommand);
    }

    @Test
    public void parse_invalidDegreeInteger_failure() {
        assertParseFailure(parser, AND_TYPE + INVALID_DEGREE_INT1,
                FilterCommandParser.MESSAGE_INPUT_NOT_IN_TRUE_FORM);

        assertParseFailure(parser, OR_TYPE + INVALID_DEGREE_INT2,
                FilterCommandParser.MESSAGE_INPUT_NOT_IN_TRUE_FORM);
    }

    @Test
    public void parse_invalidDegreeString_failure() {
        assertParseFailure(parser, AND_TYPE + INVALID_DEGREE_STRING1,
                FilterCommandParser.MESSAGE_INPUT_NOT_IN_TRUE_FORM);

        assertParseFailure(parser, AND_TYPE + INVALID_DEGREE_STRING2,
                FilterCommandParser.MESSAGE_INPUT_NOT_IN_TRUE_FORM);

        assertParseFailure(parser, AND_TYPE + INVALID_DEGREE_STRING3,
                FilterCommandParser.MESSAGE_INPUT_NOT_IN_TRUE_FORM);
    }

    /**
     * If multiple times a field is called, the first one will be taken
     */
    @Test
    public void parse_repeatedFields_success() {

        String userInputAddress = AND_TYPE + VALID_ADDRESS + " " + VALID_ADDRESS2;
        String[] criterionAddress = {null, null, null, "street".trim().toLowerCase(), null, null, null,
            null, null, null};
        FilterCommand expectedAddressCommand = new FilterCommand(criterionAddress, 2);
        assertParseSuccess(parser, userInputAddress, expectedAddressCommand);

        String userInputEducation = AND_TYPE + VALID_EDUCATION + " " + VALID_EDUCATION2;
        String[] criterionEducation = {null, null, null, null, null, null, null, "NUS".trim().toLowerCase(),
            null, null};
        FilterCommand expectedEducationCommand = new FilterCommand(criterionEducation, 2);
        assertParseSuccess(parser, userInputEducation, expectedEducationCommand);

        String userInputEmail = AND_TYPE + VALID_EMAIL + " " + VALID_EMAIL2;
        String[] criterionEmail = {null, null, "e@ex".trim().toLowerCase(), null, null, null, null, null, null, null};
        FilterCommand expectedEmailCommand = new FilterCommand(criterionEmail, 2);
        assertParseSuccess(parser, userInputEmail, expectedEmailCommand);

        String userInputEndorsement = AND_TYPE + VALID_ENDORSEMENT + " " + VALID_ENDORSEMENT2;
        String[] criterionEndorsement = {null, null, null, null, null, null, null, null,
            "2".trim().toLowerCase(), null};
        FilterCommand expectedEndorsementCommand = new FilterCommand(criterionEndorsement, 2);
        assertParseSuccess(parser, userInputEndorsement, expectedEndorsementCommand);

        String userInputGpa = AND_TYPE + VALID_GPA + " " + VALID_GPA2;
        String[] criterionGpa = {null, null, null, null, null, null, "3.1".trim().toLowerCase(), null, null, null};
        FilterCommand expectedGpaCommand = new FilterCommand(criterionGpa, 2);
        assertParseSuccess(parser, userInputGpa, expectedGpaCommand);

        String userInputName = OR_TYPE + VALID_NAME2 + " " + VALID_NAME1;
        String[] criterionName = {"Neill".trim().toLowerCase(), null, null, null, null, null, null,
            null, null, null};
        FilterCommand expectedNameCommand = new FilterCommand(criterionName, 1);
        assertParseSuccess(parser, userInputName, expectedNameCommand);

        String userInputPhone = OR_TYPE + VALID_PHONE + " " + VALID_PHONE2;
        String[] criterionPhone = {null, "92".trim().toLowerCase(), null, null, null, null, null,
            null, null, null};
        FilterCommand expectedPhoneCommand = new FilterCommand(criterionPhone, 1);
        assertParseSuccess(parser, userInputPhone, expectedPhoneCommand);

        String userInputPos = OR_TYPE + VALID_POS + " " + VALID_POS2;
        String[] criterionPos = {null, null, null, null, null, "Manager, Engineer".trim().toLowerCase(),
            null, null, null, null};
        FilterCommand expectedPosCommand = new FilterCommand(criterionPos, 1);
        assertParseSuccess(parser, userInputPos, expectedPosCommand);

        String userInputSkill = OR_TYPE + VALID_SKILL + " " + VALID_SKILL2;
        String[] criterionSkill = {null, null, null, null, "Java, C++".trim().toLowerCase(), null, null,
            null, null, null};
        FilterCommand expectedSkillCommand = new FilterCommand(criterionSkill, 1);
        assertParseSuccess(parser, userInputSkill, expectedSkillCommand);

    }
}

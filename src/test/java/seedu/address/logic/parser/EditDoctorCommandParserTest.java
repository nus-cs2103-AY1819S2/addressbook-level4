package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC_ALVINA;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC_STEVEN;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GENDER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SPECIALISATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_YEAR_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_ALVINA;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_ALVINA;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_STEVEN;
import static seedu.address.logic.commands.CommandTestUtil.SPECIALISATION_DESC_ACUPUNCTURE;
import static seedu.address.logic.commands.CommandTestUtil.SPECIALISATION_DESC_GENERAL;
import static seedu.address.logic.commands.CommandTestUtil.SPECIALISATION_DESC_MASSAGE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_ALVINA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_STEVEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ALVINA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_STEVEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SPECIALISATION_ACUPUNCTURE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SPECIALISATION_GENERAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SPECIALISATION_MASSAGE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_YEAR_ALVINA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_YEAR_STEVEN;
import static seedu.address.logic.commands.CommandTestUtil.YEAR_DESC_ALVINA;
import static seedu.address.logic.commands.CommandTestUtil.YEAR_DESC_STEVEN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPECIALISATION;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.doctor.EditDoctorCommand;
import seedu.address.logic.commands.doctor.EditDoctorCommand.EditDoctorDescriptor;
import seedu.address.logic.parser.doctor.EditDoctorCommandParser;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.doctor.Year;
import seedu.address.model.person.specialisation.Specialisation;
import seedu.address.testutil.EditDoctorDescriptorBuilder;

public class EditDoctorCommandParserTest {

    private static final String SPEC_EMPTY = " " + PREFIX_SPECIALISATION;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditDoctorCommand.MESSAGE_USAGE);

    private EditDoctorCommandParser parser = new EditDoctorCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_ALVINA, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditDoctorCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_ALVINA, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_ALVINA, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_GENDER_DESC, Gender.MESSAGE_CONSTRAINTS); // invalid gender
        assertParseFailure(parser, "1" + INVALID_YEAR_DESC, Year.MESSAGE_CONSTRAINTS); // invalid year of exp
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + INVALID_SPECIALISATION_DESC,
                Specialisation.MESSAGE_CONSTRAINTS); // invalid specialisation

        // invalid phone followed by valid year of exp
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC + YEAR_DESC_ALVINA, Phone.MESSAGE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + PHONE_DESC_ALVINA + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_SPECIALISATION} alone will reset the specs of the {@code Doctor} being edited,
        // parsing it together with a valid spec results in error
        assertParseFailure(parser, "1" + SPECIALISATION_DESC_GENERAL + SPECIALISATION_DESC_MASSAGE
                + SPEC_EMPTY, Specialisation.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + SPECIALISATION_DESC_GENERAL + SPEC_EMPTY
                + SPECIALISATION_DESC_ACUPUNCTURE, Specialisation.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + SPEC_EMPTY + SPECIALISATION_DESC_ACUPUNCTURE
                + SPECIALISATION_DESC_GENERAL, Specialisation.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_GENDER_DESC
                        + VALID_YEAR_STEVEN + VALID_PHONE_STEVEN,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_STEVEN + SPECIALISATION_DESC_GENERAL
                + YEAR_DESC_ALVINA + GENDER_DESC_ALVINA + NAME_DESC_ALVINA + SPECIALISATION_DESC_ACUPUNCTURE;

        EditDoctorDescriptor descriptor = new EditDoctorDescriptorBuilder().withName(VALID_NAME_ALVINA)
                .withGender(VALID_GENDER_ALVINA).withYear(VALID_YEAR_ALVINA).withPhone(VALID_PHONE_STEVEN)
                .withSpecs(VALID_SPECIALISATION_GENERAL, VALID_SPECIALISATION_ACUPUNCTURE).build();
        EditDoctorCommand expectedCommand = new EditDoctorCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_STEVEN + YEAR_DESC_ALVINA;

        EditDoctorDescriptor descriptor = new EditDoctorDescriptorBuilder().withPhone(VALID_PHONE_STEVEN)
                .withYear(VALID_YEAR_ALVINA).build();
        EditDoctorCommand expectedCommand = new EditDoctorCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + NAME_DESC_ALVINA;
        EditDoctorDescriptor descriptor = new EditDoctorDescriptorBuilder().withName(VALID_NAME_ALVINA).build();
        EditDoctorCommand expectedCommand = new EditDoctorCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // gender
        userInput = targetIndex.getOneBased() + GENDER_DESC_ALVINA;
        descriptor = new EditDoctorDescriptorBuilder().withGender(VALID_GENDER_ALVINA).build();
        expectedCommand = new EditDoctorCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // year
        userInput = targetIndex.getOneBased() + YEAR_DESC_ALVINA;
        descriptor = new EditDoctorDescriptorBuilder().withYear(VALID_YEAR_ALVINA).build();
        expectedCommand = new EditDoctorCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // specialisations
        userInput = targetIndex.getOneBased() + SPECIALISATION_DESC_GENERAL;
        descriptor = new EditDoctorDescriptorBuilder().withSpecs(VALID_SPECIALISATION_GENERAL).build();
        expectedCommand = new EditDoctorCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_ALVINA + GENDER_DESC_ALVINA
                + SPECIALISATION_DESC_GENERAL + PHONE_DESC_ALVINA + GENDER_DESC_ALVINA + SPECIALISATION_DESC_GENERAL
                + PHONE_DESC_STEVEN + YEAR_DESC_STEVEN + SPECIALISATION_DESC_MASSAGE;

        EditDoctorDescriptor descriptor = new EditDoctorDescriptorBuilder()
                .withGender(VALID_GENDER_ALVINA).withYear(VALID_YEAR_STEVEN).withPhone(VALID_PHONE_STEVEN)
                .withSpecs(VALID_SPECIALISATION_GENERAL, VALID_SPECIALISATION_MASSAGE)
                .build();
        EditDoctorCommand expectedCommand = new EditDoctorCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_STEVEN;
        EditDoctorDescriptor descriptor = new EditDoctorDescriptorBuilder().withPhone(VALID_PHONE_STEVEN).build();
        EditDoctorCommand expectedCommand = new EditDoctorCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + GENDER_DESC_STEVEN + YEAR_DESC_STEVEN + INVALID_PHONE_DESC
                + PHONE_DESC_STEVEN;
        descriptor = new EditDoctorDescriptorBuilder()
                .withGender(VALID_GENDER_STEVEN).withYear(VALID_YEAR_STEVEN).withPhone(VALID_PHONE_STEVEN)
                .build();
        expectedCommand = new EditDoctorCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetSpecs_failure() {
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + SPEC_EMPTY;

        EditDoctorDescriptor descriptor = new EditDoctorDescriptorBuilder().withSpecs().build();

        assertParseFailure(parser, userInput, Specialisation.MESSAGE_CONSTRAINTS);
    }
}

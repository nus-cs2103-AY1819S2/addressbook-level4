package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NRIC_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ORGANIZATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SKILLS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_ANDY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BETTY;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_ANDY;
import static seedu.address.logic.commands.CommandTestUtil.ORGANIZATION_DESC_ANDY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_ANDY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BETTY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.SKILLS_DESC_ANDY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ANDY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_ANDY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORGANIZATION_ANDY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_ANDY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BETTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditHealthWorkerCommand;
import seedu.address.logic.commands.EditHealthWorkerCommand.EditHealthWorkerDescriptor;
import seedu.address.logic.commands.request.EditRequestCommand;
import seedu.address.logic.commands.request.EditRequestCommand.EditRequestDescriptor;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Phone;
import seedu.address.model.person.healthworker.Organization;
import seedu.address.model.tag.Specialisation;
import seedu.address.testutil.EditHealthWorkerDescriptorBuilder;
import seedu.address.testutil.EditRequestDescriptorBuilder;

public class EditCommandParserTest {

    private static final String MODE_HEALTHWORKER = CommandMode.MODE_HEALTHWORKER + " ";
    private static final String MODE_REQUEST = CommandMode.MODE_REQUEST + " ";

    private static final String INVALID_EDIT_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommandParser.INVALID_COMMAND_USAGE);
    private static final String INVALID_HEALTHWORKER_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditHealthWorkerCommand.MESSAGE_USAGE);
    private static final String INVALID_REQUEST_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditRequestCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_healthWorker_missingParts() {
        // no index specified
        assertParseFailure(parser, MODE_HEALTHWORKER + VALID_NAME_AMY, INVALID_HEALTHWORKER_FORMAT);

        // no field specified
        assertParseFailure(parser, MODE_HEALTHWORKER + "1", EditHealthWorkerCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", INVALID_EDIT_FORMAT);
    }

    @Test
    public void parse_request_missingParts() {
        // no index specified
        assertParseFailure(parser, MODE_REQUEST + VALID_NAME_AMY, INVALID_REQUEST_FORMAT);

        // no field specified
        assertParseFailure(parser, MODE_REQUEST + "1", EditRequestCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", INVALID_EDIT_FORMAT);
    }

    @Test
    public void parse_healthWorker_invalidPreamble() {
        // negative index
        assertParseFailure(parser, MODE_HEALTHWORKER + "-5" + NAME_DESC_AMY, INVALID_HEALTHWORKER_FORMAT);

        // zero index
        assertParseFailure(parser, MODE_HEALTHWORKER + "0" + NAME_DESC_AMY, INVALID_HEALTHWORKER_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, MODE_HEALTHWORKER + "1 some random string", INVALID_HEALTHWORKER_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, MODE_HEALTHWORKER + "1 d/ string", INVALID_HEALTHWORKER_FORMAT);
    }

    @Test
    public void parse_request_invalidPreamble() {
        // negative index
        assertParseFailure(parser, MODE_REQUEST + "-5" + NAME_DESC_AMY, INVALID_REQUEST_FORMAT);

        // zero index
        assertParseFailure(parser, MODE_REQUEST + "0" + NAME_DESC_AMY, INVALID_REQUEST_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, MODE_REQUEST + "1 some random string", INVALID_REQUEST_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, MODE_REQUEST + "1 d/ string", INVALID_REQUEST_FORMAT);
    }

    @Test
    public void parse_healthWorker_invalidValue() {
        // invalid name
        assertParseFailure(parser, MODE_HEALTHWORKER + "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS);
        // invalid phone
        assertParseFailure(parser, MODE_HEALTHWORKER + "1" + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);
        // invalid nric
        assertParseFailure(parser, MODE_HEALTHWORKER + "1" + INVALID_NRIC_DESC, Nric.MESSAGE_CONSTRAINTS);


        // invalid phone followed by valid name
        assertParseFailure(parser, MODE_HEALTHWORKER + "1" + INVALID_PHONE_DESC + NAME_DESC_ANDY,
                Phone.MESSAGE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        assertParseFailure(parser, MODE_HEALTHWORKER + "1" + PHONE_DESC_BOB + INVALID_PHONE_DESC,
                Phone.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, MODE_HEALTHWORKER + "1" + INVALID_NAME_DESC + VALID_PHONE_AMY,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_request_invalidValue() {
        // invalid name
        assertParseFailure(parser, MODE_REQUEST + "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS);
        // invalid phone
        assertParseFailure(parser, MODE_REQUEST + "1" + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);
        // invalid nric
        assertParseFailure(parser, MODE_REQUEST + "1" + INVALID_NRIC_DESC, Nric.MESSAGE_CONSTRAINTS);
        // invalid address
        assertParseFailure(parser, MODE_REQUEST + "1" + INVALID_ADDRESS_DESC, Address.MESSAGE_CONSTRAINTS);

        // invalid phone followed by valid name
        assertParseFailure(parser, MODE_REQUEST + "1" + INVALID_PHONE_DESC + NAME_DESC_ANDY,
                Phone.MESSAGE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        assertParseFailure(parser, MODE_REQUEST + "1" + PHONE_DESC_BOB + INVALID_PHONE_DESC,
                Phone.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, MODE_REQUEST + "1" + INVALID_NAME_DESC + VALID_PHONE_AMY,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_healthWorker_allFields() {
        Index targetIndex = INDEX_SECOND;
        String userInput = MODE_HEALTHWORKER + targetIndex.getOneBased() + PHONE_DESC_BOB
                + NAME_DESC_AMY + NRIC_DESC_AMY;

        EditHealthWorkerDescriptor descriptor = new EditHealthWorkerDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB).withNric(VALID_NRIC_AMY).build();
        EditHealthWorkerCommand expectedCommand = new EditHealthWorkerCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_request_allFields() {
        // TODO: Some fields missing for EditRequestDescriptorBuilder
    }

    @Test
    public void parse_healthWorker_someFields() {
        Index targetIndex = INDEX_FIRST;
        String userInput =  MODE_HEALTHWORKER + targetIndex.getOneBased() + PHONE_DESC_BOB;

        EditHealthWorkerDescriptor descriptor = new EditHealthWorkerDescriptorBuilder()
                .withPhone(VALID_PHONE_BOB).build();
        EditHealthWorkerCommand expectedCommand = new EditHealthWorkerCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_request_someFields() {
        Index targetIndex = INDEX_FIRST;
        String userInput =  MODE_REQUEST + targetIndex.getOneBased() + PHONE_DESC_BOB;

        EditRequestDescriptor descriptor = new EditRequestDescriptorBuilder().withPhone(VALID_PHONE_BOB).build();
        EditRequestCommand expectedCommand = new EditRequestCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_healthWorker_repeatedFields() {
        Index targetIndex = INDEX_FIRST;
        String userInput = MODE_HEALTHWORKER + targetIndex.getOneBased() + PHONE_DESC_AMY
                + PHONE_DESC_AMY + PHONE_DESC_BOB;

        EditHealthWorkerDescriptor descriptor = new EditHealthWorkerDescriptorBuilder()
                .withPhone(VALID_PHONE_BOB).build();
        EditHealthWorkerCommand expectedCommand = new EditHealthWorkerCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_request_repeatedFields() {
        Index targetIndex = INDEX_FIRST;
        String userInput = MODE_REQUEST + targetIndex.getOneBased() + PHONE_DESC_AMY
                + PHONE_DESC_AMY + PHONE_DESC_BOB;

        EditRequestDescriptor descriptor = new EditRequestDescriptorBuilder().withPhone(VALID_PHONE_BOB).build();
        EditRequestCommand expectedCommand = new EditRequestCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST;
        String userInput = MODE_HEALTHWORKER + targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_BOB;
        EditHealthWorkerDescriptor descriptor = new EditHealthWorkerDescriptorBuilder()
                .withPhone(VALID_PHONE_BOB).build();
        EditHealthWorkerCommand expectedCommand = new EditHealthWorkerCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = MODE_HEALTHWORKER + targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_BOB;
        descriptor = new EditHealthWorkerDescriptorBuilder()
                .withPhone(VALID_PHONE_BOB).build();
        expectedCommand = new EditHealthWorkerCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parseEditHealthWorker_invalidFields() {
        // no field specified
        assertParseFailure(parser, MODE_HEALTHWORKER + "1", EditHealthWorkerCommand.MESSAGE_NOT_EDITED);

        // invalid field descriptions
        assertParseFailure(parser, MODE_HEALTHWORKER + "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, MODE_HEALTHWORKER + "1" + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, MODE_HEALTHWORKER + "1" + INVALID_NRIC_DESC, Nric.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, MODE_HEALTHWORKER + "1" + INVALID_ORGANIZATION_DESC,
                Organization.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, MODE_HEALTHWORKER + "1" + INVALID_SKILLS_DESC,
                Specialisation.MESSAGE_CONSTRAINTS);

        // valid input followed by invalid input
        assertParseFailure(parser, MODE_HEALTHWORKER + "1" + PHONE_DESC_ANDY + INVALID_PHONE_DESC,
                Phone.MESSAGE_CONSTRAINTS);

        // multiple invalid fields, only first is captured
        assertParseFailure(parser, MODE_HEALTHWORKER + "1" + INVALID_NAME_DESC + INVALID_PHONE_DESC
                        + INVALID_ADDRESS_DESC, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parseEditHealthWorker_validFields() {
        // some fields specified
        Index targetIndex = INDEX_FIRST;
        String input = MODE_HEALTHWORKER + targetIndex.getOneBased() + NAME_DESC_ANDY + PHONE_DESC_BETTY;
        EditHealthWorkerDescriptor descriptor = new EditHealthWorkerDescriptorBuilder().withName(VALID_NAME_ANDY)
                .withPhone(VALID_PHONE_BETTY).build();
        EditHealthWorkerCommand expectedCommand = new EditHealthWorkerCommand(targetIndex, descriptor);

        assertParseSuccess(parser, input, expectedCommand);

        // all fields specified
        input = MODE_HEALTHWORKER + targetIndex.getOneBased() + NAME_DESC_ANDY + PHONE_DESC_BETTY + NRIC_DESC_ANDY
                + ORGANIZATION_DESC_ANDY + SKILLS_DESC_ANDY;
        descriptor = new EditHealthWorkerDescriptorBuilder().withName(VALID_NAME_ANDY)
                .withPhone(VALID_PHONE_BETTY).withNric(VALID_NRIC_ANDY).withOrganization(VALID_ORGANIZATION_ANDY)
                .withSkills(Specialisation.GENERAL_PRACTICE.name(), Specialisation.PHYSIOTHERAPY.name()).build();
        expectedCommand = new EditHealthWorkerCommand(targetIndex, descriptor);

        assertParseSuccess(parser, input, expectedCommand);

        // valid name
        input = MODE_HEALTHWORKER + targetIndex.getOneBased() + NAME_DESC_ANDY;
        descriptor = new EditHealthWorkerDescriptorBuilder().withName(VALID_NAME_ANDY).build();
        expectedCommand = new EditHealthWorkerCommand(targetIndex, descriptor);
        assertParseSuccess(parser, input, expectedCommand);

        // valid phone
        input = MODE_HEALTHWORKER + targetIndex.getOneBased() + PHONE_DESC_ANDY;
        descriptor = new EditHealthWorkerDescriptorBuilder().withPhone(VALID_PHONE_ANDY).build();
        expectedCommand = new EditHealthWorkerCommand(targetIndex, descriptor);
        assertParseSuccess(parser, input, expectedCommand);

        // valid name
        input = MODE_HEALTHWORKER + targetIndex.getOneBased() + ORGANIZATION_DESC_ANDY;
        descriptor = new EditHealthWorkerDescriptorBuilder().withOrganization(VALID_ORGANIZATION_ANDY).build();
        expectedCommand = new EditHealthWorkerCommand(targetIndex, descriptor);
        assertParseSuccess(parser, input, expectedCommand);

        // valid skills
        input = MODE_HEALTHWORKER + targetIndex.getOneBased() + SKILLS_DESC_ANDY;
        descriptor = new EditHealthWorkerDescriptorBuilder().withSkills(Specialisation.GENERAL_PRACTICE.name(),
                Specialisation.PHYSIOTHERAPY.name()).build();
        expectedCommand = new EditHealthWorkerCommand(targetIndex, descriptor);
        assertParseSuccess(parser, input, expectedCommand);

        // valid nric
        input = MODE_HEALTHWORKER + targetIndex.getOneBased() + NRIC_DESC_ANDY;
        descriptor = new EditHealthWorkerDescriptorBuilder().withNric(VALID_NRIC_ANDY).build();
        expectedCommand = new EditHealthWorkerCommand(targetIndex, descriptor);
        assertParseSuccess(parser, input, expectedCommand);

        // multiple fields specified, accepts last
        input = MODE_HEALTHWORKER + targetIndex.getOneBased() + NAME_DESC_BETTY + NAME_DESC_ANDY + PHONE_DESC_BETTY;
        descriptor = new EditHealthWorkerDescriptorBuilder().withName(VALID_NAME_ANDY)
                .withPhone(VALID_PHONE_BETTY).build();
        expectedCommand = new EditHealthWorkerCommand(targetIndex, descriptor);

        assertParseSuccess(parser, input, expectedCommand);

        // invalid field followed by valid field
        input = MODE_HEALTHWORKER + targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_ANDY;
        descriptor = new EditHealthWorkerDescriptorBuilder().withPhone(VALID_PHONE_ANDY).build();
        expectedCommand = new EditHealthWorkerCommand(targetIndex, descriptor);

        assertParseSuccess(parser, input, expectedCommand);
    }
}

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.BATCHNUMBER_DESC_AMOXICILLIN;
import static seedu.address.logic.commands.CommandTestUtil.BATCHNUMBER_DESC_GABAPENTIN;
import static seedu.address.logic.commands.CommandTestUtil.EXPIRY_DESC_AMOXICILLIN;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_BATCHNUMBER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EXPIRY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_QUANTITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.QUANTITY_DESC_AMOXICILLIN;
import static seedu.address.logic.commands.CommandTestUtil.QUANTITY_DESC_GABAPENTIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BATCHNUMBER_AMOXICILLIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BATCHNUMBER_GABAPENTIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPIRY_AMOXICILLIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_AMOXICILLIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_GABAPENTIN;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_MEDICINE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_MEDICINE;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UpdateCommand;
import seedu.address.logic.commands.UpdateCommand.UpdateBatchDescriptor;
import seedu.address.model.medicine.BatchNumber;
import seedu.address.model.medicine.Expiry;
import seedu.address.model.medicine.Quantity;
import seedu.address.testutil.UpdateBatchDescriptorBuilder;

public class UpdateCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE);

    private UpdateCommandParser parser = new UpdateCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, BATCHNUMBER_DESC_AMOXICILLIN, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", UpdateCommand.MESSAGE_MISSING_PARAMETER);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

        // no batch number
        assertParseFailure(parser, "1" + " " + QUANTITY_DESC_AMOXICILLIN, UpdateCommand.MESSAGE_MISSING_PARAMETER);

        // no quantity and expiry
        assertParseFailure(parser, "1" + " " + BATCHNUMBER_DESC_AMOXICILLIN, UpdateCommand.MESSAGE_MISSING_PARAMETER);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + BATCHNUMBER_DESC_AMOXICILLIN + QUANTITY_DESC_AMOXICILLIN,
                MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + BATCHNUMBER_DESC_AMOXICILLIN + QUANTITY_DESC_AMOXICILLIN,
                MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_BATCHNUMBER_DESC + QUANTITY_DESC_AMOXICILLIN + EXPIRY_DESC_AMOXICILLIN,
                BatchNumber.MESSAGE_CONSTRAINTS); // invalid batch number
        assertParseFailure(parser, "1" + BATCHNUMBER_DESC_AMOXICILLIN + INVALID_QUANTITY_DESC + EXPIRY_DESC_AMOXICILLIN,
                Quantity.MESSAGE_CONSTRAINTS); // invalid quantity
        assertParseFailure(parser, "1" + BATCHNUMBER_DESC_AMOXICILLIN + QUANTITY_DESC_AMOXICILLIN + INVALID_EXPIRY_DESC,
                Expiry.MESSAGE_CONSTRAINTS); // invalid expiry
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_MEDICINE;
        String userInput = targetIndex.getOneBased() + BATCHNUMBER_DESC_AMOXICILLIN + QUANTITY_DESC_AMOXICILLIN
                + EXPIRY_DESC_AMOXICILLIN;

        UpdateBatchDescriptor newBatchDetails = new UpdateBatchDescriptorBuilder()
                .withBatchNumber(VALID_BATCHNUMBER_AMOXICILLIN).withQuantity(VALID_QUANTITY_AMOXICILLIN)
                .withExpiry(VALID_EXPIRY_AMOXICILLIN).build();
        UpdateCommand expectedCommand = new UpdateCommand(targetIndex, newBatchDetails);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_expiryNotSpecified_success() {
        Index targetIndex = INDEX_FIRST_MEDICINE;
        String userInput = targetIndex.getOneBased() + BATCHNUMBER_DESC_GABAPENTIN + QUANTITY_DESC_GABAPENTIN;
        UpdateBatchDescriptor newBatchDetails = new UpdateBatchDescriptorBuilder()
                .withBatchNumber(VALID_BATCHNUMBER_GABAPENTIN).withQuantity(VALID_QUANTITY_GABAPENTIN).build();
        UpdateCommand expectedCommand = new UpdateCommand(targetIndex, newBatchDetails);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_quantityNotSpecified_success() {
        Index targetIndex = INDEX_FIRST_MEDICINE;
        String userInput = targetIndex.getOneBased() + BATCHNUMBER_DESC_GABAPENTIN + EXPIRY_DESC_AMOXICILLIN;
        UpdateBatchDescriptor newBatchDetails = new UpdateBatchDescriptorBuilder()
                .withBatchNumber(VALID_BATCHNUMBER_GABAPENTIN).withExpiry(VALID_EXPIRY_AMOXICILLIN).build();
        UpdateCommand expectedCommand = new UpdateCommand(targetIndex, newBatchDetails);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_MEDICINE;
        String userInput = targetIndex.getOneBased() + BATCHNUMBER_DESC_GABAPENTIN + QUANTITY_DESC_GABAPENTIN
                + EXPIRY_DESC_AMOXICILLIN + BATCHNUMBER_DESC_GABAPENTIN + QUANTITY_DESC_AMOXICILLIN
                + BATCHNUMBER_DESC_GABAPENTIN + EXPIRY_DESC_AMOXICILLIN;

        UpdateBatchDescriptor newBatchDetails = new UpdateBatchDescriptorBuilder()
                .withBatchNumber(VALID_BATCHNUMBER_GABAPENTIN).withQuantity(VALID_QUANTITY_AMOXICILLIN)
                .withExpiry(VALID_EXPIRY_AMOXICILLIN).build();

        UpdateCommand expectedCommand = new UpdateCommand(targetIndex, newBatchDetails);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_MEDICINE;
        String userInput = targetIndex.getOneBased() + QUANTITY_DESC_AMOXICILLIN + INVALID_BATCHNUMBER_DESC
                + BATCHNUMBER_DESC_GABAPENTIN;

        UpdateBatchDescriptor newBatchDetails = new UpdateBatchDescriptorBuilder()
                .withBatchNumber(VALID_BATCHNUMBER_GABAPENTIN).withQuantity(VALID_QUANTITY_AMOXICILLIN).build();
        UpdateCommand expectedCommand = new UpdateCommand(targetIndex, newBatchDetails);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + QUANTITY_DESC_AMOXICILLIN + INVALID_BATCHNUMBER_DESC
                + BATCHNUMBER_DESC_GABAPENTIN + QUANTITY_DESC_GABAPENTIN + BATCHNUMBER_DESC_AMOXICILLIN
                + EXPIRY_DESC_AMOXICILLIN;
        newBatchDetails = new UpdateBatchDescriptorBuilder()
                .withBatchNumber(VALID_BATCHNUMBER_AMOXICILLIN).withQuantity(VALID_QUANTITY_GABAPENTIN)
                .withExpiry(VALID_EXPIRY_AMOXICILLIN).build();
        expectedCommand = new UpdateCommand(targetIndex, newBatchDetails);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}

package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.prescription.AddPrescriptionCommand;
import seedu.address.logic.parser.prescription.AddPrescriptionCommandParser;
import seedu.address.model.medicalhistory.ValidDate;
import seedu.address.model.person.PersonId;
import seedu.address.model.prescription.Description;
import seedu.address.model.prescription.Medicine;
import seedu.address.model.prescription.Prescription;

public class AddPrescriptionCommandParserTest {
    private AddPrescriptionCommandParser parser = new AddPrescriptionCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        AddPrescriptionCommand command = new AddPrescriptionCommand(
                new Prescription(new PersonId("1"), new PersonId("2"),
                        new ValidDate("2018-05-13"), new Medicine("testMedicine"),
                        new Description("testDescription")));

        assertParseSuccess(parser, " pid/1 did/2 dp/2018-05-13 mn/testMedicine d/testDescription", command);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid date
        assertParseFailure(parser, " pid/1 " + " did/2 " + " dp/2020-05-13 "
                + " mn/testMedicine " + " d/testDescription ", ValidDate.MESSAGE_CONSTRAINTS);


    }

}

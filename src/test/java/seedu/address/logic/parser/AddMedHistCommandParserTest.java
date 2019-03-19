package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddMedHistCommand;
import seedu.address.model.medicalhistory.MedicalHistory;
import seedu.address.model.medicalhistory.WriteUp;
import seedu.address.model.person.Name;

public class AddMedHistCommandParserTest {
    private AddMedHistCommandParser parser = new AddMedHistCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        AddMedHistCommand command = new AddMedHistCommand(
                new MedicalHistory(null, null, new Name("testName"), new WriteUp("testWriteUp")));

        assertParseSuccess(parser, " n/testName sw/testWriteUp", command);
    }

}

package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.medicalhistory.AddMedHistCommand;
import seedu.address.logic.parser.medicalhistory.AddMedHistCommandParser;
import seedu.address.model.medicalhistory.MedicalHistory;
import seedu.address.model.medicalhistory.ValidDate;
import seedu.address.model.medicalhistory.WriteUp;
import seedu.address.model.person.PersonId;

public class AddMedHistCommandParserTest {
    private AddMedHistCommandParser parser = new AddMedHistCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        AddMedHistCommand command = new AddMedHistCommand(
                new MedicalHistory(new PersonId("1"), new PersonId("1"),
                        new ValidDate("2018-05-05"), new WriteUp("testWriteUp")));

        assertParseSuccess(parser, " pid/1 did/1 d/2018-05-05 sw/testWriteUp", command);
    }

}

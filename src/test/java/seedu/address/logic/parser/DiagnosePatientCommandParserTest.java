package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.logic.commands.DiagnosePatientCommand;
import seedu.address.model.consultation.Assessment;
import seedu.address.model.consultation.Diagnosis;
import seedu.address.model.consultation.Symptom;

public class DiagnosePatientCommandParserTest {

    private DiagnosePatientCommandParser parser = new DiagnosePatientCommandParser();

    @Test
    public void parseDiagnosis() {
        String input = " a/migrane s/constant headache s/blurred vision";
        Assessment assessment = new Assessment("migrane");
        ArrayList<Symptom> symptoms = new ArrayList<>();
        symptoms.add(new Symptom("constant headache"));
        symptoms.add(new Symptom("blurred vision"));

        assertParseSuccess(parser, input, new DiagnosePatientCommand(new Diagnosis(assessment, symptoms)));

        input = " a/migrane";
        assertParseFailure(parser, input, "Some details are left out, please retype the command");

        input = " s/constant headache s/blurred vision";
        assertParseFailure(parser, input, "Some details are left out, please retype the command");
    }

}

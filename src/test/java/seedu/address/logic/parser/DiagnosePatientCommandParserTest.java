package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.logic.commands.DiagnosePatientCommand;
import seedu.address.model.consultation.Assessment;
import seedu.address.model.consultation.Diagnosis;
import seedu.address.model.consultation.Symptom;
import seedu.address.testutil.Assert;

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
    }

    @Test
    public void argumentscheck() {
        String input = " s/sympt0m 1 s/5ymptom5 2 a/m1gr4n3";
        Assessment assessment = new Assessment("m1gr4n3");
        ArrayList<Symptom> symptoms = new ArrayList<>();
        symptoms.add(new Symptom("sympt0m 1"));
        symptoms.add(new Symptom("5ymptom5 2"));
        assertParseSuccess(parser, input, new DiagnosePatientCommand(new Diagnosis(assessment, symptoms)));
    }

    @Test
    public void argumentsFailCheck() {
        String input = " s/sympt()m 1 2 a/m1gr4n3";
        Assert.assertThrows(IllegalArgumentException.class, () -> parser.parse(input));

        String input2 = " s/sympt0m 1 a/@ss3ssm3n+";
        Assert.assertThrows(IllegalArgumentException.class, () -> parser.parse(input2));
    }

    @Test
    public void invalidDiagnosis() {
        String input = " a/migrane";
        assertParseFailure(parser, input, DiagnosePatientCommandParser.INVALID_ARGUMENTS_DIAGNOSIS);

        input = " s/constant headache s/blurred vision";
        assertParseFailure(parser, input, DiagnosePatientCommandParser.INVALID_ARGUMENTS_DIAGNOSIS);
    }

}

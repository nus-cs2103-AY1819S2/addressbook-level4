package quickdocs.logic.parser;

import static quickdocs.logic.parser.CommandParserTestUtil.assertParseFailure;
import static quickdocs.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;

import org.junit.Test;

import quickdocs.logic.commands.DiagnosePatientCommand;
import quickdocs.model.consultation.Assessment;
import quickdocs.model.consultation.Diagnosis;
import quickdocs.model.consultation.Symptom;
import quickdocs.testutil.Assert;

public class DiagnosePatientCommandParserTest {

    private DiagnosePatientCommandParser parser = new DiagnosePatientCommandParser();

    @Test
    public void parseDiagnosis_validArgs_success() {
        String input = " a/migrane s/constant headache s/blurred vision";
        Assessment assessment = new Assessment("migrane");
        ArrayList<Symptom> symptoms = new ArrayList<>();
        symptoms.add(new Symptom("constant headache"));
        symptoms.add(new Symptom("blurred vision"));
        assertParseSuccess(parser, input, new DiagnosePatientCommand(new Diagnosis(assessment, symptoms)));

        input = " s/sympt0m 1 s/5ymptom5 2 a/m1gr4n3";
        assessment = new Assessment("m1gr4n3");
        symptoms = new ArrayList<>();
        symptoms.add(new Symptom("sympt0m 1"));
        symptoms.add(new Symptom("5ymptom5 2"));
        assertParseSuccess(parser, input, new DiagnosePatientCommand(new Diagnosis(assessment, symptoms)));

        // minimum requirement
        input = " s/sympt0m 1 a/m1gr4n3";
        assessment = new Assessment("m1gr4n3");
        symptoms = new ArrayList<>();
        symptoms.add(new Symptom("sympt0m 1"));
        assertParseSuccess(parser, input, new DiagnosePatientCommand(new Diagnosis(assessment, symptoms)));
    }

    @Test
    public void parseDiagnosis_insufficientArgs_failure() {
        String input = " a/migrane";
        assertParseFailure(parser, input, DiagnosePatientCommandParser.INVALID_ARGUMENTS_DIAGNOSIS);

        input = " s/constant headache s/blurred vision";
        assertParseFailure(parser, input, DiagnosePatientCommandParser.INVALID_ARGUMENTS_DIAGNOSIS);

        input = " ";
        assertParseFailure(parser, input, DiagnosePatientCommandParser.INVALID_ARGUMENTS_DIAGNOSIS);

        input = "";
        assertParseFailure(parser, input, DiagnosePatientCommandParser.INVALID_ARGUMENTS_DIAGNOSIS);
    }

    @Test
    public void parseDiagnosis_invalidArgs_failure() {
        String input2 = " s/sympt()m 1 2 a/m1gr4n3";
        Assert.assertThrows(IllegalArgumentException.class, () -> parser.parse(input2));

        String input3 = " s/sympt0m 1 a/@ss3ssm3n+";
        Assert.assertThrows(IllegalArgumentException.class, () -> parser.parse(input3));
    }

}

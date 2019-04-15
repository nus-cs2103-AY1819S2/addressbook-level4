package quickdocs.logic.commands;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import quickdocs.logic.CommandHistory;
import quickdocs.logic.commands.exceptions.CommandException;
import quickdocs.model.ModelManager;
import quickdocs.model.consultation.Assessment;
import quickdocs.model.consultation.Diagnosis;
import quickdocs.model.consultation.Prescription;
import quickdocs.model.consultation.Symptom;
import quickdocs.model.medicine.Medicine;
import quickdocs.model.patient.Address;
import quickdocs.model.patient.Contact;
import quickdocs.model.patient.Dob;
import quickdocs.model.patient.Email;
import quickdocs.model.patient.Gender;
import quickdocs.model.patient.Name;
import quickdocs.model.patient.Nric;
import quickdocs.model.patient.Patient;
import quickdocs.model.tag.Tag;
import quickdocs.testutil.Assert;

public class EndConsultationTest {

    private ModelManager modelManager = new ModelManager();
    private final CommandHistory history = new CommandHistory();
    private Patient patient1;

    @Before
    public void init() {
        Name name = new Name("Peter Tan");
        Nric nric = new Nric("S9123456A");
        Email email = new Email("ptan@gmail.com");
        Address address = new Address("1 Simei Road");
        Contact contact = new Contact("91111111");
        Gender gender = new Gender("M");
        Dob dob = new Dob("1991-01-01");
        ArrayList<Tag> tagList = new ArrayList<Tag>();
        patient1 = new Patient(name, nric, email, address, contact, gender, dob, tagList);
        modelManager.addPatient(patient1);
    }

    @Test
    public void endConsultation_noConsultation_throwsCommandException() {
        EndConsultationCommand command = new EndConsultationCommand();
        Assert.assertThrows(CommandException.class, () -> command.execute(modelManager, history));
    }

    @Test
    public void endConsultation_noDiagnosisAndPrescription_throwsCommandException() {
        modelManager.createConsultation(modelManager.getPatientByNric(patient1.getNric().toString()));
        EndConsultationCommand command = new EndConsultationCommand();
        Assert.assertThrows(CommandException.class, () -> command.execute(modelManager, history));
    }

    @Test
    public void endConsultation_noPrescription_throwsCommandException() {
        modelManager.createConsultation(modelManager.getPatientByNric(patient1.getNric().toString()));
        EndConsultationCommand command = new EndConsultationCommand();

        // no prescription
        Assessment assessment = new Assessment("migrane");
        ArrayList<Symptom> symptoms = new ArrayList<>();
        symptoms.add(new Symptom("constant headache"));
        modelManager.diagnosePatient(new Diagnosis(assessment, symptoms));
        Assert.assertThrows(CommandException.class, () -> command.execute(modelManager, history));
    }

    @Test
    public void endConsultation_noDiagnosis_throwsCommandException() {
        modelManager.createConsultation(modelManager.getPatientByNric(patient1.getNric().toString()));
        EndConsultationCommand command = new EndConsultationCommand();

        // no diagnosis
        ArrayList<Prescription> prescriptions = new ArrayList<>();
        Medicine med1 = new Medicine("migrane_medicine", 1);
        med1.setPrice(BigDecimal.valueOf(20.00));
        prescriptions.add(new Prescription(med1, 1));
        modelManager.prescribeMedicine(prescriptions);
        Assert.assertThrows(CommandException.class, () -> command.execute(modelManager, history));
    }

    @Test
    public void endConsultation_validConsultation_success() {

        modelManager.createConsultation(modelManager.getPatientByNric(patient1.getNric().toString()));
        EndConsultationCommand command = new EndConsultationCommand();

        Assessment assessment = new Assessment("migrane");
        ArrayList<Symptom> symptoms = new ArrayList<>();
        symptoms.add(new Symptom("constant headache"));
        modelManager.diagnosePatient(new Diagnosis(assessment, symptoms));
        ArrayList<Prescription> prescriptions = new ArrayList<>();
        Medicine med1 = new Medicine("migrane_medicine", 1);
        med1.setPrice(BigDecimal.valueOf(20.00));
        prescriptions.add(new Prescription(med1, 1));
        modelManager.prescribeMedicine(prescriptions);

        try {
            org.junit.Assert.assertEquals(command.execute(modelManager, history).getFeedbackToUser(),
                    String.format(EndConsultationCommand.END_CONSULT_FEEDBACK,
                            modelManager.getPatientByNric(patient1.getNric().toString()).getNric()));
        } catch (CommandException ce) {
            org.junit.Assert.fail();
        }

    }

}

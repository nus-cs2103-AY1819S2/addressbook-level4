package seedu.address.logic.commands;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.consultation.Assessment;
import seedu.address.model.consultation.Diagnosis;
import seedu.address.model.consultation.Prescription;
import seedu.address.model.consultation.Symptom;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.patient.Address;
import seedu.address.model.patient.Contact;
import seedu.address.model.patient.Dob;
import seedu.address.model.patient.Email;
import seedu.address.model.patient.Gender;
import seedu.address.model.patient.Name;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Patient;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.Assert;

public class EndConsultationTest {

    private ModelManager modelManager = new ModelManager();
    private final CommandHistory history = new CommandHistory();

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
        Patient patient1 = new Patient(name, nric, email, address, contact, gender, dob, tagList);
        modelManager.addPatient(patient1);
    }

    @Test
    public void endConsultation() {

        modelManager.createConsultation(modelManager.getPatientAtIndex(1));

        EndConsultationCommand command = new EndConsultationCommand();

        Assert.assertThrows(CommandException.class, () -> command.execute(modelManager, history));

        Assessment assessment = new Assessment("migrane");

        ArrayList<Symptom> symptoms = new ArrayList<>();
        symptoms.add(new Symptom("constant headache"));

        modelManager.diagnosePatient(new Diagnosis(assessment, symptoms));

        Assert.assertThrows(CommandException.class, () -> command.execute(modelManager, history));

        ArrayList<Prescription> prescriptions = new ArrayList<>();
        Medicine med1 = new Medicine("migrane medicine", 1);
        med1.setPrice(BigDecimal.valueOf(20.00));
        prescriptions.add(new Prescription(med1, 1));
        modelManager.prescribeMedicine(prescriptions);

        try {
            org.junit.Assert.assertEquals(command.execute(modelManager, history).getFeedbackToUser(),
                    String.format(EndConsultationCommand.END_CONSULT_FEEDBACK,
                            modelManager.getPatientAtIndex(1).getNric()));
        } catch (CommandException ce) {
            org.junit.Assert.fail();
        }

    }

}

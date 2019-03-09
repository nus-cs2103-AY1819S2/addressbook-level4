package seedu.address.logic.commands;

import java.lang.reflect.Array;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.consultation.Assessment;
import seedu.address.model.consultation.Consultation;
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

public class ListConsultationCommandTest {

    private ModelManager modelManager = new ModelManager();
    private final CommandHistory history = new CommandHistory();

    @Test
    public void noRecords() {
        // nothing in consultation list yet
        ListConsultationCommand command = new ListConsultationCommand(1);
        Assert.assertThrows(CommandException.class, () -> command.execute(modelManager, history));

        StringBuilder sb = new StringBuilder();
        sb.append("Listing consultation records\n");
        sb.append("====================\n");
        sb.append(ListConsultationCommand.NO_RECORDS);
        ListConsultationCommand command2 = new ListConsultationCommand("S9123456A");
        try {
            org.junit.Assert.assertEquals(sb.toString(),
                    command2.execute(modelManager, history).getFeedbackToUser());
        } catch (CommandException ce) {
            org.junit.Assert.fail();
        }
    }

    @Test
    public void consultationsPresent() {
        Name name = new Name("John Tan");
        Nric nric = new Nric("S9123456A");
        Email email = new Email("jtan@gmail.com");
        Address address = new Address("1 Simei Road");
        Contact contact = new Contact("91111111");
        Gender gender = new Gender("M");
        Dob dob = new Dob("1991-01-01");
        ArrayList<Tag> tagList = new ArrayList<Tag>();
        tagList.add(new Tag("Diabetes"));
        Patient patient1 = new Patient(name, nric, email, address, contact, gender, dob, tagList);

        ArrayList<Symptom> symptoms = new ArrayList<>();
        symptoms.add(new Symptom("symptom 1"));
        Diagnosis diagnosis = new Diagnosis(new Assessment("assessment 1"), symptoms);
        ArrayList<Prescription> prescriptions = new ArrayList<>();
        prescriptions.add(new Prescription(new Medicine("med1"), 1));

        modelManager.createConsultation(patient1);
        modelManager.diagnosePatient(diagnosis);
        modelManager.prescribeMedicine(prescriptions);

        Consultation consultation = modelManager.getCurrentConsultation();

        modelManager.endConsultation();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        StringBuilder sb = new StringBuilder();
        sb.append("Listing consultation records\n");
        sb.append("====================\n");
        sb.append(1 + consultation.getIndex() + ") ");
        sb.append(" visit for " + consultation.getDiagnosis().getAssessment() + " ");
        sb.append(" on " + consultation.getSession().format(formatter));
        sb.append("\n");

        ListConsultationCommand command = new ListConsultationCommand("S9123456A");
        try {
            org.junit.Assert.assertEquals(sb.toString(),
                    command.execute(modelManager, history).getFeedbackToUser());
        } catch (CommandException ce) {
            org.junit.Assert.fail();
        }

        // no consultation record by patient of different nric
        ListConsultationCommand command2 = new ListConsultationCommand("S9123456B");
        sb = new StringBuilder();
        sb.append("Listing consultation records\n");
        sb.append("====================\n");
        sb.append(ListConsultationCommand.NO_RECORDS);
        try {
            org.junit.Assert.assertEquals(sb.toString(),
                    command2.execute(modelManager, history).getFeedbackToUser());
        } catch (CommandException ce) {
            org.junit.Assert.fail();
        }

        //index listing
        ListConsultationCommand command3 = new ListConsultationCommand(1);
        sb = new StringBuilder();
        sb.append(consultation);
        try {
            org.junit.Assert.assertEquals(sb.toString(),
                    command3.execute(modelManager, history).getFeedbackToUser());
        } catch (CommandException ce) {
            org.junit.Assert.fail();
        }
    }

}

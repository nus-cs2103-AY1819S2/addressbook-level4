package seedu.address.model.consultation;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import seedu.address.model.patient.Address;
import seedu.address.model.patient.Contact;
import seedu.address.model.patient.Dob;
import seedu.address.model.patient.Email;
import seedu.address.model.patient.Gender;
import seedu.address.model.patient.Name;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.PatientManager;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.Assert;

public class ConsultationManagerTest {

    private static ConsultationManager consultationManager;
    private static PatientManager patientManager;
    private Patient patient1;

    @Before
    public void init() {
        this.consultationManager = new ConsultationManager();
        this.patientManager = new PatientManager();

        Name name = new Name("Peter Tan");
        Nric nric = new Nric("S9123456A");
        Email email = new Email("ptan@gmail.com");
        Address address = new Address("1 Simei Road");
        Contact contact = new Contact("91111111");
        Gender gender = new Gender("M");
        Dob dob = new Dob("1991-01-01");
        ArrayList<Tag> tagList = new ArrayList<Tag>();
        patient1 = new Patient(name, nric, email, address, contact, gender, dob, tagList);

        patientManager.addPatient(patient1);
    }

    @Test
    public void createConsultation() {
        Patient patient = patientManager.getPatientByNric(patient1.getNric().toString());
        consultationManager.createConsultation(patient);
        //attempt to start another consultation when there is an existing current session
        Assert.assertThrows(IllegalArgumentException.class, () -> consultationManager.createConsultation(patient));
    }

    @Test
    public void checkConsultation() {
        org.junit.Assert.assertFalse(consultationManager.checkConsultation());

        Patient patient = patientManager.getPatientByNric(patient1.getNric().toString());
        consultationManager.createConsultation(patient);
        org.junit.Assert.assertTrue(consultationManager.checkConsultation());
    }

    @Test
    public void getCurrentConsultation() {
        org.junit.Assert.assertTrue(consultationManager.getCurrentConsultation() == null);

        Patient patient = patientManager.getPatientByNric(patient1.getNric().toString());
        consultationManager.createConsultation(patient);

        org.junit.Assert.assertTrue(consultationManager.getCurrentConsultation() != null);
    }

    @Test
    public void endConsultation() {
        Assert.assertThrows(NullPointerException.class, () -> consultationManager.endConsultation());

        Patient patient = patientManager.getPatientByNric(patient1.getNric().toString());
        consultationManager.createConsultation(patient);

        // diagnosis and prescription null
        consultationManager.endConsultation();

        org.junit.Assert.assertTrue(consultationManager.getCurrentConsultation() == null);


    }

}

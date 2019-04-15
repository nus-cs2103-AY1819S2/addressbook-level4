package quickdocs.model.consultation;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import quickdocs.model.medicine.Medicine;
import quickdocs.model.patient.Address;
import quickdocs.model.patient.Contact;
import quickdocs.model.patient.Dob;
import quickdocs.model.patient.Email;
import quickdocs.model.patient.Gender;
import quickdocs.model.patient.Name;
import quickdocs.model.patient.Nric;
import quickdocs.model.patient.Patient;
import quickdocs.model.patient.PatientManager;
import quickdocs.model.tag.Tag;
import quickdocs.testutil.Assert;

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

    @Test
    public void abortConsultation() {

        consultationManager.abortConsultation();
        org.junit.Assert.assertTrue(consultationManager.getCurrentConsultation() == null);

        Patient patient = patientManager.getPatientByNric(patient1.getNric().toString());
        consultationManager.createConsultation(patient);
        consultationManager.abortConsultation();
        org.junit.Assert.assertTrue(consultationManager.getCurrentConsultation() == null);

    }

    @Test
    public void addConsultation() {
        Patient patient = patientManager.getPatientByNric(patient1.getNric().toString());
        //consultationManager.createConsultation(patient);

        Consultation consultation = new Consultation(patient);


        ArrayList<Symptom> symptoms = new ArrayList<>(Arrays.asList(new Symptom("Symptom 1")));
        Assessment assessment1 = new Assessment("Assessment 1");
        Diagnosis diagnosis1 = new Diagnosis(assessment1, symptoms);

        ArrayList<Prescription> prescriptions = new ArrayList<>(Arrays.asList(
                new Prescription(new Medicine("med1", 10), 1),
                new Prescription(new Medicine("med2", 10), 1)
        ));

        consultation.setDiagnosis(diagnosis1);
        consultation.setPrescriptions(prescriptions);

        consultationManager.addConsultation(consultation);

        //consultationManager.endConsultation();
        org.junit.Assert.assertTrue(consultationManager.getConsultationList().size() == 1);
    }

}

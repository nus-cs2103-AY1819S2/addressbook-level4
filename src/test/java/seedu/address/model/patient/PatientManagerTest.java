package seedu.address.model.patient;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import seedu.address.model.tag.Tag;

public class PatientManagerTest {
    private static PatientManager patientManager;

    @Before
    public void init() {
        this.patientManager = new PatientManager();

        Name name = new Name("Peter Tan");
        Nric nric = new Nric("S9123456A");
        Email email = new Email("ptan@gmail.com");
        Address address = new Address("1 Simei Road");
        Contact contact = new Contact("91111111");
        Gender gender = new Gender("M");
        Dob dob = new Dob("1991-01-01");
        ArrayList<Tag> tagList = new ArrayList<Tag>();
        Patient patient1 = new Patient(name, nric, email, address, contact, gender, dob, tagList);

        patientManager.addPatient(patient1);
    }

    @Test
    public void testDuplicates() {
        Name name = new Name("Pepper Toh");
        Nric nric = new Nric("S9123456A");
        Email email = new Email("ptoh@gmail.com");
        Address address = new Address("2 Simei Road");
        Contact contact = new Contact("92222222");
        Gender gender = new Gender("F");
        Dob dob = new Dob("1993-03-03");
        ArrayList<Tag> tagList = new ArrayList<Tag>();
        Patient patient1 = new Patient(name, nric, email, address, contact, gender, dob, tagList);
        Assert.assertTrue(patientManager.duplicatePatient(patient1));

        Nric nric2 = new Nric("S9123456B");
        Patient patient2 = new Patient(name, nric2, email, address, contact, gender, dob, tagList);
        Assert.assertFalse(patientManager.duplicatePatient(patient2));
    }

    @Test
    public void testAddPatients() {
        Name name = new Name("Pepper Toh");
        Nric nric = new Nric("S9123456A");
        Email email = new Email("ptoh@gmail.com");
        Address address = new Address("2 Simei Road");
        Contact contact = new Contact("92222222");
        Gender gender = new Gender("F");
        Dob dob = new Dob("1993-03-03");
        ArrayList<Tag> tagList = new ArrayList<Tag>();
        Patient patient1 = new Patient(name, nric, email, address, contact, gender, dob, tagList);
        patientManager.addPatient(patient1);
        Assert.assertTrue(patientManager.getPatientList().size() == 2);
    }



}

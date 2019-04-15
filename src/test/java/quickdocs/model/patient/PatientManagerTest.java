package quickdocs.model.patient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import quickdocs.model.tag.Tag;

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
    public void isDuplicate_noDuplicate_returnsTrue() {
        Name name = new Name("Pepper Toh");
        Nric nric = new Nric("S9123456A");
        Email email = new Email("ptoh@gmail.com");
        Address address = new Address("2 Simei Road");
        Contact contact = new Contact("92222222");
        Gender gender = new Gender("F");
        Dob dob = new Dob("1993-03-03");
        ArrayList<Tag> tagList = new ArrayList<Tag>();
        Patient patient1 = new Patient(name, nric, email, address, contact, gender, dob, tagList);
        Assert.assertTrue(patientManager.isDuplicatePatient(patient1));
    }

    @Test
    public void isDuplicate_haveDuplicate_returnsFalse() {
        Name name = new Name("Pepper Toh");
        Nric nric = new Nric("S9123456B");
        Email email = new Email("ptoh@gmail.com");
        Address address = new Address("2 Simei Road");
        Contact contact = new Contact("92222222");
        Gender gender = new Gender("F");
        Dob dob = new Dob("1993-03-03");
        ArrayList<Tag> tagList = new ArrayList<Tag>();
        Patient patient2 = new Patient(name, nric, email, address, contact, gender, dob, tagList);
        assertFalse(patientManager.isDuplicatePatient(patient2));
    }

    @Test
    public void addPatient_validAddPatient_patientAdded() {
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

    @Test
    public void isPatientListEmpty_isEmpty_returnsTrue() {
        patientManager = new PatientManager();
        Assert.assertTrue(patientManager.isPatientListEmpty());
    }

    @Test
    public void isPatientListEmpty_notEmpty_returnsFalse() {
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
        assertFalse(patientManager.isPatientListEmpty());
    }

    @Test
    public void checkValidIndex() {

        Assert.assertTrue(patientManager.checkValidIndex(0));

        assertFalse(patientManager.checkValidIndex(2));

        assertFalse(patientManager.checkValidIndex(1));

        assertFalse(patientManager.checkValidIndex(-1));
    }

    @Test
    public void checkDuplicatePatientAfterEdit() {
        Name name = new Name("Pepper Toh");
        Nric nric = new Nric("S9123456B");
        Email email = new Email("ptoh@gmail.com");
        Address address = new Address("2 Simei Road");
        Contact contact = new Contact("92222222");
        Gender gender = new Gender("F");
        Dob dob = new Dob("1993-03-03");
        ArrayList<Tag> tagList = new ArrayList<Tag>();
        Patient patient1 = new Patient(name, nric, email, address, contact, gender, dob, tagList);
        patientManager.addPatient(patient1);

        // edited patient have the same nric as the second patient in list
        Name name2 = new Name("Peter Tan");
        Nric nric2 = new Nric("S9123456B");
        Email email2 = new Email("ptan@gmail.com");
        Address address2 = new Address("1 Simei Road");
        Contact contact2 = new Contact("91111111");
        Gender gender2 = new Gender("M");
        Dob dob2 = new Dob("1991-01-01");
        ArrayList<Tag> tagList2 = new ArrayList<Tag>();
        Patient editedPatient = new Patient(name2, nric2, email2, address2, contact2, gender2, dob2, tagList2);
        Assert.assertTrue(patientManager.checkDuplicatePatientAfterEdit(0, editedPatient));

        Nric nric3 = new Nric("S9123456C");
        editedPatient = new Patient(name2, nric3, email2, address2, contact2, gender2, dob2, tagList2);
        assertFalse(patientManager.checkDuplicatePatientAfterEdit(1, editedPatient));
    }

    @Test
    public void findPatientByName() {

        Name name = new Name("Perry Ng");
        Nric nric = new Nric("S9234567B");
        Email email = new Email("png@gmail.com");
        Address address = new Address("2 Simei Road");
        Contact contact = new Contact("92222222");
        Gender gender = new Gender("M");
        Dob dob = new Dob("1992-02-02");
        ArrayList<Tag> tagList = new ArrayList<Tag>();
        Patient patient1 = new Patient(name, nric, email, address, contact, gender, dob, tagList);

        patientManager.addPatient(patient1);

        Name name2 = new Name("Piper Wright");
        Nric nric2 = new Nric("S9273478C");
        Email email2 = new Email("pwright@gmail.com");
        Address address2 = new Address("3 Simei Road");
        Contact contact2 = new Contact("92222222");
        Gender gender2 = new Gender("F");
        Dob dob2 = new Dob("1992-02-02");
        ArrayList<Tag> tagList2 = new ArrayList<Tag>();
        Patient patient2 = new Patient(name2, nric2, email2, address2, contact2, gender2, dob2, tagList2);

        patientManager.addPatient(patient2);

        StringBuilder sb = new StringBuilder();
        sb.append(1 + ") " + "Peter Tan"
                + " " + "S9123456A"
                + " " + "M"
                + " " + "1991-01-01"
                + "\n"
        );
        sb.append(2 + ") " + name
                + " " + nric
                + " " + gender
                + " " + dob
                + "\n"
        );
        sb.append(3 + ") " + name2
                + " " + nric2
                + " " + gender2
                + " " + dob2
                + "\n"
        );
        sb.append("\n");

        assertEquals(patientManager.findPatientsByName("Pe"), sb.toString());

        assertEquals(patientManager.findPatientsByName("Be"), "No patient record found");

        assertEquals(patientManager.findPatientsByName("Peter"), patientManager.getPatientAtIndex(0).toString());
    }

    @Test
    public void findPatientByNric() {
        Name name = new Name("Perry Ng");
        Nric nric = new Nric("S9234567B");
        Email email = new Email("png@gmail.com");
        Address address = new Address("2 Simei Road");
        Contact contact = new Contact("92222222");
        Gender gender = new Gender("M");
        Dob dob = new Dob("1992-02-02");
        ArrayList<Tag> tagList = new ArrayList<Tag>();
        Patient patient1 = new Patient(name, nric, email, address, contact, gender, dob, tagList);

        patientManager.addPatient(patient1);

        Name name2 = new Name("Piper Wright");
        Nric nric2 = new Nric("S9273478C");
        Email email2 = new Email("pwright@gmail.com");
        Address address2 = new Address("3 Simei Road");
        Contact contact2 = new Contact("92222222");
        Gender gender2 = new Gender("F");
        Dob dob2 = new Dob("1992-02-02");
        ArrayList<Tag> tagList2 = new ArrayList<Tag>();
        Patient patient2 = new Patient(name2, nric2, email2, address2, contact2, gender2, dob2, tagList2);

        patientManager.addPatient(patient2);

        StringBuilder sb = new StringBuilder();
        sb.append(2 + ") " + name
                + " " + nric
                + " " + gender
                + " " + dob
                + "\n"
        );
        sb.append(3 + ") " + name2
                + " " + nric2
                + " " + gender2
                + " " + dob2
                + "\n"
        );
        sb.append("\n");

        assertEquals(patientManager.findPatientsByNric("S92"), sb.toString());

        assertEquals(patientManager.findPatientsByNric("S88"), "No patient record found");
    }


    @Test
    public void findPatientByTag() {
        this.patientManager = new PatientManager();

        Name name = new Name("Peter Tan");
        Nric nric = new Nric("S9123456A");
        Email email = new Email("ptan@gmail.com");
        Address address = new Address("1 Simei Road");
        Contact contact = new Contact("91111111");
        Gender gender = new Gender("M");
        Dob dob = new Dob("1991-01-01");
        ArrayList<Tag> tagList = new ArrayList<Tag>();
        tagList.add(new Tag("Diabetes"));
        Patient patient1 = new Patient(name, nric, email, address, contact, gender, dob, tagList);

        Name name2 = new Name("Perry Ng");
        Nric nric2 = new Nric("S9123456B");
        ArrayList<Tag> tagList2 = new ArrayList<Tag>();
        tagList2.add(new Tag("Highbloodpressure"));
        Patient patient2 = new Patient(name2, nric2, email, address, contact, gender, dob, tagList2);

        Name name3 = new Name("Pablo Alena");
        Nric nric3 = new Nric("S9123456C");
        ArrayList<Tag> tagList3 = new ArrayList<Tag>();
        tagList3.add(new Tag("Diabetes"));
        tagList3.add(new Tag("Highbloodpressure"));
        Patient patient3 = new Patient(name3, nric3, email, address, contact, gender, dob, tagList3);

        Name name4 = new Name("Pamela Lee");
        Nric nric4 = new Nric("S9123456D");
        ArrayList<Tag> tagList4 = new ArrayList<Tag>();
        tagList4.add(new Tag("Hepatitis"));
        Patient patient4 = new Patient(name4, nric4, email, address, contact, gender, dob, tagList4);

        patientManager.addPatient(patient1);
        patientManager.addPatient(patient2);
        patientManager.addPatient(patient3);
        patientManager.addPatient(patient4);

        StringBuilder sb = new StringBuilder();
        sb.append(1 + ") " + name
                + " " + nric
                + " " + gender
                + " " + dob
                + "\n"
        );
        sb.append(3 + ") " + name3
                + " " + nric3
                + " " + gender
                + " " + dob
                + "\n"
        );
        sb.append("\n");

        Tag tag = new Tag("Diabetes");
        Tag otherTag = new Tag("Highcholesterol");
        assertEquals(patientManager.findPatientsByTag(tag), sb.toString());
        assertEquals(patientManager.findPatientsByTag(otherTag), "No patient record found");

        Tag singleTag = new Tag("Hepatitis");
        assertEquals(patientManager.findPatientsByTag(singleTag), patient4.toString());
    }

    @Test
    public void getPatientByNric() {
        String nric = "S9123456A";
        Patient patient1 = patientManager.getPatientAtIndex(0);
        Patient patient2 = patientManager.getPatientByNric(nric);
        assertTrue(patient1.equals(patient2));

        nric = "S9123456B";
        patient2 = patientManager.getPatientByNric(nric);
        assertFalse(patient1.equals(patient2));
    }

    @Test
    public void deletePatientByNric() {

        // no matching nric
        patientManager.deletePatientByNric("S9123457A");
        assertTrue(patientManager.getPatientList().size() != 0);

        // patient deleted
        patientManager.deletePatientByNric("S9123456A");
        assertTrue(patientManager.getPatientList().size() == 0);
    }

    @Test
    public void equalTest() {

        assertTrue(patientManager.equals(patientManager));

        assertFalse(patientManager.equals(new Object()));

        PatientManager p2 = new PatientManager();
        Name name = new Name("Peter Tan");
        Nric nric = new Nric("S9123456A");
        Email email = new Email("ptan@gmail.com");
        Address address = new Address("1 Simei Road");
        Contact contact = new Contact("91111111");
        Gender gender = new Gender("M");
        Dob dob = new Dob("1991-01-01");
        ArrayList<Tag> tagList = new ArrayList<Tag>();
        Patient patient1 = new Patient(name, nric, email, address, contact, gender, dob, tagList);

        p2.addPatient(patient1);

        assertTrue(patientManager.equals(p2));


    }
}

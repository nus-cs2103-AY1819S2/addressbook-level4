package quickdocs.model.patient;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import quickdocs.model.tag.Tag;

public class PatientTest {

    @Test
    public void equals() {
        Name name = new Name("Peter Tan");
        Nric nric = new Nric("S9802708D");
        Email email = new Email("ptan@gmail.com");
        Address address = new Address("1 Tanah Merah Road");
        Contact contact = new Contact("92345678");
        Gender gender = new Gender("M");
        Dob dob = new Dob("1998-02-18");
        ArrayList tagList = new ArrayList<Tag>();

        Name name2 = new Name("Peter Toh");
        Nric nric2 = new Nric("S9802708D");
        Email email2 = new Email("ptoh@gmail.com");
        Address address2 = new Address("2 Tanah Merah Road");
        Contact contact2 = new Contact("95678234");
        Gender gender2 = new Gender("M");
        Dob dob2 = new Dob("1998-02-20");
        ArrayList tagList2 = new ArrayList<Tag>();

        Patient patient1 = new Patient(name, nric, email, address, contact, gender, dob, tagList);
        Patient patient2 = new Patient(name2, nric2, email2, address2, contact2, gender2, dob2, tagList2);

        // test equality of same referenced object
        Assert.assertTrue(patient1.equals(patient1));

        // test equality of two different object except Nric, which is the same
        Assert.assertTrue(patient1.equals(patient2));

        // test equality of two different patient object with different nric
        Nric nric4 = new Nric("S6134937E");

        Patient patient3 = new Patient(name, nric, email, address, contact, gender, dob, tagList);
        Patient patient4 = new Patient(name, nric4, email, address, contact, gender, dob, tagList);

        Assert.assertFalse(patient3.equals(patient4));

        // test equality of different object types
        Email otherObject = new Email("abc@gmail.com");
        Assert.assertFalse(patient1.equals(otherObject));
    }

}

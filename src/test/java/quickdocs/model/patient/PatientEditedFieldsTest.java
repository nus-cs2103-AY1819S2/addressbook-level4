package quickdocs.model.patient;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import quickdocs.model.tag.Tag;

public class PatientEditedFieldsTest {
    @Test
    public void checkEmpty() {
        PatientEditedFields peft = new PatientEditedFields();
        assertTrue(peft.checkEmpty());

        Name newName = new Name("Mohamed Rizal bin Ramlee");
        peft.setName(newName);
        assertFalse(peft.checkEmpty());
    }

    @Test
    public void checkEquals() {
        PatientEditedFields peft1 = new PatientEditedFields();
        assertTrue(peft1.equals(peft1));

        Email email = new Email("abc@gmail.com");
        assertFalse(peft1.equals(email));

        PatientEditedFields peft2 = new PatientEditedFields();
        assertTrue(peft1.equals(peft2));

        // different names
        peft1.setName(new Name("Peter"));
        peft2.setName(new Name("Piper"));
        assertFalse(peft1.equals(peft2));

        // for every subsequent test, peft2 will change the previous
        // field that was tested to match peft1 first before
        // testing on the next attribute

        // different nric
        peft1.setNric(new Nric("S1234567A"));
        peft2.setName(new Name("Peter"));
        peft2.setNric(new Nric("S1234567B"));
        assertFalse(peft1.equals(peft2));

        // different emails
        peft1.setEmail(new Email("peter@gmail.com"));
        peft2.setNric(new Nric("S1234567A"));
        peft2.setEmail(new Email("piper@gmail.com"));
        assertFalse(peft1.equals(peft2));

        // different address
        peft1.setAddress(new Address("1 Simei Road"));
        peft2.setEmail(new Email("peter@gmail.com"));
        peft2.setAddress(new Address("2 Simei Road"));
        assertFalse(peft1.equals(peft2));

        // different contacts
        peft1.setContact(new Contact("11111111"));
        peft2.setAddress(new Address("1 Simei Road"));
        peft2.setContact(new Contact("22222222"));
        assertFalse(peft1.equals(peft2));

        // different gender
        peft1.setGender(new Gender("M"));
        peft2.setContact(new Contact("11111111"));
        peft2.setGender(new Gender("F"));
        assertFalse(peft1.equals(peft2));

        // different dob
        peft1.setDob(new Dob("1990-01-01"));
        peft2.setGender(new Gender("M"));
        peft2.setDob(new Dob("1990-02-02"));
        assertFalse(peft1.equals(peft2));

        // different tags
        peft1.setTagList(new ArrayList<Tag>());
        peft2.setDob(new Dob("1990-01-01"));
        ArrayList<Tag> peft2Tags = new ArrayList<>();
        peft2Tags.add(new Tag("Diabetes"));
        peft2.setTagList(peft2Tags);
        assertFalse(peft1.equals(peft2));

        // all fields equals
        // due to the previous tests changing all peft2's fields
        // to match peft1, the only difference at this point is the tagList
        peft2.setTagList(new ArrayList<Tag>());
        assertTrue(peft1.equals(peft2));
    }
}

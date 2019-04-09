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

        peft1.setName(new Name("Peter"));
        peft1.setNric(new Nric("S1234567A"));
        peft1.setEmail(new Email("peter@gmail.com"));
        peft1.setAddress(new Address("1 Simei Road"));

        peft2.setName(new Name("Peter"));
        peft2.setNric(new Nric("S1234567A"));
        peft2.setEmail(new Email("peter@gmail.com"));
        peft2.setAddress(new Address("1 Simei Road"));

        assertTrue(peft1.equals(peft2));

        peft1.setContact(new Contact("11111111"));
        peft1.setGender(new Gender("M"));
        peft1.setDob(new Dob("1990-01-01"));
        peft1.setTagList(new ArrayList<Tag>());

        peft2.setContact(new Contact("11111111"));
        peft2.setGender(new Gender("M"));
        peft2.setDob(new Dob("1990-01-01"));
        ArrayList<Tag> peft2Tags = new ArrayList<>();
        peft2Tags.add(new Tag("Diabetes"));
        peft2.setTagList(peft2Tags);

        assertFalse(peft1.equals(peft2));

    }
}

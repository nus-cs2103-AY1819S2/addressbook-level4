package quickdocs.logic.parser;

import static quickdocs.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import quickdocs.logic.commands.EditPatientCommand;
import quickdocs.logic.parser.exceptions.ParseException;
import quickdocs.model.patient.Address;
import quickdocs.model.patient.Contact;
import quickdocs.model.patient.Dob;
import quickdocs.model.patient.Email;
import quickdocs.model.patient.Gender;
import quickdocs.model.patient.Name;
import quickdocs.model.patient.Nric;
import quickdocs.model.patient.PatientEditedFields;
import quickdocs.model.tag.Tag;
import quickdocs.testutil.Assert;

public class EditPatientParserTest {

    private EditPatientParser parser;

    @Before
    public void init() {
        parser = new EditPatientParser();
    }

    @Test
    public void invalidEdit() {

        Name name = new Name("Peter Tan");
        Nric nric = new Nric("S9123456A");
        Email email = new Email("ptan@gmail.com");
        Address address = new Address("1 Simei Road");
        Contact contact = new Contact("91111111");
        Gender gender = new Gender("M");
        Dob dob = new Dob("1999-09-09");
        Tag tag = new Tag("Diabetes");
        ArrayList<Tag> tagList = new ArrayList<Tag>();
        tagList.add(tag);

        // no original nric defined
        String userInput = " n/" + name.getName() + " "
                + "r/" + nric.getNric() + " "
                + "e/" + email.getEmail() + " "
                + "t/" + tag.tagName;
        Assert.assertThrows(ParseException.class, ()-> parser.parse(userInput));

        // original nric wrong format
        String userInput2 = " S111 n/" + name.getName() + " "
                + "r/" + nric.getNric() + " "
                + "e/" + email.getEmail() + " "
                + "t/" + tag.tagName;
        Assert.assertThrows(IllegalArgumentException.class, ()-> parser.parse(userInput2));

        // no fields entered for editing
        String userInput3 = " S9234123C";
        Assert.assertThrows(ParseException.class, ()-> parser.parse(userInput3));

    }

    @Test
    public void successfulEdit() {

        Name name = new Name("Peter Tan");
        Nric originalNric = new Nric("S9123456A");
        Nric nric = new Nric("S9123456B");
        Email email = new Email("ptan@gmail.com");
        Address address = new Address("1 Simei Road");
        Contact contact = new Contact("91111111");
        Gender gender = new Gender("M");
        Dob dob = new Dob("1999-09-09");
        Tag tag = new Tag("Diabetes");
        ArrayList<Tag> tagList = new ArrayList<Tag>();
        tagList.add(tag);

        String userInput = originalNric.getNric()
                + " n/" + name.getName() + " "
                + "r/" + nric.getNric() + " "
                + "e/" + email.getEmail() + " "
                + "a/" + address.getAddress() + " "
                + "c/" + contact.getContact() + " "
                + "g/" + gender.getGender() + " "
                + "d/" + dob.getDob() + " "
                + "t/" + tag.tagName;

        PatientEditedFields peft = new PatientEditedFields();
        peft.setName(new Name("Peter Tan"));
        peft.setNric(new Nric("S9123456B"));
        peft.setEmail(new Email("ptan@gmail.com"));
        peft.setAddress(new Address("1 Simei Road"));
        peft.setContact(new Contact("91111111"));
        peft.setGender(new Gender("M"));
        peft.setDob(new Dob("1999-09-09"));
        peft.setTagList(tagList);

        assertParseSuccess(parser, userInput, new EditPatientCommand(originalNric, peft));

    }

}

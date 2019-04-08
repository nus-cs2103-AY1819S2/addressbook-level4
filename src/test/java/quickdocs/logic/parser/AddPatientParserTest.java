package quickdocs.logic.parser;

import static quickdocs.logic.parser.CommandParserTestUtil.assertParseFailure;
import static quickdocs.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import quickdocs.model.patient.Address;
import quickdocs.model.patient.Contact;
import quickdocs.model.patient.Dob;
import quickdocs.model.patient.Gender;
import quickdocs.model.patient.Name;
import quickdocs.model.patient.Nric;
import quickdocs.model.patient.Patient;
import quickdocs.logic.commands.AddPatientCommand;
import quickdocs.model.patient.Email;
import quickdocs.model.tag.Tag;

public class AddPatientParserTest {

    private AddPatientParser parser;

    @Before
    public void init() {
        parser = new AddPatientParser();
    }

    @Test
    public void successfulAddPatient() {

        Name name = new Name("Peter Tan");
        Nric nric = new Nric("S9123456A");
        Email email = new Email("ptan@gmail.com");
        Address address = new Address("1 Simei Road");
        Contact contact = new Contact("91111111");
        Gender gender = new Gender("M");
        Dob dob = new Dob("1999-09-09");
        ArrayList<Tag> tagList = new ArrayList<Tag>();

        String userInput = " n/" + name.getName() + " "
                + "r/" + nric.getNric() + " "
                + "e/" + email.getEmail() + " "
                + "a/" + address.getAddress() + " "
                + "c/" + contact.getContact() + " "
                + "g/" + gender.getGender() + " "
                + "d/" + dob.getDob();
        Patient patient1 = new Patient(name, nric, email, address, contact, gender, dob, tagList);
        assertParseSuccess(parser, userInput,
                new AddPatientCommand(patient1));
    }

    @Test
    public void invalidPatientAdding() {
        Name name = new Name("Peter Tan");
        Nric nric = new Nric("S9123456A");
        Email email = new Email("ptan@gmail.com");
        Address address = new Address("1 Simei Road");
        Contact contact = new Contact("91111111");
        Gender gender = new Gender("M");
        Dob dob = new Dob("1999-09-09");
        ArrayList<Tag> tagList = new ArrayList<Tag>();

        // missing gender
        String userInput = " n/" + name.getName() + " "
                + "r/" + nric.getNric() + " "
                + "e/" + email.getEmail() + " "
                + "a/" + address.getAddress() + " "
                + "c/" + contact.getContact() + " "
                + "d/" + dob.getDob();
        assertParseFailure(parser, userInput, AddPatientParser.INVALID_ADD_ARGUMENTS);

    }

}

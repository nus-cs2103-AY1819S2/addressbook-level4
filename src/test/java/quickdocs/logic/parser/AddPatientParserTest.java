package quickdocs.logic.parser;

import static quickdocs.logic.parser.CommandParserTestUtil.assertParseFailure;
import static quickdocs.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import quickdocs.logic.commands.AddPatientCommand;
import quickdocs.model.patient.Address;
import quickdocs.model.patient.Contact;
import quickdocs.model.patient.Dob;
import quickdocs.model.patient.Email;
import quickdocs.model.patient.Gender;
import quickdocs.model.patient.Name;
import quickdocs.model.patient.Nric;
import quickdocs.model.patient.Patient;
import quickdocs.model.tag.Tag;

public class AddPatientParserTest {

    private AddPatientParser parser;

    @Before
    public void init() {
        parser = new AddPatientParser();
    }

    @Test
    public void parseAddPatient_validInput_success() {

        Name name = new Name("Peter Tan");
        Nric nric = new Nric("S9123456A");
        Email email = new Email("ptan@gmail.com");
        Address address = new Address("1 Simei Road");
        Contact contact = new Contact("91111111");
        Gender gender = new Gender("M");
        Dob dob = new Dob("1999-09-09");
        ArrayList<Tag> tagList = new ArrayList<Tag>();

        //tags
        Tag tag1 = new Tag("Diabetes");
        Tag tag2 = new Tag("Highbloodpressure");
        Tag tag3 = new Tag("Asthma");
        tagList.add(tag1);
        tagList.add(tag2);
        tagList.add(tag3);

        String userInput = createTestInput(name, nric, email, address, contact, gender, dob, tag1, tag2, tag3);

        Patient patient1 = new Patient(name, nric, email, address, contact, gender, dob, tagList);
        assertParseSuccess(parser, userInput,
                new AddPatientCommand(patient1));
    }

    @Test
    public void parseAddPatient_insufficientInputs_failure() {

        Name name = new Name("Peter Tan");
        Nric nric = new Nric("S9123456A");
        Email email = new Email("ptan@gmail.com");
        Address address = new Address("1 Simei Road");
        Contact contact = new Contact("91111111");
        Gender gender = new Gender("M");
        Dob dob = new Dob("1999-09-09");

        // missing parameters starting from name
        String userInput = createTestInput(nric, email, address, contact, gender, dob);
        assertParseFailure(parser, userInput, AddPatientParser.INVALID_ADD_ARGUMENTS);

        userInput = createTestInput(name, email, address, contact, gender, dob);
        assertParseFailure(parser, userInput, AddPatientParser.INVALID_ADD_ARGUMENTS);

        userInput = createTestInput(name, nric, address, contact, gender, dob);
        assertParseFailure(parser, userInput, AddPatientParser.INVALID_ADD_ARGUMENTS);

        userInput = createTestInput(name, nric, email, contact, gender, dob);
        assertParseFailure(parser, userInput, AddPatientParser.INVALID_ADD_ARGUMENTS);

        userInput = createTestInput(name, nric, email, address, gender, dob);
        assertParseFailure(parser, userInput, AddPatientParser.INVALID_ADD_ARGUMENTS);

        userInput = createTestInput(name, nric, email, address, contact, dob);
        assertParseFailure(parser, userInput, AddPatientParser.INVALID_ADD_ARGUMENTS);

        userInput = createTestInput(name, nric, email, address, contact, gender);
        assertParseFailure(parser, userInput, AddPatientParser.INVALID_ADD_ARGUMENTS);

    }

    /**
     * To produce the userInput for parsing testing
     *
     * @param params one or more parameters to be added to parse into an AddPatientCommand
     * @return the string input to be supplied into the AddPatientParser
     */
    public String createTestInput(Object... params) {
        String userInput = " ";

        for (Object param : params) {

            if (param instanceof Name) {
                userInput += "n/" + ((Name) param).getName() + " ";
                continue;
            }

            if (param instanceof Nric) {
                userInput += "r/" + ((Nric) param).getNric() + " ";
                continue;
            }

            if (param instanceof Email) {
                userInput += "e/" + ((Email) param).getEmail() + " ";
                continue;
            }

            if (param instanceof Address) {
                userInput += "a/" + ((Address) param).getAddress() + " ";
                continue;
            }

            if (param instanceof Contact) {
                userInput += "c/" + ((Contact) param).getContact() + " ";
                continue;
            }

            if (param instanceof Gender) {
                userInput += "g/" + ((Gender) param).getGender() + " ";
                continue;
            }

            if (param instanceof Dob) {
                userInput += "d/" + ((Dob) param).getDob() + " ";
                continue;
            }

            if (param instanceof Tag) {
                userInput += "t/" + ((Tag) param).tagName + " ";
            }
        }
        return userInput;
    }

}

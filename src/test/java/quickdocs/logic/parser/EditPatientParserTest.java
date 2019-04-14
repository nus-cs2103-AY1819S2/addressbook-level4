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

    private Name name = new Name("Peter Tan");
    private Nric nric = new Nric("S9123456A");
    private Email email = new Email("ptan@gmail.com");
    private Address address = new Address("1 Simei Road");
    private Contact contact = new Contact("91111111");
    private Gender gender = new Gender("M");
    private Dob dob = new Dob("1999-09-09");
    private Tag tag = new Tag("Diabetes");
    private ArrayList<Tag> tagList = new ArrayList<Tag>();

    @Before
    public void init() {
        parser = new EditPatientParser();
        tagList.add(tag);
    }

    @Test
    public void parseEditPatient_noPreamble_throwParseException() {

        // no original nric defined
        String userInput = createTestInput(name, nric, email, tag);
        Assert.assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parseEditPatient_premableInvalid_throwIllegalArgumentException() {
        String userInput = " S111" + createTestInput(name, nric, email, address, contact, gender, dob);
        Assert.assertThrows(IllegalArgumentException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parseEditPatient_nothingToEdit_throwParseException() {
        // no fields entered for editing
        String userInput3 = " S9234123C";
        Assert.assertThrows(ParseException.class, () -> parser.parse(userInput3));
    }

    @Test
    public void parseEditPatient_validEdit_success() {

        Nric editedNric = new Nric("S9123456B");
        String userInput = nric + createTestInput(name, editedNric, email, address, contact, gender, dob, tag);

        PatientEditedFields peft = new PatientEditedFields();
        peft.setName(new Name("Peter Tan"));
        peft.setNric(new Nric("S9123456B"));
        peft.setEmail(new Email("ptan@gmail.com"));
        peft.setAddress(new Address("1 Simei Road"));
        peft.setContact(new Contact("91111111"));
        peft.setGender(new Gender("M"));
        peft.setDob(new Dob("1999-09-09"));
        ArrayList<Tag> peftTagList = new ArrayList<Tag>();
        peftTagList.add(new Tag("Diabetes"));
        peft.setTagList(peftTagList);

        assertParseSuccess(parser, userInput, new EditPatientCommand(nric, peft));
    }

    /**
     * To produce the userInput for parsing testing
     *
     * @param params one or more parameters to be added to parsed
     * @return the string input to be supplied
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
                continue;
            }

        }
        return userInput;
    }


}

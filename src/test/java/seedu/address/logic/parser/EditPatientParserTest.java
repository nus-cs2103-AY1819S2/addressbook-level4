package seedu.address.logic.parser;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.commands.EditPatientCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.patient.Address;
import seedu.address.model.patient.Contact;
import seedu.address.model.patient.Dob;
import seedu.address.model.patient.Email;
import seedu.address.model.patient.Gender;
import seedu.address.model.patient.Name;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.PatientEditedFields;
import seedu.address.model.tag.Tag;

public class EditPatientParserTest {

    private EditPatientParser parser;

    @Before
    public void init() {
        parser = new EditPatientParser();
    }

    @Test
    public void successfulEdit() {
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

        String userInput = "0 n/" + name.getName() + " "
                + "r/" + nric.getNric() + " "
                + "e/" + email.getEmail() + " "
                + "t/" + tag.tagName;

        PatientEditedFields peft = new PatientEditedFields();
        peft.setName(new Name("Peter Tan"));
        peft.setNric(new Nric("S9123456A"));
        peft.setEmail(new Email("ptan@gmail.com"));

        try {
            Assert.assertTrue(parser.parse(userInput).equals(new EditPatientCommand(0, peft)));
        } catch (ParseException ce) {
            Assert.fail();
        }
    }

}

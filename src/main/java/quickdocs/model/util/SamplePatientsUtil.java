package quickdocs.model.util;

import java.util.ArrayList;

import quickdocs.logic.parser.AddPatientParser;
import quickdocs.model.patient.Address;
import quickdocs.model.patient.Contact;
import quickdocs.model.patient.Dob;
import quickdocs.model.patient.Email;
import quickdocs.model.patient.Gender;
import quickdocs.model.patient.Name;
import quickdocs.model.patient.Nric;
import quickdocs.model.patient.Patient;
import quickdocs.model.tag.Tag;

/**
 * Sample {@code Patient} data to initialise model
 */
public class SamplePatientsUtil {
    public static Patient[] getSamplePatients() {
        //padd n/Peter Tan a/1 Simei Road e/ptan@gmail.com c/91111111 r/S9123456A g/M d/1993-02-01 t/diabetes
        //padd n/Piper Wright a/3 diamond city e/pwright@gmail.com c/93333333 r/S9234568C g/F d/1995-03-03
        //t/highbloodpressure
        //padd n/Perry Ng a/2 Shenton Road e/ssh@gmail.com c/92222222 r/S9234567B g/M d/1995-02-02
        //t/highbloodpressure t/diabetes
        return new Patient[] {
            new Patient(new Name("Peter Tan"), new Nric("S9367777A"), new Email("ptan@gmail.com"),
                    new Address("1 Simei Road"), new Contact("91111111"), new Gender("M"),
                    new Dob("1993-02-01"), parseTags("diabetes")),
            new Patient(new Name("Piper Wright"), new Nric("S9534568C"), new Email("pwright@gmail.com"),
                    new Address("3 diamond city"), new Contact("93333333"), new Gender("F"),
                    new Dob("1995-03-03"), parseTags("highbloodpressure")),
            new Patient(new Name("Perry Ng"), new Nric("S9534567B"), new Email("ssh@gmail.com"),
                    new Address("2 Shenton Road"), new Contact("92222222"), new Gender("M"),
                    new Dob("1995-02-02"), parseTags("highbloodpressure", "diabetes")),
        };
    }

    /**
     * Parse strings into {@code Tag}
     */
    public static ArrayList<Tag> parseTags(String... strings) {
        final ArrayList<Tag> tagList = new ArrayList<>();
        for (String tagName : strings) {
            tagList.add(AddPatientParser.parseTag(tagName));
        }
        return tagList;
    }
}

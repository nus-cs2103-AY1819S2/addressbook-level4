package quickdocs.storage;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import quickdocs.model.consultation.Assessment;
import quickdocs.model.consultation.Consultation;
import quickdocs.model.consultation.Diagnosis;
import quickdocs.model.consultation.Prescription;
import quickdocs.model.consultation.Symptom;
import quickdocs.model.medicine.Medicine;
import quickdocs.model.patient.Address;
import quickdocs.model.patient.Contact;
import quickdocs.model.patient.Dob;
import quickdocs.model.patient.Email;
import quickdocs.model.patient.Gender;
import quickdocs.model.patient.Name;
import quickdocs.model.patient.Nric;
import quickdocs.model.patient.Patient;
import quickdocs.model.tag.Tag;

public class JsonAdaptedConsultationTest {

    private static final int VALID_INDEX = 1;
    private static final LocalDateTime VALID_LOCALDATETIME = LocalDateTime.now();

    private static final Symptom VALID_SYMPTOM = new Symptom("Sore throat");
    private static final ArrayList<Symptom> VALID_SYMPTOMS = new ArrayList<>(Arrays.asList(VALID_SYMPTOM));
    private static final Assessment VALID_ASSESSMENT = new Assessment("Throat infection");
    private static final Diagnosis VALID_DIAGNOSIS = new Diagnosis(VALID_ASSESSMENT, VALID_SYMPTOMS);

    private static final Medicine MED1 = new Medicine("Ibuprofen", 2);
    private static final Medicine MED2 = new Medicine("Morphine", 2);
    private static final ArrayList<Prescription> VALID_PRESCRIPTIONS =
            new ArrayList<>(Arrays.asList(new Prescription(MED1, 1),
                    new Prescription(MED2, 1)));

    // create a valid patient
    private static final String VALID_NAME = "Rachel";
    private static final String VALID_NRIC = "S1234567J";
    private static final String VALID_EMAIL = "Rachel@gmail.com";
    private static final String VALID_ADDRESS = "4 admiralty Road";
    private static final String VALID_CONTACT = "88888888";
    private static final String VALID_GENDER = "F";
    private static final String VALID_DOB = "2000-02-29";
    private static final List<JsonAdaptedTag> VALID_TAG = new ArrayList<>(
            Arrays.asList(new JsonAdaptedTag("Diabetes")));

    private static final Patient VALID_PATIENT = new Patient(
            new Name(VALID_NAME),
            new Nric(VALID_NRIC),
            new Email(VALID_EMAIL),
            new Address(VALID_ADDRESS),
            new Contact(VALID_CONTACT),
            new Gender(VALID_GENDER),
            new Dob(VALID_DOB),
            new ArrayList<>(Arrays.asList(new Tag("Diabetes")))
    );

    private static final Consultation EXPECTED = new Consultation(
            VALID_INDEX, VALID_PATIENT, VALID_LOCALDATETIME, VALID_DIAGNOSIS, VALID_PRESCRIPTIONS
    );


    @Test
    public void toModelType_validConsultationDetails_returnsConsultation() throws Exception {

        JsonAdaptedConsultation consultation = new JsonAdaptedConsultation(VALID_INDEX, VALID_LOCALDATETIME,
                VALID_PATIENT, VALID_DIAGNOSIS, VALID_PRESCRIPTIONS);

        assertEquals(EXPECTED.toString(), consultation.toModelType().toString());

        JsonAdaptedConsultation consultation2 = new JsonAdaptedConsultation(EXPECTED);
        assertEquals(consultation2.toModelType().toString(), consultation.toModelType().toString());

    }

}

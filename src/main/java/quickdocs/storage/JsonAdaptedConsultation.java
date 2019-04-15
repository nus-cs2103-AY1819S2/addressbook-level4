package quickdocs.storage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import quickdocs.commons.exceptions.IllegalValueException;
import quickdocs.model.consultation.Consultation;
import quickdocs.model.consultation.Diagnosis;
import quickdocs.model.consultation.Prescription;
import quickdocs.model.patient.Patient;

/**
 * Jackson-friendly version of {@link Consultation}.
 */
public class JsonAdaptedConsultation {

    private int index;
    private LocalDateTime session;

    private Patient patient;
    private Diagnosis diagnosis;

    private ArrayList<Prescription> prescriptions;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedConsultation(@JsonProperty("index") int index,
                                   @JsonProperty("session") LocalDateTime session,
                                   @JsonProperty("patient") Patient patient,
                                   @JsonProperty("diagnosis") Diagnosis diagnosis,
                                   @JsonProperty("prescriptions") List<Prescription> prescriptions) {
        this.index = index;
        this.session = session;
        this.patient = patient;
        this.diagnosis = diagnosis;

        this.prescriptions = new ArrayList<>();
        if (prescriptions != null) {
            this.prescriptions.addAll(prescriptions);
        }
    }

    /**
     * Converts a given {@code Consultation} into this class for Jackson use.
     */
    public JsonAdaptedConsultation(Consultation source) {

        this.index = source.getIndex();
        this.session = source.getSession();
        this.patient = source.getPatient();
        this.diagnosis = source.getDiagnosis();
        this.prescriptions = new ArrayList<>();
        this.prescriptions.addAll(source.getPrescriptions());
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @return The Consultation model object
     * @throws IllegalValueException if there were any data constraints violated in the tags.
     * @throws IllegalArgumentException if there were any data constraints violated for patient fields.
     */
    public Consultation toModelType() throws IllegalValueException, IllegalArgumentException {
        int modelIndex = this.index;
        LocalDateTime modelSession = this.session;
        Patient modelPatient = this.patient;
        Diagnosis modelDiagnosis = this.diagnosis;
        ArrayList<Prescription> prescriptions = new ArrayList<>();
        prescriptions.addAll(this.prescriptions);

        Consultation consultation = new Consultation(modelPatient);
        consultation.setIndex(modelIndex);
        consultation.setSession(modelSession);
        consultation.setDiagnosis(modelDiagnosis);
        consultation.setPrescriptions(prescriptions);
        return consultation;
    }


}

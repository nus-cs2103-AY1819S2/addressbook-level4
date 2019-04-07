package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.DocX;
import seedu.address.model.ReadOnlyDocX;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.medicalhistory.MedicalHistory;
import seedu.address.model.person.Doctor;
import seedu.address.model.person.Patient;

/**
 * An Immutable DocX that is serializable to JSON format.
 */
@JsonRootName(value = "docx")
class JsonSerializableDocX {

    public static final String MESSAGE_DUPLICATE_PERSON = "Patients list contains duplicate patient(s).";
    public static final String MESSAGE_DUPLICATE_DOCTOR = "Doctors list contains duplicate doctor(s).";
    public static final String MESSAGE_DUPLICATE_MEDHIST =
            "Medical history list contains duplicate medical history(s).";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT = "Appointment list contains duplicate appointments.";

    private final List<JsonAdaptedPatient> patients = new ArrayList<>();
    private final List<JsonAdaptedDoctor> doctors = new ArrayList<>();
    private final List<JsonAdaptedMedicalHistory> medicalHistories = new ArrayList<>();
    private final List<JsonAdaptedAppointment> appointments = new ArrayList<>();
    private final JsonAdaptedPersonIdCounter personIdCounter;

    /**
     * Constructs a {@code JsonSerializableDocX} with the given patients.
     */
    @JsonCreator
    public JsonSerializableDocX(@JsonProperty("patients") List<JsonAdaptedPatient> patients,
                                @JsonProperty("doctors") List<JsonAdaptedDoctor> doctors,
                                @JsonProperty("medicalHistories")
                                               List<JsonAdaptedMedicalHistory> medicalHistories,
                                @JsonProperty("appointments") List<JsonAdaptedAppointment> appointments,
                                @JsonProperty("personIdCounter") JsonAdaptedPersonIdCounter personIdCounter)
            throws IllegalValueException {
        this.patients.addAll(patients);
        this.doctors.addAll(doctors);
        this.appointments.addAll(appointments);
        this.medicalHistories.addAll(medicalHistories);
        this.personIdCounter = personIdCounter;
    }

    /**
     * Converts a given {@code ReadOnlyDocX} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableDocX}.
     */
    public JsonSerializableDocX(ReadOnlyDocX source) {
        patients.addAll(source.getPatientList().stream().map(JsonAdaptedPatient::new).collect(Collectors.toList()));

        doctors.addAll(source.getDoctorList().stream().map(JsonAdaptedDoctor::new).collect(Collectors.toList()));

        medicalHistories.addAll(source.getMedHistList().stream().map(JsonAdaptedMedicalHistory::new)
                .collect(Collectors.toList()));

        appointments.addAll(source.getAppointmentList().stream().map(JsonAdaptedAppointment::new)
                .collect(Collectors.toList()));

        personIdCounter = new JsonAdaptedPersonIdCounter(source.getPersonIdCounter().getCurrentMaxId());
    }

    /**
     * Converts this docX into the model's {@code DocX} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public DocX toModelType() throws IllegalValueException {
        DocX docX = new DocX();

        for (JsonAdaptedAppointment jsonAdaptedAppointment : appointments) {
            Appointment appointment = jsonAdaptedAppointment.toModelType();
            if (docX.hasAppointment(appointment)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_APPOINTMENT);
            }
            //System.out.println(appointment);
            docX.addAppointment(appointment);
        }

        for (JsonAdaptedDoctor jsonAdaptedDoctor : doctors) {
            Doctor doctor = jsonAdaptedDoctor.toModelType();
            if (docX.hasDoctor(doctor)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_DOCTOR);
            }
            docX.addDoctor(doctor);
        }

        for (JsonAdaptedPatient jsonAdaptedPatient : patients) {
            Patient patient = jsonAdaptedPatient.toModelType();
            if (docX.hasPatient(patient)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            docX.addPatient(patient);
        }

        for (JsonAdaptedMedicalHistory jsonAdaptedMedicalHistory : medicalHistories) {
            MedicalHistory medicalHistory = jsonAdaptedMedicalHistory.toModelType();
            if (docX.hasMedHist(medicalHistory)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_MEDHIST);

            }
            docX.addMedHist(medicalHistory);
        }

        //docX.setIdCounter(idCounter.toModelType());

        return docX;
    }
}

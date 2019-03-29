package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.QuickDocs;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentManager;
import seedu.address.model.consultation.Consultation;
import seedu.address.model.consultation.ConsultationManager;
import seedu.address.model.medicine.Directory;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.medicine.MedicineManager;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.PatientManager;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.ReminderManager;

/**
 * QuickDocs serializable to json format
 */
public class JsonSerializableQuickDocs {

    private static final String MESSAGE_DUPLICATE_PATIENT = "Patients list contains duplicate patient(s)";
    private static final String MESSAGE_DUPLICATE_APPOINTMENT = "Appointment list contains duplicate appointment(s)";
    private static final String MESSAGE_DUPLICATE_REMINDER = "Reminder list contains duplicate reminder(s)";
    private static final String MESSGAE_DUPLICATE_MEDICINE = "Medicine list contains medicines with same name.";
    private static final String MESSAGE_NONEXISTING_MEDICINE =
            "A Directory contains a medicine not found in the list of medicines.";

    private final List<JsonAdaptedPatient> patientList = new ArrayList<>();
    private final List<JsonAdaptedConsultation> consultationList = new ArrayList<>();
    private final List<JsonAdaptedAppointment> appointmentList = new ArrayList<>();
    private final List<JsonAdaptedReminder> reminderList = new ArrayList<>();
    private final List<JsonAdaptedMedicine> medicineList = new ArrayList<>();
    private JsonAdaptedDirectory rootDirectory;

    @JsonCreator
    public JsonSerializableQuickDocs(@JsonProperty("patientList") List<JsonAdaptedPatient> patients,
                                     @JsonProperty("consultationList") List<JsonAdaptedConsultation> consultations,
                                     @JsonProperty("appointmentList") List<JsonAdaptedAppointment> appointments,
                                     @JsonProperty("reminderList") List<JsonAdaptedReminder> reminders,
                                     @JsonProperty("medicineList") List<JsonAdaptedMedicine> medicines,
                                     @JsonProperty("rootDirectory") JsonAdaptedDirectory rootDirectory) {
        this.patientList.addAll(patients);
        this.consultationList.addAll(consultations);
        this.appointmentList.addAll(appointments);
        this.reminderList.addAll(reminders);
        this.medicineList.addAll(medicines);
        this.rootDirectory = rootDirectory;
    }

    /**
     * Converts a given {@code QuickDocs} into this class for Jackson use.
     */
    public JsonSerializableQuickDocs(QuickDocs source) {
        patientList.addAll(source.getPatientManager().getPatientList()
                .stream().map(JsonAdaptedPatient::new).collect(Collectors.toList()));
        consultationList.addAll(source.getConsultationManager().getConsultationList()
                .stream().map(JsonAdaptedConsultation::new).collect(Collectors.toList()));
        appointmentList.addAll(source.getAppointmentManager().getAppointmentList()
                .stream().map(JsonAdaptedAppointment::new).collect(Collectors.toList()));
        reminderList.addAll(source.getReminderManager().getReminderList()
                .stream().map(JsonAdaptedReminder::new).collect(Collectors.toList()));
        medicineList.addAll(source.getMedicineManager().getListOfMedicine()
                .stream().map(JsonAdaptedMedicine::new).collect(Collectors.toList()));
        rootDirectory = new JsonAdaptedDirectory(source.getMedicineManager().getRoot());
    }

    /**
     * Converts this address book into the model's {@code QuickDocs} object.
     *
     * @throws IllegalValueException    if there were any data constraints violated.
     * @throws IllegalArgumentException if there were any data constraints violated for any class fields
     */
    public QuickDocs toModelType() throws IllegalValueException, IllegalArgumentException {
        QuickDocs quickDocs = new QuickDocs();

        PatientManager patientManager = quickDocs.getPatientManager();
        for (JsonAdaptedPatient jsonAdaptedPatient : patientList) {
            Patient patient = jsonAdaptedPatient.toModelType();

            // handle duplicates
            if (patientManager.isDuplicatePatient(patient)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PATIENT);
            }
            patientManager.addPatient(patient);
        }

        ConsultationManager consultationManager = quickDocs.getConsultationManager();
        for (JsonAdaptedConsultation jsonAdaptedConsultation : consultationList) {
            Consultation consultation = jsonAdaptedConsultation.toModelType();
            consultationManager.addConsultation(consultation);
        }

        // loop for medicine, appointment, and records
        AppointmentManager appointmentManager = quickDocs.getAppointmentManager();
        for (JsonAdaptedAppointment jsonAdaptedAppointment : appointmentList) {
            Appointment appointment = jsonAdaptedAppointment.toModelType();

            if (appointmentManager.duplicateApp(appointment)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_APPOINTMENT);
            }
            appointmentManager.addAppointment(appointment);
        }

        ReminderManager reminderManager = quickDocs.getReminderManager();
        for (JsonAdaptedReminder jsonAdaptedReminder : reminderList) {
            Reminder reminder = jsonAdaptedReminder.toModelType();

            if (reminderManager.duplicateReminder(reminder)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_REMINDER);
            }
            reminderManager.addReminder(reminder);
        }

        MedicineManager medicineManager = quickDocs.getMedicineManager();
        ArrayList<Medicine> listOfMedicine = new ArrayList<>();
        listOfMedicine.addAll(medicineList
                .stream()
                .map((JsonAdaptedMedicine::toModelType))
                .collect(Collectors.toList()));
        HashMap<String, Medicine> medicineHashMap = new HashMap<>();
        for (Medicine medicine : listOfMedicine) {
            String medicineName = medicine.name;
            if (medicineHashMap.containsKey(medicineName)) {
                throw new IllegalValueException(MESSGAE_DUPLICATE_MEDICINE);
            } else {
                medicineHashMap.put(medicineName, medicine);
            }
        }
        Directory modelTypeRoot = toModelTypeDirectory(medicineHashMap, rootDirectory);
        medicineManager.setRoot(modelTypeRoot);
        medicineManager.setListOfMedicine(listOfMedicine);
        return quickDocs;
    }

    /**
     * Convert a {@link JsonAdaptedDirectory} to a Directory using information from medicineHashMap
     * @param map A hashmap of medicine name mapping to medicine
     * @param jsonDirectory the JsonAdaptedDirectory to convert from
     * @return The converted directory
     * @throws IllegalValueException if a directory contains medicine not from map
     */
    private Directory toModelTypeDirectory(HashMap<String, Medicine> map, JsonAdaptedDirectory jsonDirectory)
            throws IllegalValueException {
        Directory directory = new Directory(jsonDirectory.getName());
        directory.setThreshold(jsonDirectory.getThreshold().get());
        ArrayList<String> medicineNames = jsonDirectory.getListOfMedicineNames();
        for (String medicineName : medicineNames) {
            if (!map.containsKey(medicineName)) {
                throw new IllegalValueException(MESSAGE_NONEXISTING_MEDICINE);
            }
            directory.addMedicine(map.get(medicineName));
        }
        ArrayList<JsonAdaptedDirectory> jsonAdaptedDirectories = jsonDirectory.getListOfDirectories();
        for (JsonAdaptedDirectory jsonAdaptedDirectory : jsonAdaptedDirectories) {
            directory.addDirectory(toModelTypeDirectory(map, jsonAdaptedDirectory));
        }
        return directory;
    }
}

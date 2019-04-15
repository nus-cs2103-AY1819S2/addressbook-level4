package quickdocs.storage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import quickdocs.commons.exceptions.IllegalValueException;
import quickdocs.model.QuickDocs;
import quickdocs.model.appointment.Appointment;
import quickdocs.model.appointment.AppointmentManager;
import quickdocs.model.consultation.Consultation;
import quickdocs.model.consultation.ConsultationManager;
import quickdocs.model.medicine.Directory;
import quickdocs.model.medicine.Medicine;
import quickdocs.model.medicine.MedicineManager;
import quickdocs.model.patient.Patient;
import quickdocs.model.patient.PatientManager;
import quickdocs.model.record.MonthStatistics;
import quickdocs.model.record.StatisticsManager;
import quickdocs.model.reminder.Reminder;
import quickdocs.model.reminder.ReminderManager;

/**
 * This class allows QuickDocs to be saved into the external storage file in the json format.
 * Using json data from the external storage file, the QuickDocs object can be reconstructed
 * for use when QuickDocs is started
 */
public class JsonSerializableQuickDocs {

    public static final String MESSAGE_DUPLICATE_PATIENT = "Patients list contains duplicate patient(s).";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT = "Appointment list contains duplicate appointment(s).";
    public static final String MESSAGE_DUPLICATE_REMINDER = "Reminder list contains duplicate reminder(s).";
    public static final String MESSAGE_DUPLICATE_MEDICINE = "Medicine list contains medicines with same name.";
    public static final String MESSAGE_NONEXISTING_MEDICINE =
            "A Directory contains a medicine not found in the list of medicines.";
    public static final String MESSAGE_INVALID_CONSULTATION_FEE = "Consultation Fee is not a non-negative number.";

    private final List<JsonAdaptedPatient> patientList = new ArrayList<>();
    private final List<JsonAdaptedConsultation> consultationList = new ArrayList<>();
    private final List<JsonAdaptedAppointment> appointmentList = new ArrayList<>();
    private final List<JsonAdaptedReminder> reminderList = new ArrayList<>();
    private final List<JsonAdaptedMedicine> medicineList = new ArrayList<>();
    private JsonAdaptedDirectory rootDirectory;
    private final List<JsonAdaptedMonthStatistics> monthStatisticsList = new ArrayList<>();
    private BigDecimal consultationFee = StatisticsManager.DEFAULT_CONSULTATION_FEE;

    @JsonCreator
    public JsonSerializableQuickDocs(@JsonProperty("patientList") List<JsonAdaptedPatient> patients,
                                     @JsonProperty("consultationList") List<JsonAdaptedConsultation> consultations,
                                     @JsonProperty("appointmentList") List<JsonAdaptedAppointment> appointments,
                                     @JsonProperty("reminderList") List<JsonAdaptedReminder> reminders,
                                     @JsonProperty("medicineList") List<JsonAdaptedMedicine> medicines,
                                     @JsonProperty("rootDirectory") JsonAdaptedDirectory rootDirectory,
                                     @JsonProperty("monthStatisticsList")
                                             List<JsonAdaptedMonthStatistics> monthStatisticsList,
                                     @JsonProperty("consultationFee") BigDecimal consultationFee) {
        this.patientList.addAll(patients);
        this.consultationList.addAll(consultations);
        this.appointmentList.addAll(appointments);
        this.reminderList.addAll(reminders);
        this.medicineList.addAll(medicines);
        this.rootDirectory = rootDirectory;
        this.monthStatisticsList.addAll(monthStatisticsList);
        this.consultationFee = consultationFee;
    }

    /**
     * Converts a given {@code QuickDocs} into a JsonSerializableQuickDocs to save into
     * an external Json file using Jackson
     *
     * @param source The QuickDocs model object in memory that is currently holding the data from
     *               all five modules
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
        monthStatisticsList.addAll(source.getStatisticsManager().getMonthStatisticsList()
                .stream().map(JsonAdaptedMonthStatistics::new).collect(Collectors.toList()));
        consultationFee = source.getStatisticsManager().getConsultationFee();
    }

    /**
     * Converts the JsonSerializableQuickDocs into the model's {@code QuickDocs} object.
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

        AppointmentManager appointmentManager = quickDocs.getAppointmentManager();
        for (JsonAdaptedAppointment jsonAdaptedAppointment : appointmentList) {
            Appointment appointment = jsonAdaptedAppointment.toModelType();

            // handle duplicates
            if (appointmentManager.hasDuplicateAppointment(appointment)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_APPOINTMENT);
            }
            appointmentManager.addAppointment(appointment);
        }

        ReminderManager reminderManager = quickDocs.getReminderManager();
        for (JsonAdaptedReminder jsonAdaptedReminder : reminderList) {
            Reminder reminder = jsonAdaptedReminder.toModelType();

            // handle duplicates
            if (reminderManager.hasDuplicateReminder(reminder)) {
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
        listOfMedicine.sort(Comparator.comparing((Medicine medicine) -> (medicine.name.toLowerCase())));
        HashMap<String, Medicine> medicineHashMap = new HashMap<>();
        for (Medicine medicine : listOfMedicine) {
            String medicineName = medicine.name;
            if (medicineHashMap.containsKey(medicineName)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_MEDICINE);
            } else {
                medicineHashMap.put(medicineName, medicine);
            }
        }
        Directory modelTypeRoot = toModelTypeDirectory(medicineHashMap, rootDirectory);
        medicineManager.setRoot(modelTypeRoot);
        medicineManager.setListOfMedicine(listOfMedicine);

        StatisticsManager statisticsManager = quickDocs.getStatisticsManager();
        for (JsonAdaptedMonthStatistics jsonAdaptedMonthStatistics : monthStatisticsList) {
            MonthStatistics monthStatistics = jsonAdaptedMonthStatistics.toModelType();
            statisticsManager.addMonthStatistics(monthStatistics);
        }
        if (this.consultationFee.compareTo(BigDecimal.ZERO) == -1) {
            throw new IllegalValueException(MESSAGE_INVALID_CONSULTATION_FEE);
        }
        statisticsManager.setConsultationFee(this.consultationFee);

        return quickDocs;
    }

    /**
     * Convert a {@link JsonAdaptedDirectory} to a Directory using information from medicineHashMap
     *
     * @param map           A hashmap of medicine name mapping to medicine
     * @param jsonDirectory the JsonAdaptedDirectory to convert from
     * @return The converted directory
     * @throws IllegalValueException if a directory contains medicine not from map
     */
    private Directory toModelTypeDirectory(HashMap<String, Medicine> map, JsonAdaptedDirectory jsonDirectory)
            throws IllegalValueException {
        Directory directory = new Directory(jsonDirectory.getName());
        if (jsonDirectory.getThreshold().isPresent()) {
            directory.setThreshold(jsonDirectory.getThreshold().get());
        }
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

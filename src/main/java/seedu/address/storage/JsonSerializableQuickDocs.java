package seedu.address.storage;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.PatientManager;
import seedu.address.model.record.MonthStatistics;
import seedu.address.model.record.StatisticsManager;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.ReminderManager;

/**
 * QuickDocs serializable to json format
 */
public class JsonSerializableQuickDocs {

    private static final String MESSAGE_DUPLICATE_PATIENT = "Patients list contains duplicate patient(s)";
    private static final String MESSAGE_DUPLICATE_APPOINTMENT = "Appointment list contains duplicate appointment(s)";
    private static final String MESSAGE_DUPLICATE_REMINDER = "Reminder list contains duplicate reminder(s)";

    private final List<JsonAdaptedPatient> patientList = new ArrayList<>();
    private final List<JsonAdaptedConsultation> consultationList = new ArrayList<>();
    private final List<JsonAdaptedAppointment> appointmentList = new ArrayList<>();
    private final List<JsonAdaptedReminder> reminderList = new ArrayList<>();
    private final List<JsonAdaptedMedicine> medicineList = new ArrayList<>();
    private final List<JsonAdaptedMonthStatistics> monthStatisticsList = new ArrayList<>();
    private BigDecimal consultationFee = StatisticsManager.DEFAULT_CONSULTATION_FEE;

    @JsonCreator
    public JsonSerializableQuickDocs(@JsonProperty("patientList") List<JsonAdaptedPatient> patients,
                                     @JsonProperty("consultationList") List<JsonAdaptedConsultation> consultations,
                                     @JsonProperty("appointmentList") List<JsonAdaptedAppointment> appointments,
                                     @JsonProperty("reminderList") List<JsonAdaptedReminder> reminders,
                                     @JsonProperty("medicineList") List<JsonAdaptedMedicine> medicines,
                                     @JsonProperty("monthStatisticsList")
                                                 List<JsonAdaptedMonthStatistics> monthStatisticsList,
                                     @JsonProperty("consultationFee") BigDecimal consultationFee) {
        this.patientList.addAll(patients);
        this.consultationList.addAll(consultations);
        this.appointmentList.addAll(appointments);
        this.reminderList.addAll(reminders);
        this.medicineList.addAll(medicines);
        this.monthStatisticsList.addAll(monthStatisticsList);
        this.consultationFee = consultationFee;
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
        monthStatisticsList.addAll(source.getStatisticsManager().getMonthStatisticsList()
                .stream().map(JsonAdaptedMonthStatistics::new).collect(Collectors.toList()));
        consultationFee = source.getStatisticsManager().getConsultationFee();
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

        StatisticsManager statisticsManager = quickDocs.getStatisticsManager();
        for (JsonAdaptedMonthStatistics jsonAdaptedMonthStatistics : monthStatisticsList) {
            MonthStatistics monthStatistics = jsonAdaptedMonthStatistics.toModelType();
            statisticsManager.addMonthStatistics(monthStatistics);
        }
        statisticsManager.setConsultationFee(this.consultationFee);

        return quickDocs;
    }

}

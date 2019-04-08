package seedu.address.model;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.consultation.Consultation;
import seedu.address.model.consultation.Diagnosis;
import seedu.address.model.consultation.Prescription;
import seedu.address.model.medicine.Directory;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Patient;
import seedu.address.model.record.Record;
import seedu.address.model.record.Statistics;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.tag.Tag;

/**
 * The API of the Model component.
 */
public interface Model {

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    void updateFilteredReminderList(Predicate<Reminder> predicate);

    ObservableList<Reminder> getFilteredReminderList();

    ReadOnlyProperty<Reminder> selectedReminderProperty();

    void setSelectedReminder(Reminder reminder);

    //===========Quickdocs methods=====================================
    void initQuickDocsSampleData();

    QuickDocs getQuickDocs();

    //===========Medicine Storage =====================================
    void addMedicine(String medicineName, String[] path, BigDecimal price);

    void addMedicine(String medicineName, int quantity, String[] path, BigDecimal price);

    void addDirectory(String directoryName, String[] path);

    Optional<Medicine> findMedicine(String medicineName);

    Optional<Medicine> findMedicine(String[] path);

    void purchaseMedicine(String[] path, int quantity, BigDecimal cost);

    void purchaseMedicine(String medicineName, int quantity, BigDecimal cost);

    Optional<Directory> findDirectory(String[] path);

    void setThreshold(Medicine medicine, int threshold);

    void setThreshold(Directory directory, int threshold);

    void addExistingMedicineToDirectory(Medicine medicine, String[] path);

    void setPrice(Medicine medicine, BigDecimal price);

    ArrayList<String> getDirectorySuggestions(String path);

    ArrayList<String> getMedicineSuggestions(String path);
    //===========Patient module operations============================
    boolean duplicatePatient(Patient patient);

    void addPatient(Patient patient);

    boolean isPatientListEmpty();

    boolean checkValidIndex(int index);

    Patient getPatientAtIndex(int index);

    boolean checkDuplicatePatientAfterEdit(int index, Patient editedPatient);

    void replacePatient(int index, Patient editedPatient);

    String findPatientsByName(String searchSequence);

    String listFiftyPatients();

    String findPatientsByNric(String searchSequence);

    String findPatientsByTag(Tag tag);

    Patient getPatientByNric(String nric);

    Optional<Patient> getPatientByNric(Nric nric);

    int getIndexByNric(Nric nric);

    void deletePatientByNric(String nric);

    //==========Consultation methods=====================

    void createConsultation(Patient patient);

    void diagnosePatient(Diagnosis diagnosis);

    boolean checkConsultation();

    void prescribeMedicine(ArrayList<Prescription> prescriptions);

    Consultation getCurrentConsultation();

    ArrayList<Consultation> getConsultationList();

    void endConsultation();

    ArrayList<Consultation> listConsultation(String value);

    Consultation listConsultation(int index);

    //===========Appointment module operations========================
    boolean hasTimeConflicts(Appointment app);

    void addApp(Appointment app);

    String listApp(LocalDate start, LocalDate end);

    String listApp(Patient patient);

    String freeApp(LocalDate start, LocalDate end);

    Optional<Appointment> getAppointment(LocalDate date, LocalTime start);

    void deleteAppointment(Appointment appointment);

    //===========Reminder module operations===========================
    boolean duplicateRem(Reminder rem);

    void addRem(Reminder rem);

    void deleteReminder(Reminder reminder);

    void reminderForMedicine(Medicine medicine);

    Predicate<Reminder> getCurrentWeekRemindersPredicate();

    void deleteExistingReminderForMedicine(Medicine medicine);
    //===========Record module operations=============================
    Statistics getStatistics(YearMonth from, YearMonth to);

    void addRecord(Record record, Clock clock);

    void setConsultationFee(BigDecimal fee);
}

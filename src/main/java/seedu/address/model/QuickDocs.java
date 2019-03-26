package seedu.address.model;

import seedu.address.model.appointment.AppointmentManager;
import seedu.address.model.consultation.ConsultationManager;
import seedu.address.model.medicine.MedicineManager;
import seedu.address.model.patient.PatientManager;
import seedu.address.model.reminder.ReminderManager;

/**
 * Manages all managers of QuickDocs
 */
public class QuickDocs {

    private PatientManager patientManager = new PatientManager();
    private ConsultationManager consultationManager = new ConsultationManager();
    private AppointmentManager appointmentManager = new AppointmentManager();
    private ReminderManager reminderManager = new ReminderManager();
    private MedicineManager medicineManager = new MedicineManager();

    private boolean isModified = false;

    public MedicineManager getMedicineManager() {
        return medicineManager;
    }

    public void setMedicineManager(MedicineManager medicineManager) {
        this.medicineManager = medicineManager;
    }
    public PatientManager getPatientManager() {
        return patientManager;
    }

    public void setPatientManager(PatientManager patientManager) {
        this.patientManager = patientManager;
    }

    public ConsultationManager getConsultationManager() {
        return consultationManager;
    }

    public AppointmentManager getAppointmentManager() {
        return appointmentManager;
    }

    public void setAppointmentManager(AppointmentManager appointmentManager) {
        this.appointmentManager = appointmentManager;
    }

    public ReminderManager getReminderManager() {
        return reminderManager;
    }

    public void setReminderManager(ReminderManager reminderManager) {
        this.reminderManager = reminderManager;
    }

    // indicate modification of quickdocs data

    public boolean isModified() {
        return isModified;
    }

    public void indicateModification(boolean state) {
        isModified = state;
    }
}

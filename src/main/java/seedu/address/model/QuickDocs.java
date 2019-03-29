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

    public PatientManager getPatientManager() {
        return patientManager;
    }

    public ConsultationManager getConsultationManager() {
        return consultationManager;
    }

    public AppointmentManager getAppointmentManager() {
        return appointmentManager;
    }

    public ReminderManager getReminderManager() {
        return reminderManager;
    }

    // indicate modification of quickdocs data
    public boolean isModified() {
        return isModified;
    }

    public void indicateModification(boolean state) {
        isModified = state;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof QuickDocs)) {
            return false;
        }

        QuickDocs otherQuickDocs = (QuickDocs) other;
        return otherQuickDocs.patientManager.getPatientList()
                .equals(this.patientManager.getPatientList())
                && otherQuickDocs.consultationManager.getConsultationList()
                .equals(this.consultationManager.getConsultationList())
                && otherQuickDocs.appointmentManager.getAppointmentList()
                .equals(this.appointmentManager.getAppointmentList())
                && otherQuickDocs.reminderManager.getReminderList()
                .equals(this.reminderManager.getReminderList())
                && otherQuickDocs.medicineManager.getListOfMedicine()
                .equals(this.medicineManager.getListOfMedicine());
    }
}

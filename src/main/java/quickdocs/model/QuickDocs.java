package quickdocs.model;

import quickdocs.model.appointment.AppointmentManager;
import quickdocs.model.consultation.ConsultationManager;
import quickdocs.model.medicine.MedicineManager;
import quickdocs.model.patient.PatientManager;
import quickdocs.model.record.StatisticsManager;
import quickdocs.model.reminder.ReminderManager;

/**
 * Manages all managers of QuickDocs
 */
public class QuickDocs {

    private PatientManager patientManager = new PatientManager();
    private ConsultationManager consultationManager = new ConsultationManager();
    private AppointmentManager appointmentManager = new AppointmentManager();
    private ReminderManager reminderManager = new ReminderManager();
    private MedicineManager medicineManager = new MedicineManager();
    private StatisticsManager statisticsManager = new StatisticsManager();

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

    public StatisticsManager getStatisticsManager() {
        return statisticsManager;
    }

    public void setStatisticsManager(StatisticsManager statisticsManager) {
        this.statisticsManager = statisticsManager;
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
                && otherQuickDocs.appointmentManager.equals(this.appointmentManager)
                && otherQuickDocs.reminderManager.equals(this.reminderManager)
                && otherQuickDocs.medicineManager.getListOfMedicine()
                .equals(this.medicineManager.getListOfMedicine());
    }
}

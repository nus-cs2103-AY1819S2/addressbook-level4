package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.DocXParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyDocX;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.medicalhistory.MedicalHistory;
import seedu.address.model.person.doctor.Doctor;
import seedu.address.model.person.patient.Patient;
import seedu.address.model.prescription.Prescription;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final CommandHistory history;
    private final DocXParser docXParser;
    private boolean docXModified;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        history = new CommandHistory();
        docXParser = new DocXParser();

        // Set DocXModified to true whenever the models' docX is modified.
        model.getDocX().addListener(observable -> docXModified = true);
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        docXModified = false;

        CommandResult commandResult;
        try {
            Command command = docXParser.parseCommand(commandText);
            commandResult = command.execute(model, history);
        } finally {
            history.add(commandText);
        }

        if (docXModified) {
            logger.info("docX modified, saving to file.");
            try {
                storage.saveDocX(model.getDocX());
            } catch (IOException ioe) {
                throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
            }
        }

        return commandResult;
    }

    @Override
    public ReadOnlyDocX getDocX() {
        return model.getDocX();
    }

    @Override
    public ObservableList<Patient> getFilteredPatientList() {
        return model.getFilteredPatientList();
    }

    @Override
    public ObservableList<MedicalHistory> getFilteredMedHistList() {
        return model.getFilteredMedHistList();
    }

    @Override
    public ObservableList<Prescription> getFilteredPrescriptionList() {
        return model.getFilteredPrescriptionList();
    }

    @Override
    public ObservableList<Appointment> getFilteredAppointmentList() {
        return model.getFilteredAppointmentList();
    }

    @Override
    public ObservableList<String> getHistory() {
        return history.getHistory();
    }

    @Override
    public Path getDocXFilePath() {
        return model.getDocXFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public ReadOnlyProperty<Patient> selectedPatientProperty() {
        return model.selectedPatientProperty();
    }

    @Override
    public void setSelectedPatient(Patient patient) {
        model.setSelectedPatient(patient);
    }

    @Override
    public ReadOnlyProperty<MedicalHistory> selectedMedHistProperty() {
        return model.selectedMedHistProperty();
    }

    @Override
    public void setSelectedMedHist(MedicalHistory medHist) {
        model.setSelectedMedHist(medHist);
    }

    @Override
    public ReadOnlyProperty<Prescription> selectedPrescriptionProperty() {
        return model.selectedPrescriptionProperty();
    }

    @Override
    public void setSelectedPrescription(Prescription prescription) {
        model.setSelectedPrescription(prescription);
    }

    @Override
    public ReadOnlyProperty<Appointment> selectedAppointmentProperty() {
        return model.selectedAppointmentProperty();
    }

    @Override
    public void setSelectedAppointment (Appointment appointment) {
        model.setSelectedAppointment(appointment);
    }

    @Override
    public ObservableList<Doctor> getFilteredDoctorList() {
        return model.getFilteredDoctorList();
    }

    @Override
    public ReadOnlyProperty<Doctor> selectedDoctorProperty() {
        return model.selectedDoctorProperty();
    }

    @Override
    public void setSelectedDoctor(Doctor doctor) {
        model.setSelectedDoctor(doctor);
    }
}

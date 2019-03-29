package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalAppointments.getTypicalAppointmentsQuickDocs;
import static seedu.address.testutil.TypicalPatients.getTypicalPatientQuickDocs;
import static seedu.address.testutil.TypicalReminders.getTypicalRemindersQuickDocs;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.QuickDocs;

public class JsonSerializableQuickDocsTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableQuickDocsTest");

    //patients
    private static final Path TYPICAL_PATIENTS_FILE = TEST_DATA_FOLDER.resolve("typicalPatientsQuickDocs.json");
    private static final Path INVALID_PATIENT_FILE = TEST_DATA_FOLDER.resolve("invalidPatientQuickDocs.json");
    private static final Path DUPLICATE_PATIENT_FILE = TEST_DATA_FOLDER.resolve("duplicatePatientQuickDocs.json");

    //appointments
    private static final Path TYPICAL_APPOINTMENTS_FILE = TEST_DATA_FOLDER.resolve("typicalAppointmentsQuickDocs.json");
    private static final Path INVALID_APPOINTMENT_FILE = TEST_DATA_FOLDER.resolve("invalidAppointmentQuickDocs.json");
    private static final Path DUPLICATE_APPOINTMENT_FILE = TEST_DATA_FOLDER.resolve(
            "duplicateAppointmentQuickDocs.json");

    //reminders
    private static final Path TYPICAL_REMINDERS_FILE = TEST_DATA_FOLDER.resolve("typicalRemindersQuickDocs.json");
    private static final Path INVALID_REMINDER_FILE = TEST_DATA_FOLDER.resolve("invalidReminderQuickDocs.json");
    private static final Path DUPLICATE_REMINDER_FILE = TEST_DATA_FOLDER.resolve("duplicateReminderQuickDocs.json");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalPatientsFile_success() throws Exception {
        JsonSerializableQuickDocs dataFromFile = JsonUtil.readJsonFile(TYPICAL_PATIENTS_FILE,
                JsonSerializableQuickDocs.class).get();
        QuickDocs quickDocsFromFile = dataFromFile.toModelType();
        QuickDocs typicalPatientsQuickDocs = getTypicalPatientQuickDocs();
        assertEquals(quickDocsFromFile, typicalPatientsQuickDocs);
    }

    @Test
    public void toModelType_invalidPatientFile_throwsIllegalArgumentException() throws Exception {
        JsonSerializableQuickDocs dataFromFile = JsonUtil.readJsonFile(INVALID_PATIENT_FILE,
                JsonSerializableQuickDocs.class).get();
        thrown.expect(IllegalArgumentException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicatePatient_throwsIllegalValueException() throws Exception {
        JsonSerializableQuickDocs dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PATIENT_FILE,
                JsonSerializableQuickDocs.class).get();
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(JsonSerializableQuickDocs.MESSAGE_DUPLICATE_PATIENT);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_typicalAppointmentsFile_success() throws Exception {
        JsonSerializableQuickDocs dataFromFile = JsonUtil.readJsonFile(TYPICAL_APPOINTMENTS_FILE,
                JsonSerializableQuickDocs.class).get();
        QuickDocs quickDocsFromFile = dataFromFile.toModelType();
        QuickDocs typicalAppointmentsQuickDocs = getTypicalAppointmentsQuickDocs();
        assertEquals(quickDocsFromFile, typicalAppointmentsQuickDocs);
    }

    @Test
    public void toModelType_invalidAppointmentFile_throwsIllegalArgumentException() throws Exception {
        JsonSerializableQuickDocs dataFromFile = JsonUtil.readJsonFile(INVALID_APPOINTMENT_FILE,
                JsonSerializableQuickDocs.class).get();
        thrown.expect(IllegalArgumentException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateAppointment_throwsIllegalValueException() throws Exception {
        JsonSerializableQuickDocs dataFromFile = JsonUtil.readJsonFile(DUPLICATE_APPOINTMENT_FILE,
                JsonSerializableQuickDocs.class).get();
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(JsonSerializableQuickDocs.MESSAGE_DUPLICATE_APPOINTMENT);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_typicalRemindersFile_success() throws Exception {
        JsonSerializableQuickDocs dataFromFile = JsonUtil.readJsonFile(TYPICAL_REMINDERS_FILE,
                JsonSerializableQuickDocs.class).get();
        QuickDocs quickDocsFromFile = dataFromFile.toModelType();
        QuickDocs typicalRemindersQuickDocs = getTypicalRemindersQuickDocs();
        assertEquals(quickDocsFromFile, typicalRemindersQuickDocs);
    }

    @Test
    public void toModelType_invalidReminderFile_throwsIllegalArgumentException() throws Exception {
        JsonSerializableQuickDocs dataFromFile = JsonUtil.readJsonFile(INVALID_REMINDER_FILE,
                JsonSerializableQuickDocs.class).get();
        thrown.expect(IllegalArgumentException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateReminder_throwsIllegalValueException() throws Exception {
        JsonSerializableQuickDocs dataFromFile = JsonUtil.readJsonFile(DUPLICATE_REMINDER_FILE,
                JsonSerializableQuickDocs.class).get();
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(JsonSerializableQuickDocs.MESSAGE_DUPLICATE_REMINDER);
        dataFromFile.toModelType();
    }
}

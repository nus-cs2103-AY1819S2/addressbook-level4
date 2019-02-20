package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AddressBook;
import seedu.address.testutil.TypicalMedicines;

public class JsonSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_MEDICINES_FILE = TEST_DATA_FOLDER.resolve("typicalMedicinesAddressBook.json");
    private static final Path INVALID_MEDICINE_FILE = TEST_DATA_FOLDER.resolve("invalidMedicineAddressBook.json");
    private static final Path DUPLICATE_MEDICINE_FILE = TEST_DATA_FOLDER.resolve("duplicateMedicineAddressBook.json");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalMedicinesFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_MEDICINES_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalMedicinesAddressBook = TypicalMedicines.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalMedicinesAddressBook);
    }

    @Test
    public void toModelType_invalidMedicineFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_MEDICINE_FILE,
                JsonSerializableAddressBook.class).get();
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateMedicines_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_MEDICINE_FILE,
                JsonSerializableAddressBook.class).get();
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(JsonSerializableAddressBook.MESSAGE_DUPLICATE_MEDICINE);
        dataFromFile.toModelType();
    }

}

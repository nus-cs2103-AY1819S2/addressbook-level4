//package seedu.hms.storage;
//
//import static org.junit.Assert.assertEquals;
//
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.rules.ExpectedException;
//
//import seedu.hms.commons.exceptions.IllegalValueException;
//import seedu.hms.commons.util.JsonUtil;
//import seedu.hms.model.HotelManagementSystem;
//import seedu.hms.testutil.TypicalCustomers;
//
//public class JsonSerializableHotelManagementSystemTest {
//
//    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
//        "JsonSerializableHotelManagementSystemTest");
//    private static final Path TYPICAL_CUSTOMERS_FILE = TEST_DATA_FOLDER.resolve(
//        "typicalCustomersHotelManagementSystem.json");
//    private static final Path INVALID_CUSTOMER_FILE = TEST_DATA_FOLDER.resolve("invalidCustomerHotelManagementSystem"
//        + ".json");
//    private static final Path DUPLICATE_CUSTOMER_FILE = TEST_DATA_FOLDER.resolve(
//        "duplicateCustomerHotelManagementSystem.json");
//
//    @Rule
//    public ExpectedException thrown = ExpectedException.none();
//
//    @Test
//    public void toModelType_typicalCustomersFile_success() throws Exception {
//        JsonSerializableHotelManagementSystem dataFromFile = JsonUtil.readJsonFile(TYPICAL_CUSTOMERS_FILE,
//            JsonSerializableHotelManagementSystem.class).get();
//        HotelManagementSystem hotelManagementSystemFromFile = dataFromFile.toModelType();
//        HotelManagementSystem typicalCustomersHotelManagementSystem =
//            TypicalCustomers.getTypicalHotelManagementSystem();
//        assertEquals(hotelManagementSystemFromFile, typicalCustomersHotelManagementSystem);
//    }
//
//    @Test
//    public void toModelType_invalidCustomerFile_throwsIllegalValueException() throws Exception {
//        JsonSerializableHotelManagementSystem dataFromFile = JsonUtil.readJsonFile(INVALID_CUSTOMER_FILE,
//            JsonSerializableHotelManagementSystem.class).get();
//        thrown.expect(IllegalValueException.class);
//        dataFromFile.toModelType();
//    }
//
//    @Test
//    public void toModelType_duplicateCustomers_throwsIllegalValueException() throws Exception {
//        JsonSerializableHotelManagementSystem dataFromFile = JsonUtil.readJsonFile(DUPLICATE_CUSTOMER_FILE,
//            JsonSerializableHotelManagementSystem.class).get();
//        thrown.expect(IllegalValueException.class);
//        thrown.expectMessage(JsonSerializableHotelManagementSystem.MESSAGE_DUPLICATE_CUSTOMER);
//        dataFromFile.toModelType();
//    }
//
//}

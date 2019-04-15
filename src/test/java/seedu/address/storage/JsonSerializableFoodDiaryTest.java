package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.FoodDiary;
import seedu.address.testutil.TypicalRestaurants;

public class JsonSerializableFoodDiaryTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonSerializableFoodDiaryTest");
    private static final Path TYPICAL_RESTAURANTS_FILE = TEST_DATA_FOLDER.resolve("typicalRestaurantsFoodDiary.json");
    private static final Path INVALID_RESTAURANT_FILE = TEST_DATA_FOLDER.resolve("invalidRestaurantFoodDiary.json");
    private static final Path DUPLICATE_RESTAURANT_FILE = TEST_DATA_FOLDER
            .resolve("duplicateRestaurantFoodDiary.json");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalRestaurantsFile_success() throws Exception {
        JsonSerializableFoodDiary dataFromFile = JsonUtil.readJsonFile(TYPICAL_RESTAURANTS_FILE,
                JsonSerializableFoodDiary.class).get();
        FoodDiary foodDiaryFromFile = dataFromFile.toModelType();
        FoodDiary typicalRestaurantsFoodDiary = TypicalRestaurants.getTypicalFoodDiary();
        assertEquals(foodDiaryFromFile, typicalRestaurantsFoodDiary);
    }

    @Test
    public void toModelType_invalidRestaurantFile_throwsIllegalValueException() throws Exception {
        JsonSerializableFoodDiary dataFromFile = JsonUtil.readJsonFile(INVALID_RESTAURANT_FILE,
                JsonSerializableFoodDiary.class).get();
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateRestaurants_throwsIllegalValueException() throws Exception {
        JsonSerializableFoodDiary dataFromFile = JsonUtil.readJsonFile(DUPLICATE_RESTAURANT_FILE,
                JsonSerializableFoodDiary.class).get();
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(JsonSerializableFoodDiary.MESSAGE_DUPLICATE_RESTAURANT);
        dataFromFile.toModelType();
    }

}

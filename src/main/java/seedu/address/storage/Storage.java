package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.order.ReadOnlyOrders;
import seedu.address.model.ReadOnlyRestOrRant;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends RestOrRantStorage, UserPrefsStorage, OrdersStorage, StatisticsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getRestOrRantFilePath();
    // Path getMenuFilePath();
    Path getOrdersFilePath();

    @Override
    Optional<ReadOnlyRestOrRant> readRestOrRant() throws DataConversionException, IOException;
    // Optional<ReadOnlyRestOrRant> readMenu() throws DataConversionException, IOException;
    Optional<ReadOnlyOrders> readOrders() throws DataConversionException, IOException;

    @Override
    void saveRestOrRant(ReadOnlyRestOrRant restOrRant) throws IOException;
    // void saveMenu(ReadOnlyRestOrRant menu) throws IOException;
    void saveOrders(ReadOnlyOrders orders) throws IOException;
}

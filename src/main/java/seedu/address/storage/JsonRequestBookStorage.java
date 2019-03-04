//package seedu.address.storage;
//
//import static java.util.Objects.requireNonNull;
//
//import java.io.IOException;
//import java.nio.file.Path;
//import java.util.Optional;
//import java.util.logging.Logger;
//
//import seedu.address.commons.core.LogsCenter;
//import seedu.address.commons.exceptions.DataConversionException;
//import seedu.address.commons.exceptions.IllegalValueException;
//import seedu.address.commons.util.FileUtil;
//import seedu.address.commons.util.JsonUtil;
//import seedu.address.model.ReadOnlyAddressBook;
//import seedu.address.model.ReadOnlyRequestBook;
//
///**
// * A class to access AddressBook data stored as a json file on the hard disk.
// */
//public class JsonRequestBookStorage implements RequestBookStorage {
//
//    private static final Logger logger = LogsCenter.getLogger(JsonRequestBookStorage.class);
//
//    private Path filePath;
//
//    public JsonRequestBookStorage(Path filePath) {
//        this.filePath = filePath;
//    }
//
//    public Path getAddressBookFilePath() {
//        return filePath;
//    }
//
//    /**
//     * Similar to {@link #readAddressBook()}.
//     *
//     * @param filePath location of the data. Cannot be null.
//     * @throws DataConversionException if the file is not in the correct format.
//     */
//    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataConversionException {
//        requireNonNull(filePath);
//
//        Optional<JsonSerializableAddressBook> jsonAddressBook = JsonUtil.readJsonFile(
//                filePath, JsonSerializableAddressBook.class);
//        if (!jsonAddressBook.isPresent()) {
//            return Optional.empty();
//        }
//
//        try {
//            return Optional.of(jsonAddressBook.get().toModelType());
//        } catch (IllegalValueException ive) {
//            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
//            throw new DataConversionException(ive);
//        }
//    }
//
//    @Override
//    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
//        saveAddressBook(addressBook, filePath);
//    }
//
//    /**
//     * Similar to {@link #saveAddressBook(ReadOnlyAddressBook)}.
//     *
//     * @param filePath location of the data. Cannot be null.
//     */
//    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
//        requireNonNull(addressBook);
//        requireNonNull(filePath);
//
//        FileUtil.createIfMissing(filePath);
//        JsonUtil.saveJsonFile(new JsonSerializableAddressBook(addressBook), filePath);
//    }
//
//    /**
//     * Returns the file path of the data file.
//     */
//    @Override
//    public Path getRequestBookFilePath() {
//        return null;
//    }
//
//    /**
//     * Returns RequestBook data as a {@link ReadOnlyRequestBook}.
//     * Returns {@code Optional.empty()} if storage file is not found.
//     *
//     * @throws DataConversionException if the data in storage is not in the expected format.
//     * @throws IOException             if there was any problem when reading from the storage.
//     */
//    @Override
//    public Optional<ReadOnlyRequestBook> readRequestBook() throws DataConversionException, IOException {
//        return Optional.empty();
//    }
//
//    /**
//     * @param filePath
//     * @see #getRequestBookFilePath()
//     */
//    @Override
//    public Optional<ReadOnlyRequestBook> readRequestBook(Path filePath) throws DataConversionException, IOException {
//        return Opti;
//    }
//
//    /**
//     * Saves the given {@link ReadOnlyRequestBook} to the storage.
//     *
//     * @param requestBook cannot be null.
//     * @throws IOException if there was any problem writing to the file.
//     */
//    @Override
//    public void saveRequestBook(ReadOnlyRequestBook requestBook) throws IOException {
//
//    }
//
//    /**
//     * @param readOnlyRequestBook
//     * @param filePath
//     * @see #saveRequestBook(ReadOnlyAddressBook)
//     */
//    @Override
//    public void saveRequestBook(ReadOnlyRequestBook readOnlyRequestBook, Path filePath) throws IOException {
//
//    }
//}

package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.model.user.User;

/**
 * A class to access user  stored in the hard disk as a csv file
 */
public class CsvUserImportExport implements UserImportExport {

    private Path filePath;

    public CsvUserImportExport(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getImportExportFilePath() {
        return null;
    }

    @Override
    public Optional<User> importUser(Path filePath) throws IOException {
        return Optional.empty();
    }

    @Override
    public void exportUser(User user, Path filePath) throws IOException {

    }
}

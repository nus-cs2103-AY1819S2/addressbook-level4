package seedu.address.storage;

import java.io.File;
import java.util.HashSet;

/**
 * A class that contains information parsed for ImportCommand and ExportCommand.
 */
public class ParsedInOut {
    private File file;
    private HashSet<Integer> parsedIndex;

    public ParsedInOut(File file, HashSet<Integer> parsedIndex) {
        this.file = file;
        this.parsedIndex = parsedIndex;
    }

    public File getFile() {
        return file;
    }

    public HashSet<Integer> getParsedIndex() {
        return parsedIndex;
    }
}

package seedu.address.storage;

import java.io.File;
import java.util.HashSet;

/**
 * A class that contains information parsed for ImportCommand and ExportCommand.
 */
public class ParsedInOut {
    private File file;
    private HashSet<Integer> parsedIndex;
    private boolean isAll;

    public ParsedInOut(File file, HashSet<Integer> parsedIndex) {
        this.file = file;
        this.parsedIndex = parsedIndex;
        this.isAll = false;
    }

    public ParsedInOut(File file) {
        this.file = file;
        this.isAll = true;
    }

    public File getFile() {
        return file;
    }

    public HashSet<Integer> getParsedIndex() {
        return parsedIndex;
    }

    public boolean getArgIsAll() {
        return isAll;
    }
}

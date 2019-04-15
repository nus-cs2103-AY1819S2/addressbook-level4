package seedu.address.storage;

import java.io.File;
import java.util.HashSet;

/**
 * A class that contains information parsed for OpenCommand, SaveCommand, ImportCommand, ExportCommand.
 */
public class ParsedInOut {
    private File file;
    private String type;
    private HashSet<Integer> parsedIndex;
    private boolean isAll;

    public ParsedInOut(File file, String type, HashSet<Integer> parsedIndex) {
        this.file = file;
        this.type = type;
        this.parsedIndex = parsedIndex;
        this.isAll = false;
    }

    public ParsedInOut(File file, String type) {
        this.file = file;
        this.type = type;
        this.parsedIndex = new HashSet<>();
        this.isAll = true;
    }

    public File getFile() {
        return file;
    }

    public String getType() {
        return type;
    }

    public HashSet<Integer> getParsedIndex() {
        return parsedIndex;
    }

    public boolean getArgIsAll() {
        return isAll;
    }
}

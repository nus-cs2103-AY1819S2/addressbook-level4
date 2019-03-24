package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a File name (without the file extension) in MediTabs.
 * Standardise the File Naming Convention in MediTabs and also to ensure the file name
 * specified (without the file extension) is valid. It does not check whether
 * the file name is too long (Windows has an issue with long file names and it also involves file systems:
 * <a href="https://docs.microsoft.com/en-us/windows/desktop/FileIO/naming-a-file#short-vs-long-names" target="_blank">
 *     Link</a>
 * <p>
 * We suggest complementing this class with Java build in class such as
 * {@link java.io.File File} or {@link java.nio.file.Files Files} and handling
 * the exceptions thrown by the methods used in those classes when creating files with the file name validated to
 * resolve potential exceptions involving permissions, security, file name being too long, etc.
 * </p>
 * Note: In an effort to make it platform independent, the validation is such that it ensures that the specified
 * file name does not violate file naming conventions in any platform especially Windows which has a very strict
 * file name conventions:
 * <a href="https://docs.microsoft.com/en-us/windows/desktop/FileIO/naming-a-file#naming-conventions" target="_blank">
 *     Link</a>
 * <p>
 * Guarantees: immutable; is valid as declared in {@link #isValidFileName(String)}
 * </p>
 */
public class FileName {

    public static final String MESSAGE_CONSTRAINTS =
            "File Names (without including file format) should only start with alphanumeric characters following "
                    + "which can contain alphanumeric characters "
                    + "or \"_\" or \"-\" (without quotation marks) and it should not be blank";

    /*
     * The characters of the file name must not contain any whitespace
     * and does not contain certain symbols like \/:*?"<>|.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum}_-]*";

    /*
     * Additional REGEX to check for reserved names which are not allowed as file names due to Windows
     * reserving these names as stated in the Windows documentation.
     * This is to ensure that the file name validated by isValidFileName() method is platform independent.
     * The Windows reserved names are as follows:
     * CON, PRN, AUX, NUL, COM0, COM1, COM2, COM3, COM4, COM5, COM6, COM7, COM8, COM9, LPT0, LPT1, LPT2, LPT3, LPT4,
     * LPT5, LPT6, LPT7, LPT8, and LPT9
     * Note: COM0 and LPT0 is added in the REGEX and list above though not stated as reserved names in
     * Windows documentation, however, based on personal manual testing on Windows 10, both names are also reserved
     * names and hence are also included and not allowed as file names.
     */
    public static final String RESERVED_NAMES_REGEX = "CON|PRN|AUX|NUL|COM[0-9]|LPT[0-9]";

    public final String fullFileName;

    /**
     * Constructs a {@code FileName}.
     *
     * @param fileName A valid file name (without the file extension).
     */
    public FileName(String fileName) {
        requireNonNull(fileName);
        checkArgument(isValidFileName(fileName), MESSAGE_CONSTRAINTS);
        fullFileName = fileName;
    }

    /**
     * Returns true if a given string is a valid file name.
     * @param fileNameToCheck The input file name to check (without the file extension)
     */
    public static boolean isValidFileName(String fileNameToCheck) {
        requireNonNull(fileNameToCheck);
        if (fileNameToCheck.isEmpty() || fileNameToCheck.matches(RESERVED_NAMES_REGEX)) {
            return false;
        }
        return fileNameToCheck.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullFileName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FileName // instanceof handles nulls
                && fullFileName.equals(((FileName) other).fullFileName)); // state check
    }

    @Override
    public int hashCode() {
        return fullFileName.hashCode();
    }

}

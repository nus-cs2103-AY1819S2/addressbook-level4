package seedu.address.model.medicine;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.binarySearch;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;


/**
 * A model representing a folder-like directory storing subdirectories and medicines
 */
public class Directory {

    public static final String VALIDATION_REGEX = "[^\\s].*";
    public static final String MESSAGE_CONSTRAINTS = "Directory name can take any values, and it should not be blank";
    public static final String ERROR_MESSAGE_MEDICINE_ALREADY_EXISTS_UNDER_SAME_DIRECTORY =
            "Medicine with same name already exist under the same directory";

    private ArrayList<Medicine> listOfMedicine;
    private final String name;
    private ArrayList<Directory> listOfDirectory;
    private Optional<Integer> threshold;

    public Directory(String name) {
        requireNonNull(name);
        checkArgument(isValidDirectory(name), MESSAGE_CONSTRAINTS);
        this.name = name;
        this.listOfDirectory = new ArrayList<>();
        this.listOfMedicine = new ArrayList<>();
        this.threshold = Optional.empty();
    }

    private boolean isValidDirectory(String test) {
        return test.matches(VALIDATION_REGEX);
    }
    /**
     * Add a medicine to this directory
     * @param medicine the medicine to add
     */
    public void addMedicine(Medicine medicine) {
        requireNonNull(medicine);
        checkArgument(isValidMedicine(medicine), ERROR_MESSAGE_MEDICINE_ALREADY_EXISTS_UNDER_SAME_DIRECTORY);
        if (threshold.isPresent()) {
            medicine.setThreshold(threshold.get());
        }
        listOfMedicine.add(medicine);
        listOfMedicine.sort(Comparator.comparing((Medicine med) -> (med.name)));
    }

    /**
     * Add a new sub-directory under this directory
     * @param name the name of the new directory
     */
    public void addDirectory(String name) {
        requireNonNull(name);
        checkArgument(isValidNewDirectory(name), "Directory with the same name already exists");
        Directory newDirectory = new Directory(name);
        if (threshold.isPresent()) {
            newDirectory.setThresholdForAll(threshold.get());
        }
        listOfDirectory.add(newDirectory);
        listOfDirectory.sort(Comparator.comparing((Directory directory) -> (directory.name)));
    }

    /**
     * Check whether there is no medicine with identical name in the directory
     * @param med the medicine that needs checking
     * @return if there is no existing medicine with the identical name
     */
    private boolean isValidMedicine(Medicine med) {
        for (Medicine medicine : listOfMedicine) {
            if (medicine.name.equals(med.name)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks whether there is no existing directory with identical name
     * @param directoryName the directory name that need checking
     * @return if there is no existing sub-directory with identical name
     */
    private boolean isValidNewDirectory(String directoryName) {
        for (Directory directory : listOfDirectory) {
            if (directory.name.equals(directoryName)) {
                return false;
            }
        }
        return true;
    }

    /**
     * To find a medicine given the path
     * @param path the path of the medicine in the form of String[]
     * @param pointer the pointer indicating the position of the current directory
     * @return if the medicine exists under the given path, return a optional object of that medicine;
     * else return Optional.empty()
     */
    public Optional<Medicine> findMedicine(String[] path, int pointer) {
        if (!path[pointer].equals(this.name)) {
            throw new IllegalStateException("Path not corresponding to current directory.");
        }
        if (path.length == pointer + 2) {
            return searchAmongMedicine(path[pointer + 1]);
        } else {
            Optional<Directory> directory = searchAmongDirectory(path[pointer + 1]);
            if (!directory.isPresent()) {
                return Optional.empty();
            }
            return directory.get().findMedicine(path, pointer + 1);
        }
    }
    /**
     * To find a directory given the path
     * @param path the path of the medicine in the form of String[]
     * @param pointer the pointer indicating the position of the current directory
     * @return if the directory exists under the given path, return a optional object of that directory;
     * else return Optional.empty()
     */
    public Optional<Directory> findDirectory(String[] path, int pointer) {
        if (!path[pointer].equals(this.name)) {
            throw new IllegalStateException("Path not corresponding to current directory.");
        }
        if (path.length == pointer + 1) {
            return Optional.of(this);
        } else {
            Optional<Directory> directory = searchAmongDirectory(path[pointer + 1]);
            if (!directory.isPresent()) {
                return Optional.empty();
            } else {
                return directory.get().findDirectory(path, pointer + 1);
            }
        }
    }

    private Optional<Medicine> searchAmongMedicine(String name) {
        Comparator<String> comparator = Comparator.naturalOrder();
        return binarySearch(listOfMedicine, (Medicine med) -> (comparator.compare(med.name, name)));
    }

    private Optional<Directory> searchAmongDirectory(String name) {
        Comparator<String> comparator = Comparator.naturalOrder();
        return binarySearch(listOfDirectory, (Directory directory) -> (comparator.compare(directory.name, name)));
    }

    @Override
    public String toString() {
        return "- " + name;
    }

    /**
     * Return a String representation of the content of the directory
     * @return
     */
    public String viewDetail() {
        StringBuilder sb = new StringBuilder();
        if (!listOfDirectory.isEmpty()) {
            sb.append("List of sub-directories: \n");
            for (Directory directory : listOfDirectory) {
                sb.append(directory.toString() + "\n");
            }
        }
        if (!listOfMedicine.isEmpty()) {
            sb.append("List of Medicine under this directory: \n");
            for (Medicine medicine : listOfMedicine) {
                sb.append(medicine.viewDetail() + "\n");
            }
        }
        if (listOfMedicine.isEmpty() && listOfDirectory.isEmpty()) {
            sb.append("Empty directory\n");
        }
        return sb.toString();
    }

    /**
     * set a default alarm level for all medicine under this directory and all medicine under its sub-directories
     * and so on
     * @param thres the alarm level
     */
    public void setThresholdForAll(int thres) {
        this.threshold = Optional.of(thres);
        for (Medicine medicine : listOfMedicine) {
            medicine.setThreshold(thres);
        }
        for (Directory directory : listOfDirectory) {
            directory.setThresholdForAll(thres);
        }
    }
}

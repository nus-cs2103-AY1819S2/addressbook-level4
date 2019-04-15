package quickdocs.model.medicine;

import static quickdocs.commons.util.CollectionUtil.binarySearch;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * An model for overall storage of medicine
 */
public class MedicineManager {

    public static final String ERROR_MESSAGE_MEDICINE_WITH_SAME_NAME_EXISTS_IN_LIST =
            "Medicine with the same name already exists in the storage.";
    public static final String ERROR_MESSAGE_NO_DIRECTORY_FOUND =
            "No directory is found at the given path.";
    public static final String ERROR_MESSAGE_NO_MEDICINE_FOUND_BY_PATH =
            "No Medicine is found at the given path.";
    public static final String ERROR_MESSAGE_NO_MEDICINE_FOUND_BY_NAME =
            "No Medicine is found by the given name.";
    public static final String ERROR_MESSAGE_NO_EXISTING_MED_FOUND =
            "No existing medicine with name %1$s found in the storage.";

    private Directory root;
    private ArrayList<Medicine> listOfMedicine;

    public MedicineManager() {
        root = new Directory("root");
        listOfMedicine = new ArrayList<>();
    }

    /**
     * To add a medicine into a directory. If medicine with same name already exist, add that medicine to the directory.
     * If no medicine with same name exists, add a new medicine
     *
     * @param medicineName name of medicine
     * @param path         path the madicine to be added to
     * @return the new medicine added
     */
    public Medicine addMedicine(String medicineName, String[] path, BigDecimal price) {
        return (this.addMedicine(medicineName, 0, path, price));
    }

    /**
     * To add a medicine to the storage by specifying the name of medicine, quantity of it and the path
     *
     * @param medicineName name of medicine
     * @param quantity     quantity of medicine
     * @param path         the path to store to
     * @return the new medicine added
     */
    public Medicine addMedicine(String medicineName, int quantity,
                                String[] path, BigDecimal price) {
        Optional<Medicine> findMedicine = findMedicine(medicineName);
        if (findMedicine.isPresent()) {
            throw new IllegalArgumentException(ERROR_MESSAGE_MEDICINE_WITH_SAME_NAME_EXISTS_IN_LIST);
        }
        Optional<Directory> directory = root.findDirectory(path, 0);
        if (!directory.isPresent()) {
            throw new IllegalArgumentException(ERROR_MESSAGE_NO_DIRECTORY_FOUND);
        }
        Medicine medicine = new Medicine(medicineName, quantity);
        medicine.setPrice(price);
        listOfMedicine.add(medicine);
        listOfMedicine.sort(Comparator.comparing((Medicine x) -> (x.name)));
        directory.get().addMedicine(medicine);
        return medicine;
    }

    /**
     * Add a new directory under a directory specified by the path
     *
     * @param directoryName the name of the new directory
     * @param path          the path of the destination directory
     * @return the new directory added
     */
    public Directory addDirectory(String directoryName, String[] path) {
        Optional<Directory> directory = root.findDirectory(path, 0);
        if (!directory.isPresent()) {
            throw new IllegalArgumentException(ERROR_MESSAGE_NO_DIRECTORY_FOUND);
        }
        Directory newDirectory = directory.get().addDirectory(directoryName);
        return newDirectory;
    }

    /**
     * find a medicine by its name
     *
     * @param medicineName the medicine name to search for
     * @return Optional.empty() if there is no medicine with the desired name;
     * Optional.of(E) if E's name matches the key
     */
    public Optional<Medicine> findMedicine(String medicineName) {
        Comparator comparator = Comparator.naturalOrder();
        return binarySearch(
                listOfMedicine, (Medicine med) -> (
                        comparator.compare(med.name.toLowerCase(), medicineName.toLowerCase())));
    }

    public Optional<Medicine> findMedicine(String[] path) {
        return root.findMedicine(path, 0);
    }

    public Optional<Directory> findDirectory(String[] path) {
        return root.findDirectory(path, 0);
    }

    /**
     * record a purchase of medicine in the medicine storage
     *
     * @param path     the path leading to the medicine
     * @param quantity quantity of medicine purchased
     * @returns the medicine affected
     */
    public Medicine purchaseMedicine(String[] path, int quantity) {
        Optional<Medicine> medicine = findMedicine(path);
        if (!medicine.isPresent()) {
            throw new IllegalArgumentException(ERROR_MESSAGE_NO_MEDICINE_FOUND_BY_PATH);
        }
        medicine.get().addQuantity(quantity);
        return medicine.get();
    }

    /**
     * record a purchase of medicine in the medicine storage
     *
     * @param medicineName the name of the medicine
     * @param quantity     quantity of medicine purchased
     * @return the medicine affected
     */
    public Medicine purchaseMedicine(String medicineName, int quantity) {
        Optional<Medicine> medicine = findMedicine(medicineName);
        if (!medicine.isPresent()) {
            throw new IllegalArgumentException(ERROR_MESSAGE_NO_MEDICINE_FOUND_BY_NAME);
        }
        medicine.get().addQuantity(quantity);
        return medicine.get();
    }

    public Directory getRoot() {
        return root;
    }

    public void setRoot(Directory root) {
        this.root = root;
    }

    public void setListOfMedicine(ArrayList<Medicine> listOfMedicine) {
        this.listOfMedicine = listOfMedicine;
    }

    public ArrayList<Medicine> getListOfMedicine() {
        return listOfMedicine;
    }

    /**
     * Add an existing medicine to a directory specified by path
     *
     * @param medicine The medicine to add
     * @param path     The path specifying the directory
     */
    public void addExistingMedicineToDirectory(Medicine medicine, String[] path) {
        Optional<Directory> directory = findDirectory(path);
        if (!directory.isPresent()) {
            throw new IllegalArgumentException(ERROR_MESSAGE_NO_DIRECTORY_FOUND);
        }
        if (!listOfMedicine.contains(medicine)) {
            throw new IllegalArgumentException(ERROR_MESSAGE_NO_EXISTING_MED_FOUND);
        }
        directory.get().addMedicine(medicine);
    }

    private Directory getDirectoryForSuggestions(String rawPath) {
        if (!rawPath.contains("\\")) {
            throw new IllegalArgumentException("Not in path format.");
        }
        rawPath = rawPath.trim();
        rawPath = rawPath.substring(0, rawPath.lastIndexOf("\\"));
        String[] path = rawPath.split("\\\\");
        Optional<Directory> foundDirectory = findDirectory(path);
        if (!foundDirectory.isPresent()) {
            throw new IllegalArgumentException(ERROR_MESSAGE_NO_DIRECTORY_FOUND);
        }
        return foundDirectory.get();
    }

    public ArrayList<String> getDirectorySuggestions(String rawPath) {
        Directory foundDirectory = getDirectoryForSuggestions(rawPath);
        ArrayList<String> suggestions = new ArrayList<>();
        suggestions.addAll(foundDirectory.getListOfDirectory().stream()
                .map((Directory directory) -> (directory.name))
                .map((String string) -> (string.substring(0, 1).toUpperCase() + string.substring(1)))
                .collect(Collectors.toList()));
        suggestions.sort(Comparator.comparing((String::toLowerCase)));
        return suggestions;
    }

    public ArrayList<String> getMedicineSuggestions(String rawPath) {
        Directory foundDirectory = getDirectoryForSuggestions(rawPath);
        ArrayList<String> suggestions = new ArrayList<>();
        suggestions.addAll(foundDirectory.getListOfMedicine().stream()
                .map((Medicine medicine) -> (medicine.name))
                .map((String string) -> (string.substring(0, 1).toLowerCase() + string.substring(1)))
                .collect(Collectors.toList()));
        suggestions.sort(Comparator.comparing((String::toLowerCase)));
        return suggestions;
    }
}

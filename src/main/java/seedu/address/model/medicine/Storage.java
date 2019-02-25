package seedu.address.model.medicine;

import static seedu.address.commons.util.CollectionUtil.binarySearch;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;

/**
 * An model for overall storage of medicine
 */
public class Storage {

    private Directory root;
    private ArrayList<Medicine> listOfMedicine;

    public Storage() {
        root = new Directory("root");
        listOfMedicine = new ArrayList<>();
    }

    public void addMedicine(String medicineName, String[] path) {
        this.addMedicine(medicineName, 0, path);
    }

    /**
     * To add a medicine to the storage by specifying the name of medicine, quantity of it and the path
     * @param medicineName name of medicine
     * @param quantity quantity of medicine
     * @param path the path to store to
     */
    public void addMedicine(String medicineName, int quantity, String[] path) {
        try {
            Optional<Directory> directory = root.findDirectory(path, 0);
            if (!directory.isPresent()) {
                throw new IllegalArgumentException("Invalid Path");
            }
            Medicine medicine = new Medicine(medicineName, quantity);
            listOfMedicine.add(medicine);
            listOfMedicine.sort(Comparator.comparing((Medicine x) -> (x.name)));
            directory.get().addMedicine(medicine);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Invalid Path");
        }
    }

    /**
     * find a medicine by its name
     * @param medicineName the medicine name to search for
     * @return Optional.empty() if there is no medicine with the desired name;
     *         Optional.of(E) if E's name matches the key
     */
    public Optional<Medicine> findMedicine(String medicineName) {
        Comparator comparator = Comparator.naturalOrder();
        return binarySearch(
                listOfMedicine, (Medicine med)->(comparator.compare(med.name, medicineName)));
    }

    public Optional<Medicine> findMedicine(String[] path) {
        return root.findMedicine(path, 0);
    }
}

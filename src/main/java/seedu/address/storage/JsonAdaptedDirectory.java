package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.model.medicine.Directory;
import seedu.address.model.medicine.Medicine;

/**
 * A Jackson-friendly version of {@link Directory}.
 */
public class JsonAdaptedDirectory {

    private ArrayList<String> listOfMedicineNames;
    private ArrayList<JsonAdaptedDirectory> listOfDirectories;
    private String name;
    private Optional<Integer> threshold;

    @JsonCreator
    public JsonAdaptedDirectory(@JsonProperty("name") String name,
                                @JsonProperty("threshold") Optional<Integer> threshold,
                                @JsonProperty("listOfMedicineNames") List<String> listOfMedicineNames,
                                @JsonProperty("listOfDirectories") List<JsonAdaptedDirectory> listOfDirectories) {
        this.name = name;
        this.threshold = threshold;
        this.listOfMedicineNames = new ArrayList<>();
        this.listOfDirectories = new ArrayList<>();
        this.listOfDirectories.addAll(listOfDirectories);
        this.listOfMedicineNames.addAll(listOfMedicineNames);
    }

    public JsonAdaptedDirectory(Directory directory) {
        this.name = directory.name;
        this.threshold = directory.getThreshold();
        listOfMedicineNames = new ArrayList<>();
        if (!directory.getListOfMedicine().isEmpty()) {
            listOfMedicineNames.addAll(directory.getListOfMedicine()
                    .stream()
                    .map((Medicine medicine) -> medicine.name)
                    .collect(Collectors.toList()));
        }
        listOfDirectories = new ArrayList<>();
        if (!directory.getListOfDirectory().isEmpty()) {
            listOfDirectories.addAll(directory.getListOfDirectory()
                    .stream()
                    .map((Directory subDirectory) -> new JsonAdaptedDirectory(subDirectory))
                    .collect(Collectors.toList()));
        }
    }

    public ArrayList<String> getListOfMedicineNames() {
        return listOfMedicineNames;
    }

    public ArrayList<JsonAdaptedDirectory> getListOfDirectories() {
        return listOfDirectories;
    }

    public String getName() {
        return name;
    }

    public Optional<Integer> getThreshold() {
        return threshold;
    }
}

package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.model.medicine.Directory;
import seedu.address.model.medicine.Medicine;

public class JsonAdaptedDirectory {

    private ArrayList<String> listOfMedicineName;
    private ArrayList<JsonAdaptedDirectory> listOfDirectory;
    private String name;
    private Optional<Integer> threshold;

    @JsonCreator
    public JsonAdaptedDirectory(@JsonProperty String name, @JsonProperty int threshold,
                                @JsonProperty List<String> listOfMedicineName,
                                @JsonProperty List<JsonAdaptedDirectory> listOfDirectory) {
        this.name = name;
        if (threshold == -1) {
            this.threshold = Optional.empty();
        } else {
            this.threshold = Optional.of(threshold);
        }
        this.listOfDirectory.addAll(listOfDirectory);
        this.listOfMedicineName.addAll(listOfMedicineName);
    }

    public JsonAdaptedDirectory(Directory directory) {
        this.name = directory.name;
        this.threshold = directory.getThreshold();
        listOfMedicineName.addAll(directory.getListOfMedicine()
                                            .stream()
                                            .map((Medicine medicine) -> medicine.name)
                                            .collect(Collectors.toList()));
        listOfDirectory.addAll(directory.getListOfDirectory()
                                            .stream()
                                            .map((Directory subDirectory) -> new JsonAdaptedDirectory(subDirectory))
                                            .collect(Collectors.toList()));
    }
}

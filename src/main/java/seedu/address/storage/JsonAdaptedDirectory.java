package seedu.address.storage;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.model.medicine.Directory;
import seedu.address.model.medicine.Medicine;

public class JsonAdaptedDirectory {

    private ArrayList<JsonAdaptedMedicine> listOfMedicine;
    private ArrayList<JsonAdaptedDirectory> listOfDirectory;
    private String name;
    private boolean thresholdExists;
    private int treshold;

    @JsonCreator
    public JsonAdaptedDirectory(@JsonProperty String name, @JsonProperty Directory[] listOfDirectory,
                                @JsonProperty Medicine[] listOfMedicine, @JsonProperty )
}

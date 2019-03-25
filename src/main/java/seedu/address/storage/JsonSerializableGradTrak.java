package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.GradTrak;
import seedu.address.model.ReadOnlyGradTrak;
import seedu.address.model.moduletaken.ModuleTaken;

/**
 * An Immutable GradTrak that is serializable to JSON format.
 */
@JsonRootName(value = "gradtrak")
class JsonSerializableGradTrak {

    public static final String MESSAGE_DUPLICATE_MODULES_TAKEN = "ModulesTaken list contains duplicate moduleTaken(s).";

    private final List<JsonAdaptedModuleTaken> modulesTaken = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableGradTrak} with the given modulesTaken.
     */
    @JsonCreator
    public JsonSerializableGradTrak(@JsonProperty("modulesTaken") List<JsonAdaptedModuleTaken> modulesTaken) {
        this.modulesTaken.addAll(modulesTaken);
    }

    /**
     * Converts a given {@code ReadOnlyGradTrak} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableGradTrak}.
     */
    public JsonSerializableGradTrak(ReadOnlyGradTrak source) {
        modulesTaken.addAll(source.getModulesTakenList()
                .stream().map(JsonAdaptedModuleTaken::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code GradTrak} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public GradTrak toModelType() throws IllegalValueException {
        GradTrak gradTrak = new GradTrak();
        for (JsonAdaptedModuleTaken jsonAdaptedModuleTaken : modulesTaken) {
            ModuleTaken moduleTaken = jsonAdaptedModuleTaken.toModelType();
            if (gradTrak.hasModuleTaken(moduleTaken)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_MODULES_TAKEN);
            }
            gradTrak.addModuleTaken(moduleTaken);
        }
        return gradTrak;
    }

}

package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
//import seedu.address.model.medicalhistory.MedicalHistory;
import seedu.address.model.person.Patient;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Patients list contains duplicate patient(s).";

    public static final String MESSAGE_DUPLICATE_MEDHIST =
            "Medical history list contains duplicate medical history(s).";

    private final List<JsonAdaptedPatient> patients = new ArrayList<>();

    private final List<JsonAdaptedMedicalHistory> medicalHistories = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given patients.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("patients") List<JsonAdaptedPatient> patients) {
        this.patients.addAll(patients);
        //this.medicalHistories.addAll(medicalHistories);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        patients.addAll(source.getPatientList().stream().map(JsonAdaptedPatient::new).collect(Collectors.toList()));
        //medicalHistories.addAll(source.getMedHistList().stream().map(JsonAdaptedMedicalHistory::new)
        //        .collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedPatient jsonAdaptedPatient : patients) {
            Patient patient = jsonAdaptedPatient.toModelType();
            if (addressBook.hasPatient(patient)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addPatient(patient);
        }
        /*
        for (JsonAdaptedMedicalHistory jsonAdaptedMedicalHistory : medicalHistories) {
            MedicalHistory medicalHistory = jsonAdaptedMedicalHistory.toModelType();
            if (addressBook.hasMedHist(medicalHistory)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_MEDHIST);
            }
            addressBook.addMedHist(medicalHistory);
        }
        */
        return addressBook;
    }

}

package seedu.address.jsonfilegenerator;

//import javafx.beans.InvalidationListener;
//import javafx.collections.ObservableList;
//import seedu.address.commons.exceptions.DataConversionException;
//import seedu.address.model.person.healthworker.Patient;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.address.model.PatientBook;
import seedu.address.storage.JsonPatientBookStorage;
import seedu.address.storage.PatientBookStorage;
import seedu.address.testutil.TypicalPatients;

//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.List;
//import java.nio.file.Path;
//import java.util.Optional;

/**
 * This is a executable program that generates a JSON file of typical Patients.
 * This can be modified to generate a JSON file of any kind of book.
*/
public class PatientBookJsonGenerator {

    /**
     * Main functions creates a health worker book out of Patients found in TypicalPatient file
     * and saves it into a json file.
     * @param args Leave it empty
     */
    public static void main(String[] args) {
        String filepath = "toberenamed.json";

        PatientBook patientBook = TypicalPatients.getTypicalPatientBook();
        System.out.println(patientBook);

        Path patientBookFilePath = Paths.get(filepath);
        PatientBookStorage storage = new JsonPatientBookStorage(patientBookFilePath);

        try {
            storage.savePatientBook(patientBook);
            System.out.println("new file created\n");
        } catch (IOException e) {
            System.out.println("Problems with IO\n");
        }

    }

}

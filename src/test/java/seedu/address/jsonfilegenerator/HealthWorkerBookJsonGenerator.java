package seedu.address.jsonfilegenerator;

//import javafx.beans.InvalidationListener;
//import javafx.collections.ObservableList;
//import seedu.address.commons.exceptions.DataConversionException;
//import seedu.address.model.person.healthworker.HealthWorker;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.List;
//import java.nio.file.Path;
//import java.util.Optional;

import seedu.address.model.HealthWorkerBook;
import seedu.address.storage.HealthWorkerBookStorage;
import seedu.address.storage.JsonHealthWorkerBookStorage;
import seedu.address.testutil.TypicalHealthWorkers;
/**
 * This is a executable program that generates a JSON file of typical HealthWorkers.
 * This can be modified to generate a JSON file of any kind of book.
*/
public class HealthWorkerBookJsonGenerator {

    /**
     * Main functions creates a health worker book out of HealthWorkers found in TypicalHealthWorker file
     * and saves it into a json file.
     * @param args Leave it empty
     */
    public static void main(String[] args) {
        String filepath = "toberenamed.json";

        HealthWorkerBook healthWorkerBook = TypicalHealthWorkers.getTypicalHealthWorkerBook();
        System.out.println(healthWorkerBook);

        Path healthWorkerBookFilePath = Paths.get(filepath);
        HealthWorkerBookStorage storage = new JsonHealthWorkerBookStorage(healthWorkerBookFilePath);

        try {
            storage.saveHealthWorkerBook(healthWorkerBook);
            System.out.println("new file created\n");
        } catch (IOException e) {
            System.out.println("Problems with IO\n");
        }

    }

}

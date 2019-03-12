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


public class HealthWorkerBookJsonGenerator {

    static String filepath = "toberenamed.json";


    public static void main(String[] args) {

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

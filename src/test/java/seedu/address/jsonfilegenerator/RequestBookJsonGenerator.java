package seedu.address.jsonfilegenerator;

//import javafx.beans.InvalidationListener;
//import javafx.collections.ObservableList;
//import seedu.address.commons.exceptions.DataConversionException;
//import seedu.address.model.person.healthworker.Request;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.address.model.RequestBook;
import seedu.address.storage.JsonRequestBookStorage;
import seedu.address.storage.RequestBookStorage;
import seedu.address.testutil.TypicalRequests;
//import seedu.address.testutil.RequestBuilder;
//import seedu.address.testutil.RequestBookBuilder;

//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.List;
//import java.nio.file.Path;
//import java.util.Optional;

/**
 * This is a executable program that generates a JSON file of typical Requests.
 * This can be modified to generate a JSON file of any kind of book.
*/
public class RequestBookJsonGenerator {

    /**
     * Main functions creates a health worker book out of Requests found in TypicalRequest file
     * and saves it into a json file.
     * @param args Leave it empty
     */
    public static void main(String[] args) {
        String filepath = "toberenamed.json";

        RequestBook requestBook = TypicalRequests.getTypicalRequestBook();

        Path requestBookFilePath = Paths.get(filepath);
        RequestBookStorage storage = new JsonRequestBookStorage(requestBookFilePath);

        try {
            storage.saveRequestBook(requestBook);
            System.out.println("new file created\n");
        } catch (IOException e) {
            System.out.println("Problems with IO\n");
        }

    }

}

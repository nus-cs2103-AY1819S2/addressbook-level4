package seedu.address.model.dentist;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import seedu.address.model.dentist.exceptions.DentistNotFoundException;


/**
 * Class stores, retrieves and edit dentist information.
 * Streamlines the storage of dental records.
 * Class of all static methods.
 */
public class Dentist {
    private static final String filePath = "data/dentist_info.txt";

    /**
     * Gets the stored dentist name.
     * Prompts dentist name input if none is stored.
     * @return the dentist name, if it is stored.
     */
    public static String getDentistName() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String dentistName = br.readLine();

            if (!dentistName.equals("")) {
                return dentistName;
            } else {
                throw new DentistNotFoundException("Dentist name is empty");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "NIL";
        }
    }

    /**
     * Stores the dentist's name in the data filepath.
     * @param name the name to be stored.
     */
    public static void setDentistName(String name) {
        try {
            FileWriter fw = new FileWriter(filePath, false);
            fw.write(name);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

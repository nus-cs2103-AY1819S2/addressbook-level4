package seedu.address.model.dentist;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

import javafx.scene.control.TextInputDialog;


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
            br.close();

            if (dentistName != null && !dentistName.equals("")) {
                return dentistName;
            } else {
                throw new IOException();
            }
        } catch (IOException e) {
            String name = promptDentistName();
            setDentistName(name);
            return getDentistName();
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

    /**
     * Prompts user to enter his or her name if it is not specified.
     * @return the name of the user.
     */
    private static String promptDentistName() {
        TextInputDialog dialog = new TextInputDialog("Strange");
        dialog.setTitle("Set Dentist Name");
        dialog.setHeaderText("Please enter your name to continue. This will be used when you create reports.");
        dialog.setContentText("Your preferred name: ");

        Optional<String> name = dialog.showAndWait();

        return name.orElseGet(Dentist::promptDentistName);
    }
}

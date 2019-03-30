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
    public static final String FILEPATH = "data/dentist_info.txt";
    public static final String SAMPLE_NAME = "Kyler Wong";

    /**
     * Gets the stored dentist name.
     * Prompts dentist name input if none is stored.
     * @return the dentist name, if it is stored.
     */
    public static String getDentistName() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(FILEPATH));
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
            FileWriter fw = new FileWriter(FILEPATH, false);
            fw.write(name);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Uses a sample dentist name.
     */
    public static void setSampleDentistName() {
        setDentistName(SAMPLE_NAME);
    }

    /**
     * Removes the dentist's name in the data filepath.
     */
    public static void removeDentistName() {
        try {
            FileWriter fw = new FileWriter(FILEPATH, false);
            fw.write("");
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
        dialog.getDialogPane().getStylesheets().add("view/DarkTheme.css");
        dialog.setTitle("Set Dentist Name");
        dialog.setHeaderText("Please enter your name to continue. This will be used when you create reports.");
        dialog.setContentText("Your preferred name: ");

        Optional<String> name = dialog.showAndWait();

        return name.orElseGet(Dentist::promptDentistName);
    }

    /**
     * Checks if the dentist file exists with a valid name.
     * @return true if file exists with a valid name, false otherwise.
     */
    public static boolean dentistExists() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(FILEPATH));
            String dentistName = br.readLine();
            br.close();

            if (dentistName != null && !dentistName.equals("")) {
                return true;
            } else {
                throw new IOException();
            }
        } catch (IOException e) {
            return false;
        }
    }
}

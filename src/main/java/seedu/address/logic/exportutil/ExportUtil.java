package seedu.address.logic.exportutil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Set;

import seedu.address.MainApp;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * File handling utility functions for exporting data
 */
public class ExportUtil {


    /**
     * Exports utility
     * File handling checks and exports the data
     * @throws IOException if file cannot be created successfully
     */
    public static void exportDataToFile (String fileIdentifier, String extension, String data) {
        DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy_HHmmss");
        Date date = new Date();
        String now = dateFormat.format(date);

        fileIdentifier = fileIdentifier.replaceAll("[^a-zA-Z0-9]", "");
        extension = extension.replaceAll("[^a-zA-Z0-9]", "");
        String filename = fileIdentifier + "_" + generateRandom() + "_exportedOn" + now;

        File file = new File(filename + "." + extension);

        try {
            file.createNewFile();
            FileWriter writer = new FileWriter(file);

            writer.write(data);
            writer.close();
        } catch (IOException e) {
            System.out.println("File already Exists");
            exportDataToFile(fileIdentifier, extension, data);
        }
    }



    /**
     * Formats and export person to file
     */
    public static void exportPerson (Person person) {
        try {
            String name = person.getName().toString();
            String fileIdentifier = name.replaceAll("[^a-zA-Z0-9]", "");;
            String matricNumber = person.getMatricNumber().toString();
            String phone = person.getPhone().toString();
            String email = person.getEmail().toString();
            String address = person.getAddress().toString();
            String gender = person.getGender().toString();
            String yearOfStudy = person.getYearOfStudy().toString();
            String major = person.getMajor().toString();
            Set<Tag> tagSet = person.getTags();
            ArrayList<String> tagList = new ArrayList<String>();
            if (!tagSet.isEmpty()) {
                for (Tag t : tagSet) {
                    tagList.add(t.toString());
                }
            }

            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            String now = dateFormat.format(date);
            String title = name + " - " + now;


            String respath = "/exportutil/personInfo.html";
            InputStream resourceFormat = MainApp.class.getResourceAsStream(respath);

            BufferedReader reader = new BufferedReader(new InputStreamReader(resourceFormat, "UTF-8"));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            String ls = System.getProperty("line.separator");
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }
            reader.close();

            String htmlString = stringBuilder.toString();

            htmlString = htmlString.replace("$title", title);
            htmlString = htmlString.replace("$name", name);
            htmlString = htmlString.replace("$matricNumber", matricNumber);
            htmlString = htmlString.replace("$gender", gender);
            htmlString = htmlString.replace("$yearOfStudy", yearOfStudy);
            htmlString = htmlString.replace("$major", major);
            htmlString = htmlString.replace("$phone", phone);
            htmlString = htmlString.replace("$email", email);
            htmlString = htmlString.replace("$address", address);

            exportDataToFile(fileIdentifier, "html", htmlString);

        } catch (IOException e) {
            System.out.println("Cannot read format file");
        }

    }

    /**
     * Creates a random string for a part of the filename
     */
    private static String generateRandom() {
        String allowedChar = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder strBuilt = new StringBuilder();
        Random rnd = new Random();
        while (strBuilt.length() < 5) { // length of the random string.
            int index = (int) (rnd.nextFloat() * allowedChar.length());
            strBuilt.append(allowedChar.charAt(index));
        }
        String randStr = strBuilt.toString();
        return randStr;
    }
}

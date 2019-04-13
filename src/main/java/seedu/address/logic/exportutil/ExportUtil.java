package seedu.address.logic.exportutil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javafx.collections.ObservableList;
import seedu.address.MainApp;
import seedu.address.model.activity.Activity;
import seedu.address.model.person.Person;

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
        String name = person.getName().toString();
        String fileIdentifier = name.replaceAll("[^a-zA-Z0-9]", "");;
        String matricNumber = person.getMatricNumber().toString();
        String phone = person.getPhone().toString();
        String email = person.getEmail().toString();
        String address = person.getAddress().toString();
        String gender = person.getGender().toString();
        String yearOfStudy = person.getYearOfStudy().toString();
        String major = person.getMajor().toString();

        /*Set<Tag> tagSet = person.getTags();
        ArrayList<String> tagList = new ArrayList<String>();
        if (!tagSet.isEmpty()) {
            for (Tag t : tagSet) {
                tagList.add(t.toString());
            }
        }*/

        String now = generateFileDateTime();
        String title = name + " - " + now;

        String respath = "/exportutil/personInfo.html";
        String htmlString = readFormatFromHtmlFile(respath);

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

    }

    /**
     * Formats and export activity to file
     */
    public static void exportActivity (Activity activity, ObservableList<Person> personObservableList) {
        String name = activity.getName().toString();
        String fileIdentifier = name.replaceAll("[^a-zA-Z0-9]", "");;

        String description = activity.getDescription().toString();
        String dateTime = activity.getDateTime().toString();
        String location = activity.getLocation().toString();
        String status = activity.getCurrentStatus().toString();

        String now = generateFileDateTime();
        String title = name + " - " + now;

        String respath = "/exportutil/activityInfo.html";
        String htmlString = readFormatFromHtmlFile(respath);

        htmlString = htmlString.replace("$title", title);
        htmlString = htmlString.replace("$name", name);
        htmlString = htmlString.replace("$description", description);
        htmlString = htmlString.replace("$location", location);
        htmlString = htmlString.replace("$dateTime", dateTime);
        htmlString = htmlString.replace("$status", status);

        String attendanceListHtml = "";
        String ls = System.getProperty("line.separator");

        //build attendance list html
        StringBuilder attendanceListHtmlBuilder = new StringBuilder();

        if (!personObservableList.isEmpty()) {
            attendanceListHtmlBuilder.append("<h4>Members Attending :</h4>" + ls);
            attendanceListHtmlBuilder.append("<table>" + ls + "<tbody>" + ls);

            attendanceListHtmlBuilder.append("<tr>" + ls);
            attendanceListHtmlBuilder.append("<th class='tblhead'>Name</th>" + ls);
            attendanceListHtmlBuilder.append("<th class='tblhead'>Matric No.</th>" + ls);
            attendanceListHtmlBuilder.append("<th class='tblhead'>Attended</th>" + ls);
            attendanceListHtmlBuilder.append("</tr>" + ls);

            for (Person p : personObservableList) {
                attendanceListHtmlBuilder.append("<tr>" + ls);

                attendanceListHtmlBuilder.append("<td class='listName'>" + ls);
                attendanceListHtmlBuilder.append(p.getName().toString());
                attendanceListHtmlBuilder.append("</td>" + ls);
                attendanceListHtmlBuilder.append("<td class='listMatric'>" + ls);
                attendanceListHtmlBuilder.append(p.getMatricNumber().toString());
                attendanceListHtmlBuilder.append("</td>" + ls);
                attendanceListHtmlBuilder.append("<td class='listAttendBox'>" + ls);
                attendanceListHtmlBuilder.append("&nbsp;");
                attendanceListHtmlBuilder.append("</td>" + ls);

                attendanceListHtmlBuilder.append("</tr>" + ls);
            }
            attendanceListHtmlBuilder.append("</tbody>" + ls + "</table>" + ls);
        }

        attendanceListHtml = attendanceListHtmlBuilder.toString();

        htmlString = htmlString.replace("$attendanceListHtml", attendanceListHtml);

        exportDataToFile(fileIdentifier, "html", htmlString);

    }

    /**
     * Creates datetime part of the filename
     */
    private static String generateFileDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    /**
     * Build format into string from resource format file
     */
    private static String readFormatFromHtmlFile(String respath) {
        try {
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

            return stringBuilder.toString();
        } catch (IOException e) {
            System.out.println("Cannot read from format file: " + respath);
        }
        return "No format file found" + System.getProperty("line.separator");
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

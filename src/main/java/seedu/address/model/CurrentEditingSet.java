///* @@author Carrein */
//
//package seedu.address.model;
//
//import seedu.address.Notifier;
//import seedu.address.model.image.Image;
//
///**
// * CurrentEditingSet employs the singleton pattern to ensure a single modifier Image at any
// * one time. (during FomoFoto operation).
// */
//public class CurrentEditingSet {
//    // Single instance field
//    private static CurrentEditingSet instance = null;
//
//    // Temp path for image edits
//    private final String TEMP_FILEPATH = "src/main/resources/temp/";
//
//    // Data fields
//    private String name;
//    private Image currentEditingImage;
//
//    private CurrentEditingSet() {
//
//    }
//
//    public static CurrentEditingSet getInstance() {
//        if (instance == null) {
//            instance = new CurrentEditingSet();
//        }
//        return instance;
//    }
//
//    public void save(){
//
//    }
//
//    public void
//
//    public void displayImage() {
//        Notifier.firePropertyChangeListener("display", null, null);
//    }
//}

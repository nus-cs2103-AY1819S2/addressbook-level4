package seedu.address.commons.util;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.address.model.apparel.ColorValue;

/**
 * Writes and reads files
 */
public class FileUtil {

    private static final String CHARSET = "UTF-8";

    public static boolean isFileExists(Path file) {
        return Files.exists(file) && Files.isRegularFile(file);
    }

    /**
     * Returns true if {@code path} can be converted into a {@code Path} via {@link Paths#get(String)},
     * otherwise returns false.
     * @param path A string representing the file path. Cannot be null.
     */
    public static boolean isValidPath(String path) {
        try {
            Paths.get(path);
        } catch (InvalidPathException ipe) {
            return false;
        }
        return true;
    }

    /**
     * Creates a file if it does not exist along with its missing parent directories.
     * @throws IOException if the file or directory cannot be created.
     */
    public static void createIfMissing(Path file) throws IOException {
        if (!isFileExists(file)) {
            createFile(file);
        }
    }

    /**
     * Creates a file if it does not exist along with its missing parent directories.
     */
    public static void createFile(Path file) throws IOException {
        if (Files.exists(file)) {
            return;
        }

        createParentDirsOfFile(file);

        Files.createFile(file);
    }

    /**
     * Creates parent directories of file if it has a parent directory
     */
    public static void createParentDirsOfFile(Path file) throws IOException {
        Path parentDir = file.getParent();

        if (parentDir != null) {
            Files.createDirectories(parentDir);
        }
    }

    /**
     * Assumes file exists
     */
    public static String readFromFile(Path file) throws IOException {
        return new String(Files.readAllBytes(file), CHARSET);
    }

    /**
     * Writes given string to a file.
     * Will create the file if it does not exist yet.
     */
    public static void writeToFile(Path file, String content) throws IOException {
        Files.write(file, content.getBytes(CHARSET));
    }

    public static String getBeltIconFilePath(ColorValue color) {
        switch(color) {
        case BLACK: return "/images/belt_icon/belt-black.PNG";
        case BLUE: return "/images/belt_icon/belt-blue.PNG";
        case BROWN: return "/images/belt_icon/belt-brown.PNG";
        case CREAM: return "/images/belt_icon/belt-cream.PNG";
        case GREEN: return "/images/belt_icon/belt-green.PNG";
        case GREY: return "/images/belt_icon/belt-grey.PNG";
        case KHAKI: return "/images/belt_icon/belt-khaki.PNG";
        case NAVY: return "/images/belt_icon/belt-navy.PNG";
        case ORANGE: return "/images/belt_icon/belt-orange.PNG";
        case PINK: return "/images/belt_icon/belt-pink.PNG";
        case PURPLE: return "/images/belt_icon/belt-purple.PNG";
        case RED: return "/images/belt_icon/belt-red.PNG";
        case WHITE: return "/images/belt_icon/belt-white.PNG";
        case YELLOW: return "/images/belt_icon/belt-yellow.PNG";
        default: throw new IllegalArgumentException("No such color");
        }
    }

    public static String getBottomIconFilePath(ColorValue color) {
        switch(color) {
        case BLACK: return "/images/bottom_icon/bottom-black.PNG";
        case BLUE: return "/images/bottom_icon/bottom-blue.PNG";
        case BROWN: return "/images/bottom_icon/bottom-brown.PNG";
        case CREAM: return "/images/bottom_icon/bottom-cream.PNG";
        case GREEN: return "/images/bottom_icon/bottom-green.PNG";
        case GREY: return "/images/bottom_icon/bottom-grey.PNG";
        case KHAKI: return "/images/bottom_icon/bottom-khaki.PNG";
        case NAVY: return "/images/bottom_icon/bottom-navy.PNG";
        case ORANGE: return "/images/bottom_icon/bottom-orange.PNG";
        case PINK: return "/images/bottom_icon/bottom-pink.PNG";
        case PURPLE: return "/images/bottom_icon/bottom-purple.PNG";
        case RED: return "/images/bottom_icon/bottom-red.PNG";
        case WHITE: return "/images/bottom_icon/bottom-white.PNG";
        case YELLOW: return "/images/bottom_icon/bottom-yellow.PNG";
        default: throw new IllegalArgumentException("No such color");
        }
    }

    public static String getShoeIconFilePath(ColorValue color) {
        switch(color) {
        case BLACK: return "/images/shoes_icon/shoes-black.PNG";
        case BLUE: return "/images/shoes_icon/shoes-blue.PNG";
        case BROWN: return "/images/shoes_icon/shoes-brown.PNG";
        case CREAM: return "/images/shoes_icon/shoes-cream.PNG";
        case GREEN: return "/images/shoes_icon/shoes-green.PNG";
        case GREY: return "/images/shoes_icon/shoes-grey.PNG";
        case KHAKI: return "/images/shoes_icon/shoes-khaki.PNG";
        case NAVY: return "/images/shoes_icon/shoes-navy.PNG";
        case ORANGE: return "/images/shoes_icon/shoes-orange.PNG";
        case PINK: return "/images/shoes_icon/shoes-pink.PNG";
        case PURPLE: return "/images/shoes_icon/shoes-purple.PNG";
        case RED: return "/images/shoes_icon/shoes-red.PNG";
        case WHITE: return "/images/shoes_icon/shoes-white.PNG";
        case YELLOW: return "/images/shoes_icon/shoes-yellow.PNG";
        default: throw new IllegalArgumentException("No such color");
        }
    }

    public static String getTopIconFilePath(ColorValue color) {
        switch(color) {
        case BLACK: return "/images/top_icon/top-black.PNG";
        case BLUE: return "/images/top_icon/top-blue.PNG";
        case BROWN: return "/images/top_icon/top-brown.PNG";
        case CREAM: return "/images/top_icon/top-cream.PNG";
        case GREEN: return "/images/top_icon/top-green.PNG";
        case GREY: return "/images/top_icon/top-grey.PNG";
        case KHAKI: return "/images/top_icon/top-khaki.PNG";
        case NAVY: return "/images/top_icon/top-navy.PNG";
        case ORANGE: return "/images/top_icon/top-orange.PNG";
        case PINK: return "/images/top_icon/top-pink.PNG";
        case PURPLE: return "/images/top_icon/top-purple.PNG";
        case RED: return "/images/top_icon/top-red.PNG";
        case WHITE: return "/images/top_icon/top-white.PNG";
        case YELLOW: return "/images/top_icon/top-yellow.PNG";
        default: throw new IllegalArgumentException("No such color");
        }
    }

}

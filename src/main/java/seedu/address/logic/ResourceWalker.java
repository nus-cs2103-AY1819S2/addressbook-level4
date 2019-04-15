/* @@author Carrein */
package seedu.address.logic;

import static seedu.address.commons.core.Config.ASSETS_FOLDER_TEMP_NAME;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;

import org.apache.commons.io.IOUtils;

import seedu.address.model.Album;

/**
 * ResourceWalker is a helper class to read external resources into a runtime JAR project.
 * In FomoFoto, this class is only called once to import VALID images into the assets folder during runtime.
 * ResourceWalker DOES NOT CHECK FOR INVALID FILE - DO NOT PLACE NON-IMAGE FILE INTO resources/imageTest/valid.
 */
public class ResourceWalker {
    private static final Album album = Album.getInstance();

    /**
     * Given a path to a directory copy its content to a temp folder by
     * traversing all entries in the directory and performing generateTemp().
     *
     * @throws URISyntaxException File path is invalid.
     * @throws IOException        I/O operations cannot be performed on file.
     */
    public static void walk(String args) throws URISyntaxException, IOException {
        URI uri = ResourceWalker.class.getResource(args).toURI();
        if (uri.getScheme().equals("jar")) {
            FileSystems.newFileSystem(uri, Collections.emptyMap());
        }
        Path myPath = Paths.get(uri);
        Files.walkFileTree(myPath, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) {
                generateTemp(path);
                return FileVisitResult.CONTINUE;
            }
        });
        if (uri.getScheme().equals("jar")) {
            FileSystems.getFileSystem(uri).close();
        }
    }

    /**
     * Given a ZipPath convert into a temp file.
     *
     * @param path Path to be converted.
     * @return Converted File.
     */
    private static File generateTemp(Path path) {
        File tempFile = null;
        try {
            InputStream in = Files.newInputStream(path);
            String tDir = System.getProperty("user.dir") + File.separator + ASSETS_FOLDER_TEMP_NAME;
            tempFile = new File(tDir + File.separator + path.getFileName());
            try (FileOutputStream out = new FileOutputStream(tempFile)) {
                IOUtils.copy(in, out);
            }
            album.addToImageList(tempFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tempFile;
    }
}

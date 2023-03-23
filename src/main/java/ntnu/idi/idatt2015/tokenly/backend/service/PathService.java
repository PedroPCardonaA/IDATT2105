package ntnu.idi.idatt2015.tokenly.backend.service;
import java.util.UUID;

/**
 * The PathService class provides methods for generating and managing file paths.
 *
 * @author tokenly-team
 * @version 1.0
 * @since 22.03.2023
 */
public class PathService {

    private static String lastPath = "";

    /**
     * Generates a new file path based on the given file name.
     *
     * @param fileName The name of the file.
     * @return The generated file path.
     */
    public static String generatePath(String fileName){
        UUID uuid = UUID.randomUUID();
        String timeStamp = String.valueOf(System.currentTimeMillis());
        String path = uuid + timeStamp + fileName;
        lastPath = path;
        return path;
    }

    /**
     * Returns the last generated file path.
     *
     * @return The last generated file path.
     */
    public static String getLastPath(){
        return lastPath;
    }

    /**
     * Deletes the last generated file path.
     */
    public static void deleteLastPath(){
        lastPath = "";
    }

    /**
     * Returns the folder path where files are stored.
     *
     * @return The folder path where files are stored.
     */
    public static String getFolderPath(){
        return "src/main/resources/sources/";
    }

}

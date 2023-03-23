package ntnu.idi.idatt2015.tokenly.backend.service;
import java.util.UUID;
public class PathService {
    private static String lastPath = "";

    public static String generatePath(String fileName){
        UUID uuid = UUID.randomUUID();
        String timeStamp = String.valueOf(System.currentTimeMillis());
        String path = uuid+timeStamp+fileName;
        lastPath = path;
        return path;
    }

    public static String getLastPath(){
        return lastPath;
    }

    public static void  deleteLastPath(){
        lastPath = "";
    }

    public static String getFolderPath(){
        return "src/main/resources/sources/";
    }

}

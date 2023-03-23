package ntnu.idi.idatt2015.tokenly.backend.service;
import java.util.UUID;
public class PathService {
    public static String generateUniquePath(String fileName){
        UUID uuid = UUID.randomUUID();
        String timeStamp = String.valueOf(System.currentTimeMillis());
        return "src/main/resources/sources/"+ uuid+timeStamp+fileName;
    }
}

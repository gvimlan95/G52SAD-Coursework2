import java.io.File;

public class ListFilesUtil {

    public File[] listFiles(String directoryName){
        File directory = new File(directoryName);
        File[] fList = directory.listFiles();
        return fList;
    }
}
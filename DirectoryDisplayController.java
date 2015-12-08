import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DirectoryDisplayController {

    private List<Image> images = new ArrayList<>();

    public void getFilesList(){
        ListFilesUtil listFilesUtil = new ListFilesUtil();
        final String directoryLinuxMac ="/Users/VIMLANG/SAD Images/";
        File[] fileList=listFilesUtil.listFiles(directoryLinuxMac);
        for (File file : fileList){
            if (file.isFile()){
                String fileName = "/Users/VIMLANG/SAD Images/"+file.getName();
                Image newImage1 = new Image("file:"+file.getPath());
                images.add(newImage1);
            }
        }
        images.remove(0);

    }
}

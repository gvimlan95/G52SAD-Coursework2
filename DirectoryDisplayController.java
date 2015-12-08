import javafx.fxml.FXML;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.ImageView;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class DirectoryDisplayController {

    private List<Image> images = new ArrayList<>();
    private int xCod = 0;
    private int yCod = 0;

    private Label label1 = new Label();

    @FXML
    private HBox HBoxDirectory;

    public void initialize(){
        getFilesList();

    }

    public void getFilesList(){
        ListFilesUtil listFilesUtil = new ListFilesUtil();
        final String directoryLinuxMac ="/Users/VIMLANG/SAD Images/";
        File[] fileList=listFilesUtil.listFiles(directoryLinuxMac);
        for (File file : fileList){
            if (file.isFile()){
                System.out.println("filename "+ file.getAbsolutePath());
                AnchorPane pane = new AnchorPane();
                ImageView imgView = new ImageView();
//                Label label = new Label(fileName.replace(".jpg",""));
                Label label = new Label(file.getName());
                Image img = new Image("file:"+file.getAbsolutePath());
                System.out.println("Done");

                imgView.setImage(img);
                imgView.setFitHeight(100);
                imgView.setFitWidth(100);
                xCod +=10;
                imgView.setX(xCod);
                imgView.setY(10);
                double x = imgView.getX();
                label.setLayoutX(x);
                double y = imgView.getY();
                label.setLayoutY(y+100);
                pane.getChildren().add(imgView);
                pane.getChildren().add(label);

                HBoxDirectory.getChildren().add(pane);

            }
        }
    }
}

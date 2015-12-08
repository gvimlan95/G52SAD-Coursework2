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
import sun.plugin.javascript.navig.Anchor;

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

    private String directoryLocation = "/Users/VIMLANG/SAD Images/";

    public void initialize(){
        listFilesAndFilesSubDirectories(directoryLocation);

    }

    public void listFilesAndFilesSubDirectories(String location){
        ListFilesUtil listFilesUtil = new ListFilesUtil();
        File[] fileList=listFilesUtil.listFiles(location);
        for (File file : fileList){
//            if (file.isFile()){
                AnchorPane pane = new AnchorPane();
                ImageView imgView = new ImageView();
//                Label label = new Label(fileName.replace(".jpg",""));
                Label label = new Label(file.getName());
                Image img = new Image("file:"+file.getAbsolutePath());

                imgView.setImage(img);
                imgView.setFitHeight(100);
                imgView.setFitWidth(100);
                xCod +=10;
                imgView.setX(xCod);
                imgView.setY(yCod);
                double x = imgView.getX();
                label.setLayoutX(x);
                System.out.println("x "+x);
                double y = imgView.getY();
                label.setLayoutY(imgView.getFitHeight());

                pane.getChildren().add(imgView);
                pane.getChildren().add(label);

                HBoxDirectory.getChildren().add(pane);

//            }
        }
    }


}

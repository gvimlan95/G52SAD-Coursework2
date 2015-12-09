import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import sun.plugin.javascript.navig.Anchor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class DirectoryDisplayController {

    private List<Image> images = new ArrayList<>();

    @FXML
    private TilePane tilePaneItems;

    private String directoryLocation = "/Users/VIMLANG/SAD Images/";

    public void initialize(){
        listFilesAndFilesSubDirectories(directoryLocation);

    }

    public void listFilesAndFilesSubDirectories(String location){

        ListFilesUtil listFilesUtil = new ListFilesUtil();
        File[] fileList=listFilesUtil.listFiles(location);
        for (File file : fileList){

            AnchorPane pane = new AnchorPane();
            ImageView imgView = new ImageView();
            Label label = new Label(file.getName());
            Image img;

            if (file.isFile()){

               // ImageView imgView = new ImageView();
//                Label label = new Label(fileName.replace(".jpg",""));
               // Label label = new Label(file.getName());
                 img = new Image("file:"+file.getAbsolutePath());
            }else{

              //  Label label = new Label(file.getName());
                img = new Image("file:/Users/VIMLANG/G52SAD-Coursework2/icons/folder-open-512.png");

            }

            imgView.setImage(img);
            imgView.setFitHeight(100);
            imgView.setFitWidth(100);
            label.setLayoutX(imgView.getX());
            label.setPrefWidth(100.0);
            label.setLayoutY(imgView.getFitHeight());
            pane.getChildren().add(imgView);
            pane.getChildren().add(label);
            pane.setPadding(new Insets(20.0));

            pane.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                System.out.println("Tile pressed ");
                System.out.print("source "+event.toString());
                //event.consume();
            });

            tilePaneItems.getChildren().add(pane);
        }


    }

}




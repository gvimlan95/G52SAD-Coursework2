import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.lang.Object;

public class DirectoryDisplayController {

    private List<Image> images = new ArrayList<>();
    private Deque<String> pastDirectory = new ArrayDeque<String>();

    @FXML
    private TilePane tilePaneItems;

    @FXML
    private Button backButton;

    private String directoryLocation = "/Users/VIMLANG/SAD Images/";

    public void initialize(){
        listFilesAndFilesSubDirectories(directoryLocation);

    }

    public void listFilesAndFilesSubDirectories(String location){


        tilePaneItems.getChildren().clear();
        ListFilesUtil listFilesUtil = new ListFilesUtil();
        File[] fileList=listFilesUtil.listFiles(location);
        for (File file : fileList){

            AnchorPane pane = new AnchorPane();
            ImageView imgView = new ImageView();
            Label label = new Label(file.getName());
            Image img;

            if (file.isFile()){
                 img = new Image("file:"+file.getAbsolutePath());
            }else{
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

                if(event.getClickCount() == 2) {
                    System.out.println(file.getAbsolutePath());
                    listFilesAndFilesSubDirectories(file.getAbsolutePath());
                    pastDirectory.push(location);
                }

            });

            tilePaneItems.getChildren().add(pane);
        }
    }

    public void goToPreviousDirectory(){
        if(!pastDirectory.isEmpty()) {
            String dir = pastDirectory.pop();
            listFilesAndFilesSubDirectories(dir);
        }else{
            backButton.setVisible(false);
        }
    }

}




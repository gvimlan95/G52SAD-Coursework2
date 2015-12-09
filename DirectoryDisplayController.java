import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.TilePane;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.lang.Object;
import java.util.logging.Logger;


public class DirectoryDisplayController {

    private List<Image> images = new ArrayList<>();
    private Deque<String> pastDirectory = new ArrayDeque<String>();

    @FXML
    private AnchorPane root;

    @FXML
    private Button backButton;

    @FXML
    private ToolBar topToolbar;

    @FXML
    private ToolBar bottomToolbar;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private TilePane tilePane;

    @FXML
    private FlowPane flowPane;

    private String directoryLocation = "/Users/VIMLANG/SAD Images/";

    public void initialize(){
        bindValues();
        listFilesAndFilesSubDirectories(directoryLocation);
    }

    public void bindValues(){
        topToolbar.prefWidthProperty().bind(root.widthProperty());
//        topToolbar.prefHeightProperty().bind(root.heightProperty().multiply(0.1));

        bottomToolbar.prefWidthProperty().bind(root.widthProperty());
//        bottomToolbar.prefHeightProperty().bind(root.heightProperty().multiply(0.1));

        flowPane.prefWidthProperty().bind(root.widthProperty());
        flowPane.prefHeightProperty().bind(root.heightProperty());

        scrollPane.prefWidthProperty().bind(flowPane.widthProperty());
        scrollPane.prefHeightProperty().bind(flowPane.heightProperty().subtract(120));

        tilePane.prefWidthProperty().bind(scrollPane.widthProperty());
        tilePane.prefHeightProperty().bind(scrollPane.heightProperty());
    }

    public void listFilesAndFilesSubDirectories(String location){


        tilePane.getChildren().clear();
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
                img = new Image("file:/Users/VIMLANG/G52SAD-Coursework2/icons/black-white-metro-folder-icon.png");
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

//                Stage stage = (Stage)root.getScene().getWindow();
//                Stage stage = ((Node) event.getSource().getScene().getWindow());

                if(event.getClickCount() == 2) {
                    listFilesAndFilesSubDirectories(file.getAbsolutePath());
                    pastDirectory.push(location);
                }
                if(event.getClickCount() == 1){
//                    Parent imageViewParent = null;
//                    try {
//                        imageViewParent = FXMLLoader.load(getClass().getResource("ImageView.fxml"));
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    Scene imageViewPage = new Scene(imageViewParent);
//                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//                    stage.hide();
//                    stage.setScene(imageViewPage);
//                    stage.show();

                    SceneManager sm = SceneManager.getInstance();
                    sm.getSomeThing();
                }

            });

            tilePane.getChildren().add(pane);
        }
    }

    public void goToPreviousDirectory(){
        if(!pastDirectory.isEmpty()) {
            String dir = pastDirectory.pop();
            listFilesAndFilesSubDirectories(dir);
        }
    }

    public void createNewFolder(){
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Create New Folder");
        dialog.setHeaderText("");
        dialog.setContentText("Please enter your folder name:");

// Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            File file = new File(directoryLocation+result.get());
            if (!file.exists()) {
                if (file.mkdir()) {
                    System.out.println("Directory is created!");
                } else {
                    System.out.println("Failed to create directory!");
                }
            }
        }


    }

}




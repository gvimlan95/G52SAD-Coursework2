import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;


public class DirectoryDisplayController {

    private List<Image> images = new ArrayList<>();
    private List<File> selectedFileList = new ArrayList<>();
    private Deque<String> pastDirectory = new ArrayDeque<String>();
    private String directoryLocation = System.getProperty("user.home");
    private Boolean isMoveEnabled = false;

    @FXML
    private AnchorPane root;

    @FXML
    private Button backButton;

    @FXML
    private Button moveHere;

    @FXML
    private Button moveFile;

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

    @FXML
    private Label fileDirectoryLabel;

    @FXML
    private Label moveStatusLabel;

    public void initialize(){
        bindValues();
        listFilesAndFilesSubDirectories(directoryLocation);
        fileDirectoryLabel.setText("Current Directory: "+directoryLocation);
    }

    public void bindValues(){
        topToolbar.prefWidthProperty().bind(root.widthProperty());

        bottomToolbar.prefWidthProperty().bind(root.widthProperty());

        flowPane.prefWidthProperty().bind(root.widthProperty());
        flowPane.prefHeightProperty().bind(root.heightProperty());

        scrollPane.prefWidthProperty().bind(flowPane.widthProperty());
        scrollPane.prefHeightProperty().bind(flowPane.heightProperty().subtract(120));

        tilePane.prefWidthProperty().bind(scrollPane.widthProperty());
        tilePane.prefHeightProperty().bind(scrollPane.heightProperty());
    }

    public void setIsMoveEnabled(){
        isMoveEnabled = !isMoveEnabled;

        if(isMoveEnabled){
            moveStatusLabel.setText("Move is enabled");

        }else{
            moveStatusLabel.setText("Move is disabled");
        }
    }

    public void moveFilesHere(){
        if(isMoveEnabled && selectedFileList.size()!=0){
            for(File selectedFile:selectedFileList) {
                try {
                    Path FROM = Paths.get(selectedFile.getAbsolutePath());
                    Path TO = Paths.get(directoryLocation+"/"+selectedFile.getName());
                    CopyOption[] options = new CopyOption[]{
                            StandardCopyOption.REPLACE_EXISTING,
                            StandardCopyOption.COPY_ATTRIBUTES
                    };
                    Files.copy(FROM, TO, options);
                    selectedFile.delete();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        listFilesAndFilesSubDirectories(directoryLocation);
    }

    public void deleteFileAndFolder() {

        if(selectedFileList.size() != 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("");
            if (selectedFileList.size() > 1) {
                alert.setContentText("Are you sure want to delete these items?");
            } else {
                alert.setContentText("Are you sure want to delete this item?");
            }

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                for (File selectedFile : selectedFileList) {
                    selectedFile.delete();
                }
                listFilesAndFilesSubDirectories(directoryLocation);
            } else {
                // ... user chose CANCEL or closed the dialog
            }
        }
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
                img = new Image("file:/Users/VIMLANG/G52SAD-Coursework2/icons/folder.png");
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

                if(event.getClickCount() == 1){
                    if(file.isFile()){
                        if(pane.getOpacity() == 0.5){
                            pane.setOpacity(1);
                            selectedFileList.remove(file);
                        }else{
                            pane.setOpacity(0.5);
                            selectedFileList.add(file);
                        }
                    }
                }

                if(event.getClickCount() == 2) {
//                    pane.setOpacity(0);
                    if(file.isDirectory()) {
                        listFilesAndFilesSubDirectories(file.getAbsolutePath());
                        directoryLocation = file.getAbsolutePath();
                        fileDirectoryLabel.setText("Current Directory: "+directoryLocation);
                        pastDirectory.push(location);
                    }else{
                        SceneManager.getInstance().getImageViewController().getFilesList(directoryLocation);
                        ImageViewApplication.changeScene(false);
                        SceneManager.getInstance().getImageViewController().setImage(new Image("file:" + file.getAbsolutePath()));
                    }
                }

            });

            tilePane.getChildren().add(pane);
        }
    }

    public void goToPreviousDirectory(){
        if(!pastDirectory.isEmpty()) {
            String dir = pastDirectory.pop();
            listFilesAndFilesSubDirectories(dir);
            directoryLocation = dir;
            fileDirectoryLabel.setText("Current Directory: "+directoryLocation);
        }
    }

    public void createNewFolder(){
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Create New Folder");
        dialog.setHeaderText("");
        dialog.setContentText("Please enter your folder name:");

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
        listFilesAndFilesSubDirectories(directoryLocation);
    }

}




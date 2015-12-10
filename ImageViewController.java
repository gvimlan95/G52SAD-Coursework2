import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageViewController
{
    private int xRotate=0;
    private int effectId = -1;
    private double imageWidth;
    private double imageHeight;
    private String currentDirectory = System.getProperty("user.home");

    ColorAdjust effectAdjust = new ColorAdjust();

    // Private fields for components
    @FXML
    private ImageView myImage;
    private Image newImage;

    @FXML
    private AnchorPane root;

    private List<Image> images = new ArrayList<>();

    private int index = 0;

    @FXML
    private Slider effectSlider;

    @FXML
    private Slider zoomSlider;

    @FXML
    private Button saveButton;

    @FXML
    private ScrollPane imageScrollPane;

    @FXML
    private ToolBar topToolbar;

    @FXML
    private ToolBar bottomToolbar;

    @FXML
    private FlowPane flowPane;

    @FXML
    private ScrollPane scrollPane;

    public void initialize(){
        getFilesList(currentDirectory);
        bindValues();
    }

    public void returnToGallery(){

        ImageViewApplication.changeScene(true);
    }

    public void bindValues(){
        topToolbar.prefWidthProperty().bind(root.widthProperty());
        bottomToolbar.prefWidthProperty().bind(root.widthProperty());

        flowPane.prefWidthProperty().bind(root.widthProperty());
        flowPane.prefHeightProperty().bind(root.heightProperty());

        scrollPane.prefWidthProperty().bind(flowPane.prefWidthProperty().subtract(100));
        scrollPane.prefHeightProperty().bind(flowPane.prefHeightProperty().subtract(150));
    }

    public void getFilesList(String dir){
        images.removeAll(images);
        ListFilesUtil listFilesUtil = new ListFilesUtil();
        File[] fileList=listFilesUtil.listFiles(dir);
        for (File file : fileList){
            if (file.isFile()){
                Image newImage1 = new Image("file:"+file.getPath());
                images.add(newImage1);
            }
        }
    }

    public void setImage(Image image){
        myImage.setImage(image);
        imageWidth = myImage.getFitWidth();
        imageHeight = myImage.getFitHeight();
    }

    public void setCurrentDirectory(String dir){
        currentDirectory = dir;
    }

    public void fileChooser(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

        fileChooser.setTitle("Select File");

        File file = fileChooser.showOpenDialog(null);

        if(file!=null)
        setImage(newImage);
    }

    public void rotateLeft(){

        xRotate+=90;
        myImage.setRotate(xRotate);
    }

    public void rotateRight(){

        xRotate-=90;
        myImage.setRotate(xRotate);
    }

    public void nextImage(){
        resetImage();
        if(index<images.size()-1) {
            index++;
        }
        myImage.setImage(images.get(index));
    }

    public void previousImage(){
        resetImage();
        if(index >0) {
            index--;
        }
        myImage.setImage(images.get(index));
    }

    public void saveToFile(Image image) {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

        fileChooser.setTitle("Select File");

        File file = fileChooser.showSaveDialog(null);

        if(file != null){
            WritableImage snapshot = myImage.snapshot(new SnapshotParameters(), null);
            BufferedImage bImage = SwingFXUtils.fromFXImage(snapshot, null);
            try {
                ImageIO.write(bImage, "png", file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void saveHandler(){
        Image image = myImage.getImage();
        saveToFile(image);
    }

    public void enableEffectSlider(){
        effectSlider.setVisible(true);
        effectSlider.setValue(0.0);
    }

    public void getSliderValue(){
        double value = effectSlider.getValue();

        effectSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            switch(effectId){
                case 0 :
                    setBrightness(newValue.doubleValue());
                    break;
                case 1 :
                    setContrast(newValue.doubleValue());
                    break;
                case 2 :
                    setSaturation(newValue.doubleValue());
                    break;
                case 3 :
                    setHue(newValue.doubleValue());
                    break;
            }
        });
    }

    public void setBrightnessSlider(){
        effectId=0;
        enableEffectSlider();
    }

    public void setContrastsSlider(){
        effectId=1;
        enableEffectSlider();
    }

    public void setSaturationSlider(){
        effectId=2;
        enableEffectSlider();
    }

    public void setHueSlider(){
        effectId=3;
        enableEffectSlider();
    }

    public void resetImage() {
        effectSlider.setValue(0.0);
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(0);
        colorAdjust.setSaturation(0);
        colorAdjust.setContrast(0);
        colorAdjust.setHue(0);
        myImage.setEffect(colorAdjust);
    }

    public void setBrightness(double value){
        effectAdjust.setBrightness(value);
        myImage.setEffect(effectAdjust);
    }

    public void setSaturation(double value){
        effectAdjust.setSaturation(value);
        myImage.setEffect(effectAdjust);
    }

    public void setContrast(double value){
        effectAdjust.setContrast(value);
        myImage.setEffect(effectAdjust);
    }

    public void setHue(double value){
        effectAdjust.setContrast(value);
        myImage.setEffect(effectAdjust);
    }

    public void zoomImage(){

        zoomSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            myImage.setFitWidth(imageWidth*newValue.doubleValue());
            myImage.setFitHeight(imageHeight*newValue.doubleValue());
        });

    }

    public void fullscreenHandler(){
        Stage stage = (Stage)root.getScene().getWindow();

        if(stage.isFullScreen()){
            stage.setFullScreen(false);
        }else{
            stage.setFullScreen(true);
        }
    }
}

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.text.Text;
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

    final DoubleProperty zoomProperty = new SimpleDoubleProperty(200);

    private int xRotate=0;
    private int effectId = -1;

    ColorAdjust effectAdjust = new ColorAdjust();

    // Private fields for components 
    @FXML
    private ImageView myImage;
    private Image newImage;

    // Private fields for the dog and cat images
    private List<Image> images = new ArrayList<>();
    private int index = 0;

    @FXML
    private Slider effectSlider;

    @FXML
    private Button saveButton;

    @FXML
    private ScrollPane imageScrollPane;

    // Initialize method
    public void initialize(){
      // Load the dog and cat images
        getFilesList();
        myImage.setImage(images.get(0));
    }

    public void getFilesList(){
        images.removeAll(images);
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
        myImage.setImage(images.get(1));

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

    public void fileChooser(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

        fileChooser.setTitle("Select File");

        File file = fileChooser.showOpenDialog(null);

        if(file!=null)

            myImage.setRotate(0);
            System.out.println("File path "+file.getPath());
            newImage = new Image("file:"+file.getPath());
            myImage.setImage(newImage);
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
        File outputFile = new File("C:/JavaFX/");
        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
        try {
            ImageIO.write(bImage, "png", outputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void invalidated() {
        myImage.setFitWidth(zoomProperty.get() * 4);
        myImage.setFitHeight(zoomProperty.get() * 3);
    }

    public void handle(ScrollEvent event) {
        if (event.getDeltaY() > 0) {
            zoomProperty.set(zoomProperty.get() * 1.1);
        } else if (event.getDeltaY() < 0) {
            zoomProperty.set(zoomProperty.get() / 1.1);
        }
    }

    public void enableEffectSlider(){
        effectSlider.setVisible(true);
        effectSlider.setValue(0.0);
    }

    public void getSliderValue(){
        double value = effectSlider.getValue();
        System.out.println("value "+value);
        switch(effectId){
            case 0 :
                setBrightness(value);
                break;
            case 1 :
                setContrast(value);
                break;
            case 2 :
                setSaturation(value);
                break;
            case 3 :
                setHue(value);
                break;
        }
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
}

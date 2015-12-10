import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOError;
import java.io.IOException;

/*Works as a singleton class, to join all the controllers together and making all methods accessible from different controllers */

public class SceneManager {

    private static SceneManager instance;

    ImageViewController imageViewController;
    DirectoryDisplayController directoryDisplayController;

    Scene imageViewScene;
    Scene directoryDisplayScene;

    private SceneManager(){
        try {
            loadImageViewFXML();
            loadDirectoryDisplayFXML();
        } catch (IOException e) {
            System.out.println("Some FXML file was probably not found.");
            e.printStackTrace();
        }
    }

    public static SceneManager getInstance(){
        if(instance == null){
            instance = new SceneManager();
        }
        return instance;
    }

    public void loadImageViewFXML() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ImageView.fxml"));
        Parent parent = loader.load();
        imageViewController = loader.getController();
        imageViewScene = new Scene(parent);
    }

    public void loadDirectoryDisplayFXML() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DirectoryDisplay.fxml"));
        Parent parent = loader.load();
        directoryDisplayController = loader.getController();
        directoryDisplayScene = new Scene(parent);
    }

    public Scene getDirectoryDisplayScene() {
        return directoryDisplayScene;
    }

    public ImageViewController getImageViewController() {
        return imageViewController;
    }

    public DirectoryDisplayController getDirectoryDisplayController() {
        return directoryDisplayController;
    }

    public Scene getImageViewScene() {
        return imageViewScene;
    }
}

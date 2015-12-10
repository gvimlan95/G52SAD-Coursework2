import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class ImageViewApplication extends Application
{
    private static Stage stage;

    public void start(Stage st) throws Exception
    {
        stage = st;
        stage.setTitle("GV Photo Viewer");
        stage.setScene(SceneManager.getInstance().getDirectoryDisplayScene());
        stage.show();
    }

    public static void changeScene(boolean isDirectory) {
        if (isDirectory) {
            stage.setScene(SceneManager.getInstance().getDirectoryDisplayScene());
        } else {
            stage.setScene(SceneManager.getInstance().getImageViewScene());
        }
    }

    public static void main(String[] args) {

        launch(args);
    }

}



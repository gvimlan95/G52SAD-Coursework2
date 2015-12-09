import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class ImageView extends Application
{

   private Scene directoryDisplay;
   private Scene imageView;
   private Stage stage;

   public void start(Stage stage) throws Exception
   {
       this.stage = stage;
       imageView = createImageView();
       directoryDisplay = createDirectoryDisplay();
      stage.setTitle("Vimlan.G Awesome Photo Viewer");
      stage.setScene(directoryDisplay);
      stage.show();
   }

    private Scene createImageView() throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("ImageView.fxml"));

        // Build the scene graph.
        Scene scene = new Scene(parent);
        return scene;
    }

    private Scene createDirectoryDisplay() throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("DirectoryDisplay.fxml"));

        // Build the scene graph.
        Scene scene = new Scene(parent);
        return scene;
    }

    public static void main(String[] args) {

        launch(args);
    }

 }



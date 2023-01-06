import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

public class WordGameClientMain extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    /** start executes the fxml file and loads the window */
    public void start(Stage primaryStage) throws IOException {
        Object myfxml = getClass().getResource("signInMenu.fxml");
        if (myfxml == null) {
            System.out.println("BAD ERROR, File not found");
        }
        else {
            Parent root = FXMLLoader.load((URL) myfxml);
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
    }
}

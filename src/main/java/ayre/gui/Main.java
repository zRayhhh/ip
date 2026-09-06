package ayre.gui;

import java.io.IOException;

import ayre.Ayre;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * A GUI for Ayre using FXML.
 */
public class Main extends Application {

    private Ayre ayre = new Ayre();

    @Override
    public void start(Stage stage) {
        try {
            stage.setMinHeight(220);
            stage.setMinWidth(417);
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.getIcons().add(new Image("/images/Emblem_ACVI_Ayre.png"));
            stage.setTitle("Ayre");
            fxmlLoader.<MainWindow>getController().setAyre(ayre); // inject the Ayre instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

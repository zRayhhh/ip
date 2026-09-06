package ayre.gui;

import java.util.Objects;

import ayre.Ayre;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;

    private Ayre ayre;

    private Image userImage = new Image("/images/Emblem_ACVI_C4-621_Raven.png");
    private Image ayreImage = new Image("/images/Emblem_ACVI_Ayre.png");

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the Ayre instance and adds relevant startup dialog
     */
    public void setAyre(Ayre d) {
        ayre = d;
        dialogContainer.getChildren().addAll(
                DialogBox.getAyreDialog(this.ayre.getLoadMessage(), ayreImage),
                DialogBox.getAyreDialog("""
                <<Main System: Activating Support Mode.>>
                     █████╗ ██╗   ██╗██████╗ ███████╗
                    ██╔══██╗╚██╗ ██╔╝██╔══██╗██╔════╝
                    ███████║ ╚████╔╝ ██████╔╝█████╗
                    ██╔══██║  ╚██╔╝  ██╔══██╗██╔══╝
                    ██║  ██║   ██║   ██║  ██║███████╗
                    ╚═╝  ╚═╝   ╚═╝   ╚═╝  ╚═╝╚══════╝
                    .~"~.__.~"~.__.~"~.__.~"~.__.~"~.
                """, ayreImage),
                DialogBox.getAyreDialog("~ Hello, Raven. What shall we do today?", ayreImage));
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Ayre's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = ayre.getResponse(input);
        if (response.equals("TERMINATE")) {
            Platform.exit();
        }
        // String commandType = ayre.getCommandType();
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                // DialogBox.getAyreDialog(response, dukeImage, commandType)
                DialogBox.getAyreDialog(response, ayreImage)
        );
        userInput.clear();
    }
}

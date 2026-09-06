package ayre.gui;

import ayre.Ayre;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextArea userInput;

    private Ayre ayre;

    private static final Image USER_IMAGE = new Image("/images/Emblem_ACVI_C4-621_Raven.png");
    private static final Image AYRE_IMAGE = new Image("/images/Emblem_ACVI_Ayre.png");

    private static final double MIN_HEIGHT = 30;
    private static final double MAX_HEIGHT = 120;

    @FXML
    public void initialize() {
        dialogContainer.heightProperty().addListener((obs, oldVal, newVal) -> {
            Platform.runLater(() -> scrollPane.setVvalue(1.0));
        });
        userInput.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER && !event.isShiftDown()) {
                event.consume();
                handleUserInput();
            }
        });
        userInput.textProperty().addListener((obs, oldText, newText) -> {
            Platform.runLater(() -> {
                userInput.setPrefHeight(computeContentHeight(userInput));
            });
        });
        userInput.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            Platform.runLater(() -> {
                userInput.setPrefHeight(computeContentHeight(userInput));
            });
        });
    }

    private double computeContentHeight(TextArea textArea) {
        Text helper = new Text(textArea.getText());
        helper.setFont(textArea.getFont());
        helper.setWrappingWidth(textArea.getWidth() - 20);
        double textHeight = helper.getLayoutBounds().getHeight();
        return Math.clamp(textHeight + 24, MIN_HEIGHT, MAX_HEIGHT);
    }

    /**
     * Injects the Ayre instance and adds relevant startup dialog
     *
     * @param ayre Injected instance
     */
    public void setAyre(Ayre ayre) {
        this.ayre = ayre;
        dialogContainer.getChildren().addAll(
                DialogBox.getAyreDialog(this.ayre.getLoadMessage(), AYRE_IMAGE),
                DialogBox.getAyreDialog("""
                <<Main System: Activating Support Mode.>>
                     █████╗ ██╗   ██╗██████╗ ███████╗
                    ██╔══██╗╚██╗ ██╔╝██╔══██╗██╔════╝
                    ███████║ ╚████╔╝ ██████╔╝█████╗
                    ██╔══██║  ╚██╔╝  ██╔══██╗██╔══╝
                    ██║  ██║   ██║   ██║  ██║███████╗
                    ╚═╝  ╚═╝   ╚═╝   ╚═╝  ╚═╝╚══════╝
                    .~"~.__.~"~.__.~"~.__.~"~.__.~"~.
                """, AYRE_IMAGE),
                DialogBox.getAyreDialog("~ Hello, Raven. What shall we do today?", AYRE_IMAGE));
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Ayre's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    private void handleUserInput() {
        String input = userInput.getText();
        String response = ayre.getResponse(input);
        if (response.equals("TERMINATE")) {
            Platform.exit();
        }
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, USER_IMAGE),
                DialogBox.getAyreDialog(response, AYRE_IMAGE)
        );
        userInput.clear();
    }
}

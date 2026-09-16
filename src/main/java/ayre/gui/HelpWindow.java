package ayre.gui;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Displays a short guide explaining how to use Ayre.
 */
public final class HelpWindow {
    private static final double MIN_WINDOW_WIDTH = 650;
    private static final double MIN_WINDOW_HEIGHT = 420;

    private static final String GUIDE_TEXT = """
            ~ Getting started

            Type a command in the message box and press Enter.

            ~ Available commands
            
            Usage of shorthand aliases is specified in the command after the "||" marker.
            
            todo <description> || t <description>
             > Add a todo task.
            
            deadline <description> /by <date> || d <description> /b <date>
             > Add a task with a deadline.
            
            event <description> /from <date> /to <date> || e <description> /f <date> /t <date>
             > Add a task with a start and end <date>.
            
            list || l
             > Show all tasks.
            
            mark <number> || m <number>
             > Mark the <number>th task in the list as done.
            
            unmark <number> || u <number>
             > Mark the <number>th task in the list as not done.
            
            delete <number> || rm <number>
             > Delete the <number>th task in the list.
            
            find <keyword> || f <keyword>
             > Find and list all tasks which description contains <keyword>.
            
            bye || b
             > Exit Ayre.

            * All <date> entries should use the ISO_LOCAL_DATE format of yyyy-mm-dd.
            """;

    private static Stage helpStage;

    private HelpWindow() {
    }

    /**
     * Creates and displays the user guide window.
     */
    public static void show() {
        if (helpStage != null) {
            helpStage.toFront();
            helpStage.requestFocus();
            return;
        }

        helpStage = new Stage();
        Label title = new Label("Ayre User Guide");
        title.setMaxWidth(Double.MAX_VALUE);
        title.getStyleClass().add("help-title");

        Label guide = new Label(GUIDE_TEXT);
        guide.setWrapText(true);
        guide.setMaxWidth(Double.MAX_VALUE);
        guide.getStyleClass().add("help-text");

        ScrollPane scrollPane = new ScrollPane(guide);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.getStyleClass().add("help-scroll-pane");

        VBox content = new VBox(title, scrollPane);
        content.getStyleClass().add("help-window");
        VBox.setVgrow(scrollPane, javafx.scene.layout.Priority.ALWAYS);

        Scene scene = new Scene(content);
        scene.getStylesheets().add(HelpWindow.class.getResource("/css/main.css").toExternalForm());
        helpStage.setTitle("Ayre Help");
        helpStage.setScene(scene);
        helpStage.setMinWidth(MIN_WINDOW_WIDTH);
        helpStage.setMinHeight(MIN_WINDOW_HEIGHT);
        helpStage.setOnHidden(event -> helpStage = null);
        helpStage.show();
        helpStage.sizeToScene();
    }

    /**
     * Closes the help window if it is currently open.
     */
    public static void close() {
        if (helpStage != null) {
            helpStage.close();
        }
    }
}

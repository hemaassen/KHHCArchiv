package application.controller;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class HelpWindowController {

    public static Main main;

    @FXML
    private Button closeHelp;

    @FXML
    private Button closeHelpManual;

    @FXML
    private Button closeHelpAuto;

    @FXML
    private Button closeHelpSearch;

    @FXML
    private Button closeHelpConfig;

    /**
     * Schliesst das Hilfefenster.
     * 
     * @author holger, helge
     * @param event
     *            Button Schliessen wurde geklickt
     */
    @FXML
    void closeHelpWindow(ActionEvent event) {
        Stage stage = (Stage) closeHelp.getScene().getWindow();
        stage.close();
    }

    @FXML
    void setOnMouseEntered(MouseEvent event) {
        main.getPrimarayStage().getScene().setCursor(Cursor.HAND);
    }

    @FXML
    void setOnMouseExited(MouseEvent event) {
        main.getPrimarayStage().getScene().setCursor(Cursor.DEFAULT);
    }

}
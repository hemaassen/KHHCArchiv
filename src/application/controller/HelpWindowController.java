package application.controller;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class HelpWindowController {

  public static Main main;

  @FXML
  private Tab        helpGeneral;

  @FXML
  private Tab        helpManual;

  @FXML
  private Tab        helpAutomatic;

  @FXML
  private Tab        helpSearch;

  @FXML
  private Tab        helbConfig;

  @FXML
  private Button     closeHelp;

  /**
   * Schliesst das Hilfefenster.
   * @author holger, helge
   * @param event Button Schliessen wurde geklickt
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
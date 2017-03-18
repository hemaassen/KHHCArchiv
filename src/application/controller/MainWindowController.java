package application.controller;

import java.io.IOException;
import java.util.List;


import application.Main;
import helper.PaneHelper;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

/**
 * Controller für das Hauptfenster
 * 
 * @author kerstin, helge, chris, holger
 *
 */
public final class MainWindowController {

  public Main        main;

  @FXML
  private AnchorPane anchorMainMain;

  @FXML
  private Button manualStore;

  @FXML
  private Button autoStore;

  @FXML
  private Button search;

  @FXML
  private Button config;

  @FXML
  private Button stop;

  @FXML
  private AnchorPane anchorDetails;

  @FXML
  private Label      labelHeadline;

  @FXML
  private Label      labelSite;
  
  @FXML
  private MenuBar menuBar;
  
  @FXML
  private MenuItem menuBarFileCloseAplication;

  @FXML
  private MenuItem menuBarHelpHelp;

  @FXML
  private MenuItem menuBarHelpFAQ;

  @FXML
  private MenuItem menuBarHelpAbaout;
  
  @FXML
  private Button mainFooterLogo;

  /**
   * @author kerstin
   * @param main
   *          main
   */
  public void setMain(Main main) {
    this.main = main;

    // Ueberschrift wird auf den Wert Archivierung (Version 1.0) gesetzt
    labelHeadline.setText("Archivierung (Version 1.0)");
  }

  /**
   * Aufruf der manuellen Ablage
   * 
   * @author holger, helge
   * @throws IOException
   *           IOException
   * @param event
   *          event
   */
  @FXML
  private void handleManualButtonAction(ActionEvent event) throws IOException {
    /*
     * Auswählbare Buttons werden enabled, nicht auswählbare Buttons werden
     * disabled. Aufruf der Folgeseite über die Zuweisung an pane.
     * Ueberschrift wird auf den Wert "Manuelle Ablage" gesetzt
     */
    manualStore.setDisable(true);
    search.setDisable(false);
    config.setDisable(false);
    AnchorPane pane = FXMLLoader
        .load(getClass().getResource("../fxml/ManualWindow.fxml"));
    ManualWindowController.main=this.main;
    anchorDetails.getChildren().setAll(pane);
    labelSite.setText("Manuelle Ablage");

  }

  /**
   * Aufruf der Dokumentensuche
   * 
   * @author holger, helge
   * @throws IOException
   *           IOException
   * @param event
   *          event
   */
  @FXML
  private void handleSearchButton(ActionEvent event) throws IOException {
    /*
     * Auswählbare Buttons werden enabled, nicht auswählbare Buttons werden
     * disabled. Aufruf der Folgeseite über die Zuweisung an pane.
     * Ueberschrift wird auf den Wert "Dokument suchen" gesetzt
     */
    manualStore.setDisable(false);
    search.setDisable(true);
    config.setDisable(false);
    AnchorPane pane = FXMLLoader
        .load(getClass().getResource("../fxml/SearchWindow.fxml"));
    SearchWindowController.main=this.main;
    anchorDetails.getChildren().setAll(pane);
    labelSite.setText("Dokument suchen");

  }

  /**
   * Aufruf der Einstellungen
   * 
   * @author holger, helge
   * @throws IOException
   *           IOException
   * @param event
   *          event
   */
  @FXML
  private void handleConfigButtonAction(ActionEvent event) throws IOException {
    /*
     * Auswählbare Buttons werden enabled, nicht auswählbare Buttons werden
     * disabled. Aufruf der Folgeseite über die Zuweisung an pane.
     * Ueberschrift wird auf den Wert "Einstellungen" gesetzt
     */
    // ConfigWindowController myController=new ConfigWindowController();
    manualStore.setDisable(false);
    search.setDisable(false);
    config.setDisable(true);
    AnchorPane configPane = FXMLLoader
        .load(getClass().getResource("../fxml/ConfigWindow.fxml"));
    ConfigWindowController.main = this.main;
    List<Node> activeNodes = PaneHelper.activeNodes(configPane);
    int count = 0;
    for (Node n : activeNodes) {
      if (PaneHelper.initializeLabel(n, "labelPathSourceLocation",
          main.getMyConfig().getSourceDir())) {
        count++;
      }
      if (PaneHelper.initializeLabel(n, "labelPathDestinationLocation",
          main.getMyConfig().getDestinationDir())) {
        count++;

      }
      if (count == 2) {
        break;
      }
      anchorDetails.getChildren().setAll(configPane);
      labelSite.setText("Einstellungen");
    }
  }

  /**
   * Beenden der Anwendung
   * 
   * @author kerstin
   */
  @FXML
  private void stopProgram() {
    /*
     * KK 170222 Programm wird beendet Lifecycle einer fx-Anwendung ist:
     * init - start - (warten auf Platform.exit) - stop init und stop sind
     * in Application bereits codiert (leer) und müssen nicht überschrieben
     * werden start muss auscodiert werden Platform.exit() erlaubt noch das
     * Aufrufen von stop System.exit() wäre eine Alternative !!es gibt dann
     * aber keine Möglichkeit auf das Aufrufen von stop()!!
     */
    Platform.exit();
  }

}

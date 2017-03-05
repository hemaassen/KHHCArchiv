package application;

import java.io.IOException;

import application.controller.MainWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import persistence.ConfigTable;

/**
 * Hauptfenster der Anwendung mit Weiterleitungen zur
 * <ul>
 * <li>manuellen Ablage,
 * <li>automatischen Ablage,
 * <li>Dokumentensuche und zu den 
 * <li>Einstellungen
 * </li>
 * </ul>
 * 
 * @author kerstin, helge, chris, holger
 *
 */
public class Main extends Application {

  private Stage primarayStage; // ist das klassische "Fenster" unsere Bühne
  private ConfigTable myConfig;
  public Stage getPrimarayStage() {
    return primarayStage;
  }

  public ConfigTable getMyConfig() {
	return myConfig;
}

public void setMyConfig(ConfigTable myConfig) {
	this.myConfig = myConfig;
}

@Override
  public void start(Stage primaryStage) {
	  myConfig= new ConfigTable();
	  this.primarayStage = primaryStage;
	  primarayStage.setTitle("Archivierung V 1.0");
    mainWindow();
  }

  public void mainWindow() {
    try {
      FXMLLoader loader = new FXMLLoader(
          Main.class.getResource("fxml/MainWindow.fxml"));
      // es muss hier der äußerste Container geladen werden hier SplitPane
      AnchorPane pane = loader.load(); // der Container in dem alle Elemente
                                       // liegen

      // Was wird hier gespielt - die erste Szene = das Fenster im Urzustand
      Scene scene = new Scene(pane);

      // kopplung des Fensters (Bühne) mit der Szene
      primarayStage.setScene(scene);
      // der Vorgang geht auf

      // und hier steht das Drehbuch
      MainWindowController mainWindowController = loader.getController();
      // dem Drehbuch mitteilen zu welchem St�ck es gehört
      mainWindowController.setMain(this);
      primarayStage.show();
    } catch (IOException e) {
      // TODO Automatisch generierter Erfassungsblock
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    launch(args);
  }

}

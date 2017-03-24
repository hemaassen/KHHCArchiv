package application;

import java.io.IOException;

import application.controller.MainWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import persistence.ConfigTable;

/**
 * Hauptfenster der Anwendung mit Weiterleitungen zur
 * <ul>
 * <li>manuellen Ablage,
 * <li>automatischen Ablage,
 * <li>Dokumentensuche und zu den
 * <li>Einstellungen</li>
 * </ul>
 * 
 * @author kerstin, helge, chris, holger
 *
 */
public class Main extends Application {

	private Stage primaryStage; // ist das klassische "Fenster" unsere Bühne
	private ConfigTable myConfig;
	private String title="Archivierung V 1.0";
	private String lastScene;
	
	public String getLastScene() {
		return lastScene;
	}

	public void setLastScene(String lastScene) {
		this.lastScene = lastScene;
	}

	public String getTitle() {
		return title;
	}

	public Stage getPrimarayStage() {
		return primaryStage;
	}

	public ConfigTable getMyConfig() {
		return myConfig;
	}

	public void setMyConfig(ConfigTable myConfig) {
		this.myConfig = myConfig;
	}

	@Override
	public void start(Stage primarayStage) {
		myConfig = new ConfigTable();
		this.primaryStage = primarayStage;
		primarayStage.setTitle(getTitle());
		primarayStage.getIcons().add(new Image("file:resources/images/archiv_icon.png"));
		mainWindow();
	}

	public void mainWindow() {
		try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("fxml/MainWindow.fxml"));
			// es muss hier der äußerste Container geladen werden hier AnchorPane
			AnchorPane pane = loader.load(); // der Container in dem alle Elemente liegen
			// Was wird hier gespielt - die erste Szene = das Fenster im Urzustand
			Scene scene = new Scene(pane);
			// kopplung des Fensters (Bühne) mit der Szene
			primaryStage.setScene(scene);
			// und hier steht das Drehbuch
			MainWindowController mainWindowController = loader.getController();
			// dem Drehbuch mitteilen zu welchem Stück es gehört
			mainWindowController.setMain(this);
			// der Vorgang geht auf
			primaryStage.show();
		} catch (IOException e) {
			// TODO Automatisch generierter Erfassungsblock
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}

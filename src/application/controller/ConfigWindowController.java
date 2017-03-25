package application.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import application.Main;
import helper.FileHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import persistence.ConfigTable;

/**
 * Controller für das Fenster der Einstellungen
 * 
 * @author kerstin, helge, chris, holger
 *
 */
public class ConfigWindowController implements Initializable {

	public static Main main;

	private ConfigTable con = new ConfigTable();

	@FXML
	private Button searchDestinationLocation;

	@FXML
	private Label labelPathSourceLocation;

	@FXML
	private Label labelLocation;

	@FXML
	private Button searchSourceLocation;

	@FXML
	private Label labelPathDestinationLocation;

	@FXML
	private Label labelStoreType;

	@FXML
	private RadioButton radioButtonStoreManual;

	@FXML
	private RadioButton radioButtonStoreAuto;

	@FXML
	private Label labelScannerChoice;

	@FXML
	private ChoiceBox<?> listScanner;

	@FXML
	private Button abort;

	@FXML
	private Button takeChange;

	@FXML
	private RadioButton fileNameAuto;

	@FXML
	private RadioButton fileNameManual;

	@FXML
	void setOnMouseEntered(MouseEvent event) {
		main.getPrimarayStage().getScene().setCursor(Cursor.HAND);
	}

	@FXML
	void setOnMouseExited(MouseEvent event) {
		main.getPrimarayStage().getScene().setCursor(Cursor.DEFAULT);
	}

	@FXML
	void handleSearchDestinationLocation(ActionEvent event) {
		String title = "Bitte wählen Sie das WurzelVerzeichnis aus, in das Ihre Dateien abgelegt werden sollen";
		useDirChooser(labelPathDestinationLocation, title);
		takeChange.setDisable(false);
	}

	@FXML
	void handleSearchSourceLocation(ActionEvent event) {
		String title = "Bitte wählen Sie das Verzeichnis aus, in dem die zu archivierenden Dateien liegen";
		useDirChooser(labelPathSourceLocation, title);
		takeChange.setDisable(false);
	}

	@FXML
	void handleTakeChange(ActionEvent event) {
		File source = new File(labelPathSourceLocation.getText());
		File destination = new File(labelPathDestinationLocation.getText());
		main.getMyConfig().setDestinationDir(labelPathDestinationLocation.getText());
		main.getMyConfig().setSourceDir(labelPathSourceLocation.getText());
		if (source.exists() && destination.exists()) {
			con.insertConfig(labelPathSourceLocation.getText(), labelPathDestinationLocation.getText(), "true"); // Version
			takeChange.setDisable(true);
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

	 private void useDirChooser(Label lbl, String title) {
	        /*
	         * kerstin: damit nicht mehrmals faktisch derselbe Code dasteht -> eigene Methode. Es wird
	         * ein Directory ausgewählt und das Ergebnis in das entsprechende Label geschrieben. Bricht
	         * der Anwender die Auswahl ab, passiert nix.
	         */
	        DirectoryChooser dirChooser = new DirectoryChooser();
	        File myFile = new File(lbl.getText());
	        if (myFile.exists() && myFile.isDirectory()) {
	            dirChooser.setInitialDirectory(myFile);
	        }
	        dirChooser.setTitle(title);
	        // dir wird gelesen
	        myFile = dirChooser.showDialog(lbl.getScene().getWindow());
	        if (myFile != null) {
	            // Bei null hätte der User die Auswahl abgebrochen und es würde
	            // nullpointerException geben.
	            // Hier soll verhindert werden das ein Verzeichnis eingestellt wird auf das der User
	            // keinen schreibenden Zugriff hat.
	            if (FileHelper.isDirWriteable(myFile)) {
	                //mache einene Eintrag im Label wenn DU schreiben kannst
	                lbl.setText(myFile.getAbsolutePath());
	            } else {
	                //mache nichts mit dem Label wenn Du nicht schreiben kannst
	                //der alte Wert sollte bleiben
	                Alert dialog = new Alert(AlertType.ERROR);
	                dialog.setTitle("Rechteproblem");
	                dialog.setContentText("Auf diesem Ordner fehlen Ihnen Lese- oder Schreibrechte."
	                        + " Bitte wählen Sie einen Anderen.");
	                dialog.showAndWait();
	                myFile = null;
	            }
	        }
	    }

}

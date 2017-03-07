package application.controller;

import java.io.File;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import helper.PDFHelper;

/**
 * Controller für das Fenster zur manuellen Ablage
 * 
 * @author kerstin, helge, chris, holger
 *
 */
public class ManualWindowController {

	public static Main main;

	@FXML
	private AnchorPane anchorMain;

	@FXML
	private Label labelActualDoc;

	@FXML
	private Button searchDoc;

	@FXML
	private Label labelPath;

	@FXML
	private ImageView imageActualDoc;

	@FXML
	private Label labelKeywords;

	@FXML
	private DatePicker datePicker;

	@FXML
	private Label labelDate;

	@FXML
	private Label labelKeywordOne;

	@FXML
	private Label labelKeywordTwo;

	@FXML
	private Label labelKeywordtThree;

	@FXML
	private Label labelKeywordFour;

	@FXML
	private Label labelKeywordFive;

	@FXML
	private Button save;

	@FXML
	private ComboBox<String> listKeywordOne;

	@FXML
	private ComboBox<String> listKeywordTwo;

	@FXML
	private ComboBox<String> listKeywordThree;

	@FXML
	private ComboBox<String> listKeywordFour;

	@FXML
	private ComboBox<String> listKeywordFive;

	@FXML
	private Button zoomPlus;

	@FXML
	private Button zoomMinus;

	/**
	 * Suche des manuell abzulegenden Dokuments <br>
	 * Folgende Formate können gelesen und verarbeitet werden:
	 * <ul>
	 * <li>PNG,
	 * <li>JPG, JPEG,
	 * <li>GIF,
	 * <li>BMP,
	 * <li>PDF</li>
	 * </ul>
	 * 
	 * @author kerstin
	 */
	@FXML
	public void handleSearchDoc() {

		// noch fest codiert, wenn wir eine Datenbankanbindung haben sollte
		// hier der Wert von config.rootDir
		File sourceDir = new File("C:");
		// vordefinierte Klasse zur Dateiauswahl
		FileChooser fileChooser = new FileChooser();
		// grundkonfiguration
		fileChooser.setTitle("Bitte gewünschte Datei auswählen");
		fileChooser.setInitialDirectory(sourceDir);
		fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp"),
				new ExtensionFilter("PDF Files", "*.pdf"));

		// das wird unsere Datei
		File myFile;
		Image fxImage = null;
		Boolean isPdf;
		// file wird gelesen
		myFile = fileChooser.showOpenDialog(labelPath.getScene().getWindow());
		if (myFile != null) {
			// Path-angaben ausgeben
			labelPath.setText(myFile.getPath());
			labelPath.setVisible(true);

			// überprüfung ob die Datei ein Pdf ist
			isPdf = myFile.getName().toString().endsWith(".pdf");

			// ausgewählte Datei anzeigen
			try {
				if (isPdf) {
					fxImage = PDFHelper.convertAwtToFx(PDFHelper.convertPdfToAwt(myFile));
					imageActualDoc.setImage(fxImage);
				} else {
					Image myImage = new Image(myFile.toURI().toURL().toExternalForm(), 595.0, 842.0, false, true);
					System.out.println((myImage).heightProperty());
					imageActualDoc.setImage(myImage);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		zoomPlus.setDisable(false);
		zoomMinus.setDisable(false);
	}

	/**
	 * hier kommt rein was die methode macht....
	 * @author helge
	 * @param event
	 */
	@FXML
	void inputManualDate(ActionEvent event) {

		if (datePicker.getValue() != null) {
			listKeywordOne.setDisable(false);
		} else {
			listKeywordOne.setValue("");
			listKeywordTwo.setValue("");
			listKeywordThree.setValue("");
			listKeywordFour.setValue("");
			listKeywordFive.setValue("");
			listKeywordOne.setDisable(true);
			listKeywordTwo.setDisable(true);
			listKeywordThree.setDisable(true);
			listKeywordFour.setDisable(true);
			listKeywordFive.setDisable(true);
			save.setDisable(true);
		}
	}

	@FXML
	void inputManualKeywordOne(ActionEvent event) {
		if (listKeywordOne.getValue().length() > 0) {
			listKeywordOne.setDisable(false);
			listKeywordTwo.setDisable(false);
			save.setDisable(false);
		} else {
			listKeywordTwo.setValue(null);
			listKeywordThree.setValue(null);
			listKeywordFour.setValue(null);
			listKeywordFive.setValue(null);
			listKeywordTwo.setDisable(true);
			listKeywordThree.setDisable(true);
			listKeywordFour.setDisable(true);
			listKeywordFive.setDisable(true);
			save.setDisable(true);
		}
	}

	@FXML
	void inputManualKeywordOneChanged(InputMethodEvent event) {
		if (listKeywordOne.getValue() != null) {
			listKeywordOne.setDisable(false);
			listKeywordTwo.setDisable(false);
			save.setDisable(false);
		} else {
			listKeywordTwo.setValue(null);
			listKeywordThree.setValue(null);
			listKeywordFour.setValue(null);
			listKeywordFive.setValue(null);
			listKeywordTwo.setDisable(true);
			listKeywordThree.setDisable(true);
			listKeywordFour.setDisable(true);
			listKeywordFive.setDisable(true);
			save.setDisable(true);
		}
	}

	@FXML
	void inputManualKeywordTwo(ActionEvent event) {
		if (listKeywordTwo.getValue().length() > 0) {
			listKeywordOne.setDisable(false);
			listKeywordTwo.setDisable(false);
			listKeywordThree.setDisable(false);
		} else {
			listKeywordThree.setValue("");
			listKeywordFour.setValue("");
			listKeywordFive.setValue("");
			listKeywordThree.setDisable(true);
			listKeywordFour.setDisable(true);
			listKeywordFive.setDisable(true);
		}
	}

	@FXML
	void inputManualKeywordThree(ActionEvent event) {
		if (listKeywordThree.getValue().length() > 0) {
			listKeywordOne.setDisable(false);
			listKeywordTwo.setDisable(false);
			listKeywordThree.setDisable(false);
			listKeywordFour.setDisable(false);
		} else {
			listKeywordFour.setValue("");
			listKeywordFive.setValue("");
			listKeywordFour.setDisable(true);
			listKeywordFive.setDisable(true);
		}
	}

	@FXML
	void inputManualKeywordFour(ActionEvent event) {
		if (listKeywordFour.getValue().length() > 0) {
			listKeywordOne.setDisable(false);
			listKeywordTwo.setDisable(false);
			listKeywordThree.setDisable(false);
			listKeywordFour.setDisable(false);
			listKeywordFive.setDisable(false);
		} else {
			listKeywordFive.setValue("");
			listKeywordFive.setDisable(true);
		}
	}

}
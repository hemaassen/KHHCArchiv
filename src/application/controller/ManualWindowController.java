package application.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import application.KeyWord;
import application.Main;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import persistence.KeywordTable;
import sun.nio.ch.SelChImpl;
import helper.PDFHelper;

/**
 * Controller für das Fenster zur manuellen Ablage
 * 
 * @author kerstin, helge, chris, holger
 *
 */
public class ManualWindowController implements Initializable {

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
	private ComboBox<KeyWord> listKeywordOne;

	@FXML
	private ComboBox<KeyWord> listKeywordTwo;

	@FXML
	private ComboBox<KeyWord> listKeywordThree;

	@FXML
	private ComboBox<KeyWord> listKeywordFour;

	@FXML
	private ComboBox<KeyWord> listKeywordFive;

	@FXML
	private Button zoomPlus;

	@FXML
	private Button zoomMinus;

	KeyWord selectedKeywordOne;
	KeyWord selectedKeyWordTwo;
	ObservableList<KeyWord> olLevel1 = KeywordTable.selectLevel(1);

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

		File sourceDir = new File(main.getMyConfig().getSourceDir());
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
	 * 
	 * @author helge
	 * @param event
	 */
	@FXML
	void inputManualDate(ActionEvent event) {

		if (datePicker.getValue() != null) {
			listKeywordOne.setDisable(false);
		} else {
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
		if (listKeywordOne.getValue() !=null ) {
		// listKeywordOne.setDisable(false);
		listKeywordTwo.setDisable(false);
		save.setDisable(false);
		 } else {
		// listKeywordTwo.setValue(null);
		// listKeywordThree.setValue(null);
		// listKeywordFour.setValue(null);
		// listKeywordFive.setValue(null);
		// listKeywordTwo.setDisable(true);
		// listKeywordThree.setDisable(true);
		// listKeywordFour.setDisable(true);
		// listKeywordFive.setDisable(true);
		 save.setDisable(true);
		 }

		try {
			selectedKeywordOne = listKeywordOne.getValue();
			if (selectedKeywordOne.getKeyword().equals("Neuer Eintrag..")) {
				listKeywordTwo.setDisable(true);
				listKeywordTwo.setItems(null);
				save.setDisable(true);
				System.out.println("jetzt muss der dialog auf");
			} else {
				System.out.println("children vom gewählten Eintrag müssen ermittelt und zugewiesen werden");
				listKeywordTwo.setItems(KeywordTable.getChildren(selectedKeywordOne.getId()));
			}
			System.out.println("level 1 "+ selectedKeywordOne.getId() + " " + selectedKeywordOne.getKeyword());
			
		} catch (Exception e) {
			//da der geworfene Fehler völlig sinnlos ist,
			//fangen wir ihn und ignorieren ihn
			
//			System.out.println("Fehlergrund: " + e.getCause());
//			System.out.println("Fehlermeldung: " + e.getMessage());
//			System.out.println("Fehlerlokalisiertemeldung: " + e.getLocalizedMessage());
		}

	}

	@FXML
	void inputManualKeywordOneChanged(InputMethodEvent event) {
		System.out.println("inputManualKeywordOneChanged");
		// if (listKeywordOne.getValue() != null) {
		// listKeywordOne.setDisable(false);
		// listKeywordTwo.setDisable(false);
		// save.setDisable(false);
		// } else {
		// listKeywordTwo.setValue(null);
		// listKeywordThree.setValue(null);
		// listKeywordFour.setValue(null);
		// listKeywordFive.setValue(null);
		// listKeywordTwo.setDisable(true);
		// listKeywordThree.setDisable(true);
		// listKeywordFour.setDisable(true);
		// listKeywordFive.setDisable(true);
		// save.setDisable(true);
		// }
	}

	@FXML
	void inputManualKeywordTwo(ActionEvent event) {
		if (listKeywordTwo.getValue()!=null) {
			listKeywordThree.setDisable(false);
		} else {
//			listKeywordThree.setDisable(true);
//			listKeywordFour.setDisable(true);
//			listKeywordFive.setDisable(true);
		}
		try {
			selectedKeyWordTwo = listKeywordTwo.getValue();
			if (selectedKeyWordTwo.getKeyword().equals("Neuer Eintrag..")) {
				System.out.println("jetzt muss der dialog auf");
			} else {
				System.out.println("children vom gewählten Eintrag müssen ermittelt und zugewiesen werden");
				listKeywordThree.setItems(KeywordTable.getChildren(selectedKeyWordTwo.getId()));
			}
			System.out.println("level 2 "+selectedKeyWordTwo.getId() + " " + selectedKeyWordTwo.getKeyword());
		} catch (Exception e) {
			//da der geworfene Fehler völlig sinnlos ist,
			//fangen wir ihn und ignorieren ihn
			
//			System.out.println("Fehlergrund: " + e.getCause());
//			System.out.println("Fehlermeldung: " + e.getMessage());
//			System.out.println("Fehlerlokalisiertemeldung: " + e.getLocalizedMessage());
		}
	}

	@FXML
	void inputManualKeywordThree(ActionEvent event) {
//		if (listKeywordThree.getValue().length() > 0) {
//			listKeywordOne.setDisable(false);
//			listKeywordTwo.setDisable(false);
//			listKeywordThree.setDisable(false);
//			listKeywordFour.setDisable(false);
//		} else {
//			listKeywordFour.setValue("");
//			listKeywordFive.setValue("");
//			listKeywordFour.setDisable(true);
//			listKeywordFive.setDisable(true);
//		}
	}

	@FXML
	void inputManualKeywordFour(ActionEvent event) {
//		if (listKeywordFour.getValue().length() > 0) {
//			listKeywordOne.setDisable(false);
//			listKeywordTwo.setDisable(false);
//			listKeywordThree.setDisable(false);
//			listKeywordFour.setDisable(false);
//			listKeywordFive.setDisable(false);
//		} else {
//			listKeywordFive.setValue("");
//			listKeywordFive.setDisable(true);
//		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		listKeywordOne.setItems(KeywordTable.selectLevel(1));

	}

}
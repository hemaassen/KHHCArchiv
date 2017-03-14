package application.controller;

import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import application.KeyWord;
import application.Main;
import helper.ZoomHelper;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import persistence.KeywordTable;
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
	private Label labelKeywordThree;

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

	@FXML
	private ScrollPane imageScrollPane;

	@FXML
	private JFXButton changeKeywordOne;

	@FXML
	private JFXButton changeKeywordTwo;

	@FXML
	private JFXButton changeKeywordThree;

	@FXML
	private JFXButton changeKeywordFour;

	@FXML
	private JFXButton changeKeywordFive;

	KeyWord selectedKeywordOne;
	KeyWord selectedKeywordTwo;
	KeyWord selectedKeywordThree;
	KeyWord selectedKeywordFour;
	KeyWord selectedKeywordFive;
	ObservableList<KeyWord> olLevel1 = KeywordTable.selectLevel(1);
	ObservableList<KeyWord> olLevel2 = KeywordTable.selectLevel(2);
	ObservableList<KeyWord> olLevel3 = KeywordTable.selectLevel(3);
	ObservableList<KeyWord> olLevel4 = KeywordTable.selectLevel(4);
	ObservableList<KeyWord> olLevel5 = KeywordTable.selectLevel(5);

	final DoubleProperty zoomProperty = new SimpleDoubleProperty(200);

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
	void onChangeKeywordOne(ActionEvent event) {

	}

	@FXML
	void onChangeKeywordTwo(ActionEvent event) {

	}

	@FXML
	void onChangeKeywordThree(ActionEvent event) {

	}

	@FXML
	void onChangeKeywordFour(ActionEvent event) {

	}

	@FXML
	void onChangeKeywordFive(ActionEvent event) {

	}

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

			// ausgewählte Datei anzeigen mit Zoommöglichkeit über Mausrad
			try {
				if (isPdf) {
					fxImage = PDFHelper.convertAwtToFx(PDFHelper.convertPdfToAwt(myFile));
					zoomProperty.set(200);
					imageActualDoc.setImage(
							ZoomHelper.zoomMouse(fxImage, imageActualDoc, anchorMain, imageScrollPane, zoomProperty));
				} else {
					Image myImage = new Image(myFile.toURI().toURL().toExternalForm(), 595.0, 842.0, false, true);
					zoomProperty.set(200);
					imageActualDoc.setImage(
							ZoomHelper.zoomMouse(myImage, imageActualDoc, anchorMain, imageScrollPane, zoomProperty));
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
		zoomPlus.setDisable(false);
		zoomMinus.setDisable(false);
	}

	/**
	 * Zwei Methoden für Zoom In und Zoom Out per Mausklick auf die
	 * entsprechenden Buttons
	 * 
	 * @author kerstin, helge, chris, holger
	 * @param event
	 * @throws Exception
	 */
	@FXML
	void onClickZoomIn(MouseEvent event) throws Exception {
		ZoomHelper.zoomIn(event, zoomProperty);
	}

	@FXML
	void onClickZoomOut(MouseEvent event) throws Exception {
		ZoomHelper.zoomOut(event, zoomProperty);
	}

	/**
	 * Auswahl eines Datums -ohne Datum darf kein Schlüsselwort ausgewählt oder
	 * hinzugefügt werden und eine Speicherung findet nicht statt
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
		
		
		listKeywordOne.setValue(inputManualKeyword(listKeywordOne, listKeywordTwo, 1, 1));
		
	}

	@FXML
	void inputManualKeywordTwo(ActionEvent event) {
		listKeywordTwo
				.setValue(inputManualKeyword(listKeywordTwo, listKeywordThree, 2, listKeywordOne.getValue().getId()));

	}

	@FXML
	void inputManualKeywordThree(ActionEvent event) {
		listKeywordThree
				.setValue(inputManualKeyword(listKeywordThree, listKeywordFour, 3, listKeywordTwo.getValue().getId()));
	}

	@FXML
	void inputManualKeywordFour(ActionEvent event) {
		listKeywordFour
				.setValue(inputManualKeyword(listKeywordFour, listKeywordFive, 4, listKeywordThree.getValue().getId()));
	}

	@FXML
	void inputManualKeywordFive(ActionEvent event) {
		listKeywordFive.setValue(inputManualKeyword(listKeywordFive, null, 5, listKeywordFour.getValue().getId()));
	}

	/**
	 * öffnet die Inputbox für ein neues Schlüsselwort und speichert es mit
	 * seinen Beziehungen in der Datenbank
	 * 
	 * @author Kerstin
	 * @param myLevel
	 *            = das eigene Level des Schlüsselwortes
	 * @param myParentID
	 *            = die Id des ElternSchlüsselwortes (liefert die Combobox
	 *            davor)
	 */
	KeyWord newKeywordDialog(int myLevel, Integer myParentID) {
		KeyWord k = new KeyWord();
		try {
			TextInputDialog dialog = new TextInputDialog();

			dialog.setTitle("Neuer Eintrag");
			dialog.setHeaderText("Bitte geben Sie das neue Schlüsselwort ein");
			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()) {
				String s = result.get();
				if (s != null) {
					k.setId(KeywordTable.getHighestID() + 1);
					k.setKeyword(s);
					k.setPath(s);
					k.setLevel(myLevel);
					k.setParent(myParentID);
					KeywordTable.insertKeyword(k);
				}

			} else {
				k = null;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return k;
	}

	KeyWord inputManualKeyword(ComboBox<KeyWord> myBox, ComboBox<KeyWord> myChild, int level, Integer myParentId) {
		final KeyWord myWord = myBox.getValue();
		
		if (myBox.getValue() != null) {
			// in der box ist ein Wert ausgewählt
			// die darunterliegende Combobox wird aktiv geschalten
			if (myChild != null) {
				myChild.setDisable(false);
			}
			// der Speicherknopf wird aktiv geschalten
			save.setDisable(false);
		} else {
			// es ist nichts ausgewäht - der Speicherknopf wird deaktiviert
			save.setDisable(true);
		}
		try {
			// in myBox steht der gewählte Wert
			if (myWord.getKeyword().equals("Neuer Eintrag..")) {
				if(myWord.getId()!=1){
					myWord.setId(1);
				}
				// in der Box steht "Neuer Eintrag.."
				Platform.runLater(new Runnable() {
					// wird nur ausgeführt wenn ein Neuer Eintrag gewählt wurde
					@Override
					public void run() {
						System.out.println(myWord.getId());
						//if (myWord.getKeyword().equals("Neuer Eintrag..")) {
						if (myWord.getId() == 1) {
							// das neue Keyword wird geholt
							KeyWord newKeyWord = newKeywordDialog(level, myParentId);
							if (newKeyWord != null) {
								// dialog wurde nicht abgebrochen
								// neues Keyword an die Liste anfügen
								myBox.getItems().add(newKeyWord);
								// Liste sortieren
								Collections.sort(myBox.getItems());
								// die ComboBox auf den neuen Wert setzen
								myBox.setValue(newKeyWord);
								//Id muss auf den neuen Wert "verbogen" werden, damit es keine Doppel gibt
								myWord.setId(myBox.getValue().getId());
								

							}
						}
					}
				});
			} else {
				if (myChild != null) {
					myChild.setItems(KeywordTable.getChildren(myWord.getId()));
				}
			}
		} catch (Exception e) {
			// da der geworfene Fehler völlig sinnlos ist,
			// fangen wir ihn und ignorieren ihn

			// System.out.println("Fehlergrund: " + e.getCause());
			// System.out.println("Fehlermeldung: " + e.getMessage());
		}
		return myWord;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// olLevel1.addAll(KeywordTable.selectLevel(1));
		// listTest.setItems(listData);
		listKeywordOne.setItems(olLevel1);

	}
}

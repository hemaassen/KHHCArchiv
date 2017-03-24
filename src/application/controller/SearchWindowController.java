package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.KeyWord;
import application.Main;
import helper.EditKeywordHelper;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import persistence.KeywordTable;

/**
 * Controller für das Fenster zur Dokumentensuche
 * 
 * @author kerstin, helge, chris, holger
 *
 */
public class SearchWindowController implements Initializable {

	public static Main main;
	@FXML
	private DatePicker dateFrom;

	@FXML
	private DatePicker dateTill;

	@FXML
	private ComboBox<KeyWord> listSearchKeywordOne;

	@FXML
	private ComboBox<KeyWord> listSearchKeywordTwo;

	@FXML
	private ComboBox<KeyWord> listSearchKeywordThree;

	@FXML
	private ComboBox<KeyWord> listSearchKeywordFour;

	@FXML
	private ComboBox<KeyWord> listSearchKeywordFive;

	@FXML
	private Button buttonSearch;

	@FXML
	private ListView<String> listResult;

	@FXML
	private Button zoomPlus;

	@FXML
	private Button zoomMinus;

	@FXML
	private Button print;

	@FXML
	private Button send;

	@FXML
	private ScrollPane imageScrollPane;

	@FXML
	private ImageView choosenDoc;

	@FXML
	private Button newStore;

	
	private int[] searchPathArray = new int[5];
	private EditKeywordHelper editKeyword5;
	private EditKeywordHelper editKeyword4;
	private EditKeywordHelper editKeyword3;
	private EditKeywordHelper editKeyword2;
	private EditKeywordHelper editKeyword1;

	@FXML
	void setOnMouseEntered(MouseEvent event) {
		main.getPrimarayStage().getScene().setCursor(Cursor.HAND);
	}

	@FXML
	void setOnMouseExited(MouseEvent event) {
		main.getPrimarayStage().getScene().setCursor(Cursor.DEFAULT);
	}

	/**
	 * @author helge
	 * @param event
	 */

	@FXML
	void inputFromDate(ActionEvent event) {
		if (dateFrom.getValue() != null) {
			buttonSearch.setDisable(false);
		} else {
			buttonSearch.setDisable(true);
		}
	}

	@FXML
	void inputTillDate(ActionEvent event) {
		if (dateTill.getValue() != null) {
			buttonSearch.setDisable(false);
		} else {
			buttonSearch.setDisable(true);
		}
	}

	@FXML
	void listSearchKeywordOne(ActionEvent event) {
		if (listSearchKeywordOne.getValue() != null && listSearchKeywordOne.getValue().getKeyword().length() >0) {
				buttonSearch.setDisable(false);
				dateFrom.setDisable(false);
				dateTill.setDisable(false);
			} else {
				buttonSearch.setDisable(true);
				dateFrom.setDisable(true);
				dateTill.setDisable(true);
			}
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			editKeyword5 = new EditKeywordHelper(listSearchKeywordFive, listSearchKeywordFour, null, 5, true);
			editKeyword4 = new EditKeywordHelper(listSearchKeywordFour, listSearchKeywordThree, editKeyword5, 4, true);
			editKeyword3 = new EditKeywordHelper(listSearchKeywordThree, listSearchKeywordTwo, editKeyword4, 3, true);
			editKeyword2 = new EditKeywordHelper(listSearchKeywordTwo, listSearchKeywordOne, editKeyword3, 2, true);
			editKeyword1 = new EditKeywordHelper(listSearchKeywordOne,  null, editKeyword2, 1, true);
			listSearchKeywordOne.setItems(KeywordTable.selectLevel(1, true));
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	/**
	 * Alle Level (Ebenen) welche sich unterhalb des per Parameter übergebenen
	 * befinden, werden initialisiert.
	 * 
	 * @author holger
	 * @param level
	 *            Übergabe der aktuellen Ebene.
	 */
	void initializeChildren(int level) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					switch (level) {
					case 1:
						listSearchKeywordTwo.setValue(null);
						listSearchKeywordTwo.setDisable(true);
						listSearchKeywordThree.setValue(null);
						listSearchKeywordThree.setDisable(true);
						listSearchKeywordFour.setValue(null);
						listSearchKeywordFour.setDisable(true);
						listSearchKeywordFive.setValue(null);
						listSearchKeywordFive.setDisable(true);
						searchPathArray[1] = -1;
						searchPathArray[2] = -1;
						searchPathArray[3] = -1;
						searchPathArray[4] = -1;
						break;
					case 2:
						listSearchKeywordThree.setValue(null);
						listSearchKeywordFour.setValue(null);
						listSearchKeywordFour.setDisable(true);
						listSearchKeywordFive.setValue(null);
						listSearchKeywordFive.setDisable(true);
						searchPathArray[2] = -1;
						searchPathArray[3] = -1;
						searchPathArray[4] = -1;
						break;
					case 3:
						listSearchKeywordFour.setValue(null);
						listSearchKeywordFive.setValue(null);
						listSearchKeywordFive.setDisable(true);
						searchPathArray[3] = -1;
						searchPathArray[4] = -1;
						break;
					case 4:
						listSearchKeywordFive.setValue(null);
						searchPathArray[4] = -1;
						break;
					default:
						break;
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		});
	}

	/**
	 * Bisher werden durch anklicken des Suchen-Buttons nur die ausgwählten Ids
	 * der Schlüsselworte angezeigt. <br>
	 * Der Wert -1 bedeutet dass keine Auswahl auf dieser Ebene getroffen wurde
	 * und wird somit überlesen.
	 * 
	 * @author holger
	 * @param event
	 *            *
	 */
	@FXML
	void searchContent(ActionEvent event) {
		// suche ausführen.....
		for (Integer ia : searchPathArray) {
			if (ia > 0)
				System.out.println(ia);
		}
		System.out.println("----");
	}

}

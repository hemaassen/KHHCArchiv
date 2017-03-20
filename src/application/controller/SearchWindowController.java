package application.controller;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * Controller für das Fenster zur Dokumentensuche
 * 
 * @author kerstin, helge, chris, holger
 *
 */
public class SearchWindowController {

	public static Main main;
	@FXML
	private DatePicker dateFrom;

	@FXML
	private DatePicker dateTill;

	@FXML
	private ComboBox<?> listSearchKeywordOne;

	@FXML
	private ComboBox<?> listSearchKeywordTwo;

	@FXML
	private ComboBox<?> listSearchKeywordThree;

	@FXML
	private ComboBox<?> listSearchKeywordFour;

	@FXML
	private ComboBox<?> listSearchKeywordFive;

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
	private ImageView choosenDoc;

	@FXML
	private Button newStore;

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

	}

	@FXML
	void listSearchKeywordTwo(ActionEvent event) {

	}

	@FXML
	void listSearchKeywordThree(ActionEvent event) {

	}

	@FXML
	void listSearchKeywordFour(ActionEvent event) {

	}

	@FXML
	void listSearchKeywordFive(ActionEvent event) {

	}
}

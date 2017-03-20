package application.controller;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
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
	private ChoiceBox<String> listSearchKeywordOne;

	@FXML
	private ChoiceBox<String> listSearchKeywordTwo;

	@FXML
	private ChoiceBox<String> listSearchKeywordThree;

	@FXML
	private ChoiceBox<String> listSearchKeywordFour;

	@FXML
	private ChoiceBox<String> listSearchKeywordFive;

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
	void inputKeywordOneTextChange(InputMethodEvent event) {
		if (listSearchKeywordOne.getValue().length() > 0) {
			buttonSearch.setDisable(false);
		} else {
			buttonSearch.setDisable(true);
		}

	}

	@FXML
	void inputKeywordTwoTextChange(InputMethodEvent event) {
		if (listSearchKeywordTwo.getValue().length() > 0) {
			buttonSearch.setDisable(false);
		} else {
			buttonSearch.setDisable(true);
		}
	}

	@FXML
	void inputKeywordThreeTextChange(InputMethodEvent event) {
		if (listSearchKeywordThree.getValue().length() > 0) {
			buttonSearch.setDisable(false);
		} else {
			buttonSearch.setDisable(true);
		}
	}

	@FXML
	void inputKeywordFourTextChange(InputMethodEvent event) {
		if (listSearchKeywordFour.getValue().length() > 0) {
			buttonSearch.setDisable(false);
		} else {
			buttonSearch.setDisable(true);
		}
	}

	@FXML
	void inputKeywordFiveTextChange(InputMethodEvent event) {
		if (listSearchKeywordFive.getValue().length() > 0) {
			buttonSearch.setDisable(false);
		} else {
			buttonSearch.setDisable(true);
		}
	}
}

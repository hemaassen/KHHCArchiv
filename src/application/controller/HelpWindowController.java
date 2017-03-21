package application.controller;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseEvent;

public class HelpWindowController {

	public static Main main;

	@FXML
	private Tab helpGeneral;

	@FXML
	private Tab helpManual;

	@FXML
	private Tab helpAutomatic;

	@FXML
	private Tab helpSearch;

	@FXML
	private Tab helbConfig;

	@FXML
	void setOnMouseEntered(MouseEvent event) {
		main.getPrimaryStage().getScene().setCursor(Cursor.HAND);
	}

	@FXML
	void setOnMouseExited(MouseEvent event) {
		main.getPrimaryStage().getScene().setCursor(Cursor.DEFAULT);
	}

}
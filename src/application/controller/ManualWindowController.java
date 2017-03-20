package application.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import application.KeyWord;
import application.Main;
import helper.ZoomHelper;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import persistence.KeywordTable;
import helper.PDFHelper;
import helper.KeyWordHelper;

/**
 * Controller für das Fenster zur manuellen Ablage
 * 
 * @author kerstin, helge, chris, holger
 *
 */
public class ManualWindowController implements Initializable {

  public static Main        main;

  @FXML
  private AnchorPane        anchorMain;

  @FXML
  private Label             labelActualDoc;

  @FXML
  private Button            searchDoc;

  @FXML
  private Label             labelPath;

  @FXML
  private ImageView         imageActualDoc;

  @FXML
  private Label             labelKeywords;

  @FXML
  private DatePicker        datePicker;

  @FXML
  private Label             labelDate;

  @FXML
  private Label             labelKeywordOne;

  @FXML
  private Label             labelKeywordTwo;

  @FXML
  private Label             labelKeywordThree;

  @FXML
  private Label             labelKeywordFour;

  @FXML
  private Label             labelKeywordFive;

  @FXML
  private Button            save;

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
  private Button            zoomPlus;

  @FXML
  private Button            zoomMinus;

  @FXML
  private ScrollPane        imageScrollPane;

  @FXML
  private Label             manualLabelDestinationPath;

  @FXML
  private Button            changeKeywordOne;

  @FXML
  private Button            changeKeywordTwo;

  @FXML
  private Button            changeKeywordThree;

  @FXML
  private Button            changeKeywordFour;

  @FXML
  private Button            changeKeywordFive;

  final DoubleProperty      zoomProperty = new SimpleDoubleProperty(200);

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
  void setOnMouseEntered(MouseEvent event) {
    main.getPrimarayStage().getScene().setCursor(Cursor.HAND);
  }

  @FXML
  void setOnMouseExited(MouseEvent event) {
    main.getPrimarayStage().getScene().setCursor(Cursor.DEFAULT);
  }

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
    fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files",
        "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp"),
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
          fxImage = PDFHelper.convertPDFToImage(myFile);
          zoomProperty.set(200);
          imageActualDoc.setImage(ZoomHelper.zoomMouse(fxImage, imageActualDoc,
              anchorMain, imageScrollPane, zoomProperty));
        } else {
          Image myImage = new Image(myFile.toURI().toURL().toExternalForm(),
              595.0, 842.0, false, true);
          zoomProperty.set(200);
          imageActualDoc.setImage(ZoomHelper.zoomMouse(myImage, imageActualDoc,
              anchorMain, imageScrollPane, zoomProperty));
        }
      } catch (Exception e) {
        System.out.println("Fehler in handleSearchDoc");
        System.out.println(e.getMessage());

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
    if (listKeywordOne.getValue() != null) {
      listKeywordOne.setValue(KeyWordHelper.inputManualKeyword(listKeywordOne,
          listKeywordTwo, 1, 1, changeKeywordOne, save));
      deactivateGrandchild(1);
    }
  }

  void deactivateGrandchild(int level) {
    Platform.runLater(new Runnable() {

      @Override
      public void run() {
        try {
          switch (level) {
          case 1:
            if (listKeywordOne.getValue().getKeyword().length() == 0) {
              changeKeywordOne.setDisable(true);
              listKeywordTwo.setDisable(true);
              save.setDisable(true);
            }
            listKeywordThree.setValue(null);
            listKeywordThree.setDisable(true);
            listKeywordFour.setDisable(true);
            listKeywordFour.setValue(null);
            listKeywordFive.setDisable(true);
            listKeywordFive.setValue(null);
            break;

          case 2:
            if (listKeywordTwo.getValue().getKeyword().length() == 0) {
              listKeywordThree.setDisable(true);
              changeKeywordTwo.setDisable(true);
            }
            listKeywordFour.setDisable(true);
            listKeywordFour.setValue(null);
            listKeywordFive.setDisable(true);
            listKeywordFive.setValue(null);
            break;
          case 3:
            if (listKeywordThree.getValue().getKeyword().length() == 0) {
              listKeywordFour.setDisable(true);
              changeKeywordThree.setDisable(true);
            }
            listKeywordFive.setDisable(true);
            listKeywordFive.setValue(null);

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

  @FXML
  void onMouseClicked(MouseEvent event) {
    System.out.println("onMouseClicked");
  }

  @FXML
  void inputManualKeywordTwo(ActionEvent event) {
    if (listKeywordTwo.getValue() != null) {
      listKeywordTwo.setValue(
          KeyWordHelper.inputManualKeyword(listKeywordTwo, listKeywordThree, 2,
              listKeywordOne.getValue().getId(), changeKeywordTwo, save));
      deactivateGrandchild(2);
    }
  }

  @FXML
  void inputManualKeywordThree(ActionEvent event) {
    if (listKeywordThree.getValue() != null) {
      listKeywordThree.setValue(
          KeyWordHelper.inputManualKeyword(listKeywordThree, listKeywordFour, 3,
              listKeywordTwo.getValue().getId(), changeKeywordThree, save));
      deactivateGrandchild(3);
    }
  }

  @FXML
  void inputManualKeywordFour(ActionEvent event) {
    if (listKeywordFour.getValue() != null) {
      listKeywordFour.setValue(
          KeyWordHelper.inputManualKeyword(listKeywordFour, listKeywordFive, 4,
              listKeywordThree.getValue().getId(), changeKeywordFour, save));
    }
  }

  @FXML
  void inputManualKeywordFive(ActionEvent event) {
    if (listKeywordFour.getValue() != null) {
      listKeywordFive.setValue(KeyWordHelper.inputManualKeyword(listKeywordFive,
          null, 5, listKeywordFour.getValue().getId(), changeKeywordFive, save));
    }
  }

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    try {
      listKeywordOne.setItems(KeywordTable.selectLevel(1));
      manualLabelDestinationPath
          .setText(main.getMyConfig().getDestinationDir().toString());
      System.out.println(main.getMyConfig().getDestinationDir());
    } catch (Exception e) {
      // System.out.println("Fehler in ManualWindowController - initialize");
      e.getStackTrace();
    }
  }
}

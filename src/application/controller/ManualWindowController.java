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
  private JFXButton         changeKeywordOne;

  @FXML
  private JFXButton         changeKeywordTwo;

  @FXML
  private JFXButton         changeKeywordThree;

  @FXML
  private JFXButton         changeKeywordFour;

  @FXML
  private JFXButton         changeKeywordFive;

  KeyWord                   selectedKeywordOne;
  KeyWord                   selectedKeywordTwo;
  KeyWord                   selectedKeywordThree;
  KeyWord                   selectedKeywordFour;
  KeyWord                   selectedKeywordFive;
  ObservableList<KeyWord>   olLevel1     = KeywordTable.selectLevel(1);
  ObservableList<KeyWord>   olLevel2     = KeywordTable.selectLevel(2);
  ObservableList<KeyWord>   olLevel3     = KeywordTable.selectLevel(3);
  ObservableList<KeyWord>   olLevel4     = KeywordTable.selectLevel(4);
  ObservableList<KeyWord>   olLevel5     = KeywordTable.selectLevel(5);

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
          fxImage = PDFHelper.convertAwtToFx(PDFHelper.convertPdfToAwt(myFile));
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
    // Prüfung, ob was ausgewählt ist
    if (listKeywordOne.getValue() != null) {
      listKeywordTwo.setDisable(false);
      save.setDisable(false);
    } else {
      save.setDisable(true);
    }
    try {
      selectedKeywordOne = listKeywordOne.getValue();
      if (selectedKeywordOne.getKeyword().equals("Neuer Eintrag..")) {
        Platform.runLater(new Runnable() {
          @Override
          public void run() {
            if (selectedKeywordOne.getId() == 1) {
              KeyWord newKeyWord = newKeywordDialog(1, 1);
              listKeywordOne.getItems().add(newKeyWord);
              Collections.sort(listKeywordOne.getItems());
              listKeywordOne.setValue(newKeyWord);
              selectedKeywordOne = listKeywordOne.getValue();
            }
          }
        });
      } else {
        listKeywordTwo
            .setItems(KeywordTable.getChildren(selectedKeywordOne.getId()));
      }
    } catch (Exception e) {
      // da der geworfene Fehler völlig sinnlos ist,
      // fangen wir ihn und ignorieren ihn

      // System.out.println("Fehlergrund: " + e.getCause());
      // System.out.println("Fehlermeldung: " + e.getMessage());
    }
  }

  @FXML
  void inputManualKeywordOneChanged(InputMethodEvent event) {

  }

  @FXML
  void inputManualKeywordTwo(ActionEvent event) {
    if (listKeywordTwo.getValue() != null) {
      listKeywordThree.setDisable(false);
    } else {
      // listKeywordThree.setDisable(true);
      // listKeywordFour.setDisable(true);
      // listKeywordFive.setDisable(true);
    }
    try {
      selectedKeywordTwo = listKeywordTwo.getValue();
      if (selectedKeywordTwo.getKeyword().equals("Neuer Eintrag..")) {
        Platform.runLater(new Runnable() {
          @Override
          public void run() {
            if (selectedKeywordTwo.getId() == 1) {
              KeyWord newKeyWord = newKeywordDialog(2,
                  selectedKeywordOne.getId());
              listKeywordTwo.getItems().add(newKeyWord);
              Collections.sort(listKeywordTwo.getItems());
              listKeywordTwo.setValue(newKeyWord);
              selectedKeywordTwo = listKeywordTwo.getValue();
            }
          }
        });
        // Das sollte der Coder für Popup sein!!!!!!!!!
        // try {
        // FXMLLoader fxmlLoader = new
        // FXMLLoader(getClass().getResource("../fxml/NewKeywordWindow.fxml"));
        // Parent root1 = (Parent) fxmlLoader.load();
        // Stage stage = new Stage();
        // stage.setScene(new Scene(root1));
        // stage.show();
        // } catch (Exception e) {
        // System.out.println("Popup kann leider nicht geöffnet
        // werden");
        // }
      } else {
        listKeywordThree
            .setItems(KeywordTable.getChildren(selectedKeywordTwo.getId()));
      }

    } catch (Exception e) {
      // da der geworfene Fehler völlig sinnlos ist,
      // fangen wir ihn und ignorieren ihn

      // System.out.println("Fehlergrund: " + e.getCause());
      // System.out.println("Fehlermeldung: " + e.getMessage());
    }
  }

  /**
   * öffnet die Inputbox für ein neues Schlüsselwort und speichert es mit
   * seinen Beziehungen in der Datenbank
   * 
   * @author Kerstin
   * @param myLevel
   *          = das eigene Level des Schlüsselwortes
   * @param myParentID
   *          = die Id des ElternSchlüsselwortes (liefert die Combobox
   *          davor)
   */

  @FXML
  void inputManualKeywordThree(ActionEvent event) {
    // if (listKeywordThree.getValue().length() > 0) {
    // listKeywordOne.setDisable(false);
    // listKeywordTwo.setDisable(false);
    // listKeywordThree.setDisable(false);
    // listKeywordFour.setDisable(false);
    // } else {
    // listKeywordFour.setValue("");
    // listKeywordFive.setValue("");
    // listKeywordFour.setDisable(true);
    // listKeywordFive.setDisable(true);
    // }
  }

  @FXML
  void inputManualKeywordFour(ActionEvent event) {
    // if (listKeywordFour.getValue().length() > 0) {
    // listKeywordOne.setDisable(false);
    // listKeywordTwo.setDisable(false);
    // listKeywordThree.setDisable(false);
    // listKeywordFour.setDisable(false);
    // listKeywordFive.setDisable(false);
    // } else {
    // listKeywordFive.setValue("");
    // listKeywordFive.setDisable(true);
    // }
  }

  KeyWord newKeywordDialog(int myLevel, Integer myParentID) {
    KeyWord k = new KeyWord();
    try {
      TextInputDialog dialog = new TextInputDialog();

      dialog.setTitle("Neuer Eintrag");
      dialog.setHeaderText("Bitte geben Sie das neue Schlüsselwort ein");
      Optional<String> result = dialog.showAndWait();
      if (result.isPresent()) {
        String s = result.get();
        k.setId(KeywordTable.getHighestID() + 1);
        k.setKeyword(s);
        k.setPath(s);
        k.setLevel(myLevel);
        k.setParent(myParentID);
        KeywordTable.insertKeyword(k);
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return k;
  }

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {

    // olLevel1.addAll(KeywordTable.selectLevel(1));
    // listTest.setItems(listData);
    listKeywordOne.setItems(olLevel1);

  }
}

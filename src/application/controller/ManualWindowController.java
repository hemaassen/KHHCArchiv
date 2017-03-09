package application.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import application.KeyWord;
import application.Main;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
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
  private Label             labelKeywordtThree;

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

  // hs
  @FXML
  private ScrollPane        ImageScrollPane;

  KeyWord                   selectedKeywordOne;
  KeyWord                   selectedKeyWordTwo;
  ObservableList<KeyWord>   olLevel1     = KeywordTable.selectLevel(1);

  // hs - test zum zoomen
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

      // ausgewählte Datei anzeigen
      try {
        if (isPdf) {
          fxImage = PDFHelper.convertAwtToFx(PDFHelper.convertPdfToAwt(myFile));
          // hs - test zum zoomen über mausrad
          // *****************************************************************
          zoomProperty.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable arg0) {
              imageActualDoc.setFitWidth(zoomProperty.get() * 4);
              imageActualDoc.setFitHeight(zoomProperty.get() * 3);
            }
          });
          anchorMain.addEventFilter(ScrollEvent.ANY,
              new EventHandler<ScrollEvent>() {
                @Override
                public void handle(ScrollEvent event) {
                  if (event.getDeltaY() > 0) {
                    zoomProperty.set(zoomProperty.get() * 1.1);
                  } else if (event.getDeltaY() < 0) {
                    zoomProperty.set(zoomProperty.get() / 1.1);
                  }
                }
              });
          imageActualDoc.setImage(fxImage);
          imageActualDoc.preserveRatioProperty().set(true);
          ImageScrollPane.setContent(imageActualDoc);
          imageActualDoc.setImage(fxImage);
          // *****************************************************************
        } else {
          Image myImage = new Image(myFile.toURI().toURL().toExternalForm(),
              595.0, 842.0, false, true);

          // hs - test zum zoomen über mausrad
          // *****************************************************************
          zoomProperty.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable arg0) {
              imageActualDoc.setFitWidth(zoomProperty.get() * 4);
              imageActualDoc.setFitHeight(zoomProperty.get() * 3);
            }
          });
          anchorMain.addEventFilter(ScrollEvent.ANY,
              new EventHandler<ScrollEvent>() {
                @Override
                public void handle(ScrollEvent event) {
                  if (event.getDeltaY() > 0) {
                    zoomProperty.set(zoomProperty.get() * 1.1);
                  } else if (event.getDeltaY() < 0) {
                    zoomProperty.set(zoomProperty.get() / 1.1);
                  }
                }
              });

          imageActualDoc.setImage(myImage);
          imageActualDoc.preserveRatioProperty().set(true);
          ImageScrollPane.setContent(imageActualDoc);
          // imageActualDoc.setImage(myImage); -> original
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
     zoomPlus.setDisable(false); // hs
     zoomMinus.setDisable(false); // hs
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
    if (listKeywordOne.getValue() != null) {
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

        // Das sollte der Coder für Popup sein!!!!!!!!!
        // try {
        // FXMLLoader fxmlLoader = new
        // FXMLLoader(getClass().getResource("../fxml/NewKeywordWindow.fxml"));
        // Parent root1 = (Parent) fxmlLoader.load();
        // Stage stage = new Stage();
        // stage.setScene(new Scene(root1));
        // stage.show();
        // } catch (Exception e) {
        // System.out.println("Popup kann leider nicht geöffnet werden");
        // }

      } else {
        System.out.println(
            "children vom gewählten Eintrag müssen ermittelt und zugewiesen werden");
        listKeywordTwo
            .setItems(KeywordTable.getChildren(selectedKeywordOne.getId()));
      }
      System.out.println("level 1 " + selectedKeywordOne.getId() + " "
          + selectedKeywordOne.getKeyword());

    } catch (Exception e) {
      // da der geworfene Fehler völlig sinnlos ist,
      // fangen wir ihn und ignorieren ihn

      // System.out.println("Fehlergrund: " + e.getCause());
      // System.out.println("Fehlermeldung: " + e.getMessage());
      // System.out.println("Fehlerlokalisiertemeldung: " +
      // e.getLocalizedMessage());
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
    if (listKeywordTwo.getValue() != null) {
      listKeywordThree.setDisable(false);
    } else {
      // listKeywordThree.setDisable(true);
      // listKeywordFour.setDisable(true);
      // listKeywordFive.setDisable(true);
    }
    try {
      selectedKeyWordTwo = listKeywordTwo.getValue();
      if (selectedKeyWordTwo.getKeyword().equals("Neuer Eintrag..")) {
        System.out.println("jetzt muss der dialog auf");
      } else {
        System.out.println(
            "children vom gewählten Eintrag müssen ermittelt und zugewiesen werden");
        listKeywordThree
            .setItems(KeywordTable.getChildren(selectedKeyWordTwo.getId()));
      }
      System.out.println("level 2 " + selectedKeyWordTwo.getId() + " "
          + selectedKeyWordTwo.getKeyword());
    } catch (Exception e) {
      // da der geworfene Fehler völlig sinnlos ist,
      // fangen wir ihn und ignorieren ihn

      // System.out.println("Fehlergrund: " + e.getCause());
      // System.out.println("Fehlermeldung: " + e.getMessage());
      // System.out.println("Fehlerlokalisiertemeldung: " +
      // e.getLocalizedMessage());
    }
  }

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
  @FXML
  void onClickZoomIn(MouseEvent event) {
	  zoomProperty.set(zoomProperty.get() * 1.1);
  }
  @FXML
  void onClickZoomOut(MouseEvent event) {
	  zoomProperty.set(zoomProperty.get() / 1.1);
  }
  
  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    listKeywordOne.setItems(KeywordTable.selectLevel(1));

  }
}

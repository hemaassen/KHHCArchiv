package application.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JFrame;

import application.KeyWord;
import application.Main;
import helper.EditKeywordHelper;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import persistence.KeywordTable;
import helper.ListingFilesHelper;
import helper.PdfHelper;
import helper.ZoomHelper;
import helper.ShowPdf;

/**
 * Controller für das Fenster zur Dokumentensuche
 * 
 * @author kerstin, helge, chris, holger
 *
 */
public class SearchWindowController implements Initializable {

  public static Main        main;

  @FXML
  private AnchorPane        anchorSearchMain;

  @FXML
  private DatePicker        dateFrom;

  @FXML
  private DatePicker        dateTill;

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
  private Button            buttonSearch;

  @FXML
  private ListView<String>  listResult;

  @FXML
  private Button            zoomPlus;

  @FXML
  private Button            zoomMinus;

  @FXML
  private Button            printDok;

  @FXML
  private Button            send;

  @FXML
  private ScrollPane        imageScrollPane;

  @FXML
  private ImageView         choosenDoc;

  @FXML
  private Button            newStore;

  @FXML
  private Label             labelStoredPath;

  private String            searchPath      = "";
  private String[]          searchPathArray = new String[5];
  private EditKeywordHelper editKeyword5;
  private EditKeywordHelper editKeyword4;
  private EditKeywordHelper editKeyword3;
  private EditKeywordHelper editKeyword2;
  private EditKeywordHelper editKeyword1;
  private LocalDate         myDateFrom;
  private LocalDate         myDateTill;
  private List<String>      myresultList;
  final DoubleProperty      zoomProperty    = new SimpleDoubleProperty(200);
  private File              myChoosenFile;
  private static Boolean    isPdf           = false;
  private static Image      fxImage         = null;
  private Path              myPath;

  @FXML
  void setOnMouseEntered(MouseEvent event) {
    main.getPrimarayStage().getScene().setCursor(Cursor.HAND);
  }

  @FXML
  void setOnMouseExited(MouseEvent event) {
    main.getPrimarayStage().getScene().setCursor(Cursor.DEFAULT);
  }

  /**
   * Übernahme des ausgewählten Datum von in ein lokales Feld, um damit später
   * das Suchergebnis zu
   * filtern.
   * 
   * @author helge, holger
   * @param event
   *          ActionEvent
   */
  @FXML
  void inputFromDate(ActionEvent event) {
    if (dateFrom.getValue() != null) {
      myDateFrom = dateFrom.getValue();
    } else {
      myDateFrom = null;
    }
  }

  /**
   * Übernahme des ausgewählten Datum bis in ein lokales Feld, um damit später
   * das Suchergebnis zu
   * filtern.
   * 
   * @author helge, holger
   * @param event
   *          ActionEvent
   */
  @FXML
  void inputTillDate(ActionEvent event) {
    if (dateTill.getValue() != null) {
      myDateTill = dateTill.getValue();
    } else {
      myDateTill = null;
    }
  }

  @FXML
  void listSearchKeywordOne(ActionEvent event) {
    if (listSearchKeywordOne.getValue() != null
        && listSearchKeywordOne.getValue().getKeyword().length() > 0) {
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
      editKeyword5 = new EditKeywordHelper(listSearchKeywordFive,
          listSearchKeywordFour, null, 5, true);
      editKeyword4 = new EditKeywordHelper(listSearchKeywordFour,
          listSearchKeywordThree, editKeyword5, 4, true);
      editKeyword3 = new EditKeywordHelper(listSearchKeywordThree,
          listSearchKeywordTwo, editKeyword4, 3, true);
      editKeyword2 = new EditKeywordHelper(listSearchKeywordTwo,
          listSearchKeywordOne, editKeyword3, 2, true);
      editKeyword1 = new EditKeywordHelper(listSearchKeywordOne, null,
          editKeyword2, 1, true);
      listSearchKeywordOne.setItems(KeywordTable.selectLevel(1, true));
    } catch (Exception e) {
      e.getStackTrace();
    }
  }

  /**
   * Durch anklicken des Suchen-Buttons werden die im ausgewählten Pfad
   * enthaltenen Dokumente
   * angezeigt. <br>
   * Hierbei wird auf für die Anwendung gültige Dateiformate über ein
   * Glob-Pattern eingegrenzt.
   * <br>
   * 
   * @author holger
   * @param event
   *          ActionEvent *
   */
  // Suche ausführen.....
  @FXML
  void searchContent(ActionEvent event) {
    choosenDoc.setImage(null);

    // lokales Array mit den Schlüsselwörtern füllen
    // bei einer Exception wird ein Leerstring als Inhalt übernommen
    try {
      searchPathArray[0] = listSearchKeywordOne.getValue().getKeyword();
    } catch (Exception e) {
      searchPathArray[0] = "";
    }

    try {
      searchPathArray[1] = listSearchKeywordTwo.getValue().getKeyword();
    } catch (Exception e) {
      searchPathArray[1] = "";
    }

    try {
      searchPathArray[2] = listSearchKeywordThree.getValue().getKeyword();
    } catch (Exception e) {
      searchPathArray[2] = "";
    }

    try {
      searchPathArray[3] = listSearchKeywordFour.getValue().getKeyword();
    } catch (Exception e) {
      searchPathArray[3] = "";
    }

    try {
      searchPathArray[4] = listSearchKeywordFive.getValue().getKeyword();
    } catch (Exception e) {
      searchPathArray[4] = "";
    }

    // Abfrage ob Datum bis kleiner als Datum von
    if (dateFrom.getValue() != null & dateTill.getValue() != null) {
      try {
        if (dateFrom.getValue().compareTo(dateTill.getValue()) > 0) {
          Alert alert = new Alert(AlertType.ERROR);
          alert.setTitle("Achtung");
          alert.setHeaderText("Fehler bei Datumauswahl!");
          alert
              .setContentText("Datum bis darf nicht jünger als Datum von sein");
          alert.showAndWait();
        }
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }

    // Pfad für die Suche aus den ausgewählten Schlüsselwörtern zusammensetzen
    searchPath = main.getMyConfig().getDestinationDir().toString();
    for (String sa : searchPathArray) {
      if (sa.length() > 0) {
        searchPath += File.separator;
        searchPath += sa;
      }
    }
    myPath = Paths.get(searchPath);
    // Eingrenzung auf gültige Dateiformate
    String pattern = "glob:*.{pdf,png,jpg,jpeg,gif,bmp}";
    // Durchsuchen des Verzeichnisses über eine Hilfsmethode
    try {
      myresultList = ListingFilesHelper.searchContentOfDirectory(myPath,
          pattern, myDateFrom, myDateTill);
      // Anzeige der gefundenen Dokumente in der ListView der Gui
      listResult.setItems((ObservableList<String>) FXCollections
          .observableArrayList(myresultList));
      // Anzeige des kompletten Pfads in der Gui
      labelStoredPath.setText(myPath.toString() + File.separator);
      // ausgewähltes Listenelement anzeigen
      listResult.setOnMouseClicked(new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
          String choosenFileString = listResult.getSelectionModel()
              .getSelectedItem();
          choosenDoc.setVisible(true);
          zoomPlus.setDisable(false);
          zoomMinus.setDisable(false);
          printDok.setDisable(false);
          // überprüfung ob die Datei ein Pdf ist
          isPdf = choosenFileString.endsWith(".pdf");
          // ausgewähltes Listenelement als File übernehmen
          myChoosenFile = new File(myPath + File.separator + choosenFileString);
          // ausgewählte Datei anzeigen mit Zoommöglichkeit über Mausrad
          try {
            if (isPdf) {
              fxImage = PdfHelper.convertPDFToImage(myChoosenFile);
              zoomProperty.set(200);
              choosenDoc.setImage(ZoomHelper.zoomMouse(fxImage, choosenDoc,
                  anchorSearchMain, imageScrollPane, zoomProperty));
            } else {
              Image myImage = new Image(
                  myChoosenFile.toURI().toURL().toExternalForm(), 595.0, 842.0,
                  false, true);
              zoomProperty.set(200);
              choosenDoc.setImage(ZoomHelper.zoomMouse(myImage, choosenDoc,
                  anchorSearchMain, imageScrollPane, zoomProperty));
            }
          } catch (Exception e) {
            System.out.println(
                "Fehler in searchContent (Auswahl eines Dokuments zur Anzeige)");
            System.out.println(e.getMessage());
          }
        }
      });
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  @FXML
  void onClickZoomIn(MouseEvent event) throws Exception {
    ZoomHelper.zoomIn(event, zoomProperty);
  }

  @FXML
  void onClickZoomOut(MouseEvent event) throws Exception {
    ZoomHelper.zoomOut(event, zoomProperty);
  }

  @FXML
  void printThis(ActionEvent event) {
    
//    String pdfFile = myChoosenFile.toString();
//    showPDF show = new showPDF(pdfFile);
//    //créer le JFrame
//    JFrame f = new JFrame("Lecteur PDF");
//    f.setSize(1024,768);
//    f.setLocationRelativeTo(null);
//    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    f.setVisible(true);
//    f.getContentPane().add(show);
    
    try {
      Desktop.getDesktop().print(new File(myChoosenFile.toString()));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

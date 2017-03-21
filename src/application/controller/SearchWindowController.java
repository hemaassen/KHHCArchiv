package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.KeyWord;
import application.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
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

  public static Main        main;
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
  private Button            print;

  @FXML
  private Button            send;

  @FXML
  private ImageView         choosenDoc;

  @FXML
  private Button            newStore;

  private static int        myId1           = 0;
  private static int        myId2           = 0;
  private static int        myId3           = 0;
  private static int        myId4           = 0;
  private static int        myId5           = 0;
  private int[]             searchPathArray = new int[5];

  @FXML
  void setOnMouseEntered(MouseEvent event) {
    main.getPrimaryStage().getScene().setCursor(Cursor.HAND);
  }

  @FXML
  void setOnMouseExited(MouseEvent event) {
    main.getPrimaryStage().getScene().setCursor(Cursor.DEFAULT);
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
    listSearchKeywordOne.setItems(KeywordTable.selectLevel(1, true));
    if (listSearchKeywordOne.getValue() != null) {
      searchPathArray = new int[5];
      if (listSearchKeywordOne.getValue().getKeyword().length() == 0) {
        initializeChildren(1);
        buttonSearch.setDisable(true);
        searchPathArray = new int[5];
      } else {
        myId1 = listSearchKeywordOne.getValue().getId();
        listSearchKeywordTwo.setItems(KeywordTable.getChildren(myId1, true));
        listSearchKeywordThree.setItems(KeywordTable.getChildren(-1, true));
        listSearchKeywordFour.setItems(KeywordTable.getChildren(-1, true));
        listSearchKeywordFive.setItems(KeywordTable.getChildren(-1, true));
        buttonSearch.setDisable(false);
        searchPathArray[0] = myId1;
        listSearchKeywordTwo.setDisable(false);
      }
    } else {
      buttonSearch.setDisable(true);
      listSearchKeywordTwo.setDisable(true);
      listSearchKeywordThree.setDisable(true);
      listSearchKeywordFour.setDisable(true);
      listSearchKeywordFive.setDisable(true);
    }
  }

  @FXML
  void listSearchKeywordTwo(ActionEvent event) {
    if (listSearchKeywordTwo.getValue() != null) {
      initializeChildren(2);
      if (listSearchKeywordTwo.getValue().getKeyword().length() != 0) {
        myId2 = listSearchKeywordTwo.getValue().getId();
        listSearchKeywordThree.setItems(KeywordTable.getChildren(myId2, true));
        listSearchKeywordFour.setItems(KeywordTable.getChildren(-1, true));
        listSearchKeywordFive.setItems(KeywordTable.getChildren(-1, true));
        searchPathArray[1] = myId2;
        listSearchKeywordThree.setDisable(false);
      } else {
        searchPathArray[1] = -1;
        listSearchKeywordThree.setDisable(true);
        listSearchKeywordFour.setDisable(true);
        listSearchKeywordFive.setDisable(true);
      }
    }
  }

  @FXML
  void listSearchKeywordThree(ActionEvent event) {
    if (listSearchKeywordThree.getValue() != null) {
      initializeChildren(3);
      if (listSearchKeywordThree.getValue().getKeyword().length() != 0) {
        myId3 = listSearchKeywordThree.getValue().getId();
        listSearchKeywordFour.setItems(KeywordTable.getChildren(myId3, true));
        listSearchKeywordFive.setItems(KeywordTable.getChildren(-1, true));
        searchPathArray[2] = myId3;
        listSearchKeywordFour.setDisable(false);
      } else {
        searchPathArray[2] = -1;
        listSearchKeywordFour.setDisable(true);
        listSearchKeywordFive.setDisable(true);
      }
    }
  }

  @FXML
  void listSearchKeywordFour(ActionEvent event) {
    if (listSearchKeywordFour.getValue() != null) {
      if (listSearchKeywordFour.getValue().getKeyword().length() != 0) {
        initializeChildren(4);
        myId4 = listSearchKeywordFour.getValue().getId();
        listSearchKeywordFive.setItems(KeywordTable.getChildren(myId4, true));
        searchPathArray[3] = myId4;
        listSearchKeywordFive.setDisable(false);
      } else {
        searchPathArray[3] = -1;
        listSearchKeywordFive.setDisable(true);
      }
    }
  }

  @FXML
  void listSearchKeywordFive(ActionEvent event) {
    if (listSearchKeywordFive.getValue() != null) {
      if (listSearchKeywordFive.getValue().getKeyword().length() != 0) {
        myId5 = listSearchKeywordFive.getValue().getId();
        searchPathArray[4] = myId5;
      } else {
        searchPathArray[4] = -1;
      }
    }
  }

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    try {
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
   *          Übergabe der aktuellen Ebene.
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
   * der Schlüsselworte angezeigt.
   * <br>
   * Der Wert -1 bedeutet dass keine Auswahl auf dieser Ebene getroffen wurde
   * und wird somit überlesen.
   * 
   * @author holger
   * @param event
   *          *
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

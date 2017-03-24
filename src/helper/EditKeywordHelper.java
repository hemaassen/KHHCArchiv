package helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import application.KeyWord;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import persistence.KeywordTable;

/**
 * 
 * @author Kerstin
 *
 */
public class EditKeywordHelper {

  private ComboBox<KeyWord> myBox;
  private EditKeywordHelper myChild;
  private int               myLevel;
  private ComboBox<KeyWord> myParent;
  private int               parentId = 1;
  boolean                   forSearch;

  public EditKeywordHelper() {

  }

  public ComboBox<KeyWord> getMyBox() {
    return myBox;
  }

  public EditKeywordHelper(ComboBox<KeyWord> myBox, ComboBox<KeyWord> myParent,
      EditKeywordHelper myChild, int myLevel, boolean forSearch) {
    try {
      this.myBox = myBox;
      this.myChild = myChild;
      this.myLevel = myLevel;
      this.myParent = myParent;
      this.forSearch = forSearch;

      myBox.valueProperty().addListener(new ChangeListener<KeyWord>() {

        @Override
        public void changed(ObservableValue<? extends KeyWord> arg0,
            KeyWord oldValue, KeyWord newValue) {
          if (newValue != null) {
            // ist auf was anderes als null gesetzt
            if (newValue.getKeyword().length() > 0) {
              // ist auf was anderes als den leeren Eintrag
              // gesetzt
              if (newValue.getKeyword().equals("Neuer Eintrag..")) {
                // Hole den neuen Eintrag
                if (myParent != null) {
                  parentId = myParent.getValue().getId();
                }
                myBox.setValue(inputManualKeyword());
              } else {
                // einfach nur ein normaler Wert
                myBox.setValue(inputManualKeyword());
                if (myChild != null) {
                  // Das Kind könnte leer sein (beim letzten
                  // Teil)
                  // dann darf das hier nicht passieren
                  myChild.switchON();
                  myChild.refresh();
                }
                switchON();
                // myButton.setDisable(false);
              } // ende normaler Wert
            } else {
              // leerString
              myChild.switchOff();
              // myButton.setDisable(true);
            }

          } else {
            // ist null
            switchOff();
          }

        }
      });

    } catch (Exception e) {
      System.out.println("Konstruktor schlägt fehl");
    }

  }

  public void switchON() {
    myBox.setDisable(false);
    // myButton.setDisable(false);

  }

  public void switchOff() {
    myBox.setDisable(true);
    myBox.setValue(null);
    if (myChild != null) {
      myChild.switchOff();
    }
  }

  public void refresh() {
    if (myLevel == 1) {
      myBox.setItems(KeywordTable.selectLevel(myLevel, forSearch));
    } else {
      myBox.setItems(
          KeywordTable.getChildren(myParent.getValue().getId(), forSearch));
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
   * @param myBox
   *          = die ComboBox für die, die Methode gerufen wird
   */
  private KeyWord newKeywordDialog(int myLevel, Integer myParentID) {
    KeyWord k = new KeyWord();
    try {
      // Initialisieren des Dialogs
      TextInputDialog dialog = new TextInputDialog();
      dialog.setTitle("Neuer Eintrag");
      dialog.setHeaderText("Bitte geben Sie das neue Schlüsselwort ein");
      Optional<String> result = dialog.showAndWait();
      String s = null;
      if (result.isPresent()) {
        // es wurde entweder ok oder abbrechen gedrückt
        s = result.get(); // den String extrahieren
        if (s != null & s.length() > 0) {
          // Bestätigungs-Dialog öffnen....
          Alert alertConfirmNewKeyword = new Alert(AlertType.CONFIRMATION);
          alertConfirmNewKeyword.setTitle("Bitte bestätigen oder abbrechen");
          alertConfirmNewKeyword.setHeaderText("Sind diese Eingaben richtig?"
              + " (Wenn Sie mit OK bestätigen, wird das Schlüsselwort gespeichert)");
          alertConfirmNewKeyword
              .setContentText("Ihr neues Schlüsselwort: " + result.get());
          Optional<ButtonType> result2 = alertConfirmNewKeyword.showAndWait();
          if (result2.get() == ButtonType.OK) {
            // es wurde ok gedrückt und auch was eingegeben
            // alle aktuellen Einträge der Box ermitteln
            List<String> listMyChild = new ArrayList<String>();
            for (KeyWord kw : myBox.getItems()) {
              listMyChild.add(kw.getPath());
            }
            if (!listMyChild.contains(s)) {
              // neuer Eintrag ist noch nicht enthalten
              // neues Keyword wird zusammengebaut
              k.setId(KeywordTable.getHighestID() + 1);
              k.setKeyword(s);
              k.setPath(s);
              k.setLevel(myLevel);
              k.setParent(myParentID);
              // und in die Datenbank geschrieben
              KeywordTable.insertKeyword(k);
            } else {
              // diesen Eintrag gab es schon
              k = null; // Rückgabewert auf null(Fehlerfall) setzen
              // Nutzer über den Doppelten Eintrag informieren
              Alert alert = new Alert(AlertType.ERROR);
              alert.setTitle("Doppelter Eintrag");
              // alert.setHeaderText("Look, an Information Dialog");
              alert
                  .setContentText("Dieser Eintrag ist bereits vorhanden: " + s);
              alert.showAndWait();
            }
          } else {
            // es wurde kein Eintrag eingegeben, aber ok gedrückt
            k = null;// Rückgabewert auf null(Fehlerfall) setzen
          }
        } else {
          // es wurde kein Eintrag eingegeben, aber ok gedrückt
          k = null;// Rückgabewert auf null(Fehlerfall) setzen
        }
      } else {
        // es wurde abgebrochen
        k = null;// Rückgabewert auf null(Fehlerfall) setzen
      }
    } catch (Exception e) {
      System.out.println("Fehler in newKeyword");
      System.out.println(e.getMessage());
    }
    return k;
  }

  /**
   * @author Kerstin
   * @return
   */
  private KeyWord inputManualKeyword() {
    final KeyWord myWord = myBox.getValue();
    try {
      // in myBox steht der gewählte Wert
      if (myWord.getKeyword().equals("Neuer Eintrag..")) {
        if (myWord.getId() != 1) {
          myWord.setId(1);
        }
        // in der Box steht "Neuer Eintrag.."
        Platform.runLater(new Runnable() {
          // wird nur ausgeführt wenn ein Neuer Eintrag gewählt wurde
          @Override
          public void run() {
            if (myWord.getId() == 1) {
              // das neue Keyword wird geholt
              KeyWord newKeyWord = newKeywordDialog(myLevel, parentId);
              if (newKeyWord != null) {
                // dialog wurde nicht abgebrochen,nichts doppelt
                // und kein
                // leerstring
                // neues Keyword an die Liste anfügen
                myBox.getItems().add(newKeyWord);
                // Liste sortieren
                Collections.sort(myBox.getItems());
                // die ComboBox auf den neuen Wert setzen
                myBox.setValue(newKeyWord);
                // Id muss auf den neuen Wert "verbogen" werden,
                // damit es keine Doppel in der Anzeige der
                // Dialogbox gibt
                myWord.setId(myBox.getValue().getId());
              } else {
                /*
                 * wenn kein Keyword zurückgegeben wird: - bei
                 * leerstring und ok drücken - bei abbrechen -
                 * bei doppelten Eintrag
                 */
                for (KeyWord k : myBox.getItems()) {
                  // auf das "leere" Keywort setzen und
                  // abbrechen
                  if (k.getKeyword().equals("")) {
                    myBox.setValue(k);
                    break;
                  }
                } // for-each-Schleife
              } // else-Zweig Überprüfung ob Keyword zurückkam
            } // Ende if wenn Neuereintrag gewählt wurde - kein else
              // erforderlich
          }// Ende run-methode
        });// ende Run-later
      } else {
        if (myChild != null) {
          myChild.getMyBox()
              .setItems(KeywordTable.getChildren(myWord.getId(), false));
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
}

package helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import application.KeyWord;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import persistence.KeywordTable;

public class ManualWindowHelper {

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
  public static KeyWord newKeywordDialog(int myLevel, Integer myParentID,
      ComboBox<KeyWord> myBox) {
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
            alert.setContentText(s + " ist schon enthalten");
            alert.showAndWait();
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
      System.out.println(e.getMessage());
    }
    return k;
  }
  
  public static KeyWord inputManualKeyword(ComboBox<KeyWord> myBox,
      ComboBox<KeyWord> myChild, int level, Integer myParentId,
      Button myChangeButton, Button mySave) {
    final KeyWord myWord = myBox.getValue();
    if (myBox.getValue() != null) {
      // in der box ist ein Wert ausgewählt
      // die darunterliegende Combobox wird aktiv geschalten
      if (myBox.getValue().getKeyword().length() > 0
          & !myBox.getValue().getKeyword().equals("Neuer Eintrag..")) {
        myChangeButton.setDisable(false);
      }
      if (myChild != null) {
        myChild.setDisable(false);
      }
      // der Speicherknopf wird aktiv geschalten
      mySave.setDisable(false);
    } else {
      // es ist nichts ausgewäht - der Speicherknopf wird deaktiviert
      mySave.setDisable(true);

    }
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
              KeyWord newKeyWord = newKeywordDialog(level, myParentId, myBox);
              if (newKeyWord != null) {
                // dialog wurde nicht abgebrochen,nichts doppelt und kein
                // leerstring
                // neues Keyword an die Liste anfügen
                myBox.getItems().add(newKeyWord);
                // Liste sortieren
                Collections.sort(myBox.getItems());
                // die ComboBox auf den neuen Wert setzen
                myBox.setValue(newKeyWord);
                // Id muss auf den neuen Wert "verbogen" werden,
                // damit es keine Doppel in der Anzeige der Dialogbox gibt
                myWord.setId(myBox.getValue().getId());
              } else {
                /*
                 * wenn kein Keyword zurückgegeben wird:
                 * - bei leerstring und ok drücken
                 * - bei abbrechen
                 * - bei doppelten Eintrag
                 */
                for (KeyWord k : myBox.getItems()) {
                  // auf das "leere" Keywort setzen und abbrechen
                  if (k.getKeyword().equals("")) {
                    myBox.setValue(k);
                    break;
                  }
                }// for-each-Schleife
              }// else-Zweig Überprüfung ob Keyword zurückkam
            }// Ende if wenn Neuereintrag gewählt wurde - kein else erforderlich
          }// Ende run-methode
        });// ende Run-later
      } else {
        if (myChild != null) {
          myChild.setItems(KeywordTable.getChildren(myWord.getId(), false));
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

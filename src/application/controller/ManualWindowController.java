package application.controller;

import java.io.File;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
import helper.FilePusherHelper;
import helper.ManualWindowHelper;

/**
 * Controller für das Fenster zur manuellen Ablage
 * 
 * @author kerstin, helge, chris, holger
 *
 */
public class ManualWindowController implements Initializable {

    public static Main main;

    // das wird unsere Datei
    private File choosedSourceFile;

    private String pathToDestination = "";

    @FXML
    private AnchorPane anchorMain;

    @FXML
    private Label labelActualDoc;

    @FXML
    private Button searchDoc;

    @FXML
    private Label labelPath;

    @FXML
    private ImageView imageActualDoc;

    @FXML
    private Label labelKeywords;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Label labelDate;

    @FXML
    private Label labelKeywordOne;

    @FXML
    private Label labelKeywordTwo;

    @FXML
    private Label labelKeywordThree;

    @FXML
    private Label labelKeywordFour;

    @FXML
    private Label labelKeywordFive;

    @FXML
    private Button save;

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
    private Button zoomPlus;

    @FXML
    private Button zoomMinus;

    @FXML
    private ScrollPane imageScrollPane;

    @FXML
    private Label manualLabelDestinationPath;

    @FXML
    private Button changeKeywordOne;

    @FXML
    private Button changeKeywordTwo;

    @FXML
    private Button changeKeywordThree;

    @FXML
    private Button changeKeywordFour;

    @FXML
    private Button changeKeywordFive;

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

    final DoubleProperty zoomProperty = new SimpleDoubleProperty(200);

    @FXML
    void clickedOnManualKeywordOne(MouseEvent event) {
        // prüft ob schon ein Dokument gesetzt wurde
        System.out.println(event.getTarget().getClass().getCanonicalName());
        if (event.getSource().equals(listKeywordOne)) {
            isThereAnOpenDocument(event);
        }

    }

    @FXML
    void setOnMouseEntered(MouseEvent event) {
        // ändert den Cursor in eine Hand
        main.getPrimarayStage().getScene().setCursor(Cursor.HAND);
    }

    @FXML
    void setOnMouseExited(MouseEvent event) {
        main.getPrimarayStage().getScene().setCursor(Cursor.DEFAULT);
    }

    /**
     * Suche des manuell abzulegenden Dokuments <br>
     * Folgende Formate können gelesen und verarbeitet werden:
     * <ul>
     * <li>PNG,
     * <li>JPG, JPEG,
     * <li>GIF,
     * <li>BMP,
     * <li>PDF.</li>
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
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp"),
                new ExtensionFilter("PDF Files", "*.pdf"));

        Image fxImage = null;
        Boolean isPdf;
        // file wird gelesen
        choosedSourceFile = fileChooser.showOpenDialog(labelPath.getScene().getWindow());
        if (choosedSourceFile != null) {
            // Path-angaben ausgeben
            labelPath.setText(choosedSourceFile.getPath());
            labelPath.setVisible(true);

            // überprüfung ob die Datei ein Pdf ist
            isPdf = choosedSourceFile.getName().toString().endsWith(".pdf");

            // ausgewählte Datei anzeigen mit Zoommöglichkeit über Mausrad
            try {
                if (isPdf) {
                    fxImage = PDFHelper.convertPDFToImage(choosedSourceFile);
                    zoomProperty.set(200);
                    imageActualDoc.setImage(ZoomHelper.zoomMouse(fxImage, imageActualDoc,
                            anchorMain, imageScrollPane, zoomProperty));
                } else {
                    Image myImage = new Image(choosedSourceFile.toURI().toURL().toExternalForm(),
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
     * Zwei Methoden für Zoom In und Zoom Out per Mausklick auf die entsprechenden Buttons.
     * 
     * @author kerstin, helge, chris, holger
     * @param event
     *            MouseEvent
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
     * Auswahl eines Datums -ohne Datum darf kein Schlüsselwort ausgewählt oder hinzugefügt werden
     * und eine Speicherung findet nicht statt.
     * 
     * @author helge
     * @param event
     *            ActionEvent
     */
    @FXML
    void inputManualDate(ActionEvent event) {
        if (datePicker.getValue() != null) {
            listKeywordOne.setDisable(false);
            manualLabelDestinationPath.setText(main.getMyConfig().getDestinationDir());
        } else {
            listKeywordOne.setDisable(true);
            listKeywordTwo.setDisable(true);
            listKeywordThree.setDisable(true);
            listKeywordFour.setDisable(true);
            listKeywordFive.setDisable(true);
            save.setDisable(true);
        }
    }

    boolean isThereAnOpenDocument(MouseEvent event) {
        event.consume();
        if (choosedSourceFile != null && choosedSourceFile.length() > 0) {
            return true;
        } else {
            // es wurde noch kein Dokument ausgewählt
            // das gibt ERROR Terror!!!
            Alert dialog = new Alert(AlertType.ERROR);
            dialog.setTitle("Kein Dokument Ausgewählt");
            dialog.setContentText(
                    "Bitte suchen Sie zunächst ein Dokument aus das Sie ablegen wollen!");
            dialog.showAndWait();
        }
        return false;
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

    private void setPathToDestination(int level) {
        String tmp = "";
        pathToDestination = "";
        for (int i = 1; i <= level; i++) {
            switch (i) {
                case 1:
                    tmp = listKeywordOne.getValue().getPath();
                    break;
                case 2:
                    tmp = listKeywordTwo.getValue().getPath();
                    break;
                case 3:
                    tmp = listKeywordThree.getValue().getPath();
                    break;
                case 4:
                    tmp = listKeywordFour.getValue().getPath();
                    break;
                case 5:
                    System.out.println("IstNull ?" + (listKeywordFive.getValue() == null));
                    tmp = listKeywordFive.getValue().getKeyword();
                    break;
                default:
                    throw new IllegalArgumentException(
                            "setPathToDestination mit falschen Paramter aufgerufen");
            }
            // wenn tmp leer ist kein Seperator anhängen
            pathToDestination += tmp.length() > 0 ? tmp + File.separator : "";
        }
        System.out.println(pathToDestination);
    }

    @FXML
    void onMouseClicked(MouseEvent event) {
        System.out.println("onMouseClicked");
    }

    @FXML
    void inputManualKeywordOne(ActionEvent event) {
        if (listKeywordOne.getValue() != null) {
            listKeywordOne.setValue(ManualWindowHelper.inputManualKeyword(listKeywordOne,
                    listKeywordTwo, 1, 1, changeKeywordOne, save));
            setPathToDestination(1);
            deactivateGrandchild(1);
        }
    }

    @FXML
    void inputManualKeywordTwo(ActionEvent event) {
        if (listKeywordTwo.getValue() != null) {
            listKeywordTwo.setValue(
                    ManualWindowHelper.inputManualKeyword(listKeywordTwo, listKeywordThree, 2,
                            listKeywordOne.getValue().getId(), changeKeywordTwo, save));
            setPathToDestination(2);
            deactivateGrandchild(2);
        }
    }

    @FXML
    void inputManualKeywordThree(ActionEvent event) {
        if (listKeywordThree.getValue() != null) {
            listKeywordThree.setValue(
                    ManualWindowHelper.inputManualKeyword(listKeywordThree, listKeywordFour, 3,
                            listKeywordTwo.getValue().getId(), changeKeywordThree, save));
            setPathToDestination(3);
            deactivateGrandchild(3);
        }
    }

    @FXML
    void inputManualKeywordFour(ActionEvent event) {
        if (listKeywordFour.getValue() != null) {
            listKeywordFour.setValue(
                    ManualWindowHelper.inputManualKeyword(listKeywordFour, listKeywordFive, 4,
                            listKeywordThree.getValue().getId(), changeKeywordFour, save));
            setPathToDestination(4);
        }
    }

    @FXML
    void inputManualKeywordFive(ActionEvent event) {
        if (listKeywordFive.getValue() != null) {
            listKeywordFive.setValue(ManualWindowHelper.inputManualKeyword(listKeywordFive, null, 5,
                    listKeywordFour.getValue().getId(), changeKeywordFive, save));
            if (listKeywordFive.getValue().toString().length() > 0) {
                setPathToDestination(5);
            }
        }
    }

    /**
     * Löst alle Aktionen aus die zum Speichern der Datei erforderlich sind.
     * 
     * @param event
     *            Action Event vom Click
     * @author christian
     */
    @FXML
    void onClickSaveButton(ActionEvent event) {

        System.out.println(pathToDestination);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        try {
            listKeywordOne.setItems(KeywordTable.selectLevel(1));
            File myFile = new File(main.getMyConfig().getDestinationDir());
            if (!myFile.exists()) {
                Alert dialog = new Alert(AlertType.ERROR);
                dialog.setTitle("Ziellaufwerk nicht erreichbar");
                // alert.setHeaderText("Look, an Information Dialog");
                dialog.setContentText(
                        main.getMyConfig().getDestinationDir() + " kann nicht erreicht werden");
                dialog.showAndWait();
            }
            manualLabelDestinationPath.setText(main.getMyConfig().getDestinationDir().toString());
            System.out.println(main.getMyConfig().getDestinationDir());
        } catch (Exception e) {
            // System.out.println("Fehler in ManualWindowController -
            // initialize");
            e.getStackTrace();
        }
    }
}

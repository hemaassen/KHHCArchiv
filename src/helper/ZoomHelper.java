package helper;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;

/**
 * Die ZoomHelper-Klasse dient dazu Image-Dateien vergrößert oder verkleinert
 * anzuzeigen.
 * <br>
 * <br>
 * Für eine Zoomfunktion über das Mausrad dient die Methode zoomMouse().
 * <br>
 * Für eine schrittweise Zoomfunktion zum einen festen Wert wie z.B. bei
 * Verwendung eines Buttons, dienen die Methoden:
 * <ul>
 * <li>zoomIn()</li>
 * </ul>
 * und
 * <ul>
 * <li>zoomOut()</li>
 * </ul>
 * 
 * @author holger *
 */
public class ZoomHelper {

  /**
   * Vergrößerte/verkleinerte Ansicht eines Images über das Mausrad
   * 
   * @param image
   *          Image welches verarbeitet werden soll
   * @param imageView
   *          Imageview in welcher das Image angezeigt werden soll
   * @param anchorPane
   *          Pane in welcher die ImageView implementiert ist
   * @param imageScrollPane
   *          zur Pane zugehörige (integrierte) ScrollPane
   * @param zoomProperty
   *          Ausgangswert für die Zoomfunktion
   * @return Rückgabewert ist das vergrößert bzw. verkleinert dargestellte
   *         Image.
   * @throws Exception
   *           Exception
   */
  public static Image zoomMouse(Image image, ImageView imageView,
      AnchorPane anchorPane, ScrollPane imageScrollPane,
      DoubleProperty zoomProperty) throws Exception {

    zoomProperty.addListener(new InvalidationListener() {
      @Override
      public void invalidated(Observable arg0) {
        imageView.setFitWidth(zoomProperty.get() * 4);
        imageView.setFitHeight(zoomProperty.get() * 3);
      }
    });

    anchorPane.addEventFilter(ScrollEvent.ANY, new EventHandler<ScrollEvent>() {
      @Override
      public void handle(ScrollEvent event) {
        if (event.getDeltaY() > 0) {
          zoomProperty.set(zoomProperty.get() * 1.1);
        } else if (event.getDeltaY() < 0) {
          zoomProperty.set(zoomProperty.get() / 1.1);
        }
      }
    });
    imageView.setImage(image);
    imageView.preserveRatioProperty().set(true);
    imageScrollPane.setContent(imageView);
    return image;
  }

  /**
   * Vergrößerte Ansicht eines Images um einen festen Wert in Bezug zum
   * Ausgangswert (neuer Wert = alter Wert * 1,1)
   * 
   * @param event
   *          Mouseevent
   * @param zoomProperty
   *          Ausgangswert für die Zoomfunktion
   * @throws Exception
   *           Exception
   */
  public static void zoomIn(MouseEvent event, DoubleProperty zoomProperty)
      throws Exception {
    zoomProperty.set(zoomProperty.get() * 1.1);
  }

  /**
   * Verkleinerte Ansicht eines Images um einen festen Wert in Bezug zum
   * Ausgangswert (neuer Wert = alter Wert / 1,1)
   * 
   * @param event
   *          Mouseevent
   * @param zoomProperty
   *          Ausgangswert für die Zoomfunktion
   * @throws Exception
   *           Exception
   */
  public static void zoomOut(MouseEvent event, DoubleProperty zoomProperty)
      throws Exception {
    zoomProperty.set(zoomProperty.get() / 1.1);
  }

}

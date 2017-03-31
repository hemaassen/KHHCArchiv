package helper;

import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import org.icepdf.ri.util.FontPropertiesManager;
import org.icepdf.ri.util.PropertiesManager;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;

public class PdfDruckHelper extends Application {

  private static String   pdfPath;

  private SwingController swingController;

  private JComponent      viewerPanel;
  
  public static void setPdfPath(String pdfPath) {
    PdfDruckHelper.pdfPath = pdfPath;
  }

  public static String getPdfPath() {
    return pdfPath;
  }

  public static void main(String[] args) {
    pdfPath = args[0];
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    BorderPane borderPane = new BorderPane();
    Scene scene = new Scene(borderPane);

    // add viewer content pane
    createViewer(borderPane);

    borderPane.setPrefSize(768, 900);

    createResizeListeners(scene, viewerPanel);

    primaryStage.setOnCloseRequest(
        we -> SwingUtilities.invokeLater(() -> swingController.dispose()));

    primaryStage.setTitle("PDF Druck");
    primaryStage.setResizable(false);
    primaryStage.setScene(scene);
    primaryStage.sizeToScene();
    primaryStage.centerOnScreen();
    primaryStage.show();

    openDocument(pdfPath);
  }

  private void createResizeListeners(Scene scene, JComponent viewerPanel) {
    scene.widthProperty().addListener((observable, oldValue, newValue) -> {
      SwingUtilities.invokeLater(() -> {
        viewerPanel.setSize(
            new Dimension(newValue.intValue(), (int) scene.getHeight()));
        viewerPanel.setPreferredSize(
            new Dimension(newValue.intValue(), (int) scene.getHeight()));
        viewerPanel.repaint();
      });
    });

    scene.heightProperty().addListener((observable, oldValue, newValue) -> {
      SwingUtilities.invokeLater(() -> {
        viewerPanel.setSize(
            new Dimension((int) scene.getWidth(), newValue.intValue()));
        viewerPanel.setPreferredSize(
            new Dimension((int) scene.getWidth(), newValue.intValue()));
        viewerPanel.repaint();
      });
    });
  }

  private void createViewer(BorderPane borderPane) {
    try {
      SwingUtilities.invokeAndWait(() -> {
        // create the viewer ri components.
        swingController = new SwingController();
        swingController.setIsEmbeddedComponent(true);
        PropertiesManager properties = new PropertiesManager(
            System.getProperties(),
            ResourceBundle.getBundle(PropertiesManager.DEFAULT_MESSAGE_BUNDLE));

        // read/store the font cache.
        ResourceBundle messageBundle = ResourceBundle
            .getBundle(PropertiesManager.DEFAULT_MESSAGE_BUNDLE);
        new FontPropertiesManager(properties, System.getProperties(),
            messageBundle);
        properties.set(PropertiesManager.PROPERTY_DEFAULT_ZOOM_LEVEL, "1.00");
        properties.set(PropertiesManager.PROPERTY_SHOW_UTILITY_OPEN, "false");
        properties.set(PropertiesManager.PROPERTY_SHOW_UTILITY_SAVE, "false");
        properties.set(PropertiesManager.PROPERTY_SHOW_UTILITY_SEARCH, "false");
        properties.set(PropertiesManager.PROPERTY_SHOW_UTILITY_PRINT, "true");
        properties.set(PropertiesManager.PROPERTY_SHOW_UTILITY_UPANE, "false");
        properties.set(
            PropertiesManager.PROPERTY_SHOW_TOOLBAR_ANNOTATION_HIGHLIGHT,
            "false");
        properties.set(PropertiesManager.PROPERTY_SHOW_TOOLBAR_ANNOTATION_TEXT,
            "false");
        properties.set(
            PropertiesManager.PROPERTY_SHOW_TOOLBAR_ANNOTATION_SELECTION,
            "false");
        // hide the status bar
        properties.set(PropertiesManager.PROPERTY_SHOW_STATUSBAR, "false");
        // hide a few toolbars, just to show how the prefered size of the viewer
        // changes.
        properties.set(PropertiesManager.PROPERTY_SHOW_TOOLBAR_FIT, "false");
        properties.set(PropertiesManager.PROPERTY_SHOW_TOOLBAR_ROTATE, "false");
        properties.set(PropertiesManager.PROPERTY_SHOW_TOOLBAR_TOOL, "false");
        properties.set(PropertiesManager.PROPERTY_SHOW_TOOLBAR_FORMS, "false");

        swingController.getDocumentViewController().setAnnotationCallback(
            new org.icepdf.ri.common.MyAnnotationCallback(
                swingController.getDocumentViewController()));

        SwingViewBuilder factory = new SwingViewBuilder(swingController,
            properties);

        viewerPanel = factory.buildViewerPanel();
        viewerPanel.revalidate();

        SwingNode swingNode = new SwingNode();
        swingNode.setContent(viewerPanel);
        borderPane.setCenter(swingNode);

      });
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
  }

  private void openDocument(String document) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        swingController.openDocument(document);
        viewerPanel.revalidate();
      }
    });

  }

  // private void buildButton(FlowPane flowPane, AbstractButton jButton) {
  // SwingNode swingNode = new SwingNode();
  // swingNode.setContent(jButton);
  // flowPane.getChildren().add(swingNode);
  // }
  //
  // private void buildJToolBar(FlowPane flowPane, JToolBar jToolBar) {
  // SwingNode swingNode = new SwingNode();
  // swingNode.setContent(jToolBar);
  // flowPane.getChildren().add(swingNode);
  // }

}

package helper;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

/**
 * Die PDFHelper-Klasse dient dazu eine PDF-Datei intern in eine Image-Datei
 * umzuwandeln, damit diese dann im ImageView-Bereich der FXML-Datei angezeigt
 * werden kann. <br>
 * 
 * @author holger
 *
 */
public class PdfHelper {

    private static PDDocument  document    = null;
    private static PDFRenderer pdfRenderer = null;
    // private static int pageNumber = 0; // funzt noch nicht

    /*
     * Hauptmethode welche aufgerufen wird um ein PDF in ein Image umzuwnadeln
     */
    /**
     * Konvertiert ein PDF in ein JavaFX-Image
     * 
     * @param file
     *            PDF Datei welche übergeben werden muss.
     * @return Rückgabewert ist ein JavaFX-Image.
     * @throws IOException
     *             PDDocument in PDFHelper.convertPDFToImage() throws
     *             IOException
     *             file
     */
    public static javafx.scene.image.Image convertPDFToImage(File file)
            throws IOException {
        File myFile = file;
        try {
            document = PDDocument.load(myFile);
            pdfRenderer = new PDFRenderer(document);
        } catch (IOException ex) {
            throw new UncheckedIOException(
                    "PDDocument in PDFHelper.convertPDFToImage() throws IOException file="
                            + myFile,
                    ex);
        }
        // pageNumber = numPages(); // funzt noch nicht
        return getImage(0);
    }

    /*
     * zusätzliche Methode um das in convertPDFToImage geladene PDDocument zu
     * rendern
     */
    /**
     * @param pageNumber
     *            Nummer der Seite, damit diese angezeigt werden kann
     * @return Gibt ein Javafx Image zurück
     */
    static Image getImage(int pageNumber) {
        BufferedImage pageImage;
        try {
            pageImage = pdfRenderer.renderImage(pageNumber);
        } catch (IOException ex) {
            throw new UncheckedIOException(
                    "PDFRenderer in getImage() throws IOException", ex);
        }
        try {
            document.close();
        } catch (IOException e) {
            throw new UncheckedIOException(
                    "close document in getImage() throws IOException", e);
        }
        return SwingFXUtils.toFXImage(pageImage, null);
    }

    /**
     * @deprecated Ist als deprecated markiert, weil die Methode noch nicht
     *             funzt
     * @return Anzahl der Seiten des Dokuments
     */
    static int numPages() {
        return document.getPages().getCount();
    }
}
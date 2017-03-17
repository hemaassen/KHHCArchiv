package helper;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import javax.imageio.ImageIO;

import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;

/**
 * Die PDFHelper-Klasse dient dazu PDF-Dateien intern in eine Image-Datei
 * umzuwandeln, damit diese dann im ImageView-Bereich der FXML-Datei angezeigt
 * werden kann. <br>
 * <br>
 * Dies geschieht, indem man in der rufenden Klasse erst die Methode
 * <ul>
 * <li>convertPdfToAwt()</li>
 * </ul>
 * und dann anschließend die Methode
 * <ul>
 * <li>convertAwtToFx()</li>
 * </ul>
 * aufruft.
 * 
 * @author holger, kerstin, helge
 *
 */
public class PDFHelper {

	/**
	 * Konvertiert ein PDF in ein awt-Image
	 * 
	 * @param file
	 *            Eingabeparameter muss eine PDF-Datei sein.
	 * @return Rückgabewert ist ein awt-Image.
	 * @throws IOException
	 *             IOException
	 */
	public static java.awt.Image convertPdfToAwt(File file){
		// load a pdf from a byte buffer
		java.awt.Image img = null;
		try (RandomAccessFile raf = new RandomAccessFile(file, "r");) {

			FileChannel channel = raf.getChannel();
			ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
			PDFFile pdffile = new PDFFile(buf);

			// draw the first page to an image
			PDFPage page = pdffile.getPage(0);

			// get the width and height for the doc at the default zoom
			Rectangle rect = new Rectangle(0, 0, (int) page.getBBox().getWidth(), (int) page.getBBox().getHeight());

			// generate the image
			img = page.getImage(rect.width, rect.height, // width &
															// height
					rect, // clip rect
					null, // null for the ImageObserver
					true, // fill background with white
					true // block until drawing is done
			);
		}catch (Exception e){
			System.out.println("Fehler in convertPdfToAwt");
			System.out.println(e.getMessage());
		}
		return img;
	}

	/**
	 * Konvertiert ein awt-Image in ein javafx-Image.
	 * 
	 * @param awtImage
	 *            Eingabeparameter muss ein awt-Image sein.
	 * @return Rückgabewert ist ein javafx-Image.
	 * @throws IOException
	 *             IOException
	 */
	public static javafx.scene.image.Image convertAwtToFx(java.awt.Image awtImage){
		if (!(awtImage instanceof RenderedImage)) {
			BufferedImage bufferedImage = new BufferedImage(awtImage.getWidth(null), awtImage.getHeight(null),
					BufferedImage.TYPE_INT_ARGB);
			Graphics g = bufferedImage.createGraphics();
			g.drawImage(awtImage, 0, 0, null);
			g.dispose();

			awtImage = bufferedImage;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			ImageIO.write((RenderedImage) awtImage, "png", out);
			out.flush();
		} catch (Exception e) {
			System.out.println("Fehler in convertAwtToFx");
			e.printStackTrace();
		}
		
		ByteArrayInputStream sceneImage = new ByteArrayInputStream(out.toByteArray());
		return new javafx.scene.image.Image(sceneImage);
	}

}

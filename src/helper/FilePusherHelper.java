package helper;

import application.Main;
import java.io.File;

public class FilePusherHelper {

    /**
     * Diese Methode kopiert im Erfolgsfall die Quelldatei und erzeugt daraus die Zieldatei. Ist das
     * erfolgt, so wird die Quelldatei gelöscht.
     * 
     * @param srcFile
     *            File mit Pfad und Dateiname
     * @param destination
     *            String mit Pfad und Seperator ( / oder \) 
     * @param destFileName 
     *            String : Grundlage für den Dateinamen
     * @param main
     *           
     * @return boolean true bei Erfolg
     * @author Christian
     */

    public static boolean doFileMove(File srcFile, String destination, String destFileName,
            Main main) {
        
        String destinationRoot = main.getMyConfig().getDestinationDir();
        String ext = getFileExtension(srcFile);
        File destinationFile = new File(
                destinationRoot + File.separator + destination + destFileName + "." + ext);
        int i = 0;
        String plus = "";
        while (destinationFile.exists()) { // wenn datei nicht existiert passiert hier nix
            plus = makeMyPlus(i);
            destinationFile = new File(destinationRoot + File.separator + destination + destFileName
                    + plus + "." + ext);
            i++;
        }

        System.out.println("Filename: " + destFileName + "  Ext: " + ext + "\nQuelle: "
                + srcFile.toString() + "\nZiel: " + destinationFile); // remove

        return false;
    }

    /**
     * Wenn der Dateiname schon existiert erzeugen wir mit makeMyPlus maximal maxChars Zeichen die
     * an den Dateinamen gehängt werden können.
     * 
     * @param i
     *            Der Zähler aus dem der String erzeugt wird wenn der Bereich nicht
     *            überschritten wird.
     * @return String der angehängt werden kann
     * @author Christian
     */
    private static String makeMyPlus(int i) {
        int maxChars = 2; // sollte so problemlos erhöht werden können
        int maxIntValue = ((int) Math.pow(10, maxChars)) - 1;
        if (i > maxIntValue) {
            System.out.println(
                    "So nicht User, kauf die Profiversion wenn Du mehr als 99 Einträge pro Tag "
                            + " in einer Kategorie hast.");
            return null;
        } else {
            return "" + i;
        }
    }

    //
    /**
     * Gibt die Extention des Filenamen zurück. Wollen wir immer schön darauf hoffen das niemand auf
     * die Idee kommt eine anderes Trennzeichen als den Punkt zwischen Name und Extension
     * einzuführen.
     * 
     * @param file
     *            File
     * @return String z.B. pdf jpg png doc ?
     * @author Christian
     */

    private static String getFileExtension(File file) {
        String name = file.getName();
        try {
            return name.substring(name.lastIndexOf(".") + 1);
        } catch (Exception e) {
            return "";
        }
    }

}

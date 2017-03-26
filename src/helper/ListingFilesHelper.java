package helper;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;

public class ListingFilesHelper extends SimpleFileVisitor<Path> {
  static String            myTmpDateString;
  static LocalDate         myFileDate;
  static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd",
      Locale.ENGLISH);
  static String            pattern   = "{pdf,png,jpg,jpeg,gif}";

  public static List<String> searchContentOfDirectory(final Path searchPath,
      final String searchPattern, LocalDate searchdateFrom,
      LocalDate searchdateTill) throws IOException {

    final List<String> files = new ArrayList<String>();
    final PathMatcher myMatcher = FileSystems.getDefault()
        .getPathMatcher(searchPattern);
    // Begrenzung der Suchtiefe auf das aktuelle Verzeichnis
    Files.walkFileTree(searchPath, EnumSet.noneOf(FileVisitOption.class), 1,
        new SimpleFileVisitor<Path>() {

          @Override
          public FileVisitResult visitFile(Path file,
              BasicFileAttributes attributes) throws IOException {
            // nur das aktuelle Verzeichnis durchsuchen
            if (!attributes.isDirectory()) {
              // nur gültige Dateiformate
              if (myMatcher.matches(file.getFileName())) {
                // Datum aus Dateiname extrahieren
                myTmpDateString = (file.getFileName().toString());
                if (myTmpDateString.endsWith(".jpeg")) {
                  myTmpDateString = myTmpDateString
                      .substring(myTmpDateString.length() - 15);
                  System.out.println(myTmpDateString);
                } else {
                  myTmpDateString = myTmpDateString
                      .substring(myTmpDateString.length() - 14);
                  System.out.println(myTmpDateString);
                }
                myTmpDateString = myTmpDateString.substring(0, 10);
                System.out.println("myTmpDateString: " + myTmpDateString);

                myFileDate = LocalDate.parse(myTmpDateString, formatter);
                System.out.println("myTmpDate: " + myFileDate);
                // Wenn die Methode zum Datumvergleich true zurückgibt, wird die
                // Datei zur Liste hinzugefügt
                if (DateCompareHelper.compareDate(searchdateFrom,
                    searchdateTill, myFileDate)) {
                  // Übernahme des Dateinamens in die Liste
                  files.add(file.getFileName().toString());
                }
              }
            }
            return FileVisitResult.CONTINUE;
          }
        });
    return files;
  }
}
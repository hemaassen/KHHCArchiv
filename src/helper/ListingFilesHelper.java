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
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class ListingFilesHelper extends SimpleFileVisitor<Path> {

  public static List<String> searchContentOfDirectory(final Path searchPath,
      final String searchPattern) throws IOException {

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
                files.add(file.getFileName().toString());
              }
            }
            return FileVisitResult.CONTINUE;
          }
        });
    return files;
  }
}
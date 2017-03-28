package helper;

import java.time.LocalDate;

public class DateCompareHelper {
  private static Boolean isValid = false;

  public static Boolean compareDate(LocalDate searchdateFrom,
      LocalDate searchdateTill, LocalDate myFileDate) {
    isValid = false;
    if (searchdateFrom != null) {
      if (searchdateTill != null) {
        // Datum von und Datum bis sind gesetzt
        if ((searchdateFrom.compareTo(myFileDate) < 0
            | searchdateFrom.compareTo(myFileDate) == 0)
            & (searchdateTill.compareTo(myFileDate) > 0
                | searchdateTill.compareTo(myFileDate) == 0)) {
          isValid = true;
        }
      } else {
        // nur Datum von ist gesetzt
        if (searchdateFrom.compareTo(myFileDate) < 0
            | searchdateFrom.compareTo(myFileDate) == 0) {
          isValid = true;
        }
      }
    } else {
      // Datum von ist nicht gesetzt, aber Datum bis
      if (searchdateTill != null) {
        if (searchdateTill.compareTo(myFileDate) > 0
            | searchdateTill.compareTo(myFileDate) == 0) {
          isValid = true;
        }
      } else {
        // weder Datum von noch Datum bis sind gesetzt
        isValid = true;
      }
    }
    return isValid;
  }

}

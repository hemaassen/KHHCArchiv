package helper;

import java.awt.BorderLayout;
import java.io.FileInputStream;
import javax.swing.JPanel;
import com.adobe.acrobat.Viewer;

@SuppressWarnings("serial")
public class ShowPdf extends JPanel{
  private Viewer viewer;
  
  public ShowPdf(String nomfichier) throws Exception{
      this.setLayout(new BorderLayout());

      //créer le viewer qui va servir à afficher le contenu du pdf
      viewer = new Viewer();
      this.add(viewer, BorderLayout.CENTER);
      FileInputStream fis = new FileInputStream(nomfichier);
      viewer.setDocumentInputStream(fis);
      viewer.activate();
  }
}

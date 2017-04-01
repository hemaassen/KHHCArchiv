package helper;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class PaneHelper {

    public PaneHelper() {
        // TODO Auto-generated constructor stub
    }

    public void enableNodes(Pane pane) {

    }

    public static boolean initializeLabel(Node n, String name, String value) {
        boolean result = false;
        if (n.getId().equals(name)) {
            Label lb = (Label) n;
            lb.setText(value);
            n = lb;
            result = true;
        }
        return result;

    }

    public static List<Node> activeNodes(Pane pane) {
        List<Node> result = new ArrayList<>();
        for (Node n : pane.getChildren()) {
            if (n.getId() != null) {
                result.add(n);
            }
        }

        return result;
    }

}

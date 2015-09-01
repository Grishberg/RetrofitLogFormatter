package sample;

import com.grishberg.Parser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import javax.swing.text.html.parser.Entity;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.net.URL;
import java.util.*;
import java.util.List;

public class Controller implements Initializable {
    @FXML
    private TextArea taInput;
    @FXML
    private TreeView tvOutput;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void onFormatClicked(ActionEvent event) {
        String input = taInput.getText();
        Map<String, Object> map = Parser.format(input);
        String output = Parser.prettyPrint(map);

        TreeItem<String> rootNode =
                new TreeItem<String>("response");

        rootNode.setExpanded(true);
        List<TreeItem<String>> subnodes = walk(map);
        for (TreeItem<String> subnode : subnodes) {
            rootNode.getChildren().add(subnode);
        }
        tvOutput.setRoot(rootNode);
        taInput.setText(output);
    }

    private List<TreeItem<String>> walk(Map<String, Object> map) {
        List<TreeItem<String>> nodes = new ArrayList<>();
        for (Map.Entry<String, Object> set : map.entrySet()) {
            TreeItem<String> node = null;
            if (set.getValue() instanceof Map) {
                node = new TreeItem<>();
                node.setExpanded(true);
                node.setValue(set.getKey());
                List<TreeItem<String>> subnodes = walk((Map) set.getValue());
                for (TreeItem<String> subnode : subnodes) {
                    node.getChildren().add(subnode);
                }
            } else {
                node = getNode(set.getKey(), set.getValue());
            }
            if (node != null) {
                nodes.add(node);
            }
        }
        return nodes;
    }

    private TreeItem<String> getNode(String key, Object value) {
        TreeItem<String> node = new TreeItem<>();
        node.setExpanded(true);
        if (value instanceof String) {
            node.setValue(String.format("\"%s\" : \"%s\"", key, value));
        } else if (value instanceof Long) {
            node.setValue(String.format("\"%s\" : %d", key, value));
        } else if (value instanceof Float) {
            node.setValue(String.format("\"%s\" : %f", key, value));
        } else if (value instanceof Double) {
            node.setValue(String.format("\"%s\" : %f", key, value));
        } else if (value instanceof List) {
            node.setValue(String.format("\"%s\" : [ ", key));
            for (Object val : (List) value) {
                TreeItem<String> subnode = getNode(val);
                node.getChildren().add(subnode);
            }
            TreeItem<String> n = new TreeItem<>();
            n.setValue("]");
            node.getChildren().add(n);
        }
        return node;
    }

    private TreeItem<String> getNode(Object value) {
        TreeItem<String> node = new TreeItem<>();
        node.setExpanded(true);
        if (value instanceof String) {
            node.setValue(String.format("\"%s\"", value));
        } else if (value instanceof Integer) {
            node.setValue(String.format("%d", value));
        } else if (value instanceof Float) {
            node.setValue(String.format("%f", value));
        }else if (value instanceof Double) {
            node.setValue(String.format("%f", value));
        } else if (value instanceof List) {
            node.setValue(String.format(" [ "));
            for (Object val : (List) value) {
                TreeItem<String> subnode = getNode(val);
                node.getChildren().add(subnode);
            }
        }
        return node;
    }
}
